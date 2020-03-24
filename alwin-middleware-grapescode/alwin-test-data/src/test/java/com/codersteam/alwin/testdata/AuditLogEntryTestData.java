package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import com.codersteam.alwin.jpa.issue.Issue;

import java.math.BigDecimal;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue4WithActivity;
import static com.codersteam.alwin.testdata.PersonTestData.person1;
import static com.codersteam.alwin.testdata.UserRevEntityTestData.userRevEntity1;
import static org.hibernate.envers.RevisionType.ADD;
import static org.hibernate.envers.RevisionType.DEL;
import static org.hibernate.envers.RevisionType.MOD;

/**
 * @author Tomasz Sliwinski
 */
public class AuditLogEntryTestData {

    public static AuditLogEntry firstPersonAuditLogEntry() {
        return new AuditLogEntry<>(person1(), userRevEntity1(), ADD);
    }

    public static AuditLogEntry firstIssueAuditLogEntry() {
        return new AuditLogEntry<>(issue1(), userRevEntity1(), MOD);
    }

    public static AuditLogEntry secondIssueAuditLogEntry() {
        return new AuditLogEntry<>(issue2(), userRevEntity1(), ADD);
    }

    public static AuditLogEntry fourIssueWithActivityAuditLogEntry() {
        return new AuditLogEntry<>(issue4WithActivity(), userRevEntity1(), ADD);
    }

    public static AuditLogEntry secondIssueAuditLogEntryWithUpdatedBalance() {
        final Issue issue = issue2();
        final BigDecimal modifiedCurrentBalancePLN = issue.getCurrentBalancePLN().add(BigDecimal.TEN);
        issue.setCurrentBalancePLN(modifiedCurrentBalancePLN);
        return new AuditLogEntry<>(issue, userRevEntity1(), MOD);
    }

    public static AuditLogEntry fourIssueWithActivityAuditLogEntryWithUpdatedBalance() {
        final Issue issue = issue4WithActivity();
        final BigDecimal modifiedCurrentBalancePLN = issue.getCurrentBalancePLN().add(BigDecimal.TEN);
        issue.setCurrentBalancePLN(modifiedCurrentBalancePLN);
        return new AuditLogEntry<>(issue, userRevEntity1(), MOD);
    }

    public static AuditLogEntry secondIssueAuditLogEntryWithRemovedContract() {
        final Issue issue = issue2();
        issue.setContract(null);
        return new AuditLogEntry<>(issue, userRevEntity1(), DEL);
    }


}
