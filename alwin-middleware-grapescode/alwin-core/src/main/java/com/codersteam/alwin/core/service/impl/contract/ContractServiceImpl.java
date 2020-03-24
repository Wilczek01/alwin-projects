package com.codersteam.alwin.core.service.impl.contract;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.contract.ContractFinancialSummaryDto;
import com.codersteam.alwin.core.api.model.contract.ContractWithExclusions;
import com.codersteam.alwin.core.api.model.contract.FinancialSummaryDto;
import com.codersteam.alwin.core.api.model.contract.FormalDebtCollectionContractWithExclusions;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceDto;
import com.codersteam.alwin.core.api.model.customer.DateRange;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceDto;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.contract.ContractService;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.api.service.issue.InvoiceService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.core.util.InvoiceUtils;
import com.codersteam.alwin.db.dao.ContractOutOfServiceDao;
import com.codersteam.alwin.db.dao.FormalDebtCollectionContractOutOfServiceDao;
import com.codersteam.alwin.jpa.customer.ContractOutOfService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.forStringValue;
import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class ContractServiceImpl implements ContractService {

    private final InvoiceService invoiceService;
    private final IssueService issueService;
    private final ContractOutOfServiceDao contractOutOfServiceDao;
    private final DateProvider dateProvider;
    private final com.codersteam.aida.core.api.service.ContractService aidaContractService;
    private final CustomerService customerService;
    private final AlwinMapper mapper;
    private final FormalDebtCollectionContractOutOfServiceDao formalDebtCollectionContractOutOfServiceDao;

    @Inject
    public ContractServiceImpl(final InvoiceService invoiceService, final IssueService issueService, final ContractOutOfServiceDao contractOutOfServiceDao,
                               final DateProvider dateProvider, final AidaService aidaService, final CustomerService customerService, final AlwinMapper mapper,
                               final FormalDebtCollectionContractOutOfServiceDao formalDebtCollectionContractOutOfServiceDao) {
        this.invoiceService = invoiceService;
        this.issueService = issueService;
        this.contractOutOfServiceDao = contractOutOfServiceDao;
        this.dateProvider = dateProvider;
        this.aidaContractService = aidaService.getContractService();
        this.customerService = customerService;
        this.mapper = mapper;
        this.formalDebtCollectionContractOutOfServiceDao = formalDebtCollectionContractOutOfServiceDao;
    }

    @Override
    public List<ContractFinancialSummaryDto> calculateIssueContractFinancialSummaries(final Long issueId,
                                                                                      final boolean notPaidOnly,
                                                                                      final boolean overdueOnly) {
        final List<InvoiceDto> invoices = invoiceService.findInvoicesForIssueId(issueId);

        if (isEmpty(invoices)) {
            return emptyList();
        }

        final IssueDto issue = issueService.findIssue(issueId);

        if (issue == null) {
            throw new IllegalStateException(format("Cannot find issue with id %d, but %d invoices found!", issueId, invoices.size()));
        }

        final Set<String> excludedContractNumbers = findCompanyExcludedContractNumbers(issue);

        return prepareContractFinancialSummaries(groupInvoicesByContractNo(invoices), issue.getStartDate(), overdueOnly, excludedContractNumbers, notPaidOnly);
    }

    @Override
    public List<ContractWithExclusions> findContractsWithExclusionsByCompanyId(final Long extCompanyId) {
        final List<AidaContractDto> contracts = aidaContractService.findContractsByCompanyId(extCompanyId);
        final List<ContractOutOfServiceDto> contractsOutOfService = customerService.findAllContractsOutOfService(extCompanyId);
        final Map<String, List<ContractOutOfServiceDto>> exclusions = new HashMap<>();

        contractsOutOfService.forEach(exclusion -> {
            exclusions.putIfAbsent(exclusion.getContractNo(), new ArrayList<>());
            exclusions.get(exclusion.getContractNo()).add(exclusion);
        });

        final Date currentDate = dateProvider.getCurrentDate();
        return contracts.stream().map(contract -> {
            final ContractWithExclusions contractWithExclusions = mapper.map(contract, ContractWithExclusions.class);
            final List<ContractOutOfServiceDto> contractExclusions = exclusions.get(contract.getContractNo());
            contractWithExclusions.setExclusions(contractExclusions);
            contractWithExclusions.setExcluded(determineExcluded(contractExclusions, currentDate));
            return contractWithExclusions;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FormalDebtCollectionContractWithExclusions> findFormalDebtCollectionContractsWithExclusionsByCompanyId(Long extCompanyId) {
        final List<AidaContractDto> contracts = aidaContractService.findContractsByCompanyId(extCompanyId);
        final List<FormalDebtCollectionContractOutOfServiceDto> contractsOutOfService = customerService.findAllFormalDebtCollectionContractsOutOfService(extCompanyId);
        final Map<String, List<FormalDebtCollectionContractOutOfServiceDto>> exclusions = new HashMap<>();

        contractsOutOfService.forEach(exclusion -> {
            exclusions.putIfAbsent(exclusion.getContractNo(), new ArrayList<>());
            exclusions.get(exclusion.getContractNo()).add(exclusion);
        });

        final Date currentDate = dateProvider.getCurrentDate();
        return contracts.stream().map(contract -> {
            final FormalDebtCollectionContractWithExclusions contractWithExclusions = mapper.map(contract, FormalDebtCollectionContractWithExclusions.class);
            final List<FormalDebtCollectionContractOutOfServiceDto> contractExclusions = exclusions.get(contract.getContractNo());
            contractWithExclusions.setExclusions(contractExclusions);
            contractWithExclusions.setExcluded(determineExcluded(contractExclusions, currentDate));
            return contractWithExclusions;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean isFormalDebtCollectionContractOutOfService(final String contractNumber, final Date inspectionDate, final DemandForPaymentTypeKey demandForPaymentTypeKey) {
        return formalDebtCollectionContractOutOfServiceDao.isFormalDebtCollectionContractOutOfService(contractNumber, inspectionDate, demandForPaymentTypeKey);
    }

    protected boolean determineExcluded(final List<? extends DateRange> exclusions, final Date currentDate) {
        if (isEmpty(exclusions)) {
            return false;
        }

        for (final DateRange exclusion : exclusions) {
            if (exclusion.getStartDate().after(currentDate)) {
                continue;
            }
            if (exclusion.getEndDate() == null) {
                return true;
            }
            if (exclusion.getEndDate().before(currentDate)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private Set<String> findCompanyExcludedContractNumbers(final IssueDto issue) {
        final Long extCompanyId = getExtCompanyId(issue);
        if (extCompanyId == null) {
            return new HashSet<>();
        }
        final List<ContractOutOfService> contractsOutOfService = contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, dateProvider.getCurrentDate());
        return contractsOutOfService.stream().map(ContractOutOfService::getContractNo).collect(toSet());
    }

    private List<ContractFinancialSummaryDto> prepareContractFinancialSummaries(final Map<String, List<InvoiceDto>> invoicesByContractNo,
                                                                                final Date issueStartDate, final boolean overdueOnly,
                                                                                final Set<String> excludedContractNumbers, final boolean notPaidOnly) {
        final List<ContractFinancialSummaryDto> contractFinancialSummaries = new ArrayList<>();
        for (final Map.Entry<String, List<InvoiceDto>> contractToInvoices : invoicesByContractNo.entrySet()) {
            final String contractNo = contractToInvoices.getKey();
            final boolean isExcluded = excludedContractNumbers.contains(contractNo);
            final List<InvoiceDto> contractInvoices = contractToInvoices.getValue();
            if (overdueOnly && !isOverdue(contractInvoices)) {
                continue;
            }
            final ContractFinancialSummaryDto contractFinancialSummary = prepareContractFinancialSummary(contractNo, contractInvoices, issueStartDate, isExcluded);
            if (notPaidOnly && isPaid(contractFinancialSummary)) {
                continue;
            }

            contractFinancialSummaries.add(contractFinancialSummary);
        }
        return contractFinancialSummaries;
    }

    private ContractFinancialSummaryDto prepareContractFinancialSummary(final String contractNo, final List<InvoiceDto> contractInvoices,
                                                                        final Date issueStartDate, final boolean isExcluded) {
        final ContractFinancialSummaryDto contractFinancialSummaryDto = new ContractFinancialSummaryDto();
        contractFinancialSummaryDto.setContractNo(contractNo);
        contractFinancialSummaryDto.setDpd(findMaxDpdValue(contractInvoices));
        contractFinancialSummaryDto.setExcluded(isExcluded);

        final Map<String, List<InvoiceDto>> invoicesByCurrency = contractInvoices.stream().collect(groupingBy(InvoiceDto::getCurrency));
        final Map<Currency, FinancialSummaryDto> currencyToSummary = contractFinancialSummaryDto.getCurrencyToSummary();

        for (final Map.Entry<String, List<InvoiceDto>> currencyInvoices : invoicesByCurrency.entrySet()) {
            final String currencyAsString = currencyInvoices.getKey();
            final List<InvoiceDto> invoices = currencyInvoices.getValue();

            final BigDecimal requiredPayment = calculateRequiredPayment(issueStartDate, invoices);
            final BigDecimal notRequiredPayment = calculateNotRequiredPayment(issueStartDate, invoices);
            final BigDecimal overpayment = calculateOverpayment(invoices);

            final Currency currency = forStringValue(currencyAsString, invoices.get(0).getNumber());
            final FinancialSummaryDto financialSummary = new FinancialSummaryDto(requiredPayment, notRequiredPayment, overpayment);
            currencyToSummary.put(currency, financialSummary);
        }

        return contractFinancialSummaryDto;
    }

    /**
     * Wyznaczenie największej wartości DPD z faktur umowy (tylko faktury nieopłacone)
     *
     * @param contractInvoices faktury umowy
     * @return - największa wartość DPD, null jeśli brak faktur nieopłaconych
     */
    protected Long findMaxDpdValue(final List<InvoiceDto> contractInvoices) {
        BigDecimal LIMIT = new BigDecimal("-100.0");
        BigDecimal SUM = BigDecimal.ZERO;
        List<InvoiceDto> collect = contractInvoices.stream()
                .filter(InvoiceUtils::isNegativeInvoiceBalance)
                .sorted(Comparator.comparing(InvoiceDto::getRealDueDate))
                .collect(Collectors.toList());

        for (InvoiceDto invoiceDto : collect) {
            SUM = SUM.add(invoiceDto.getCurrentBalance());
            if (SUM.compareTo(LIMIT) < 0) {
                return invoiceDto.getDpd();
            }
        }

        return null;
    }

    protected boolean isPaid(final ContractFinancialSummaryDto contractFinancialSummary) {
        for (final FinancialSummaryDto financialSummaryDto : contractFinancialSummary.getCurrencyToSummary().values()) {
            if (ZERO.compareTo(financialSummaryDto.getTotal()) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isOverdue(final List<InvoiceDto> contractInvoices) {
        final Date currentDate = dateProvider.getCurrentDate();
        return contractInvoices.stream().anyMatch(invoice -> currentDate.after(invoice.getRealDueDate()));
    }

    protected BigDecimal calculateOverpayment(final List<InvoiceDto> invoices) {
        return invoices.stream()
                .filter(invoice -> invoice.getCurrentBalance() != null && ZERO.compareTo(invoice.getCurrentBalance()) < 0)
                .filter(invoice -> !invoice.getExcluded())
                .map(InvoiceDto::getCurrentBalance)
                .reduce(ZERO, BigDecimal::add);
    }

    protected BigDecimal calculateNotRequiredPayment(final Date issueStartDate, final List<InvoiceDto> invoices) {
        return invoices.stream()
                .filter(invoice -> invoice.getRealDueDate().equals(issueStartDate) || invoice.getRealDueDate().after(issueStartDate))
                .filter(invoice -> invoice.getCurrentBalance() != null && ZERO.compareTo(invoice.getCurrentBalance()) > 0)
                .filter(invoice -> !invoice.getExcluded())
                .map(InvoiceDto::getCurrentBalance)
                .reduce(ZERO, BigDecimal::add)
                .abs();
    }

    protected BigDecimal calculateRequiredPayment(final Date issueStartDate, final List<InvoiceDto> invoices) {
        return invoices.stream()
                .filter(invoice -> invoice.getRealDueDate().before(issueStartDate))
                .filter(invoice -> invoice.getCurrentBalance() != null && ZERO.compareTo(invoice.getCurrentBalance()) > 0)
                .filter(invoice -> !invoice.getExcluded())
                .map(InvoiceDto::getCurrentBalance)
                .reduce(ZERO, BigDecimal::add)
                .abs();
    }

    protected Map<String, List<InvoiceDto>> groupInvoicesByContractNo(final List<InvoiceDto> invoices) {
        return invoices.stream().collect(groupingBy(InvoiceDto::getContractNumber));
    }
}
