import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AlwinMatModule} from '../alwin-mat.module';
import {AlwinPipeModule} from '../alwin-pipe.module';
import {AlwinSharedModule} from '../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DemandService} from './demand.service';
import {DemandsComponent} from './demands.component';
import {DemandStatePipe} from './pipe/demand-state.pipe';
import {DemandTypeKeyPipe} from './pipe/demand-type-key.pipe';
import {IssuedDemandsComponent} from './issued-demands.component';
import {NewDemandsComponent} from './new-demands.component';
import {RejectConfirmationDialogComponent} from './dialog/reject-confirmation-dialog.component';
import {ManualDemandsComponent} from './manual-demands.component';
import {CreateDemandDialogComponent} from './dialog/create-demand-dialog.component';
import {ManageCompaniesModule} from '../company/manage/manage-companies.module';
import {DemandManualCreationMessagesPipe} from './pipe/demand-manual-creation-messages.pipe';
import {CreateDemandResultKeyStringPipe} from './pipe/create-demand-result-key-string.pipe';
import {CreateDemandResultDialogComponent} from './dialog/create-demand-result-dialog.component';
import {ManualDemandDetailsDialogComponent} from './dialog/manual-demand-details-dialog.component';
import {RejectedDemandsComponent} from './rejected-demands.component';

/**
 * Moduł deklarujący komponenty używane w widoku wezwań do zapłaty
 */
@NgModule({
  providers: [
    DatePipe,
    DemandService
  ],
  imports: [
    CommonModule,
    AlwinMatModule,
    AlwinPipeModule,
    AlwinSharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ManageCompaniesModule
  ],
  declarations: [
    DemandsComponent,
    IssuedDemandsComponent,
    NewDemandsComponent,
    DemandStatePipe,
    DemandTypeKeyPipe,
    RejectConfirmationDialogComponent,
    RejectedDemandsComponent,
    ManualDemandsComponent,
    CreateDemandDialogComponent,
    DemandManualCreationMessagesPipe,
    CreateDemandResultKeyStringPipe,
    CreateDemandResultDialogComponent,
    ManualDemandDetailsDialogComponent
  ],
  entryComponents: [
    RejectConfirmationDialogComponent,
    CreateDemandDialogComponent,
    CreateDemandResultDialogComponent,
    ManualDemandDetailsDialogComponent
  ],
  exports: [
    DemandsComponent,
    IssuedDemandsComponent,
    NewDemandsComponent,
    DemandStatePipe,
    DemandTypeKeyPipe,
    RejectConfirmationDialogComponent,
    RejectedDemandsComponent,
    ManualDemandsComponent,
    CreateDemandDialogComponent,
    DemandManualCreationMessagesPipe,
    CreateDemandResultKeyStringPipe,
    CreateDemandResultDialogComponent,
    ManualDemandDetailsDialogComponent
  ]
})
export class DemandsModule {
}
