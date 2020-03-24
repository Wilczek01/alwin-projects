package com.codersteam.alwin.core.service.impl.notice;

import com.codersteam.aida.core.api.service.ContractService;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.termination.DemandForPaymentStatusChangeDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.notice.*;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.codersteam.alwin.common.demand.DemandForPaymentState.ISSUED;
import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResult.failed;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class DemandForPaymentServiceImpl implements DemandForPaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * faktura czynszowa (3 - Opłata okresowa)
     */

    private final DemandForPaymentDao demandForPaymentDao;
    private final DemandForPaymentCreatorService demandForPaymentCreatorService;
    private final DateProvider dateProvider;
    private final AlwinMapper alwinMapper;
    private final ProcessDemandForPaymentService processDemandForPaymentService;
    private final ContractService aidaContractService;
    private final ManualDemandForPaymentMessageService manualDemandForPaymentMessageService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public DemandForPaymentServiceImpl(final DemandForPaymentDao demandForPaymentDao, final DemandForPaymentCreatorService demandForPaymentCreatorService,
                                       final AidaService aidaService, final DateProvider dateProvider, final AlwinMapper alwinMapper,
                                       final ProcessDemandForPaymentService processDemandForPaymentService,
                                       final ManualDemandForPaymentMessageService manualDemandForPaymentMessageService) {
        this.demandForPaymentDao = demandForPaymentDao;
        this.demandForPaymentCreatorService = demandForPaymentCreatorService;
        this.dateProvider = dateProvider;
        this.alwinMapper = alwinMapper;
        this.processDemandForPaymentService = processDemandForPaymentService;
        this.aidaContractService = aidaService.getContractService();
        this.manualDemandForPaymentMessageService = manualDemandForPaymentMessageService;
    }

    @Override
    public Optional<DemandForPaymentDto> find(final Long id) {
        return demandForPaymentDao.get(id).map(this::mapDemandForPayment);
    }

    private DemandForPaymentDto mapDemandForPayment(final DemandForPayment demandForPayment) {
        return alwinMapper.map(demandForPayment, DemandForPaymentDto.class);
    }

    @Override
    public Page<DemandForPaymentDto> findAllDemandsForPayment(final DemandForPaymentSearchCriteria criteria, Map<DemandForPaymentSortField, SortOrder> sortCriteria) {
        final List<DemandForPaymentWithCompanyName> values = demandForPaymentDao.findAllDemandsForPayment(criteria, sortCriteria);
        final long totalValues = demandForPaymentDao.findAllDemandsForPaymentCount(criteria);
        final List<DemandForPaymentDto> demandForPaymentDtos = alwinMapper.mapAsList(values, DemandForPaymentDto.class);
        setManualDemandForPaymentMessages(criteria, demandForPaymentDtos);
        return new Page<>(demandForPaymentDtos, totalValues);
    }

    @Override
    public List<DemandForPaymentDto> findDemandsForPayment(final DemandForPaymentSearchCriteria criteria, Map<DemandForPaymentSortField, SortOrder> sortCriteria) {
        final List<DemandForPaymentWithCompanyName> values = demandForPaymentDao.findAllDemandsForPayment(criteria, sortCriteria);
        final List<DemandForPaymentDto> demandForPaymentDtos = alwinMapper.mapAsList(values, DemandForPaymentDto.class);
        setManualDemandForPaymentMessages(criteria, demandForPaymentDtos);
        return demandForPaymentDtos;
    }

    private void setManualDemandForPaymentMessages(final DemandForPaymentSearchCriteria criteria, final List<DemandForPaymentDto> demandForPaymentDtos) {
        if (criteria.getCreatedManually() == null || !criteria.getCreatedManually()) {
            return;
        }
        demandForPaymentDtos.forEach(demandForPayment -> {
            if (demandForPayment.getState() == DemandForPaymentState.NEW) {
                demandForPayment.setManualCreationMessage(manualDemandForPaymentMessageService.determineManualDemandForPaymentMessages(demandForPayment.getId()));
            }
        });
    }

    @Override
    public void rejectDemandsForPayment(final List<DemandForPaymentDto> demandsToReject, final Long operatorId, final String rejectedReason) {
        if (isEmpty(demandsToReject)) {
            return;
        }
        final Date rejectDate = dateProvider.getCurrentDate();
        final List<Long> demandToRejectIds = demandsToReject.stream().map(DemandForPaymentDto::getId).collect(toList());
        demandForPaymentDao.rejectDemandsForPayment(demandToRejectIds, operatorId, rejectDate, rejectedReason);
    }

    @Override
    public void processDemandsForPayment(final List<DemandForPaymentDto> demandsToSend, final String loggedOperatorFullName) {
        if (isEmpty(demandsToSend)) {
            return;
        }
        demandForPaymentDao.processDemandsForPayment(demandsToSend.stream().map(DemandForPaymentDto::getId).collect(toList()));
        executorService.submit(() -> demandsToSend.forEach(demand -> processDemandForPaymentService.sendDemand(demand, loggedOperatorFullName)));
    }

    @Override
    public List<Long> findNotExistingDemandsForPayment(final List<Long> demandsIds) {
        if (isEmpty(demandsIds)) {
            return Collections.emptyList();
        }
        final List<Long> existingDemands = demandForPaymentDao.findExistingDemandsForPayment(demandsIds);
        final List<Long> notExistingDemandsIds = new ArrayList<>(demandsIds);
        notExistingDemandsIds.removeAll(existingDemands);
        return notExistingDemandsIds;
    }

    @Override
    public List<Long> findIssuedDemandsForPayment(final List<Long> demandsIds) {
        if (isEmpty(demandsIds)) {
            return Collections.emptyList();
        }
        return demandForPaymentDao.findIssuedOrProcessingDemandsForPayment(demandsIds);
    }

    @Override
    public List<ManualPrepareDemandForPaymentResult> createDemandsForPaymentManually(final DemandForPaymentTypeKey type, final List<String> contractNumbers) {
        LOG.info("Preparing manual demands for payment for type {} and contracts {}", type, contractNumbers);
        final List<ManualPrepareDemandForPaymentResult> manualPrepareDemandForPaymentResult = new ArrayList<>();
        if (isEmpty(contractNumbers)) {
            LOG.info("No contract numbers provided - no demands for payment will be created");
            return manualPrepareDemandForPaymentResult;
        }
        contractNumbers.forEach(contractNo -> {
            try {
                manualPrepareDemandForPaymentResult.add(createDemandForPaymentManually(type, contractNo));
            } catch (final Exception e) {
                manualPrepareDemandForPaymentResult.add(failed(contractNo));
                LOG.error("Manual Demand for payment prepare failed for contract {}", contractNo);
            }
        });
        return manualPrepareDemandForPaymentResult;
    }

    protected ManualPrepareDemandForPaymentResult createDemandForPaymentManually(final DemandForPaymentTypeKey type, final String contractNo) {
        final Long extCompanyId = aidaContractService.findCompanyIdByContractNo(contractNo);
        if (extCompanyId == null) {
            return ManualPrepareDemandForPaymentResult.noContractFound(contractNo);
        }
        final ManualDemandForPaymentInitialData initialData = new ManualDemandForPaymentInitialData(type, extCompanyId, contractNo);
        return demandForPaymentCreatorService.prepareManualDemandForPayment(initialData);
    }

    @Override
    public void updateDemandForPaymentState(final long demandForPaymentId, final DemandForPaymentStatusChangeDto demandForPaymentStatusChangeDto,
                                            final DemandForPaymentState state) {
        demandForPaymentDao.get(demandForPaymentId)
                .filter(demandForPayment -> demandForPayment.getState().equals(ISSUED))
                .ifPresent(demandForPayment -> {
                    demandForPayment.setState(state);
                    demandForPayment.setReasonType(demandForPaymentStatusChangeDto.getReasonType());
                    demandForPayment.setStateChangeDate(demandForPaymentStatusChangeDto.getStateChangeDate());
                    demandForPayment.setStateChangeOperatorId(demandForPaymentStatusChangeDto.getStateChangeOperatorId());
                    demandForPayment.setStateChangeReason(demandForPaymentStatusChangeDto.getStateChangeReason());
                    demandForPaymentDao.update(demandForPayment);
                });
    }
}
