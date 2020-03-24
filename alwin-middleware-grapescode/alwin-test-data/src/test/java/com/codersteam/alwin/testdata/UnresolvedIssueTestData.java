package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.UnresolvedIssueDto;
import com.codersteam.alwin.jpa.issue.Issue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2;
import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings("WeakerAccess")
public class UnresolvedIssueTestData {

    public static List<UnresolvedIssueDto> unresolvedIssueDtos() {
        return asList(unresolvedIssueDto1(), unresolvedIssueDto2());
    }

    public static UnresolvedIssueDto unresolvedIssueDto1() {
        final Issue issue = issue1();
        return unresolvedIssueDto(new HashSet<>(asList("TEST 12/34/56", "TEST 23/45/56")), issue.getCurrentBalancePLN(), issue.getBalanceStartPLN(),
                issue.getPaymentsPLN(), issue.getId(), issue.getRpbPLN(), issue.getRpbEUR(), issue.getBalanceStartEUR(), issue.getCurrentBalanceEUR(),
                issue.getPaymentsEUR());
    }

    public static UnresolvedIssueDto unresolvedIssueDto2() {
        final Issue issue = issue2();
        return unresolvedIssueDto(new HashSet<>(asList("TEST 34/55/78", "TEST 56/78/90")), issue.getCurrentBalancePLN(), issue.getBalanceStartPLN(),
                issue.getPaymentsPLN(), issue.getId(), issue.getRpbPLN(), issue.getRpbEUR(), issue.getBalanceStartEUR(), issue.getCurrentBalanceEUR(),
                issue.getPaymentsEUR());
    }

    public static UnresolvedIssueDto unresolvedIssueDto(final Set<String> extContractsNumbers, final BigDecimal currentBalancePLN, final BigDecimal
            balanceStartPLN, final BigDecimal paymentsPLN, final Long issueId, final BigDecimal rpbPLN, final BigDecimal rpbEUR,
                                                        final BigDecimal balanceStartEUR, final BigDecimal currentBalanceEUR, final BigDecimal paymentsEUR) {
        final UnresolvedIssueDto unresolvedIssueDto = new UnresolvedIssueDto();
        unresolvedIssueDto.setExtContractsNumbers(extContractsNumbers);
        unresolvedIssueDto.setCurrentBalancePLN(currentBalancePLN);
        unresolvedIssueDto.setBalanceStartPLN(balanceStartPLN);
        unresolvedIssueDto.setPaymentsPLN(paymentsPLN);
        unresolvedIssueDto.setIssueId(issueId);
        unresolvedIssueDto.setRpbPLN(rpbPLN);
        unresolvedIssueDto.setRpbEUR(rpbEUR);
        unresolvedIssueDto.setBalanceStartEUR(balanceStartEUR);
        unresolvedIssueDto.setCurrentBalanceEUR(currentBalanceEUR);
        unresolvedIssueDto.setPaymentsEUR(paymentsEUR);
        return unresolvedIssueDto;
    }
}
