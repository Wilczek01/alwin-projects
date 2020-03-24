package com.codersteam.alwin.core.service.impl.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerExecutionInfoDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.SchedulerExecutionInfoDao;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

import static com.codersteam.alwin.core.api.model.common.Page.emptyPage;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Dariusz Rackowski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class SchedulerExecutionInfoServiceImpl implements SchedulerExecutionInfoService {

    public static final String CLEANED_UP_BEFORE_SUCCESSFUL_FINISH = "Cleaned up before successful finish";

    private final AlwinMapper mapper;
    private final DateProvider dateProvider;
    private final SchedulerExecutionInfoDao schedulerExecutionDao;

    @Inject
    public SchedulerExecutionInfoServiceImpl(final AlwinMapper mapper, @SuppressWarnings("CdiInjectionPointsInspection") final DateProvider dateProvider,
                                             final SchedulerExecutionInfoDao schedulerExecutionDao) {
        this.mapper = mapper;
        this.dateProvider = dateProvider;
        this.schedulerExecutionDao = schedulerExecutionDao;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public SchedulerExecutionInfoDto schedulerExecutionStarted(final SchedulerTaskType schedulerType, final boolean manual) {
        final SchedulerExecutionInfo schedulerExecution = new SchedulerExecutionInfo();
        schedulerExecution.setStartDate(dateProvider.getCurrentDate());
        schedulerExecution.setType(schedulerType);
        schedulerExecution.setManual(manual);
        schedulerExecutionDao.create(schedulerExecution);

        return mapper.map(schedulerExecution, SchedulerExecutionInfoDto.class);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void schedulerExecutionFinished(final Long schedulerInformationId) {
        final int updated = schedulerExecutionDao.updateEndDate(schedulerInformationId, dateProvider.getCurrentDate());
        if (updated == 0) {
            throw new IllegalArgumentException(String.format("Unable to find scheduler execution with id %s", schedulerInformationId));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void schedulerExecutionFailed(final Long schedulerInformationId, final String errorMessage) {
        final int updated = schedulerExecutionDao.updateEndDateAndState(schedulerInformationId, dateProvider.getCurrentDate(), errorMessage);
        if (updated == 0) {
            throw new IllegalArgumentException(String.format("Unable to find scheduler execution with id %s", schedulerInformationId));
        }
    }

    @Override
    public Page<SchedulerExecutionInfoDto> findSchedulersExecutions(final int firstResult, final int maxResults) {
        final List<SchedulerExecutionInfo> schedulersExecutions = schedulerExecutionDao.findSchedulersExecutions(firstResult, maxResults);
        if (isEmpty(schedulersExecutions)) {
            return emptyPage();
        }
        final long count = schedulerExecutionDao.findSchedulersExecutionsCount();
        final List<SchedulerExecutionInfoDto> mappedSchedulersExecutions = mapper.mapAsList(schedulersExecutions, SchedulerExecutionInfoDto.class);
        return new Page<>(mappedSchedulersExecutions, count);
    }

    @Override
    public void cleanupRunningSchedulerExecutions() {
        final List<SchedulerExecutionInfo> notFinishedSchedulerExecutions = schedulerExecutionDao.findByEndDateIsNull();
        notFinishedSchedulerExecutions.forEach(schedulerExecution -> schedulerExecutionFailed(schedulerExecution.getId(), CLEANED_UP_BEFORE_SUCCESSFUL_FINISH));
    }

    @Override
    public List<SchedulerBatchProcessTypeDto> findAllBatchProcessTypes() {
        return SchedulerBatchProcessTypeDto.ALL_SCHEDULER_BATCH_PROCESSES;
    }

    @Override
    public boolean isSchedulerExecutionRunning(final SchedulerTaskType schedulerType) {
        return schedulerExecutionDao.findWithoutEndDateCount(schedulerType) > 0;
    }
}
