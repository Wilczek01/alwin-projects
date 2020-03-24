package com.codersteam.alwin.core.service.impl.debt;

import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.balance.BalanceDto;
import com.codersteam.alwin.core.api.model.balance.BalanceStartAndAdditionalDto;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerRPBService;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService;
import com.codersteam.alwin.core.util.DateUtils;
import com.codersteam.alwin.core.util.InvoiceUtils;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.IssueInvoiceDao;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class BalanceCalculatorServiceImpl implements BalanceCalculatorService {

    private final DateProvider dateProvider;
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final IssueDao issueDao;
    private final IssueInvoiceDao issueInvoiceDao;
    private final CustomerRPBService customerRPBService;

    @Inject
    public BalanceCalculatorServiceImpl(final DateProvider dateProvider, final AidaService aidaService, final CustomerService customerService,
                                        final IssueDao issueDao, final IssueInvoiceDao issueInvoiceDao, final CustomerRPBService customerRPBService) {
        this.dateProvider = dateProvider;
        this.invoiceService = aidaService.getInvoiceService();
        this.customerService = customerService;
        this.issueDao = issueDao;
        this.issueInvoiceDao = issueInvoiceDao;
        this.customerRPBService = customerRPBService;
    }

    @Override
    public Map<Currency, BalanceDto> currentBalance(final Long extCompanyId) {
        final Date currentDate = dateProvider.getCurrentDateStartOfDay();
        final Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);

        final Long companyActiveIssueId = issueDao.findCompanyActiveIssueId(extCompanyId).orElse(null);
        final Set<String> invoicesOutOfService = getOutOfServiceInvoiceNumbers(companyActiveIssueId);

        final Map<Currency, BalanceDto> balances = new HashMap<>();
        balances.put(PLN, new BalanceDto(findBalanceStart(PLN, extCompanyId, currentDate, contractsOutOfService, invoicesOutOfService)));
        balances.put(EUR, new BalanceDto(findBalanceStart(EUR, extCompanyId, currentDate, contractsOutOfService, invoicesOutOfService)));

        return balances;
    }

    @Override
    @Transactional
    public Map<Currency, Balance> recalculateBalanceForIssue(final Long issueId) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final List<IssueInvoice> issueInvoices = issue.getIssueInvoices();

        final Long extCompanyId = getExtCompanyId(issue);

        final Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);
        final Set<String> invoicesOutOfService = getOutOfServiceInvoiceNumbers(issueId);

        final List<IssueInvoice> issueInvoicesInService = getIssueInvoicesInService(contractsOutOfService, invoicesOutOfService, issueInvoices);

        final Map<Currency, Balance> currencyBalances = new HashMap<>();
        currencyBalances.put(PLN, calculateBalanceFromIssueInvoiceSubjectInvoices(PLN, issueInvoicesInService, extCompanyId));
        currencyBalances.put(EUR, calculateBalanceFromIssueInvoiceSubjectInvoices(EUR, issueInvoicesInService, extCompanyId));

        return currencyBalances;
    }

    @Override
    public Map<Currency, BalanceStartAndAdditionalDto> calculateBalanceStartAndAdditional(final Long issueId) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final List<IssueInvoice> issueInvoices = issue.getIssueInvoices();

        final Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(getExtCompanyId(issue));
        final Set<String> invoicesOutOfService = getOutOfServiceInvoiceNumbers(issueId);

        final List<IssueInvoice> issueInvoicesInService = getIssueInvoicesInService(contractsOutOfService, invoicesOutOfService, issueInvoices);

        final Date startDate = dateProvider.getCurrentDateStartOfDay();
        final List<Invoice> invoicesBeforeIssueStart = getIssueStartInvoices(issueInvoicesInService, startDate);
        final List<Invoice> invoicesDuringIssue = getAdditionalInvoices(issueInvoicesInService, startDate);

        final Map<Currency, BalanceStartAndAdditionalDto> startAndAdditionalBalances = new HashMap<>();
        startAndAdditionalBalances.put(PLN, calculateBalanceStartAndAdditionalForCurrency(invoicesBeforeIssueStart, invoicesDuringIssue, PLN));
        startAndAdditionalBalances.put(EUR, calculateBalanceStartAndAdditionalForCurrency(invoicesBeforeIssueStart, invoicesDuringIssue, EUR));

        return startAndAdditionalBalances;
    }

    private BalanceStartAndAdditionalDto calculateBalanceStartAndAdditionalForCurrency(final List<Invoice> invoicesBeforeIssueStart,
                                                                                       final List<Invoice> invoicesDuringIssue, final Currency currency) {
        final BigDecimal balanceStart = invoicesBeforeIssueStart.stream()
                .filter(invoice -> isInvoiceInCurrency(currency, invoice))
                .map(Invoice::getCurrentBalance)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);

        final BigDecimal balanceAdditional = invoicesDuringIssue.stream()
                .filter(invoice -> isInvoiceInCurrency(currency, invoice))
                .map(Invoice::getCurrentBalance)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);

        return new BalanceStartAndAdditionalDto(balanceStart, balanceAdditional);
    }

    private List<Invoice> getAdditionalInvoices(final List<IssueInvoice> issueInvoices, final Date issueStartDate) {
        return issueInvoices.stream()
                .map(IssueInvoice::getInvoice)
                .filter(invoice -> isAdditionalInvoice(invoice.getRealDueDate(), issueStartDate))
                .collect(toList());
    }

    private boolean isAdditionalInvoice(final Date dueDate, final Date issueStartDate) {
        final LocalDate dueLocalDate = DateUtils.dateToLocalDate(dueDate);
        final LocalDate issueStartLocalDate = DateUtils.dateToLocalDate(issueStartDate);
        return issueStartLocalDate.compareTo(dueLocalDate) <= 0;
    }

    private List<Invoice> getIssueStartInvoices(final List<IssueInvoice> issueInvoices, final Date issueStartDate) {
        return issueInvoices.stream()
                .map(IssueInvoice::getInvoice)
                .filter(invoice -> invoice.getRealDueDate().before(issueStartDate))
                .collect(toList());
    }

    private Balance calculateBalanceFromIssueInvoiceSubjectInvoices(final Currency currency, final List<IssueInvoice> issueInvoices, final Long extCompanyId) {
        final List<IssueInvoice> subjectIssueInvoices = issueInvoices
                .stream()
                .filter(issueInvoice -> isInvoiceInCurrency(currency, issueInvoice.getInvoice()))
                .filter(IssueInvoice::getIssueSubject)
                .collect(toList());

        final BigDecimal balanceStart = subjectIssueInvoices
                .stream()
                .map(IssueInvoice::getInclusionBalance)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);

        final BigDecimal currentBalance = subjectIssueInvoices
                .stream()
                .map(IssueInvoice::getFinalBalance)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);

        final BigDecimal payments = currentBalance.subtract(balanceStart);

        final BigDecimal balanceStartPln;
        final BigDecimal currentBalancePln;
        final BigDecimal paymentsPln;
        final BigDecimal rpb;

        switch (currency) {
            case PLN:
                balanceStartPln = balanceStart;
                currentBalancePln = currentBalance;
                paymentsPln = payments;
                rpb = customerRPBService.calculateCompanyRPB(extCompanyId);
                break;
            case EUR:
                balanceStartPln = exchangeToPlnAndSum(subjectIssueInvoices, invoice -> invoice.getInvoice().getExchangeRate(), IssueInvoice::getInclusionBalance);
                currentBalancePln = exchangeToPlnAndSum(subjectIssueInvoices, invoice -> invoice.getInvoice().getExchangeRate(), IssueInvoice::getFinalBalance);
                paymentsPln = subtractIfNotNull(currentBalancePln, balanceStartPln);
                rpb = ZERO;
                break;
            default:
                balanceStartPln = null;
                currentBalancePln = null;
                paymentsPln = null;
                rpb = ZERO;
                break;
        }

        return new Balance(rpb, balanceStart, currentBalance, payments, balanceStartPln, currentBalancePln, paymentsPln);
    }

    /**
     * Sumuje przeliczone według kursu wymiany wartości z faktur. Wartość do wymiany według kursu pobierana jest przy pomocy podanej funkcji.
     *
     * @param issueInvoices            - lista powiązań faktur ze zleceniami
     * @param exchangeRateExtractor    - funkcja pobierająca wartość kursu wymiany z faktury w liście
     * @param valueToExchangeExtractor - funkcja pobierająca żądaną wartość do wymiany z faktury w liście
     * @return suma wartości pomnożona przez kurs wymiany z faktury, lub null jeśli którakolwiek wartość do wymiany lub kurs wymiany nie występuje
     */
    private <T> BigDecimal exchangeToPlnAndSum(final List<T> issueInvoices,
                                               final Function<T, BigDecimal> exchangeRateExtractor,
                                               final Function<T, BigDecimal> valueToExchangeExtractor) {
        if (allHasNonNullValues(issueInvoices, exchangeRateExtractor) && allHasNonNullValues(issueInvoices, valueToExchangeExtractor)) {
            return issueInvoices.stream()
                    .map(invoice -> exchangeRateExtractor.apply(invoice).multiply(valueToExchangeExtractor.apply(invoice)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return null;
    }

    private <T> boolean allHasNonNullValues(final List<T> collection, final Function<T, BigDecimal> valueExtractor) {
        return collection.stream().map(valueExtractor).allMatch(Objects::nonNull);
    }

    private BigDecimal subtractIfNotNull(final BigDecimal currentBalance, final BigDecimal balanceStart) {
        if (currentBalance != null && balanceStart != null) {
            return currentBalance.subtract(balanceStart);
        }
        return null;
    }

    @Override
    public Map<Currency, Balance> calculateBalancesFromIssueSubjectInvoices(final Long issueId, final Long extCompanyId,
                                                                            final List<AidaInvoiceWithCorrectionsDto> issueStartInvoices,
                                                                            final Date issueStartDate) {

        final Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);
        final Set<String> invoicesOutOfService = getOutOfServiceInvoiceNumbers(issueId);

        final List<AidaInvoiceDto> startInvoicesInService = getInvoicesInService(contractsOutOfService, invoicesOutOfService, issueStartInvoices);

        final Map<Currency, Balance> currencyBalances = new HashMap<>();
        currencyBalances.put(PLN, calculateBalanceFromIssueSubjectInvoices(PLN, startInvoicesInService, issueStartDate, extCompanyId));
        currencyBalances.put(EUR, calculateBalanceFromIssueSubjectInvoices(EUR, startInvoicesInService, issueStartDate, extCompanyId));

        return currencyBalances;
    }

    private Balance calculateBalanceFromIssueSubjectInvoices(final Currency currency, final List<AidaInvoiceDto> startInvoices, final Date issueStartDate,
                                                             final Long extCompanyId) {
        final List<AidaInvoiceDto> startInvoicesInCurrency = startInvoices
                .stream()
                .filter(invoice -> isInvoiceInCurrency(currency, invoice))
                .filter(invoice -> InvoiceUtils.isIssueSubjectForNewIssue(invoice.getBalanceOnDocument(), issueStartDate, invoice.getRealDueDate()))
                .collect(toList());

        final BigDecimal balanceStart = startInvoicesInCurrency
                .stream()
                .map(AidaInvoiceDto::getBalanceOnDocument)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);

        final BigDecimal rpb;
        final BigDecimal balanceStartInPln;
        switch (currency) {
            case PLN:
                balanceStartInPln = balanceStart;
                rpb = customerRPBService.calculateCompanyRPB(extCompanyId);
                break;
            case EUR:
                balanceStartInPln = exchangeToPlnAndSum(startInvoicesInCurrency, AidaInvoiceDto::getExchangeRate, AidaInvoiceDto::getBalanceOnDocument);
                rpb = ZERO;
                break;
            default:
                balanceStartInPln = null;
                rpb = ZERO;
                break;
        }

        return new Balance(rpb, balanceStart, balanceStart, ZERO, balanceStartInPln, balanceStartInPln, ZERO);
    }

    private boolean isInvoiceInCurrency(final Currency currency, final AidaInvoiceDto invoice) {
        return currency.getStringValue().equalsIgnoreCase(invoice.getCurrency());
    }

    private boolean isInvoiceInCurrency(final Currency currency, final Invoice invoice) {
        return currency.getStringValue().equalsIgnoreCase(invoice.getCurrency());
    }

    private Set<String> getOutOfServiceInvoiceNumbers(final Long issueId) {
        return issueInvoiceDao
                .findInvoicesOutOfService(issueId)
                .stream()
                .map(Invoice::getNumber)
                .collect(toSet());
    }

    private List<AidaInvoiceDto> getInvoicesInService(final Set<String> contractsOutOfService, final Set<String> invoicesOutOfService,
                                                      final List<AidaInvoiceWithCorrectionsDto> invoices) {
        return invoices.stream()
                .filter(i -> !contractsOutOfService.contains(i.getContractNo()))
                .filter(i -> !invoicesOutOfService.contains(i.getNumber()))
                .collect(toList());
    }

    private List<IssueInvoice> getIssueInvoicesInService(final Set<String> contractsOutOfService, final Set<String> invoicesOutOfService,
                                                         final List<IssueInvoice> issueInvoices) {
        return issueInvoices.stream()
                .filter(i -> !contractsOutOfService.contains(i.getInvoice().getContractNumber()))
                .filter(i -> !invoicesOutOfService.contains(i.getInvoice().getNumber()))
                .collect(toList());
    }

    private BigDecimal findBalanceStart(final Currency currency, final Long extCompanyId, final Date issueStartDate, final Set<String> contractsOutOfService,
                                        final Set<String> invoicesOutOfService) {
        final List<AidaInvoiceWithCorrectionsDto> invoices = invoiceService.getBalanceStartInvoicesByCompanyId(extCompanyId, issueStartDate, true);
        return calculateNotExcludedInvoicesBalanceSum(currency, invoices, contractsOutOfService, invoicesOutOfService);
    }

    private BigDecimal calculateNotExcludedInvoicesBalanceSum(final Currency currency, final List<AidaInvoiceWithCorrectionsDto> invoices,
                                                              final Set<String> contractsOutOfService, final Set<String> invoicesOutOfService) {
        return invoices.stream()
                .filter(i -> !contractsOutOfService.contains(i.getContractNo()))
                .filter(i -> !invoicesOutOfService.contains(i.getNumber()))
                .filter(invoice -> isInvoiceInCurrency(currency, invoice))
                .map(AidaInvoiceDto::getBalanceOnDocument)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);
    }
}
