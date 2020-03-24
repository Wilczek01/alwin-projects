package com.codersteam.alwin.testdata.criteria;


import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.testdata.OperatorTestData;
import com.codersteam.alwin.testdata.TagTestData;
import com.codersteam.alwin.testdata.TestDateUtils;

import java.math.BigDecimal;
import java.util.Date;

import static com.codersteam.alwin.testdata.CompanyTestData.NIP_9;
import static com.codersteam.alwin.testdata.CompanyTestData.REGON_9;
import static com.codersteam.alwin.testdata.CompanyTestData.issueFilterExtCompanyIds;
import static com.codersteam.alwin.testdata.ContactDetailTestData.CONTACT_DETAIL_CONTACT_10;
import static com.codersteam.alwin.testdata.ContactDetailTestData.CONTACT_DETAIL_CONTACT_11;
import static com.codersteam.alwin.testdata.ContactDetailTestData.CONTACT_DETAIL_CONTACT_12;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_3;
import static com.codersteam.alwin.testdata.PersonTestData.LAST_NAME_1;
import static com.codersteam.alwin.testdata.PersonTestData.NAME_1;
import static com.codersteam.alwin.testdata.PersonTestData.PESEL_1;
import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public class IssuesSearchCriteriaTestData {

    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS_5 = 5;
    private static final int MAX_RESULTS_3 = 3;

    private static final int MAX_RESULTS_2 = 2;
    private static final String PART_OF_COMPANY_NAME = "ma 99";
    private static final String PART_OF_INVOICE_CONTRACT_NO = "001/";

    private static final String PART_OF_CONTACT_PHONE = "1234567";
    private static final String CONTACT_PERSON_NAME = "jan Kochanowski";
    private static final String REVERTED_CONTACT_PERSON_NAME = "Kochanowski Jan";

    private static final Date START_START_DATE = TestDateUtils.parse("2023-07-10 00:00:00.000000");
    private static final Date END_START_DATE = TestDateUtils.parse("2023-07-10 00:00:00.000000");

    private static final Date START_CONTACT_DATE = TestDateUtils.parse("2017-07-09 00:00:00.000000");
    private static final Date END_CONTACT_DATE = TestDateUtils.parse("2017-07-11 00:00:00.000000");

    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_MIN = new BigDecimal("400000.00");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_MAX = new BigDecimal("450000.00");


    public static IssuesSearchCriteria searchCriteriaByBalance() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setTotalCurrentBalancePLNMin(TOTAL_CURRENT_BALANCE_PLN_MIN);
        searchCriteria.setTotalCurrentBalancePLNMax(TOTAL_CURRENT_BALANCE_PLN_MAX);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByTag() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setTagIds(asList(TagTestData.ID_1, TagTestData.ID_2));
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByPartOfCompanyName() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_2);
        searchCriteria.setCompanyName(PART_OF_COMPANY_NAME);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByInvoiceContractNo() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_2);
        searchCriteria.setInvoiceContractNo(CONTRACT_NUMBER_INVOICE_1);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByPartOfInvoiceContractNo() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setInvoiceContractNo(PART_OF_INVOICE_CONTRACT_NO);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByPersonPesel() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setPersonPesel(PESEL_1);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByLoweredPersonName() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setPersonName(String.format("%s %s", NAME_1, LAST_NAME_1).toLowerCase());
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByRevertedLoweredPersonName() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setPersonName(String.format("%s %s", LAST_NAME_1, NAME_1).toLowerCase());
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByCompanyNip() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_2);
        searchCriteria.setNip(NIP_9);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByContactPhone() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setContactPhone(PART_OF_CONTACT_PHONE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByContactName() {
        return getIssuesSearchCriteriaByContactName(CONTACT_PERSON_NAME);
    }

    public static IssuesSearchCriteria searchCriteriaByContactRevertedName() {
        return getIssuesSearchCriteriaByContactName(REVERTED_CONTACT_PERSON_NAME);
    }

    private static IssuesSearchCriteria getIssuesSearchCriteriaByContactName(final String contactPersonName) {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setContactName(contactPersonName);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByContactDocumentEmail() {
        return getIssuesSearchCriteriaByEmail(CONTACT_DETAIL_CONTACT_11);
    }

    public static IssuesSearchCriteria searchCriteriaByContactOfficeEmail() {
        return getIssuesSearchCriteriaByEmail(CONTACT_DETAIL_CONTACT_12);
    }

    public static IssuesSearchCriteria searchCriteriaByContactEmail() {
        return getIssuesSearchCriteriaByEmail(CONTACT_DETAIL_CONTACT_10);
    }

    private static IssuesSearchCriteria getIssuesSearchCriteriaByEmail(final String contactDetailContact10) {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        searchCriteria.setContactEmail(contactDetailContact10);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByCompanyRegon() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_2);
        searchCriteria.setRegon(REGON_9);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByPriority(final Integer priority) {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setPriority(priority);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByTotalCurrentBalancePLNMin() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setTotalCurrentBalancePLNMin(TOTAL_CURRENT_BALANCE_PLN_MIN);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByExtCompanyIds() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setExtCompanyIds(issueFilterExtCompanyIds());
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByTotalCurrentBalancePLNMax() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setTotalCurrentBalancePLNMax(TOTAL_CURRENT_BALANCE_PLN_MAX);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByStartDate() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setStartStartDate(START_START_DATE);
        searchCriteria.setEndStartDate(END_START_DATE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByStartOfStartDate() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setStartStartDate(START_START_DATE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByEndOfStartDate() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setEndStartDate(END_START_DATE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByContactDate() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setStartContactDate(START_CONTACT_DATE);
        searchCriteria.setEndContactDate(END_CONTACT_DATE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByStartOfContactDate() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setStartContactDate(START_CONTACT_DATE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByEndOfContactDate() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setEndContactDate(END_CONTACT_DATE);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByOperator() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setOperatorIds(asList(OperatorTestData.OPERATOR_ID_1, OPERATOR_ID_3));
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByNotAssigned() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setUnassigned(true);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByIssueOpenStates() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_5);
        searchCriteria.setStates(asList(IssueStateDto.NEW.getKey(), IssueStateDto.IN_PROGRESS.getKey()));
        return searchCriteria;
    }

    public static IssuesSearchCriteria emptySearchCriteria() {
        final IssuesSearchCriteria searchCriteria = new IssuesSearchCriteria();
        searchCriteria.setFirstResult(FIRST_RESULT);
        searchCriteria.setMaxResults(MAX_RESULTS_3);
        return searchCriteria;
    }

    public static IssuesSearchCriteria searchCriteriaByIssueOpenStatesAndExtCompanyIds() {
        final IssuesSearchCriteria searchCriteria = searchCriteriaByIssueOpenStates();
        searchCriteria.setExtCompanyIds(issueFilterExtCompanyIds());
        return searchCriteria;
    }
}
