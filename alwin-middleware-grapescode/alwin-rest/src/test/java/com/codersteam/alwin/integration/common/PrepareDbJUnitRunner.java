package com.codersteam.alwin.integration.common;

import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

public class PrepareDbJUnitRunner extends HttpRestTestBase {

    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json", "test-contract-out-of-service.json",
            "test-customer-out-of-service.json", "test-default-activity.json", "test-formal-debt-collection-invoice.json", "test-demand-for-payment-type.json",
            "test-demand-for-payment.json", "test-issue-termination-request.json", "test-issue-type-configuration.json", "test-last-data-sync.json",
            "test-message-template.json", "test-notification.json", "test-scheduler-execution.json", "test-issue-type-activity-type.json",
            "test-contract-termination.json", "test-formal-debt-collection-customer-out-of-service.json",
            "test-formal-debt-collection-contract-out-of-service.json"})
    @CleanupUsingScript(value = "cleanup.sql", phase = TestExecutionPhase.BEFORE)
    @Test
    public void setupDb() {
    }
}
