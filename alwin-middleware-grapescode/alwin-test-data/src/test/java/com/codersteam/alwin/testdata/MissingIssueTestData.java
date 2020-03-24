package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.MissingIssueDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_NUMBER_1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_NUMBER_2;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public class MissingIssueTestData {

    public static final MissingIssueDto MISSING_ISSUE_DTO_1 = new MissingIssueDto(EXT_COMPANY_ID_1, INVOICE_NUMBER_1, new BigDecimal("-100.00"));
    public static final MissingIssueDto MISSING_ISSUE_DTO_2 = new MissingIssueDto(EXT_COMPANY_ID_2, INVOICE_NUMBER_2, new BigDecimal("-123.02"));

    public static final List<MissingIssueDto> MISSING_ISSUE_DTOS = Arrays.asList(MISSING_ISSUE_DTO_1, MISSING_ISSUE_DTO_2);
}
