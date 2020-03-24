package com.codersteam.alwin.core.service.impl.termination;

import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationCreatorService;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static com.codersteam.alwin.common.termination.ContractTerminationType.CONDITIONAL;
import static com.codersteam.alwin.common.termination.ContractTerminationType.IMMEDIATE;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class CreateContractTerminationService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateContractTerminationService.class.getName());

    private final ContractTerminationCreatorService contractTerminationCreatorService;
    private final FormalDebtCollectionService formalDebtCollectionService;

    @Inject
    public CreateContractTerminationService(ContractTerminationCreatorService contractTerminationCreatorService,
                                            FormalDebtCollectionService formalDebtCollectionService) {
        this.contractTerminationCreatorService = contractTerminationCreatorService;
        this.formalDebtCollectionService = formalDebtCollectionService;
    }

    public void prepareContractTerminations() {
        // TODO przeniesienie konfiguracji wypowiedzeń do DB #29468
        LOG.info("Contract terminations preparation started");
        prepareConditionalContractTerminations();
        prepareImmediateContractTerminations();
        LOG.info("Contract terminations preparation ended");
    }

    /**
     * Przygotowanie wypowiedzeń warunkowych (Decyzja o wypowiedzeniu)
     */
    protected void prepareConditionalContractTerminations() {
        LOG.info("Conditional contract terminations preparation started");
        final List<ContractTerminationInitialData> conditionalTerminations = formalDebtCollectionService.findConditionalContractTerminationsToCreate();

        if (isEmpty(conditionalTerminations)) {
            LOG.info("No conditional contract terminations to prepare - finishing");
            return;
        }

        LOG.info("{} conditional contract terminations to prepare", conditionalTerminations.size());

        conditionalTerminations.forEach(conditionalContractTerminationToCreate -> {
            try {
                contractTerminationCreatorService.prepareContractTermination(conditionalContractTerminationToCreate, CONDITIONAL);
            } catch (final Exception e) {
                // TODO ponowienie tworzenia wypowiedzenia #29106
                LOG.error("Create conditional contract termination failed for {}", conditionalContractTerminationToCreate, e);
            }
        });

        LOG.info("Conditional contract terminations preparation ended");
    }

    /**
     * Przygotowanie wypowiedzeń natychmiastowych (Wezwanie do zwrotu przedmiotu)
     */
    protected void prepareImmediateContractTerminations() {
        LOG.info("Immediate contract terminations preparation started");
        final List<ContractTerminationInitialData> immediateTerminations = formalDebtCollectionService.findImmediateContractTerminationsToCreate();

        if (isEmpty(immediateTerminations)) {
            LOG.info("No immediate contract terminations to prepare - finishing");
            return;
        }

        LOG.info("{} immediate contract terminations to prepare", immediateTerminations.size());

        immediateTerminations.forEach(immediateContractTerminationToCreate -> {
            try {
                contractTerminationCreatorService.prepareContractTermination(immediateContractTerminationToCreate, IMMEDIATE);
            } catch (final Exception e) {
                // TODO ponowienie tworzenia wypowiedzenia #29106
                LOG.error("Create immediate contract termination failed for {}", immediateContractTerminationToCreate, e);
            }
        });

        LOG.info("Immediate contract terminations preparation ended");
    }
}
