package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.issue.Issue;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue3;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue4;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue10;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue21;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue5;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue6;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue7;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue8;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue9;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue19;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue20;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issue11;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issue12;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issue13;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issue14;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issue29;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.issue15;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ManagedIssuesTestData {

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_EXT_COMPANY_IDS_COUNT = 4L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_EXT_COMPANY_IDS_COUNT = 6L;
    public static final Long EXPECTED_ADMIN_BY_EXT_COMPANY_IDS_COUNT = 7L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_BALANCE_COUNT = 1L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_BALANCE_COUNT = 1L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_BALANCE_COUNT = 1L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_BALANCE_MIN_COUNT = 15L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_BALANCE_MIN_COUNT = 22L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_BALANCE_MIN_COUNT = 17L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_BALANCE_MAX_COUNT = 5L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_BALANCE_MAX_COUNT = 6L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_BALANCE_MAX_COUNT = 7L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_START_DATE_COUNT = 11L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_START_DATE_COUNT = 13L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_START_DATE_COUNT = 13L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_START_OF_START_DATE_COUNT = 12L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_START_OF_START_DATE_COUNT = 16L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_START_OF_START_DATE_COUNT = 16L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_END_OF_START_DATE_COUNT = 18L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_END_OF_START_DATE_COUNT = 24L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_END_OF_START_DATE_COUNT = 29L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_CONTACT_DATE_COUNT = 18L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_CONTACT_DATE_COUNT = 25L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_CONTACT_DATE_COUNT = 26L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_START_OF_CONTACT_DATE_COUNT = 19L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_START_OF_CONTACT_DATE_COUNT = 27L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_START_OF_CONTACT_DATE_COUNT = 32L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_END_OF_CONTACT_DATE_COUNT = 18L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_END_OF_CONTACT_DATE_COUNT = 25L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_END_OF_CONTACT_DATE_COUNT = 26L;

    public static final Long EXPECTED_MANAGED_ACTIVE_ISSUES_BY_OPERATOR_COUNT = 9L;
    public static final Long EXPECTED_MANAGED_ISSUES_BY_OPERATOR_COUNT = 12L;
    public static final Long EXPECTED_ADMIN_ISSUES_BY_OPERATOR_COUNT = 13L;

    public static final Long EXPECTED_UNASSIGNED_MANAGED_ACTIVE_ISSUES_COUNT = 9L;
    public static final Long EXPECTED_UNASSIGNED_MANAGED_ISSUES_COUNT = 14L;
    public static final Long EXPECTED_UNASSIGNED_ADMIN_ISSUES_COUNT = 18L;

    public static final Long EXPECTED_MANAGED_NEW_AND_IN_PROGRESS_ISSUES_COUNT = 19L;
    public static final Long EXPECTED_MANAGED_ISSUES_IN_ALL_STATES_COUNT = 19L;
    public static final Long EXPECTED_ADMIN_ISSUES_IN_ALL_STATES_COUNT = 22L;

    public static List<Issue> expectedManagedActiveIssuesByBalance() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedManagedActiveIssuesByExtCompanyIds() {
        return asList(issue5(), issue9(), issue13(), issue15());
    }

    public static List<Issue> expectedMangedIssuesSortedByBalanceStartDescending() {
        return asList(issue1(), issue4(), issue21());
    }

    public static List<Issue> expectedMangedIssuesSortedByCurrentBalanceDescending() {
        return asList(issue21(), issue29(), issue1());
    }

    public static List<Issue> expectedMangedIssuesSortedByCustomerNameAscending() {
        return asList(issue29(), issue21(), issue11());
    }

    public static List<Issue> expectedMangedIssuesSortedByExpirationDateAscending() {
        return asList(issue1(), issue2(), issue29());
    }

    public static List<Issue> expectedMangedIssuesSortedByPaymentsAscending() {
        return asList(issue1(), issue6(), issue7());
    }

    public static List<Issue> expectedMangedIssuesSortedByRpbAscending() {
        return asList(issue4(), issue10(), issue14());
    }

    public static List<Issue> expectedMangedIssuesSortedByStartDateAscending() {
        return asList(issue1(), issue2(), issue29());
    }

    public static List<Issue> expectedMangedIssuesSortedByStateAscending() {
        return asList(issue1(), issue2(), issue4());
    }

    public static List<Issue> expectedMangedIssuesSortedByTypeNameDescending() {
        return asList(issue1(), issue2(), issue4());
    }

    public static List<Issue> expectedManagedIssuesByExtCompanyIds() {
        return asList(issue5(), issue9(), issue13(), issue15(), issue20());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByTags() {
        return asList(issue1(), issue2());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByPartOfCompanyName() {
        return asList(issue2(), issue4());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByInvoiceContractNo() {
        return singletonList(issue1());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByPartOfInvoiceContractNo() {
        return asList(issue4(), issue21());
    }

    public static List<Issue> expectedActiveIssuesFilteredByPartOfCompanyName() {
        return asList(issue2(), issue3());
    }

    public static List<Issue> expectedActiveIssuesFilteredByInvoiceContractNo() {
        return singletonList(issue1());
    }

    public static List<Issue> expectedActiveIssuesFilteredByPartOfInvoiceContractNo() {
        return asList(issue4(), issue21());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByPersonPesel() {
        return singletonList(issue13());
    }

    public static List<Issue> expectedActiveIssuesFilteredByPersonPesel() {
        return singletonList(issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByLoweredPersonName() {
        return singletonList(issue13());
    }

    public static List<Issue> expectedActiveIssuesFilteredByLoweredPersonName() {
        return singletonList(issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByRevertedLoweredPersonName() {
        return singletonList(issue13());
    }

    public static List<Issue> expectedActiveIssuesFilteredByRevertedLoweredPersonName() {
        return singletonList(issue13());
    }

    public static List<Issue> expectedAllIssuesFilteredByContactName() {
        return asList(issue1(), issue13(), issue19());
    }

    public static List<Issue> expectedAllIssuesFilteredByContactRevertedName() {
        return asList(issue1(), issue13(), issue19());
    }

    public static List<Issue> expectedAllIssuesFilteredByDocumentEmail() {
        return asList(issue1(), issue13(), issue19());
    }

    public static List<Issue> expectedAllIssuesFilteredByOfficeEmail() {
        return asList(issue1(), issue13(), issue19());
    }

    public static List<Issue> expectedAllIssuesFilteredByContactEmail() {
        return asList(issue1(), issue13(), issue19());
    }

    public static List<Issue> expectedAllIssuesFilteredByContactPhone() {
        return asList(issue1(), issue13(), issue19());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByContactName() {
        return asList(issue1(), issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByContactRevertedName() {
        return asList(issue1(), issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByContactDocumentEmail() {
        return asList(issue1(), issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByContactOfficeEmail() {
        return asList(issue1(), issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByContactEmail() {
        return asList(issue1(), issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByContactPhone() {
        return asList(issue1(), issue13());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByCompanyNip() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedActiveIssuesFilteredByCompanyNip() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedManagedActiveIssuesFilteredByCompanyRegon() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedActiveIssuesFilteredByCompanyRegon() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedManagedIssuesByBalance() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedAdminIssuesByBalance() {
        return singletonList(issue6());
    }

    public static List<Issue> expectedAdminIssuesByExtCompanyIds() {
        return asList(issue3(), issue5(), issue9(), issue13(), issue15());
    }

    public static List<Issue> expectedManagedActiveIssuesByTotalCurrentBalancePLNMin() {
        return asList(issue6(), issue7(), issue8(), issue9(), issue10());
    }

    public static List<Issue> expectedManagedIssuesByTotalCurrentBalancePLNMin() {
        return asList(issue6(), issue7(), issue8(), issue9(), issue10());
    }

    public static List<Issue> expectedAdminIssuesByTotalCurrentBalancePLNMin() {
        return asList(issue6(), issue7(), issue8(), issue9(), issue10());
    }

    public static List<Issue> expectedManagedActiveIssuesByTotalCurrentBalancePLNMax() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesByTotalCurrentBalancePLNMax() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesByTotalCurrentBalancePLNMax() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static List<Issue> expectedManagedActiveIssuesByStartDate() {
        return asList(issue7(), issue8(), issue9(), issue10(), issue11());
    }

    public static List<Issue> expectedManagedIssuesByStartDate() {
        return asList(issue7(), issue8(), issue9(), issue10(), issue11());
    }

    public static List<Issue> expectedAdminIssuesByStartDate() {
        return asList(issue7(), issue8(), issue9(), issue10(), issue11());
    }

    public static List<Issue> expectedManagedActiveIssuesByStartOfStartDate() {
        return asList(issue7(), issue8(), issue9(), issue10(), issue11());
    }

    public static List<Issue> expectedManagedIssuesByStartOfStartDate() {
        return asList(issue7(), issue8(), issue9(), issue10(), issue11());
    }

    public static List<Issue> expectedAdminIssuesByStartOfStartDate() {
        return asList(issue7(), issue8(), issue9(), issue10(), issue11());
    }

    public static List<Issue> expectedManagedActiveIssuesByEndOfStartDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesByEndOfStartDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesByEndOfStartDate() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static List<Issue> expectedManagedActiveIssuesByContactDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesByContactDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesByContactDate() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static List<Issue> expectedManagedActiveIssuesByStartOfContactDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesByStartOfContactDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesByStartOfContactDate() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static List<Issue> expectedManagedActiveIssuesByEndOfContactDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesByEndOfContactDate() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesByEndOfContactDate() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static List<Issue> expectedManagedActiveIssuesByOperator() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesByOperator() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesByOperator() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static List<Issue> expectedUnassignedManagedActiveIssues() {
        return asList(issue11(), issue12(), issue13(), issue14(), issue15());
    }

    public static List<Issue> expectedUnassignedManagedIssues() {
        return asList(issue11(), issue12(), issue13(), issue14(), issue15());
    }

    public static List<Issue> expectedUnassignedAdminIssues() {
        return asList(issue11(), issue12(), issue13(), issue14(), issue15());
    }

    public static List<Issue> expectedManagedNewAndInProgressIssues() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedManagedIssuesInAllStates() {
        return asList(issue1(), issue2(), issue4(), issue5(), issue6());
    }

    public static List<Issue> expectedAdminIssuesInAllStates() {
        return asList(issue1(), issue2(), issue3(), issue4(), issue5());
    }

    public static Predicate<Issue> expectedPriority(final Integer priority) {
        return issue -> Objects.equals(issue.getPriority(), priority);
    }

}
