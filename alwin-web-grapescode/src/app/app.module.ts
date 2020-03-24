import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {AlwinRoutingModule} from './alwin-routing.module';
import {NotFoundComponent} from './not-found/not-found.component';
import {AuthenticationService} from './shared/authentication/authentication.service';
import {AuthGuard} from './shared/authentication/auth.guard';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import {IssueService} from './issues/shared/issue.service';
import {AlwinMatModule} from './alwin-mat.module';
import {MessageService} from './shared/message.service';
import {AlwinHttpService} from './shared/authentication/alwin-http.service';
import {CustomerDetailsDialogComponent} from './issues/shared/dialog/customer-details-dialog.component';
import {MyIssuesComponent} from './issues/my/my-issues.component';
import {IssueComponent} from './issues/issue/issue.component';
import {PersonIdDocumentDialogComponent} from 'app/customer/person-id-document-dialog.component';
import {CreateActivityDialogComponent} from './activity/create-activity-dialog.component';
import {ActivityService} from './activity/activity.service';
import {ActivityInputsComponent} from './activity/activity-inputs.component';
import {ActivityDetailsInputsComponent} from './activity/activity-details-inputs.component';
import {ProvideParentFormGroupDirective} from './shared/provide-parent-form-group.directive';
import {ProvideParentFormDirective} from './shared/provide-parent-form.directive';
import {DateBeforeValidatorDirective} from './shared/validation/date-before-validator.directive';
import {AlwinPipeModule} from './alwin-pipe.module';
import {AlwinSharedModule} from './alwin-shared.module';
import {UpdateActivityDialogComponent} from './activity/update-activity-dialog.component';
import {ActivityDeclarationsInputsComponent} from './activity/activity-declarations-inputs.component';
import {ConfirmationDialogComponent} from './shared/dialog/confirmation-dialog.component';
import {ManageIssuesComponent} from './issues/manage/manage-issues.component';
import {AddressService} from './customer/address/address-service';
import {ContactDetailService} from 'app/customer/contact-detail/contact-detail-service';
import {CreateAddressDialogComponent} from './customer/address/create-address-dialog.component';
import {CreateContactDetailDialogComponent} from './customer/contact-detail/create-contact-detail-dialog.component';
import {OperatorService} from './issues/shared/operator.service';
import {UpdateContactDetailDialogComponent} from './customer/contact-detail/update-contact-detail-dialog.component';
import {UpdateAddressDialogComponent} from './customer/address/update-address-dialog.component';
import {AssignOperatorDialogComponent} from './operator/assign/assign-operator-dialog.component';
import {UsersComponent} from './user/users.component';
import {FullUsersComponent} from './user/full-user.component';
import {UserService} from './issues/shared/user.service';
import {CreateUserDialogComponent} from './user/dialog/create-user-dialog.component';
import {UserDetailsComponent} from './user/user-details.component';
import {AddPermissionDialogComponent} from './user/dialog/add-permission-dialog.component';
import {SelectManagerDialogComponent} from './user/dialog/select-manager-dialog.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SendSmsMessageDialogComponent} from './message/send-sms-message-dialog.component';
import {SmsService} from './message/sms.service';
import {ExtCompanyService} from './company/ext-company.service';
import {CreateIssueDialogComponent} from './issues/create/dialog/create-issue-dialog.component';
import {UserAccessService} from './common/user-access.service';
import {registerLocaleData} from '@angular/common';
import localePl from '@angular/common/locales/pl';
import {NotificationService} from './notification/notification.service';
import {CreateNotificationDialogComponent} from './notification/create-notification-dialog.component';
import {CustomerContractsDialogComponent} from './issues/shared/dialog/customer-contracts-dialog.component';
import {ContractService} from './contract/contract.service';
import {IssueTerminationDialogComponent} from './issues/termination/issue-termination-dialog.component';
import {IssueTerminationService} from './issues/shared/issue-termination.service';
import {SegmentsComponent} from './segment/segments.component';
import {IssueModule} from './issues/issue/issue.module';
import {InvoiceDetailsDialogComponent} from './invoice/invoice-details-dialog.component';
import {SettlementService} from './settlement/settlement.service';
import {WalletService} from './segment/wallet.service';
import {InvoiceService} from './issues/shared/invoice.service';
import {PersonService} from './person/person.service';
import {CreatePersonDialogComponent} from './person/create-person-dialog.component';
import {RefreshIssueService} from './issues/issue/issue/refresh-issue.service';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import {MatMomentDateModule} from '@angular/material-moment-adapter';
import {ManageCompaniesModule} from './company/manage/manage-companies.module';
import {CompanyService} from './company/company.service';
import {EnvironmentSpecificService} from './shared/environment-specific.service';
import {EnvironmentSpecificProvider} from './shared/environment-specific.provider';
import {HolidaysModule} from './holiday/holidays.module';
import {DashboardPlaceholderComponent} from './dashboard-placeholder/dashboard.placeholder.component';
import {ChangePasswordComponent} from './operator/password/change-password.component';
import {ChangePasswordGuard} from './shared/authentication/changePassword.guard';
import {CompareDirective} from './operator/password/repeat-password.directive';
import {TagModule} from './tag/tag.module';
import {UpdatePriorityDialogComponent} from './issues/priority/dialog/update-priority-dialog.component';
import {ReportErrorDialogComponent} from './error/report-error-dialog.component';
import {TagIssueDialogComponent} from './issues/manage/dialog/tag-issue-dialog.component';
import {IssueAuditDialogComponent} from './issues/audit/issue-audit-dialog.component';
import {TagIssuesDialogComponent} from './issues/manage/dialog/tag-issues-dialog.component';
import {ConfigurationComponent} from './configuration/configuration.component';
import {IssueTypeComponent} from './configuration/issue-type/issue-type.component';
import {AlwinHttpLogInterceptor} from './login/alwin-http-log.interceptor';
import {RefreshTokenDialogComponent} from './login/refresh/refresh-token-dialog.component';
import {UpdateIssueTypeConfigurationDialogComponent} from './configuration/issue-type/dialog/update-issue-type-configuration-dialog.component';
import {DefaultActivityComponent} from './configuration/default-activity/default-activity.component';
import {UpdateDefaultActivityDialogComponent} from './configuration/default-activity/dialog/update-default-activity-dialog.component';
import {ActivityTypeComponent} from './configuration/activity-type/activity-type.component';
import {UpdateActivityTypeDialogComponent} from './configuration/activity-type/dialog/update-activity-type-dialog.component';
import {SchedulerModule} from './scheduler/scheduler.module';
import {VersionService} from './version/version.service';
import {MenuService} from './shared/authentication/menu.service';
import {ManageIssuesOperatorFiltersComponent} from './issues/manage/filter/manage-issues-operator-filters.component';
import {ManageIssuesManagerFiltersComponent} from './issues/manage/filter/manage-issues-manager-filters.component';
import {RefreshSchedulerHistoryService} from './scheduler/refresh-scheduler-history-service';
import {InvoiceDetailsSettlementsComponent} from './invoice/detail/invoice-details-settlements.component';
import {InvoiceDetailsCorrectionsComponent} from './invoice/detail/invoice-details-corrections.component';
import {InvoiceDetailsEntriesComponent} from './invoice/detail/invoice-details-entries.component';
import {DateService} from './shared/date/date.service';
import {WalletComponent} from './segment/wallet.component';
import {PrepareDictValuesDialogComponent} from './configuration/activity-type/dialog/prepare-dict-values-dialog.component';
import {DemandsModule} from './demand/demands.module';
import {FieldDebtIssuesComponent} from './issues/field-debt-issues/field-debt-issues.component';
import {TerminationsModule} from './termination/terminations.module';
import {ActivityAttachmentsInputsComponent} from 'app/activity/activity-attachments-inputs-component';
import {LocationModule} from './location/location.module';
import {PostponeContractTerminationDialogComponent} from 'app/termination/dialog/postpone-contract-termination-dialog.component';
import {ActivateContractDialogComponent} from 'app/termination/dialog/activate-contract-dialog.component';
import {CancelDemandDialogComponent} from 'app/demand/dialog/cancel-demand-dialog.component';

@NgModule({
  imports: [
    BrowserModule,
    AnimationModule(),
    FormsModule,
    HttpClientModule,
    AlwinRoutingModule,
    AlwinMatModule,
    ReactiveFormsModule,
    AlwinPipeModule,
    AlwinSharedModule,
    IssueModule,
    ManageCompaniesModule,
    MatMomentDateModule,
    HolidaysModule,
    TagModule,
    LocationModule,
    SchedulerModule,
    DemandsModule,
    TerminationsModule,
    BrowserAnimationsModule
  ],
  declarations: [
    AppComponent,
    MyIssuesComponent,
    IssueComponent,
    ManageIssuesComponent,
    TagIssueDialogComponent,
    TagIssuesDialogComponent,
    NotFoundComponent,
    DashboardPlaceholderComponent,
    LoginComponent,
    ChangePasswordComponent,
    UsersComponent,
    FullUsersComponent,
    UserDetailsComponent,
    SegmentsComponent,
    CustomerDetailsDialogComponent,
    PersonIdDocumentDialogComponent,
    CreateActivityDialogComponent,
    UpdateActivityDialogComponent,
    ConfirmationDialogComponent,
    ActivityInputsComponent,
    ActivityDetailsInputsComponent,
    ActivityDeclarationsInputsComponent,
    ProvideParentFormDirective,
    ProvideParentFormGroupDirective,
    DateBeforeValidatorDirective,
    CompareDirective,
    CreateAddressDialogComponent,
    CreateUserDialogComponent,
    CreateContactDetailDialogComponent,
    UpdateContactDetailDialogComponent,
    UpdateAddressDialogComponent,
    AssignOperatorDialogComponent,
    IssueTerminationDialogComponent,
    AddPermissionDialogComponent,
    SelectManagerDialogComponent,
    SendSmsMessageDialogComponent,
    CreateIssueDialogComponent,
    CreateNotificationDialogComponent,
    CustomerContractsDialogComponent,
    InvoiceDetailsDialogComponent,
    CreatePersonDialogComponent,
    UpdatePriorityDialogComponent,
    ReportErrorDialogComponent,
    IssueAuditDialogComponent,
    ConfigurationComponent,
    IssueTypeComponent,
    RefreshTokenDialogComponent,
    UpdateIssueTypeConfigurationDialogComponent,
    DefaultActivityComponent,
    UpdateDefaultActivityDialogComponent,
    ActivityTypeComponent,
    UpdateActivityTypeDialogComponent,
    PrepareDictValuesDialogComponent,
    ManageIssuesOperatorFiltersComponent,
    ManageIssuesManagerFiltersComponent,
    InvoiceDetailsSettlementsComponent,
    InvoiceDetailsCorrectionsComponent,
    InvoiceDetailsEntriesComponent,
    WalletComponent,
    FieldDebtIssuesComponent,
    ActivityAttachmentsInputsComponent,
    PostponeContractTerminationDialogComponent,
    ActivateContractDialogComponent,
    CancelDemandDialogComponent
  ],
  entryComponents: [
    CustomerDetailsDialogComponent,
    PersonIdDocumentDialogComponent,
    CreateActivityDialogComponent,
    UpdateActivityDialogComponent,
    ConfirmationDialogComponent,
    CreateAddressDialogComponent,
    CreateUserDialogComponent,
    CreateContactDetailDialogComponent,
    UpdateContactDetailDialogComponent,
    UpdateAddressDialogComponent,
    AssignOperatorDialogComponent,
    IssueTerminationDialogComponent,
    AddPermissionDialogComponent,
    SelectManagerDialogComponent,
    TagIssueDialogComponent,
    TagIssuesDialogComponent,
    SendSmsMessageDialogComponent,
    CreateIssueDialogComponent,
    CreateNotificationDialogComponent,
    CustomerContractsDialogComponent,
    InvoiceDetailsDialogComponent,
    CreatePersonDialogComponent,
    UpdatePriorityDialogComponent,
    ReportErrorDialogComponent,
    IssueAuditDialogComponent,
    RefreshTokenDialogComponent,
    IssueAuditDialogComponent,
    UpdateIssueTypeConfigurationDialogComponent,
    UpdateDefaultActivityDialogComponent,
    UpdateActivityTypeDialogComponent,
    PrepareDictValuesDialogComponent,
    PostponeContractTerminationDialogComponent,
    ActivateContractDialogComponent,
    CancelDemandDialogComponent
  ],
  providers: [
    EnvironmentSpecificService,
    EnvironmentSpecificProvider,
    AuthGuard,
    ChangePasswordGuard,
    AuthenticationService,
    AlwinHttpService,
    IssueService,
    InvoiceService,
    UserService,
    ActivityService,
    MessageService,
    AddressService,
    ContactDetailService,
    OperatorService,
    SmsService,
    ExtCompanyService,
    UserAccessService,
    NotificationService,
    ContractService,
    SettlementService,
    IssueTerminationService,
    WalletService,
    PersonService,
    RefreshIssueService,
    RefreshSchedulerHistoryService,
    CompanyService,
    VersionService,
    MenuService,
    DateService,
    {provide: LOCALE_ID, useValue: 'pl'},
    {provide: MAT_DATE_LOCALE, useValue: 'pl'},
    {provide: HTTP_INTERCEPTORS, useClass: AlwinHttpLogInterceptor, multi: true}
  ],
  exports: [HttpClientModule],
  bootstrap: [AppComponent]
})

export class AppModule {
  constructor() {
    registerLocaleData(localePl);
  }
}

/**
 * Decyduje o tym czy załadowane zostaną animacje na stronie
 * @returns {any} - moduł bez animacji jeżeli w adresie występuje parametr qa z wartością true, moduł z animacjami w przeciwnym wypadku
 */
export function AnimationModule(): any {
  return window.location.search.indexOf('qa=true') > -1 ? NoopAnimationsModule : BrowserAnimationsModule;
}
