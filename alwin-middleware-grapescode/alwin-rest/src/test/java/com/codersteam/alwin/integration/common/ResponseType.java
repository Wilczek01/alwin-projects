package com.codersteam.alwin.integration.common;

import com.codersteam.aida.core.api.model.InvoiceEntryDto;
import com.codersteam.aida.core.api.model.InvoiceSettlementDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeByStateDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.customer.AddressDto;
import com.codersteam.alwin.core.api.model.customer.AddressTypeDto;
import com.codersteam.alwin.core.api.model.customer.DocTypeDto;
import com.codersteam.alwin.core.api.model.customer.PhoneNumberDto;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.message.SmsTemplateDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeDto;
import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.core.api.model.tag.TagIconDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Typ odpowiedzi serwera do testów rest
 *
 * @author Tomasz Sliwinski
 */
public final class ResponseType {

    public static final Type ACTIVITY_LIST = new TypeToken<List<ActivityDto>>() {
    }.getType();

    public static final Type INVOICE_ENTRY_LIST = new TypeToken<List<InvoiceEntryDto>>() {
    }.getType();

    public static final Type TAG_LIST = new TypeToken<List<TagDto>>() {
    }.getType();

    public static final Type TAG_ICON_LIST = new TypeToken<List<TagIconDto>>() {
    }.getType();

    public static final Type ISSUE = new TypeToken<IssueDto>() {
    }.getType();

    public static final Type ACTIVITY_TYPE_LIST = new TypeToken<List<ActivityTypeDto>>() {
    }.getType();

    public static final Type INVOICE_SETTLEMENT_TYPE_LIST = new TypeToken<List<InvoiceSettlementDto>>() {
    }.getType();

    public static final Type ISSUE_PAGE = new TypeToken<Page<IssueDto>>() {
    }.getType();

    public static final Type ADDRESS_LIST = new TypeToken<List<AddressDto>>() {
    }.getType();

    public static final Type ADDRESS_TYPE_LIST = new TypeToken<List<AddressTypeDto>>() {
    }.getType();

    public static final Type ISSUE_TYPE_WITH_OPERATOR_TYPE_LIST = new TypeToken<List<IssueTypeWithOperatorTypesDto>>() {
    }.getType();

    public static final Type DOC_TYPE_LIST = new TypeToken<List<DocTypeDto>>() {
    }.getType();

    public static final Type ACTIVITY_PAGE = new TypeToken<Page<ActivityDto>>() {
    }.getType();

    public static final Type SMS_TEMPLATE_LIST = new TypeToken<List<SmsTemplateDto>>() {
    }.getType();

    public static final Type ISSUE_TYPE_CONFIGURATION_LIST = new TypeToken<List<IssueTypeConfigurationDto>>() {
    }.getType();

    public static final Type OPERATED_ISSUE = new TypeToken<OperatedIssueDto>() {
    }.getType();

    public static final Type ACTIVITY_TYPE_BY_STATE = new TypeToken<ActivityTypeByStateDto>() {
    }.getType();

    public static final Type ALL_WALLETS_TYPE = new TypeToken<AllWallets>() {
    }.getType();

    public static final Type DEMANDS_FOR_PAYMENT_PAGE = new TypeToken<Page<DemandForPaymentDto>>() {
    }.getType();

    public static final Type TERMINATION_CONTRACT_PAGE = new TypeToken<Page<TerminationDto>>() {
    }.getType();

    public static final Type POSTAL_CODES_PAGE = new TypeToken<Page<PostalCodeDto>>() {
    }.getType();

    public static final Type LIST_OPERATOR_TYPE = new TypeToken<List<OperatorDto>>() {
    }.getType();

    public static final Type LIST_PHONE_NUMBER_TYPE = new TypeToken<List<PhoneNumberDto>>() {
    }.getType();

    private ResponseType() {
    }

}
