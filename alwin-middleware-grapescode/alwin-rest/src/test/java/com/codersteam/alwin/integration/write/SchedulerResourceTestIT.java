package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;
import com.codersteam.alwin.model.ErrorResponse;
import com.codersteam.alwin.testdata.SchedulerConfigurationTestData;
import com.google.gson.JsonObject;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dariusz Rackowski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-scheduler-configuration.json", "test-scheduler-execution.json"})
public class SchedulerResourceTestIT extends WriteTestBase {

    private static final int HOUR_TO_SET = 6;

    @Test
    public void shouldSetExecutionTimeForSchedulerConfiguration() throws Exception {
        // given
        final String loginToken = loggedInAdmin();
        // and
        final JsonObject emptyBody = new JsonObject();

        // when
        final int responseCode = checkHttpPutCode("schedulers/configurations/" + SchedulerBatchProcessType.BASE_SCHEDULE_TASKS + "/executionTime?hour=" + HOUR_TO_SET, emptyBody, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldReturnSchedulerConfigurationByProcessType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final String url = "schedulers/configurations/" + SchedulerBatchProcessType.BASE_SCHEDULE_TASKS;
        final SchedulerConfigurationDto schedulerConfiguration = checkHttpGet(url, loginToken, SchedulerConfigurationDto.class);

        // then
        assertThat(schedulerConfiguration).isNotNull();
        assertThat(schedulerConfiguration.getId()).isEqualTo(SchedulerConfigurationTestData.ID_1);
        assertThat(schedulerConfiguration.getHour()).isEqualTo(SchedulerConfigurationTestData.HOUR_1);
    }

    @Test
    public void shouldReturnAllBatchProcessTypes() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final SchedulerBatchProcessTypeDto[] response = checkHttpGet("schedulers/batchProcessTypes", loginToken, SchedulerBatchProcessTypeDto[].class);

        // then
        assertThat(response).hasSize(SchedulerBatchProcessTypeDto.ALL_SCHEDULER_BATCH_PROCESSES.size());
        assertThat(response)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(SchedulerBatchProcessTypeDto.ALL_SCHEDULER_BATCH_PROCESSES.toArray());
    }

    @Test
    public void shouldNotAcceptStartScheduleRequestForAlreadyRunningTask() throws IOException {
        // given
        final String loginToken = loggedInAdmin();
        // and
        final JsonObject emptyBody = new JsonObject();

        // when
        String url = "schedulers/batchProcessTypes/" + SchedulerBatchProcessType.BASE_SCHEDULE_TASKS + "/start";
        final ErrorResponse errorResponse = checkHttpPost(url, emptyBody, loginToken, ErrorResponse.class);

        // then
        assertThat(errorResponse.getMessage()).isEqualTo("Niektóre z zadania z grupy zadań typu BASE_SCHEDULE_TASKS są w trakcie wykonywania");
        assertThat(errorResponse.isReportingRequired()).isFalse();
    }

    @Test
    public void shouldAcceptStartScheduleRequestForNotRunningTask() throws IOException {
        // given
        final String loginToken = loggedInAdmin();
        // and
        final JsonObject emptyBody = new JsonObject();

        // when
        String url = "schedulers/batchProcessTypes/" + SchedulerBatchProcessType.GENERATE_ISSUES + "/start";
        final int code = checkHttpPostCode(url, emptyBody, loginToken);

        // then
        assertThat(code).isEqualTo(ACCEPTED.getStatusCode());
    }

    @Test
    public void shouldReturnAllScheduleConfigurations() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final SchedulerConfigurationDto[] response = checkHttpGet("schedulers/configurations", loginToken, SchedulerConfigurationDto[].class);

        // then
        assertThat(response).hasSize(1);
        assertThat(response)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(SchedulerConfigurationTestData.schedulerConfigurationDtoList().toArray());
    }

}
