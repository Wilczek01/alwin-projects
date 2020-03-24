import {NgModule} from '@angular/core';
import {AlwinMatModule} from './alwin-mat.module';
import {AlwinPipeModule} from './alwin-pipe.module';
import {CommonModule, DatePipe} from '@angular/common';
import {PersonListComponent} from './customer/person-list.component';
import {PersonComponent} from './customer/person.component';
import {IssueTypeIconComponent} from './issues/shared/type/issue-type-icon.component';
import {CustomerContractSubjectsComponent} from './issues/shared/dialog/customer-contract-subjects.component';
import {CustomerContractPaymentSchedulesComponent} from './issues/shared/dialog/customer-contract-payment-schedules.component';
import {CustomerContractPaymentScheduleInstallmentsComponent} from './issues/shared/dialog/customer-contract-payment-schedule-installments.component';
import {ColorPickerInputComponent} from './shared/color-picker/color-picker-input.component';
import {FormsModule} from '@angular/forms';


@NgModule({
  providers: [
    DatePipe
  ],
  imports: [
    CommonModule,
    AlwinMatModule,
    AlwinPipeModule,
    FormsModule
  ],
  declarations: [
    PersonComponent,
    PersonListComponent,
    IssueTypeIconComponent,
    CustomerContractSubjectsComponent,
    CustomerContractPaymentSchedulesComponent,
    CustomerContractPaymentScheduleInstallmentsComponent,
    ColorPickerInputComponent
  ],
  exports: [
    PersonComponent,
    PersonListComponent,
    IssueTypeIconComponent,
    CustomerContractSubjectsComponent,
    CustomerContractPaymentSchedulesComponent,
    CustomerContractPaymentScheduleInstallmentsComponent,
    ColorPickerInputComponent
  ]
})
export class AlwinSharedModule {
}
