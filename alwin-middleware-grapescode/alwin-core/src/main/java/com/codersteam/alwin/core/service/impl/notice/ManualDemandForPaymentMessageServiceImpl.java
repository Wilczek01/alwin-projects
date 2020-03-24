package com.codersteam.alwin.core.service.impl.notice;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService;
import com.codersteam.alwin.core.api.service.notice.ManualDemandForPaymentMessageService;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.util.DateUtils.dateToStringWithoutTime;
import static java.lang.String.format;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class ManualDemandForPaymentMessageServiceImpl implements ManualDemandForPaymentMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String CUSTOMER_OUT_OF_SERVICE_MESSAGE = "Klient posiada aktywną blokadę windykacji formalnej";
    private static final String CONTRACT_OUT_OF_SERVICE_MESSAGE = "Umowa posiada aktywną blokadę windykacji formalnej";
    private static final String DEMAND_FOR_PAYMENT_OF_GIVEN_TYPE_ALREADY_ISSUED_MESSAGE = "Uwaga: umowa jest już windykowana – wysłano wezwanie danego stopnia dnia %s";
    private static final String MANUAL_DEMAND_FOR_PAYMENT_BREAKS_PROCESS_MESSAGE = "Uwaga: umowa jest już windykowana – wysłano wezwanie wyższego stopnia dnia %s. Wysłanie spowoduje rozpoczęcie procesu od nowa";
    private static final String PREVIOUS_DEMAND_FOR_PAYMENT_NOT_OVERDUE_MESSAGE = "Uwaga: nie upłynął termin zapłaty ostatniego wezwania wystawionego do tej umowy!";

    private final FormalDebtCollectionService formalDebtCollectionService;
    private final DemandForPaymentDao demandForPaymentDao;
    private final CustomerService customerService;
    private final DateProvider dateProvider;

    @Inject
    public ManualDemandForPaymentMessageServiceImpl(final FormalDebtCollectionService formalDebtCollectionService,
                                                    final DemandForPaymentDao demandForPaymentDao, final CustomerService customerService, final DateProvider dateProvider) {
        this.formalDebtCollectionService = formalDebtCollectionService;
        this.demandForPaymentDao = demandForPaymentDao;
        this.customerService = customerService;
        this.dateProvider = dateProvider;
    }

    @Override
    public List<String> determineManualDemandForPaymentMessages(final Long demandForPaymentId) {
        final List<String> messages = new ArrayList<>();

        final Optional<DemandForPayment> demandForPaymentOpt = demandForPaymentDao.get(demandForPaymentId);

        if (!demandForPaymentOpt.isPresent()) {
            LOG.warn("No demand for payment with id {} found", demandForPaymentId);
            return messages;
        }

        final DemandForPayment demandForPayment = demandForPaymentOpt.get();

        final String contractNumber = demandForPayment.getContractNumber();
        if (!formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(contractNumber)) {
            return messages;
        }

        final DemandForPaymentTypeKey demandForPaymentTypeKey = demandForPayment.getType().getKey();
        final Long extCompanyId = demandForPayment.getExtCompanyId();

        final Optional<String> breakProcessMessage = manualDemandForPaymentWillBreakProcessMessage(demandForPaymentTypeKey, contractNumber);
        if (breakProcessMessage.isPresent()) {
            messages.add(breakProcessMessage.get());
        } else {
            demandOfGivenTypeIssuedForContractMessage(demandForPaymentTypeKey, contractNumber).ifPresent(messages::add);
        }
        previousDemandForPaymentNotOverdueMessage(contractNumber).ifPresent(messages::add);
        customerOutOfServiceMessage(extCompanyId, demandForPaymentTypeKey).ifPresent(messages::add);
        contractOutOfServiceMessage(extCompanyId, contractNumber, demandForPaymentTypeKey).ifPresent(messages::add);

        return messages;
    }

    /**
     * Umowa w trakcie procesu windykacji formalnej, a wezwanie danego typu zostało już wysłane
     *
     * @param demandForPaymentTypeKey - klucz typu wezwania
     * @param contractNumber          - numer umowy
     * @return treść komunikatu dla operatora w przypadku zaistnienia warunku
     */
    private Optional<String> demandOfGivenTypeIssuedForContractMessage(final DemandForPaymentTypeKey demandForPaymentTypeKey, final String contractNumber) {
        final Date issueDate = formalDebtCollectionService.findIssuedDemandForPaymentIssueDate(demandForPaymentTypeKey, contractNumber);
        if (issueDate == null) {
            return Optional.empty();
        }
        return Optional.of(format(DEMAND_FOR_PAYMENT_OF_GIVEN_TYPE_ALREADY_ISSUED_MESSAGE, dateToStringWithoutTime(issueDate)));
    }

    /**
     * Umowa w trakcie procesu windykacji formalnej, a wysłanie wezwania łamało będzie kroki procesu (np. użytkownik będzie próbował wysłać monit, podczas
     * gdy w istniejącym procesie wysłano już wezwanie
     *
     * @param demandForPaymentTypeKey - klucz typu wezwania
     * @param contractNumber          - numer umowy
     * @return treść komunikatu dla operatora w przypadku zaistnienia warunku
     */
    private Optional<String> manualDemandForPaymentWillBreakProcessMessage(final DemandForPaymentTypeKey demandForPaymentTypeKey, final String contractNumber) {
        final Date succeedingDemandForPaymentIssueDate = formalDebtCollectionService.findIssuedSucceedingDemandForPaymentIssueDate(demandForPaymentTypeKey,
                contractNumber);
        if (succeedingDemandForPaymentIssueDate == null) {
            return Optional.empty();
        }
        return Optional.of(format(MANUAL_DEMAND_FOR_PAYMENT_BREAKS_PROCESS_MESSAGE, dateToStringWithoutTime(succeedingDemandForPaymentIssueDate)));
    }

    /**
     * Nie upłynął termin z poprzedniego wezwania
     *
     * @param contractNumber          - numer umowy
     * @return treść komunikatu dla operatora w przypadku zaistnienia warunku
     */
    private Optional<String> previousDemandForPaymentNotOverdueMessage(final String contractNumber) {
        final Boolean isDemandForPaymentOverdue = formalDebtCollectionService.isLatestDemandForPaymentOverdue(contractNumber);
        if (isDemandForPaymentOverdue == null || isDemandForPaymentOverdue) {
            return Optional.empty();
        }
        return Optional.of(PREVIOUS_DEMAND_FOR_PAYMENT_NOT_OVERDUE_MESSAGE);
    }

    /**
     * Klient posiada blokadę windykacji formalnej
     *
     * @param extCompanyId - identyfikator klienta w systemie zewnętrznym
     * @return treść komunikatu dla operatora w przypadku zaistnienia warunku
     */
    private Optional<String> customerOutOfServiceMessage(final Long extCompanyId, final DemandForPaymentTypeKey typeKey) {
        final boolean formalDebtCollectionCustomerOutOfService = customerService.isFormalDebtCollectionCustomerOutOfService(extCompanyId,
                dateProvider.getCurrentDateStartOfDay(), typeKey);
        return formalDebtCollectionCustomerOutOfService ? Optional.of(CUSTOMER_OUT_OF_SERVICE_MESSAGE) : Optional.empty();
    }

    /**
     * Umowa posiada blokadę windykacji formalnej
     *
     * @param extCompanyId   - identyfikator klienta w systemie zewnętrznym
     * @param contractNumber - identyfikator umowy
     * @return treść komunikatu dla operatora w przypadku zaistnienia warunku
     */
    private Optional<String> contractOutOfServiceMessage(final Long extCompanyId, final String contractNumber, final DemandForPaymentTypeKey typeKey) {
        final List<FormalDebtCollectionContractOutOfServiceDto> activeFormalDebtCollectionContractsOutOfService =
                customerService.findActiveFormalDebtCollectionContractsOutOfService(extCompanyId);
        if (isEmpty(activeFormalDebtCollectionContractsOutOfService)) {
            return Optional.empty();
        }

        final List<String> blockedContractNumbers = activeFormalDebtCollectionContractsOutOfService.stream()
                .filter(contractOutOfService -> contractOutOfService.getDemandForPaymentType() == typeKey)
                .map(FormalDebtCollectionContractOutOfServiceDto::getContractNo).collect(Collectors.toList());

        if (blockedContractNumbers.contains(contractNumber)) {
            return Optional.of(CONTRACT_OUT_OF_SERVICE_MESSAGE);
        }
        return Optional.empty();
    }
}
