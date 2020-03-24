import {NgModule} from '@angular/core';
import {CustomerTabComponent} from './customer/customer-tab.component';
import {NotificationHeaderComponent} from './notification/notification-header.component';
import {IssueHeaderComponent} from './issue/issue-header.component';
import {CommonModule, DatePipe} from '@angular/common';
import {AlwinPipeModule} from '../../alwin-pipe.module';
import {AlwinMatModule} from '../../alwin-mat.module';
import {AlwinSharedModule} from '../../alwin-shared.module';
import {CustomerDataTabsComponent} from './customer/customer-data-tabs.component';
import {CustomerAddressTabComponent} from './customer/data/address/customer-address-tab.component';
import {CustomerContactTabComponent} from './customer/data/contact/customer-contact-tab.component';
import {CustomerContractTabComponent} from './customer/data/contract/customer-contract-tab.component';
import {CustomerPersonTabComponent} from './customer/data/person/customer-person-tab.component';
import {DebtsTabComponent} from './debts/debts-tab.component';
import {ActivityTabComponent} from './activity/activity-tab.component';
import {TerminationHeaderComponent} from './termination/issue-termination-header.component';
import {IssueInvoicesComponent} from '../../invoice/issue-invoices.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ContractScheduleDialogComponent} from './customer/data/contract/schedule/contract-schedule-dialog.component';
import {PhoneCallComponent} from './phone-call/phone-call.component';
import {IssueAuditComponent} from '../work/audit/issue-audit/issue-audit.component';
import {FormalDebtCollectionInvoicesDialogComponent} from '../../invoice/formal-debt-collection-invoices-dialog.component';

/**
 * Moduł deklarujący komponenty używane w widoku obsługi zlecenia
 */
@NgModule({
  providers: [
    DatePipe
  ],
  imports: [
    CommonModule,
    AlwinMatModule,
    AlwinPipeModule,
    AlwinSharedModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    DebtsTabComponent,
    CustomerTabComponent,
    IssueHeaderComponent,
    NotificationHeaderComponent,
    CustomerDataTabsComponent,
    CustomerAddressTabComponent,
    CustomerContactTabComponent,
    CustomerContractTabComponent,
    CustomerPersonTabComponent,
    TerminationHeaderComponent,
    ActivityTabComponent,
    IssueInvoicesComponent,
    FormalDebtCollectionInvoicesDialogComponent,
    ContractScheduleDialogComponent,
    PhoneCallComponent,
    IssueAuditComponent
  ],
  entryComponents: [
    ContractScheduleDialogComponent,
    FormalDebtCollectionInvoicesDialogComponent
  ],
  exports: [
    DebtsTabComponent,
    CustomerTabComponent,
    IssueHeaderComponent,
    NotificationHeaderComponent,
    CustomerDataTabsComponent,
    CustomerAddressTabComponent,
    CustomerContactTabComponent,
    CustomerContractTabComponent,
    CustomerPersonTabComponent,
    ActivityTabComponent,
    TerminationHeaderComponent,
    PhoneCallComponent,
    ContractScheduleDialogComponent,
    FormalDebtCollectionInvoicesDialogComponent
  ]
})
export class IssueModule {
}
