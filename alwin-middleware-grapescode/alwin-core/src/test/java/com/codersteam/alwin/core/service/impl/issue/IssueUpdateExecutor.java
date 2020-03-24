package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tomasz Sliwinski
 */
public final class IssueUpdateExecutor {

    private IssueUpdateExecutor() {
    }

    public static void runParallelBalanceUpdateForIssue(final IssueBalanceUpdaterService service, final Long issueId, final int threadCount) throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        final List<Callable<Void>> updateIssueBalanceTasks = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            updateIssueBalanceTasks.add(
                    () -> {
                        service.getIssueWithUpdatedBalance(issueId);
                        return null;
                    }
            );
        }

        executor.invokeAll(updateIssueBalanceTasks);
    }
}
