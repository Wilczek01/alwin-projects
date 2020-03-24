package com.codersteam.alwin.validator;

import com.codersteam.aida.core.api.service.CompanyService;
import com.codersteam.aida.core.api.service.ContractService;
import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceInputDto;
import com.codersteam.alwin.core.api.model.customer.CustomerOutOfServiceInputDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceInputDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionCustomerOutOfServiceInputDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.exception.AlwinValidationException;
import com.codersteam.alwin.parameters.DateParam;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Logika walidująca poprawność danych dla operacji związanych z klientem
 *
 * @author Michal Horowic
 */
@Stateless
public class CustomerValidator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateParam.DATE_PATTERN).withZone(ZoneId.systemDefault());

    private static final String MISSING_DATA = "Brak wytycznych do utworzenia blokady";
    private static final String MISSING_REASON_OF_BLOCKING = "Brak powodu blokady";
    private static final String MISSING_ACTIVE_CONTRACT = "Brak aktywnego kontraktu o numerze '%s'";
    private static final String WRONG_BLOCKING_PERIOD = "Data rozpoczęcia '%s' nie może być późniejsza niż zakończenia '%s'";
    private static final String MISSING_COMPANY = "Klient o podanym identyfikatorze '%s' nie istnieje";
    private static final String MISSING_CUSTOMER_OUT_OF_SERVICE = "Blokada klienta o podanym identyfikatorze '%s' nie istnieje";
    private static final String MISSING_CONTRACT_OUT_OF_SERVICE = "Blokada umowy klienta o podanym identyfikatorze '%s' nie istnieje";
    private static final String MISSING_CUSTOMER_MESSAGE = "Brak klienta o identyfikatorze %s";
    private static final String MISSING_REASON_TYPE = "Brak wybranego powodu blokady";
    private static final String MISSING_DEMAND_FOR_PAYMENT_TYPE = "Brak wybranego typu wezwania do zapłaty";

    private final CompanyService aidaCompanyService;
    private final CustomerService customerService;
    private final ContractService aidaContractService;

    public CustomerValidator() {
        this.aidaContractService = null;
        this.aidaCompanyService = null;
        this.customerService = null;
    }

    @Inject
    public CustomerValidator(final AidaService aidaService, final CustomerService customerService) {
        this.aidaCompanyService = aidaService.getCompanyService();
        this.customerService = customerService;
        this.aidaContractService = aidaService.getContractService();
    }

    /**
     * Sprawdza czy przesłane wytyczne odnośnie blokady klienta są poprawne
     *
     * @param extCompanyId                 - zewnętrzny identyfikator klienta
     * @param customerOutOfServiceInputDto - wytyczne odnośnie blokady
     */
    public void validate(final long extCompanyId, final CustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        checkMandatoryFieldsPresence(customerOutOfServiceInputDto);
        checkBlockingPeriod(customerOutOfServiceInputDto.getStartDate(), customerOutOfServiceInputDto.getEndDate());
        checkIfCompanyExists(extCompanyId);
    }

    /**
     * Sprawdza czy przesłane wytyczne odnośnie blokady klienta w windykacji formalnej są poprawne
     *
     * @param extCompanyId                 - zewnętrzny identyfikator klienta
     * @param customerOutOfServiceInputDto - wytyczne odnośnie blokady
     */
    public void validateFormalDebtCollection(final long extCompanyId, final FormalDebtCollectionCustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        validate(extCompanyId, customerOutOfServiceInputDto);
        validateReasonType(customerOutOfServiceInputDto.getReasonType());
        validateDemandForPaymentType(customerOutOfServiceInputDto.getDemandForPaymentType());
    }

    private void validateDemandForPaymentType(final DemandForPaymentTypeKey demandForPaymentType) {
        if (demandForPaymentType == null) {
            throw new AlwinValidationException(MISSING_DEMAND_FOR_PAYMENT_TYPE);
        }
    }

    private void validateReasonType(final ReasonType reasonType) {
        if (reasonType == null) {
            throw new AlwinValidationException(MISSING_REASON_TYPE);
        }
    }

    /**
     * Sprawdza czy przesłane wytyczne odnośnie blokady kontraktu klienta są poprawne
     *
     * @param extCompanyId - zewnętrzny identyfikator klienta
     * @param contractNo   - numer kontraktu
     * @param contract     - wytyczne odnośnie blokady
     */
    public void validate(final long extCompanyId, final String contractNo, final ContractOutOfServiceInputDto contract) {
        checkMandatoryFieldsPresence(contract);
        checkBlockingPeriod(contract.getStartDate(), contract.getEndDate());
        checkIfContractExists(contractNo);
        checkIfCompanyExists(extCompanyId);
    }


    /**
     * Sprawdza czy przesłane wytyczne odnośnie blokady kontraktu klienta w windykacji formalnej są poprawne
     *
     * @param extCompanyId - zewnętrzny identyfikator klienta
     * @param contractNo   - numer kontraktu
     * @param contract     - wytyczne odnośnie blokady
     */
    public void validateFormalDebtCollectionContract(final long extCompanyId, final String contractNo,
                                                     final FormalDebtCollectionContractOutOfServiceInputDto contract) {
        validate(extCompanyId, contractNo, contract);
        validateReasonType(contract.getReasonType());
        validateDemandForPaymentType(contract.getDemandForPaymentType());
    }

    /**
     * Sprawdza czy firma o podanym identyfikatorze z zewnętrznego systemu istnieje
     *
     * @param extCompanyId- zewnętrzny identyfikator klienta
     */
    public void checkIfCompanyExists(final long extCompanyId) {
        if (aidaCompanyService.findCompanyByCompanyId(extCompanyId) == null) {
            throw new AlwinValidationException(format(MISSING_COMPANY, extCompanyId));
        }
    }

    private void checkBlockingPeriod(final Date startDate, final Date endDate) {
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            throw new AlwinValidationException(format(WRONG_BLOCKING_PERIOD, FORMATTER.format(startDate.toInstant()), FORMATTER.format(endDate.toInstant())));
        }
    }

    private void checkMandatoryFieldsPresence(final CustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        checkPresence(customerOutOfServiceInputDto);

        if (customerOutOfServiceInputDto.getStartDate() == null) {
            customerOutOfServiceInputDto.setStartDate(new Date());
        }

        checkReasonEmptiness(customerOutOfServiceInputDto.getReason());
    }

    private void checkMandatoryFieldsPresence(final ContractOutOfServiceInputDto contractOutOfServiceInputDto) {
        checkPresence(contractOutOfServiceInputDto);

        if (contractOutOfServiceInputDto.getStartDate() == null) {
            contractOutOfServiceInputDto.setStartDate(new Date());
        }

        checkReasonEmptiness(contractOutOfServiceInputDto.getReason());
    }

    private void checkIfContractExists(final String contractNo) {
        if (aidaContractService.findActiveContractByContractNo(contractNo) == null) {
            throw new AlwinValidationException(format(MISSING_ACTIVE_CONTRACT, contractNo));
        }
    }

    private void checkReasonEmptiness(final String value) {
        if (isEmpty(value)) {
            throw new AlwinValidationException(MISSING_REASON_OF_BLOCKING);
        }
    }

    private void checkPresence(final Object value) {
        if (value == null) {
            throw new AlwinValidationException(MISSING_DATA);
        }
    }

    /**
     * Sprawdza czy blokada klienta o podanym identyfikatorze istnieje w windykacji nieformalnej
     *
     * @param customerOutOfServiceId - identyfikator blokady klienta
     */
    public void checkIfCustomerOutOfServiceExists(final long customerOutOfServiceId) {
        if (customerService.checkIfCustomerOutOfServiceExists(customerOutOfServiceId)) {
            return;
        }

        throw new AlwinValidationException(format(MISSING_CUSTOMER_OUT_OF_SERVICE, customerOutOfServiceId));
    }

    /**
     * Sprawdza czy blokada klienta o podanym identyfikatorze istnieje w windykacji formalnej
     *
     * @param customerOutOfServiceId - identyfikator blokady klienta
     */
    public void checkIfFormalDebtCollectionCustomerOutOfServiceExists(final long customerOutOfServiceId) {
        if (customerService.checkIfFormalDebtCollectionCustomerOutOfServiceExists(customerOutOfServiceId)) {
            return;
        }

        throw new AlwinValidationException(format(MISSING_CUSTOMER_OUT_OF_SERVICE, customerOutOfServiceId));
    }

    /**
     * Sprawdza czy blokada umowy klienta o podanym identyfikatorze istnieje
     *
     * @param contractOutOfServiceId - identyfikator blokady umowy klienta
     */
    public void checkIfContractOutOfServiceExists(final long contractOutOfServiceId, final long extCompanyId) {
        if (customerService.checkIfContractOutOfServiceExists(contractOutOfServiceId, extCompanyId)) {
            return;
        }

        throw new AlwinValidationException(format(MISSING_CONTRACT_OUT_OF_SERVICE, contractOutOfServiceId));
    }

    /**
     * Sprawdza czy blokada umowy klienta o podanym identyfikatorze istnieje
     *
     * @param contractOutOfServiceId - identyfikator blokady umowy klienta
     */
    public void checkIfFormalDebtCollectionContractOutOfServiceExists(final long contractOutOfServiceId, final long extCompanyId) {
        if (customerService.checkIfFormalDebtCollectionContractOutOfServiceExists(contractOutOfServiceId, extCompanyId)) {
            return;
        }

        throw new AlwinValidationException(format(MISSING_CONTRACT_OUT_OF_SERVICE, contractOutOfServiceId));
    }

    /**
     * Sprawdza czy customer o podanym identyfikatorze istnieje
     *
     * @param customerId - identyfikator klienta
     */
    public void checkIfCustomerExists(final long customerId) {
        if (customerService.checkIfCustomerExists(customerId)) {
            return;
        }
        throw new AlwinValidationException(format(MISSING_CUSTOMER_MESSAGE, customerId));
    }
}
