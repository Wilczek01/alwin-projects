package com.codersteam.alwin.core.service.impl.termination;

import com.codersteam.aida.core.api.model.AidaContractWithSubjectsAndSchedulesDto;
import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.model.InstalmentDto;
import com.codersteam.aida.core.api.model.PaymentScheduleWithInstalmentsDto;
import com.codersteam.aida.core.api.service.ContractService;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.common.termination.ContractType;
import com.codersteam.alwin.common.termination.InstallationCharge;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.InvoiceService;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationCreatorService;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.ContractTerminationSubject;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.jpa.type.AddressType.CORRESPONDENCE;
import static com.codersteam.alwin.jpa.type.AddressType.RESIDENCE;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class ContractTerminationCreatorServiceImpl implements ContractTerminationCreatorService {

    private static final BigDecimal VAT_RATIO = new BigDecimal("1.23");
    private static final BigDecimal BASIC_PART_STANDARD_INSTALLATION_CHARGE = new BigDecimal("620.00");
    private static final BigDecimal BASIC_PART_INCREASED_INSTALLATION_CHARGE = new BigDecimal("830.00");
    private static final BigDecimal INSTALLATION_CHARGE_YEAR_RATIO = new BigDecimal("246.00");

    private static final Integer TERMINATION_INVOICES_DUE_DAYS = 14;

    private static final String REPURCHASE_INSTALMENT = "Wykup";
    private static final String INITIAL_INSTALMENT = "Oplata Wstepna";
    protected static final String MISSING_RESIDENCE_ADDRESS = "Brak adresu siedziby dla klienta";

    private final ContractTerminationDao contractTerminationDao;
    private final ContractService contractService;
    private final CompanyDao companyDao;
    private final AlwinMapper mapper;
    private final InvoiceService invoiceService;
    private final DateProvider dateProvider;
    private final com.codersteam.aida.core.api.service.InvoiceService aidaInvoiceService;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public ContractTerminationCreatorServiceImpl(final ContractTerminationDao contractTerminationDao, final AidaService aidaService,
                                                 final CompanyDao companyDao, final AlwinMapper mapper, final InvoiceService invoiceService,
                                                 final DateProvider dateProvider) {
        this.contractTerminationDao = contractTerminationDao;
        this.contractService = aidaService.getContractService();
        this.aidaInvoiceService = aidaService.getInvoiceService();
        this.companyDao = companyDao;
        this.mapper = mapper;
        this.invoiceService = invoiceService;
        this.dateProvider = dateProvider;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void prepareContractTermination(final ContractTerminationInitialData data, final ContractTerminationType terminationType) {
        final AidaContractWithSubjectsAndSchedulesDto contract = contractService.findContractWithSubjectAndSchedulesByContractNo(data.getContractNumber());
        if (contract == null) {
            throw new IllegalStateException(String.format("Missing contract with subjects and schedule for contract no: %s", data.getContractNumber()));
        }
        final ContractTermination termination = new ContractTermination();
        termination.setContractNumber(data.getContractNumber());
        termination.setExtCompanyId(data.getExtCompanyId());
        final Company company = companyDao.findCompanyByExtId(data.getExtCompanyId())
                .orElseThrow(() -> new IllegalStateException(String.format("Klient o nr z leo [%s] nie istnieje", data.getExtCompanyId())));
        termination.setCompanyName(company.getCompanyName());
        termination.setTerminationDate(dateProvider.getCurrentDateStartOfDay());
        termination.setType(terminationType);
        termination.setState(ContractTerminationState.NEW);
        fillAddresses(termination, company.getAddresses());
        termination.setResumptionCost(calculateResumptionCost(contract.getPaymentSchedules()));
        termination.setSubjects(prepareSubjects(contract));
        termination.setContractType(prepareContractType(contract));
        final Date todayPlus14Days = dateProvider.getDateStartOfDayPlusDays(TERMINATION_INVOICES_DUE_DAYS);
        final List<AidaInvoiceWithCorrectionsDto> invoices = invoiceService.getContractDueInvoices(data.getExtCompanyId(), data.getContractNumber(), todayPlus14Days);
        termination.setFormalDebtCollectionInvoices(mapper.mapAsList(invoices, FormalDebtCollectionInvoice.class));
        termination.setPrecedingDemandForPaymentId(data.getPrecedingDemandForPaymentId());
        termination.setPrecedingContractTerminationId(data.getPrecedingContractTerminationId());
        fillDemandForPaymentData(termination, data);

        contractTerminationDao.create(termination);
    }

    /**
     * Typ finansowania
     *
     * @return typ finansowania według nazwy pobranej z AIDY lub null jeśli nie udało się dopasować
     */
    private ContractType prepareContractType(final AidaContractWithSubjectsAndSchedulesDto contract) {
        return ContractType.byName(contract.getFinancingType());
    }

    /**
     * Opłata za wznowienie: wyliczona jako:
     * 1,2 * średnia rata (120% raty leasingowej. W związku z tym, że zdarzają się harmonogramy z ratami nieregularnymi, wyliczamy średnią dla wszystkich rat
     * (bez uwzględniania opłaty wstępnej i wykupu) i ustalamy jej 120%
     *
     * @param paymentSchedules - harmonogramy
     * @return wysokość opłaty za wznowienie
     */
    protected BigDecimal calculateResumptionCost(final List<PaymentScheduleWithInstalmentsDto> paymentSchedules) {
        if (isEmpty(paymentSchedules)) {
            return BigDecimal.ZERO;
        }
        final List<BigDecimal> allInstallments = paymentSchedules.stream()
                .filter(this::onlyActiveMainSchedule)
                .map(PaymentScheduleWithInstalmentsDto::getInstalments)
                .flatMap(List::stream)
                .filter(this::installmentDifferentThanInitialAndRepurchase)
                .map(InstalmentDto::getPayment)
                .collect(Collectors.toList());

        if (isEmpty(allInstallments)) {
            return BigDecimal.ZERO;
        }

        final BigDecimal allPaymentsSum = allInstallments.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        final BigDecimal avgPayment = allPaymentsSum.divide(new BigDecimal(allInstallments.size()), 2, BigDecimal.ROUND_HALF_UP);

        return new BigDecimal("1.2").multiply(avgPayment).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private boolean installmentDifferentThanInitialAndRepurchase(final InstalmentDto installment) {
        return !REPURCHASE_INSTALMENT.equalsIgnoreCase(installment.getFinancingElement().getPaymentKind()) && !INITIAL_INSTALMENT.equalsIgnoreCase(installment.getFinancingElement().getPaymentKind());
    }

    private boolean onlyActiveMainSchedule(final PaymentScheduleWithInstalmentsDto schedule) {
        return schedule.getCalculationType() == 1 && schedule.isActive();
    }

    private List<ContractTerminationSubject> prepareSubjects(final AidaContractWithSubjectsAndSchedulesDto contract) {
        if (isEmpty(contract.getSubjects())) {
            return Collections.emptyList();
        }
        final List<ContractTerminationSubject> subjects = new ArrayList<>();
        contract.getSubjects().forEach(subject -> {
            final ContractTerminationSubject terminationSubject = new ContractTerminationSubject();
            terminationSubject.setSubjectId(subject.getSubjectId());
            boolean gpsInstalled = contractService.checkIfContractSubjectHasInstalledGps(contract.getContractNo());
            terminationSubject.setGpsInstalled(gpsInstalled);
            final boolean installGPS = StringUtils.isNotEmpty(subject.getRegistrationNumber()) && !gpsInstalled;
            terminationSubject.setGpsToInstall(installGPS);
            final InstallationCharge installationCharge = calculateInstallationCharge(findScheduleForSubject(contract), installGPS);
            terminationSubject.setGpsInstallationCharge(installationCharge.getStandard());
            terminationSubject.setGpsIncreasedInstallationCharge(installationCharge.getIncreased());
            terminationSubject.setGpsIncreasedFee(false);
            subjects.add(terminationSubject);
        });
        return subjects;
    }

    protected PaymentScheduleWithInstalmentsDto findScheduleForSubject(final AidaContractWithSubjectsAndSchedulesDto contract) {
        if (isEmpty(contract.getPaymentSchedules())) {
            return null;
        }
        return contract.getPaymentSchedules().stream().filter(this::onlyActiveMainSchedule).findFirst().orElse(null);
    }


    /**
     * Jeśli hasGPS = False; wtedy 0; jeśli TRUE =>
     * a. Jeśli „Podwyższona opłata za GPS” = True, wtedy podstawa = 830 zł,
     * b. Jeśli „Podwyższona opłata za GPS” = False, wtedy podstawa = 620 zł
     * <p>
     * Wyliczamy liczbę lat pozostałej płatności umowy w taki sposób, że:
     * a. liczbaLat = liczba pozostałych rat / 12 (nie zaokrąglamy, bierzemy część całkowitą)
     * b. Jeśli modulo (liczba pozostałych rat / 12) jest różne od zera, wtedy
     * liczbaLat = liczbaLat +1
     * <p>
     * Następnie wykonujemy działanie: opłataNetto= podstawa + (246*liczbaLat)
     * I na końcu wyliczamy wartość brutto: opłataBrutto = opłataNetto*1,23
     * opłataBrutto to GpsFee wyliczone dla danej umowy.
     *
     * @param schedule   - harmonogram rat
     * @param installGPS - czy zainstalować GPS
     * @return opłaty za instalację GPS
     */
    protected InstallationCharge calculateInstallationCharge(final PaymentScheduleWithInstalmentsDto schedule, final boolean installGPS) {
        if (!installGPS || schedule == null || !schedule.isActive()) {
            return new InstallationCharge(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        final long remainingInstallments = schedule.getInstalments().stream().filter(instalment -> instalment.getRemainingPayment().compareTo(BigDecimal.ZERO) > 0).count();
        long basic = remainingInstallments / 12;
        if (remainingInstallments % 12 > 0) {
            basic += 1;
        }
        final BigDecimal commonChargePart = new BigDecimal(basic).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(INSTALLATION_CHARGE_YEAR_RATIO);
        final BigDecimal standardCharge = BASIC_PART_STANDARD_INSTALLATION_CHARGE.add(commonChargePart).multiply(VAT_RATIO);
        final BigDecimal increasedCharge = BASIC_PART_INCREASED_INSTALLATION_CHARGE.add(commonChargePart).multiply(VAT_RATIO);
        return new InstallationCharge(standardCharge, increasedCharge);
    }

    private void fillDemandForPaymentData(final ContractTermination termination, final ContractTerminationInitialData data) {
        termination.setDueDateInDemandForPayment(data.getDueDateInDemandForPayment());
        termination.setInvoiceNumberInitiatingDemandForPayment(data.getInitialInvoiceNo());
        final FormalDebtCollectionInvoiceDto initialInvoice = data.getInvoices().stream()
                .filter(invoice -> invoice.getInvoiceNumber().equals(data.getInitialInvoiceNo()))
                .findFirst().orElseThrow(() -> new IllegalStateException("Missing initial invoice"));
        final BigDecimal amountInDemandForPaymentPLN = calculateBalanceSumOnInvoicePerCurrency(data.getInvoices(), Currency.PLN);
        termination.setAmountInDemandForPaymentPLN(amountInDemandForPaymentPLN);
        final BigDecimal amountInDemandForPaymentEUR = calculateBalanceSumOnInvoicePerCurrency(data.getInvoices(), Currency.EUR);
        termination.setAmountInDemandForPaymentEUR(amountInDemandForPaymentEUR);
        final Set<FormalDebtCollectionInvoiceDto> invoices = mapper.mapAsSet(data.getInvoices().stream()
                .map(invoice -> this.aidaInvoiceService.getInvoiceWithBalance(invoice.getLeoId()))
                .collect(Collectors.toSet()), FormalDebtCollectionInvoiceDto.class);
        termination.setTotalPaymentsSinceDemandForPaymentPLN(calculateTotalPaymentsSinceDemandForPayment(amountInDemandForPaymentPLN, Currency.PLN, invoices));
        termination.setTotalPaymentsSinceDemandForPaymentEUR(calculateTotalPaymentsSinceDemandForPayment(amountInDemandForPaymentEUR, Currency.EUR, invoices));
        termination.setInvoiceAmountInitiatingDemandForPayment(initialInvoice.getGrossAmount());
        termination.setInvoiceBalanceInitiatingDemandForPayment(initialInvoice.getCurrentBalance());
    }

    private BigDecimal calculateTotalPaymentsSinceDemandForPayment(final BigDecimal amountInDemandForPayment, final Currency currency,
                                                                   final Set<FormalDebtCollectionInvoiceDto> invoices) {
        final BigDecimal balanceSumOnTermination = calculateBalanceSumOnInvoicePerCurrency(invoices, currency);
        return amountInDemandForPayment.subtract(balanceSumOnTermination).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateBalanceSumOnInvoicePerCurrency(final Set<FormalDebtCollectionInvoiceDto> invoices, final Currency currency) {
        return invoices.stream()
                .filter(invoice -> currency.name().equals(invoice.getCurrency()))
                .map(FormalDebtCollectionInvoiceDto::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(new BigDecimal(-1))
                .setScale(2, RoundingMode.HALF_UP);
    }

    protected void fillAddresses(final ContractTermination termination, final Set<Address> addresses) {
        if (isEmpty(addresses)) {
            throw new IllegalStateException(MISSING_RESIDENCE_ADDRESS);
        }

        final Address address = addresses.stream().filter(a -> RESIDENCE == a.getAddressType()).findFirst().orElseThrow(() -> new IllegalStateException(MISSING_RESIDENCE_ADDRESS));
        final Address correspondenceAddress = addresses.stream().filter(a -> CORRESPONDENCE == a.getAddressType()).findFirst().orElse(null);

        termination.setCompanyAddress(address);
        termination.setCompanyAddressId(address.getId());
        termination.setCompanyCorrespondenceAddress(correspondenceAddress);
        termination.setCompanyCorrespondenceAddressId(correspondenceAddress != null ? correspondenceAddress.getId() : null);
    }
}
