package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;
import com.codersteam.alwin.jpa.scheduler.SchedulerConfiguration;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;

public class SchedulerConfigurationTestData {

    public static final long ID_1 = 1;
    public static final SchedulerBatchProcessType PROCESS_1 = SchedulerBatchProcessType.BASE_SCHEDULE_TASKS;
    public static final SchedulerBatchProcessTypeDto PROCESS_1_DTO = SchedulerBatchProcessTypeDto.BASE_SCHEDULE_TASKS;
    public static final Date UPDATE_DATE_1 = parse("2018-05-22 12:00:00.000000");
    public static final int HOUR_1 = 3;

    public static final int CHANGED_HOUR_1 = 1;
    public static final Date CHANGED_UPDATE_DATE_1 = parse("2018-05-22 23:00:00.000000");

    public static List<SchedulerConfiguration> schedulerConfigurationList() {
        return Collections.singletonList(schedulerConfiguration1());
    }

    public static List<SchedulerConfigurationDto> schedulerConfigurationDtoList() {
        return Collections.singletonList(schedulerConfigurationDto1());
    }

    public static SchedulerConfiguration schedulerConfiguration1() {
        return schedulerConfiguration(ID_1, PROCESS_1, UPDATE_DATE_1, HOUR_1);
    }

    public static SchedulerConfigurationDto schedulerConfigurationDto1() {
        return schedulerConfigurationDto(ID_1, PROCESS_1_DTO, UPDATE_DATE_1, HOUR_1);
    }

    private static SchedulerConfiguration schedulerConfiguration(final Long id,
                                                                 final SchedulerBatchProcessType batchProcess,
                                                                 final Date updateDate,
                                                                 final int hour) {
        final SchedulerConfiguration schedulerExecution = new SchedulerConfiguration();
        schedulerExecution.setId(id);
        schedulerExecution.setBatchProcess(batchProcess);
        schedulerExecution.setUpdateDate(updateDate);
        schedulerExecution.setHour(hour);
        return schedulerExecution;
    }

    private static SchedulerConfigurationDto schedulerConfigurationDto(final Long id,
                                                                       final SchedulerBatchProcessTypeDto process,
                                                                       final Date updateDate,
                                                                       final int hour) {
        final SchedulerConfigurationDto schedulerExecution = new SchedulerConfigurationDto();
        schedulerExecution.setId(id);
        schedulerExecution.setBatchProcess(process);
        schedulerExecution.setUpdateDate(updateDate);
        schedulerExecution.setHour(hour);
        return schedulerExecution;
    }

}
