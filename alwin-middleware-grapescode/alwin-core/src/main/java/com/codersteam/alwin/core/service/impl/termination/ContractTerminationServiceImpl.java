package com.codersteam.alwin.core.service.impl.termination;

import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.sort.ContractTerminationSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.common.termination.ContractTerminationGroupKey;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.termination.ActivateContractTerminationDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationService;
import com.codersteam.alwin.core.api.service.termination.ProcessContractTerminationService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.termination.ContractTerminationState.*;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Dariusz Rackowski
 */
@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class ContractTerminationServiceImpl implements ContractTerminationService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ConfigurableMapper mapper;
    private final ContractTerminationDao contractTerminationDao;
    private final DateProvider dateProvider;
    private final ProcessContractTerminationService processContractTerminations;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Inject
    public ContractTerminationServiceImpl(final AlwinMapper mapper, final ContractTerminationDao contractTerminationDao,
                                          final DateProvider dateProvider, final ProcessContractTerminationService processContractTerminations) {
        this.mapper = mapper;
        this.contractTerminationDao = contractTerminationDao;
        this.dateProvider = dateProvider;
        this.processContractTerminations = processContractTerminations;
    }

    @Override
    public Page<TerminationDto> findTerminations(final ContractTerminationSearchCriteria criteria, Map<ContractTerminationSortField, SortOrder> sortCriteria) {
        final List<ContractTermination> allContractTerminations = contractTerminationDao.findByStatePaginatedByExtCompanyIdAndTerminationDateAndType(criteria, sortCriteria);
        final List<TerminationDto> values = mapToTerminationDtoList(allContractTerminations);
        final int totalValues = contractTerminationDao.countInStatePaginatedByExtCompanyIdAndTerminationDate(criteria);
        return new Page<>(values, totalValues);
    }

    @Override
    public Optional<TerminationContractDto> findTermination(final long id) {
        return contractTerminationDao.get(id).map(contractTermination -> mapper.map(contractTermination, TerminationContractDto.class));
    }

    @Override
    public List<TerminationDto> findTerminationsToProcess() {
        final List<ContractTermination> allContractTerminations = contractTerminationDao.findByStatesAndTerminationDateLte(
                OPEN_CONTRACT_TERMINATION_STATES, dateProvider.getCurrentDateEndOfDay());
        return mapToTerminationDtoList(allContractTerminations);
    }

    private List<TerminationDto> mapToTerminationDtoList(final List<ContractTermination> allContractTerminations) {
        return allContractTerminations.stream()
                .collect(Collectors.groupingBy(
                        ct -> new ContractTerminationGroupKey(ct.getTerminationDate(), ct.getExtCompanyId(), ct.getType()),
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .values().stream()
                .map(contractTerminationList -> {
                    final TerminationDto terminationDto = mapper.map(contractTerminationList.get(0), TerminationDto.class);
                    terminationDto.setContracts(mapper.mapAsList(contractTerminationList, TerminationContractDto.class));
                    return terminationDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void rejectContractTerminations(final List<TerminationDto> terminationsToReject) {
        if (isEmpty(terminationsToReject)) {
            return;
        }
        final Map<Long, TerminationContractDto> terminationsById = terminationsToReject.stream()
                .map(TerminationDto::getContracts)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(TerminationContractDto::getContractTerminationId, Function.identity()));

        final List<ContractTermination> contractTerminations = contractTerminationDao.findByIdsIn(terminationsById.keySet());
        contractTerminations.forEach(contractTermination -> {
            contractTermination.setState(REJECTED);
            contractTermination.setRemark(terminationsById.get(contractTermination.getId()).getRemark());
            contractTerminationDao.update(contractTermination);
        });
    }

    @Override
    public void postponeContractTerminations(final List<TerminationDto> terminationsToPostpone) {
        if (isEmpty(terminationsToPostpone)) {
            return;
        }
        final Map<Long, TerminationContractDto> terminationsById = terminationsToPostpone.stream()
                .flatMap(terminationDto -> terminationDto.getContracts().stream())
                .collect(Collectors.toMap(TerminationContractDto::getContractTerminationId, Function.identity()));

        final List<ContractTermination> contractTerminations = contractTerminationDao.findByIdsIn(terminationsById.keySet());
        contractTerminations.forEach(contractTermination -> {
            contractTermination.setState(POSTPONED);
            contractTermination.setTerminationDate(terminationsById.get(contractTermination.getId()).getTerminationDate());
            contractTermination.setRemark(terminationsById.get(contractTermination.getId()).getRemark());
            contractTerminationDao.update(contractTermination);
        });
    }

    @Override
    public void sendContractTerminations(final List<TerminationDto> terminationsToSend, final String loggedOperatorFullName) {
        if (isEmpty(terminationsToSend)) {
            return;
        }
        final Set<Long> terminationIds = terminationsToSend.stream()
                .map(TerminationDto::getContracts)
                .flatMap(Collection::stream)
                .map(TerminationContractDto::getContractTerminationId)
                .collect(Collectors.toSet());
        contractTerminationDao.processContractTerminations(loggedOperatorFullName, terminationIds);
        executorService.submit(() -> terminationsToSend.forEach(termination ->
                processContractTerminations.sendTermination(termination, loggedOperatorFullName)));
    }

    @Override
    public List<Long> findNotExistingContractTerminationIds(final Set<Long> contractTerminationIds) {
        if (isEmpty(contractTerminationIds)) {
            return Collections.emptyList();
        }
        final List<Long> unexistingContractIds = new ArrayList<>(contractTerminationIds);
        unexistingContractIds.removeAll(contractTerminationDao.findExistingContractTerminationIds(contractTerminationIds));
        return unexistingContractIds;
    }

    @Override
    public List<Long> findNotReadyToProcessContractTerminations(final Set<Long> contractTerminationIds) {
        if (isEmpty(contractTerminationIds)) {
            return Collections.emptyList();
        }
        final List<ContractTerminationState> statuses = new ArrayList<>(CLOSED_CONTRACT_TERMINATION_STATES);
        statuses.add(ContractTerminationState.WAITING);
        statuses.add(ContractTerminationState.FAILED);
        return contractTerminationDao.findByStatusesAndTerminationIds(statuses, contractTerminationIds);
    }

    @Override
    public Map<Long, Long> findContractIdToCompanyIds(final Set<Long> contractTerminationIds) {
        if (isEmpty(contractTerminationIds)) {
            return Collections.emptyMap();
        }
        return contractTerminationDao.findContractIdToCompanyIds(contractTerminationIds);
    }

    @Override
    public Map<Long, ContractTerminationType> findContractIdToContractType(final Set<Long> contractTerminationIds) {
        if (isEmpty(contractTerminationIds)) {
            return Collections.emptyMap();
        }
        return contractTerminationDao.findContractIdToContractType(contractTerminationIds);
    }

    @Override
    public void activateContractTermination(final long contractTerminationId, final ActivateContractTerminationDto activateContractTerminationDto) {
        contractTerminationDao.get(contractTerminationId)
                .filter(contractTermination -> contractTermination.getState().equals(ISSUED))
                .ifPresent(contractTermination -> {
                    contractTermination.setState(CONTRACT_ACTIVATED);
                    contractTermination.setActivationDate(activateContractTerminationDto.getActivationDate());
                    contractTermination.setResumptionCost(activateContractTerminationDto.getResumptionCost());
                    contractTermination.setRemark(activateContractTerminationDto.getRemark());
                    contractTerminationDao.update(contractTermination);
                });
    }
}
