package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerExecutionInfoDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerTaskTypeDto;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;

public class SchedulerExecutionTestData {

    public static final long ID_1 = 20_101;
    public static final Date START_DATE_1 = parse("2018-05-06 01:00:00.000000");
    public static final Date END_DATE_1 = parse("2018-05-06 06:00:00.000000");
    public static final String STATE_1 = null;
    public static final SchedulerTaskType TYPE_1 = SchedulerTaskType.GENERATE_ISSUES;
    public static final boolean MANUAL_1 = false;

    public static final long ID_2 = 20_102;
    public static final Date START_DATE_2 = parse("2018-05-06 12:00:00.000000");
    public static final Date END_DATE_2 = parse("2018-05-06 13:00:00.000000");
    public static final String STATE_2 = null;
    public static final SchedulerTaskType TYPE_2 = SchedulerTaskType.UPDATE_COMPANY_DATA;
    public static final SchedulerTaskTypeDto TYPE_2_DTO = SchedulerTaskTypeDto.UPDATE_COMPANY_DATA;
    public static final boolean MANUAL_2 = true;

    public static final long ID_3 = 20_103;
    public static final Date START_DATE_3 = parse("2018-05-07 06:00:00.000000");
    public static final Date END_DATE_3 = parse("2018-05-07 06:05:00.000000");
    public static final String STATE_3 = "unknown error occurred";
    public static final SchedulerTaskType TYPE_3 = SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL;
    public static final boolean MANUAL_3 = false;

    public static final long ID_4 = 20_104;
    public static final Date START_DATE_4 = parse("2018-05-09 05:00:00.000000");
    public static final Date END_DATE_4 = parse("2018-05-09 07:35:40.000000");
    public static final String STATE_4 = "To zadanie wykonało się z bardzo długim komentarzem, np. została podana jakaś bardzo długa nazwa wyjątku: javax.resource.ResourceException: Unable to get managed connection";
    public static final SchedulerTaskType TYPE_4 = SchedulerTaskType.UPDATE_ISSUES_BALANCE;
    public static final SchedulerTaskTypeDto TYPE_4_DTO = SchedulerTaskTypeDto.UPDATE_ISSUES_BALANCE;
    public static final boolean MANUAL_4 = true;

    public static final long CREATE_TEST_ID_5 = 20_505;
    public static final Date CREATE_TEST_START_DATE_5 = parse("2018-05-06 01:00:00.000000");
    public static final SchedulerTaskType CREATE_TEST_TYPE_5 = SchedulerTaskType.GENERATE_ISSUES;
    public static final boolean CREATE_TEST_MANUAL_5 = false;

    public static final Date STARTED_TEST_START_DATE_5 = parse("2018-05-11 12:00:00.000000");
    public static final boolean STARTED_TEST_MANUAL_5 = false;

    public static final long ID_6 = 20_106;
    public static final Date START_DATE_6 = parse("2018-05-09 05:15:00.000000");
    public static final Date END_DATE_6 = null;
    public static final String STATE_6 = null;
    public static final SchedulerTaskType TYPE_6 = SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT;
    public static final SchedulerTaskTypeDto TYPE_6_DTO = SchedulerTaskTypeDto.UPDATE_COMPANIES_INVOLVEMENT;
    public static final boolean MANUAL_6 = false;

    public static List<SchedulerExecutionInfo> schedulerExecutionsFirstPage() {
        return asList(schedulerExecution6(), schedulerExecution4());
    }

    public static Page<SchedulerExecutionInfoDto> schedulerExecutionsFirstPageDto() {
        return new Page<>(asList(schedulerExecution6Dto(), schedulerExecution4Dto()), 4);
    }

    public static List<SchedulerExecutionInfo> schedulerExecutionsSecondPage() {
        return asList(schedulerExecution3(), schedulerExecution2());
    }

    public static SchedulerExecutionInfo schedulerExecution1() {
        return schedulerExecution(ID_1, START_DATE_1, END_DATE_1, STATE_1, TYPE_1, MANUAL_1);
    }

    public static SchedulerExecutionInfo schedulerExecution2() {
        return schedulerExecution(ID_2, START_DATE_2, END_DATE_2, STATE_2, TYPE_2, MANUAL_2);
    }

    public static SchedulerExecutionInfo schedulerExecution3() {
        return schedulerExecution(ID_3, START_DATE_3, END_DATE_3, STATE_3, TYPE_3, MANUAL_3);
    }

    public static SchedulerExecutionInfo schedulerExecution4() {
        return schedulerExecution(ID_4, START_DATE_4, END_DATE_4, STATE_4, TYPE_4, MANUAL_4);
    }

    public static SchedulerExecutionInfoDto schedulerExecution4Dto() {
        return schedulerExecutionDto(ID_4, START_DATE_4, END_DATE_4, STATE_4, TYPE_4_DTO, MANUAL_4);
    }

    public static SchedulerExecutionInfo schedulerExecution6() {
        return schedulerExecution(ID_6, START_DATE_6, END_DATE_6, STATE_6, TYPE_6, MANUAL_6);
    }

    public static SchedulerExecutionInfoDto schedulerExecution6Dto() {
        return schedulerExecutionDto(ID_6, START_DATE_6, END_DATE_6, STATE_6, TYPE_6_DTO, MANUAL_6);
    }

    private static SchedulerExecutionInfo schedulerExecution(final Long id,
                                                             final Date startDate,
                                                             final Date endDate,
                                                             final String state,
                                                             final SchedulerTaskType type,
                                                             final boolean manual) {
        final SchedulerExecutionInfo schedulerExecution = new SchedulerExecutionInfo();
        schedulerExecution.setId(id);
        schedulerExecution.setStartDate(startDate);
        schedulerExecution.setEndDate(endDate);
        schedulerExecution.setState(state);
        schedulerExecution.setType(type);
        schedulerExecution.setManual(manual);
        return schedulerExecution;
    }

    private static SchedulerExecutionInfoDto schedulerExecutionDto(final Long id,
                                                                   final Date startDate,
                                                                   final Date endDate,
                                                                   final String state,
                                                                   final SchedulerTaskTypeDto type,
                                                                   final boolean manual) {
        final SchedulerExecutionInfoDto schedulerExecution = new SchedulerExecutionInfoDto();
        schedulerExecution.setId(id);
        schedulerExecution.setStartDate(startDate);
        schedulerExecution.setEndDate(endDate);
        schedulerExecution.setState(state);
        schedulerExecution.setType(type);
        schedulerExecution.setManual(manual);
        return schedulerExecution;
    }

    public static SchedulerExecutionInfoDto expectedCreatedSchedulerExecutionDto1() {
        final SchedulerExecutionInfoDto schedulerExecution = new SchedulerExecutionInfoDto();
        schedulerExecution.setId(CREATE_TEST_ID_5);
        schedulerExecution.setStartDate(CREATE_TEST_START_DATE_5);
        schedulerExecution.setType(SchedulerTaskTypeDto.valueOf(CREATE_TEST_TYPE_5.name()));
        schedulerExecution.setManual(CREATE_TEST_MANUAL_5);
        return schedulerExecution;
    }

    public static SchedulerExecutionInfoDto startedSchedulerExecution(Long id, SchedulerTaskType schedulerType) {
        final SchedulerExecutionInfoDto schedulerExecution = new SchedulerExecutionInfoDto();
        schedulerExecution.setId(id);
        schedulerExecution.setStartDate(STARTED_TEST_START_DATE_5);
        schedulerExecution.setType(SchedulerTaskTypeDto.valueOf(schedulerType.name()));
        schedulerExecution.setManual(STARTED_TEST_MANUAL_5);
        return schedulerExecution;
    }

}
