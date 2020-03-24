package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.aida.core.api.service.CurrencyExchangeRateService;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.exception.IssueBalanceUpdateWaitException;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService;
import com.codersteam.alwin.core.api.service.issue.InvoiceBalanceUpdaterService;
import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService;
import com.codersteam.alwin.core.service.impl.issue.preparator.BalancePreparator;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.type.IssueState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.CLOSED_ISSUE_STATES;
import static com.codersteam.alwin.core.service.AlwinParameters.CURRENT_BALANCE_VALUE_TO_CLOSE_ISSUE;
import static com.codersteam.alwin.core.util.DateUtils.diffInMinutes;
import static java.lang.String.format;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("CdiInjectionPointsInspection")
@Stateless
public class IssueBalanceUpdaterServiceImpl implements IssueBalanceUpdaterService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final long ISSUE_BALANCE_RECALCULATE_THRESHOLD_MINUTES = 15;

    private static final int MAX_WAIT_FOR_UPDATE_ISSUE_BALANCE_REPEAT = 120;

    protected static final ConcurrentHashMap.KeySetView<Long, Boolean> CURRENTLY_UPDATING_ISSUE_IDS = ConcurrentHashMap.newKeySet();

    private static final ReentrantLock THREAD_LOCK = new ReentrantLock();

    private IssueDao issueDao;
    private DateProvider dateProvider;
    private ActivityService activityService;
    private InvoiceBalanceUpdaterService invoiceBalanceUpdaterService;
    private CurrencyExchangeRateService currencyExchangeRateService;
    private BalanceCalculatorService balanceCalculatorService;
    private AlwinMapper mapper;

    @SuppressWarnings("unused")
    public IssueBalanceUpdaterServiceImpl() {
        // For deployment
    }

    @SuppressWarnings("unused")
    @Inject
    public IssueBalanceUpdaterServiceImpl(final IssueDao issueDao, final ActivityService activityService, final DateProvider dateProvider,
                                          final InvoiceBalanceUpdaterService invoiceBalanceUpdaterService, final AidaService aidaService,
                                          final BalanceCalculatorService balanceCalculatorService, final AlwinMapper alwinMapper) {
        this.issueDao = issueDao;
        this.dateProvider = dateProvider;
        this.activityService = activityService;
        this.invoiceBalanceUpdaterService = invoiceBalanceUpdaterService;
        this.currencyExchangeRateService = aidaService.getCurrencyExchangeRateService();
        this.balanceCalculatorService = balanceCalculatorService;
        this.mapper = alwinMapper;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void updateIssueBalance(final Long issueId) {
        updateIssueBalance(issueId, findExchangeRate());
    }

    @Override
    @Transactional
    public IssueDto getIssueWithUpdatedBalance(final Long issueId) {
        final IssueDto issueDto = getIssueDto(issueId);
        if (isIssueInClosedState(issueDto) || isIssueBalanceRecalculatedWithinLast15Minutes(issueDto)) {
            return issueDto;
        }

        if (isIssueAlreadyUpdating(issueId)) {
            waitForBalanceUpdateToFinish(issueId);
            return getIssueDto(issueId);
        }

        try {
            return mapper.map(updateIssueBalance(issueId, findExchangeRate()), IssueDto.class);
        } finally {
            CURRENTLY_UPDATING_ISSUE_IDS.remove(issueId);
        }
    }

    private boolean isIssueInClosedState(final IssueDto issueDto) {
        final IssueStateDto issueState = issueDto.getIssueState();
        return CLOSED_ISSUE_STATES.contains(issueState);
    }

    private IssueDto getIssueDto(final Long issueId) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        return mapper.map(issue, IssueDto.class);
    }

    private boolean isIssueAlreadyUpdating(final Long issueId) {
        THREAD_LOCK.lock();
        try {
            if (CURRENTLY_UPDATING_ISSUE_IDS.contains(issueId)) {
                return true;
            } else {
                CURRENTLY_UPDATING_ISSUE_IDS.add(issueId);
                return false;
            }
        } finally {
            THREAD_LOCK.unlock();
        }
    }

    private void waitForBalanceUpdateToFinish(final Long issueId) {
        LOG.info("Issue {} balance is already recalculating, waiting for finish...", issueId);
        int waitRepeatCount = 0;
        while (CURRENTLY_UPDATING_ISSUE_IDS.contains(issueId)) {
            try {
                // czekamy sekundę
                Thread.sleep(1000L);
            } catch (final InterruptedException e) {
                LOG.error("Waiting for issue {} balance updated interrupted", issueId, e);
                Thread.currentThread().interrupt();
                throw new IssueBalanceUpdateWaitException(format("Update issue %d balance interrupted", issueId), e);
            }
            waitRepeatCount++;
            if (waitRepeatCount > MAX_WAIT_FOR_UPDATE_ISSUE_BALANCE_REPEAT) {
                LOG.info("Waiting for issue {} balance update took too long, interrupting", issueId);
                throw new IssueBalanceUpdateWaitException(format("Maximum waiting time for issue %d balance update reached!", issueId));
            }
        }
    }

    /**
     * Pobieranie kursu wymiany euro dla aktualnej daty
     *
     * @return kurs wymiany euro lub zero jeśli nie zostanie znaleziony
     */
    private BigDecimal findExchangeRate() {
        final Date currentDate = dateProvider.getCurrentDate();
        return currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate);
    }

    private Issue updateIssueBalance(final Long issueId, final BigDecimal exchangeRate) {
        invoiceBalanceUpdaterService.updateIssueInvoicesBalance(issueId);
        return updateIssueBalanceAndCloseIssueIfBalanceSufficient(issueId, exchangeRate);
    }

    private boolean isIssueBalanceRecalculatedWithinLast15Minutes(final IssueDto issueDto) {
        final Date balanceUpdateDate = issueDto.getBalanceUpdateDate();
        if (balanceUpdateDate == null) {
            return false;
        }
        return diffInMinutes(balanceUpdateDate, dateProvider.getCurrentDate()) <= ISSUE_BALANCE_RECALCULATE_THRESHOLD_MINUTES;
    }

    /**
     * Zaktualizowanie zlecenia w zakresie płatności/należności.
     * W przypadku, gdy zaległości nie przekraczają założonego progu zlecenie oraz czynności są anulowane (tylko zlecenia utworzone automatycznie).
     *
     * @param issueId      - identyfikator zlecenia
     * @param exchangeRate - kurs wymiany waluty euro
     * @return zaktualizowane zlecenie
     */
    private Issue updateIssueBalanceAndCloseIssueIfBalanceSufficient(final Long issueId, final BigDecimal exchangeRate) {
        final Map<Currency, Balance> balances = balanceCalculatorService.recalculateBalanceForIssue(issueId);

        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));

        final Issue issueWithBalance = BalancePreparator.fillBalance(issue, balances);

        // anulowanie zlecenia i czynności windykacyjnych jeśli zadłużenie nie przekracza limitu (tylko dla zleceń założonych automatycznie)
        if (!issueWithBalance.isCreatedManually() && isBalanceEnoughToCloseIssue(balances, exchangeRate)) {
            closeIssueAndActivities(issueWithBalance);
        }

        issueWithBalance.setBalanceUpdateDate(dateProvider.getCurrentDate());
        issueDao.update(issueWithBalance);
        return issueWithBalance;
    }

    private void closeIssueAndActivities(final Issue issue) {
        issue.setIssueState(IssueState.DONE);
        activityService.closeIssueActivities(issue.getId());
    }

    private boolean isBalanceEnoughToCloseIssue(final Map<Currency, Balance> balances, final BigDecimal exchangeRate) {
        final BigDecimal balance = balances.get(PLN).getCurrentBalance().add(balances.get(EUR).getCurrentBalance().multiply(exchangeRate));
        return CURRENT_BALANCE_VALUE_TO_CLOSE_ISSUE.compareTo(balance) < 0;
    }
}
