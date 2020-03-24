package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.aida.core.api.service.CompanyService;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.activity.DeclarationService;
import com.codersteam.alwin.core.comparator.DeclarationComparator;
import com.codersteam.alwin.core.util.DateUtils;
import com.codersteam.alwin.core.util.IssueUtils;
import com.codersteam.alwin.db.dao.ActivityDao;
import com.codersteam.alwin.db.dao.DeclarationDao;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.activity.Declaration;
import com.codersteam.alwin.jpa.issue.Issue;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static java.util.stream.Collectors.toList;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class DeclarationServiceImpl implements DeclarationService {


    private final IssueDao issueDao;
    private final ActivityDao activityDao;
    private final DeclarationDao declarationDao;
    private final CompanyService companyService;

    @Inject
    public DeclarationServiceImpl(final IssueDao issueDao, final ActivityDao activityDao, final DeclarationDao declarationDao, final AidaService aidaService) {
        this.issueDao = issueDao;
        this.activityDao = activityDao;
        this.declarationDao = declarationDao;
        this.companyService = aidaService.getCompanyService();
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void updateIssueDeclarations(final Long issueId) {
        final List<Declaration> declarations = activityDao.findIssueDeclarations(issueId);

        if (isEmpty(declarations)) {
            return;
        }

        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final Long companyId = IssueUtils.getExtCompanyId(issue);

        updateDeclarationPayments(declarations, PLN, companyId);
        updateDeclarationPayments(declarations, EUR, companyId);

        for (final Declaration declaration : declarations) {
            declaration.setPaid(isDeclarationPaid(declaration));
            declarationDao.update(declaration);
        }
    }

    protected boolean isDeclarationPaid(final Declaration declaration) {
        return isAmountPaid(declaration.getDeclaredPaymentAmountPLN(), declaration.getCashPaidPLN()) &&
                isAmountPaid(declaration.getDeclaredPaymentAmountEUR(), declaration.getCashPaidEUR());
    }

    private void updateDeclarationPayments(final List<Declaration> allDeclarations, final Currency currency, final Long companyId) {
        final List<Declaration> declarations = getDeclarationsByCurrency(allDeclarations, currency);

        if (declarations.isEmpty()) {
            return;
        }

        final Date fromDate = getMinDeclarationDate(declarations);
        final Date endDate = getMaxDeclaredPaymentDate(declarations);
        final Map<Date, BigDecimal> paymentsByDate = companyService.findCompanyPayments(companyId, fromDate, endDate, currency.getStringValue());

        for (final Declaration declaration : declarations) {
            BigDecimal amountToPay = getDeclaredPaymentAmount(declaration, currency);
            Date declarationDate = new Date(declaration.getDeclarationDate().getTime());
            final Long daysBetween = DateUtils.daysBetween(declarationDate, declaration.getDeclaredPaymentDate());
            for (int i = 0; i <= daysBetween; i++) {
                BigDecimal payment = paymentsByDate.get(declarationDate);
                if (payment != null && payment.signum() > 0) {
                    if (isAmountPaid(amountToPay, payment)) {
                        paymentsByDate.put(declarationDate, payment.subtract(amountToPay));
                        amountToPay = ZERO;
                        break;
                    }
                    amountToPay = amountToPay.subtract(payment);
                    paymentsByDate.put(declarationDate, null);
                }
                declarationDate = DateUtils.datePlusOneDay(declarationDate);
            }
            final BigDecimal payments = getDeclaredPaymentAmount(declaration, currency).subtract(amountToPay);
            setCashPaid(declaration, payments, currency);
        }

    }

    /**
     * Sprawdza czy kwota do zapłaty została spłacona przez płatność
     *
     * @param amountToPay - kwota do zapłaty
     * @param payment     - płatność
     * @return true jeżeli kwota została spłacona przez płatność, false w przeciwnym przypadku
     */
    private boolean isAmountPaid(final BigDecimal amountToPay, final BigDecimal payment) {
        if (payment == null) {
            return ZERO.compareTo(amountToPay) >= 0;
        }
        return payment.compareTo(amountToPay) >= 0;
    }

    private List<Declaration> getDeclarationsByCurrency(final List<Declaration> allDeclarations, final Currency currency) {
        return allDeclarations.stream()
                .filter(declaration -> filterByDeclaredPaymentAmount(declaration, currency))
                .sorted(new DeclarationComparator(currency))
                .collect(toList());
    }

    protected Date getMaxDeclaredPaymentDate(final List<Declaration> declarations) {
        return declarations.stream().map(Declaration::getDeclaredPaymentDate).max(Date::compareTo).get();
    }

    protected Date getMinDeclarationDate(final List<Declaration> declarations) {
        return declarations.stream().map(Declaration::getDeclarationDate).min(Date::compareTo).get();
    }

    protected void setCashPaid(final Declaration declaration, final Currency currency) {
        final BigDecimal declaredPaymentAmount = getDeclaredPaymentAmount(declaration, currency);
        setCashPaid(declaration, declaredPaymentAmount, currency);
    }

    private void setCashPaid(final Declaration declaration, final BigDecimal amount, final Currency currency) {
        if (PLN.equals(currency)) {
            declaration.setCashPaidPLN(amount);
            return;
        }
        declaration.setCashPaidEUR(amount);
    }

    protected boolean filterByDeclaredPaymentAmount(final Declaration declaration, final Currency currency) {
        return isGraterThanZero(getDeclaredPaymentAmount(declaration, currency));
    }

    protected BigDecimal getDeclaredPaymentAmount(final Declaration declaration, final Currency currency) {
        if (PLN.equals(currency)) {
            return declaration.getDeclaredPaymentAmountPLN() != null ? declaration.getDeclaredPaymentAmountPLN() :
                    BigDecimal.ZERO;
        }
        return declaration.getDeclaredPaymentAmountEUR() != null ? declaration.getDeclaredPaymentAmountEUR()
                : BigDecimal.ZERO;
    }

    protected boolean isGraterThanZero(final BigDecimal amount) {
        return amount.signum() > 0;
    }
}
