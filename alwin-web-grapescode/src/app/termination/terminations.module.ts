import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AlwinMatModule} from '../alwin-mat.module';
import {AlwinPipeModule} from '../alwin-pipe.module';
import {AlwinSharedModule} from '../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TerminationService} from './termination.service';
import {TerminationsComponent} from './terminations.component';
import {NewTerminationsComponent} from './new-terminations.component';
import {TerminationStatePipe} from './pipe/termination-state.pipe';
import {TerminationContractTypePipe} from './pipe/termination-contract-type.pipe';
import {AttachmentDocumentsDialogComponent} from './dialog/attachment-documents-dialog.component';
import {ProcessedContractSubjectsDialogComponent} from './dialog/processed-contract-subjects-dialog.component';
import {NewTerminationComponent} from './new-termination.component';
import {NewContractSubjectsDialogComponent} from './dialog/new-contract-subjects-dialog.component';
import {RejectedTerminationsComponent} from './rejected-terminations.component';
import {IssuedTerminationsComponent} from './issued-terminations.component';
import {ProcessedTerminationComponent} from './processed-termination.component';
import {ContractActivatedTerminationsComponent} from "app/termination/contract-activated-terminations.component";

/**
 * Moduł deklarujący komponenty używane w widoku wypowiedzeń umów
 */
@NgModule({
  providers: [
    DatePipe,
    TerminationService
  ],
  imports: [
    CommonModule,
    AlwinMatModule,
    AlwinPipeModule,
    AlwinSharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [
    TerminationsComponent,
    NewTerminationsComponent,
    NewTerminationComponent,
    ProcessedTerminationComponent,
    IssuedTerminationsComponent,
    RejectedTerminationsComponent,
    AttachmentDocumentsDialogComponent,
    ProcessedContractSubjectsDialogComponent,
    NewContractSubjectsDialogComponent,
    TerminationStatePipe,
    TerminationContractTypePipe,
    ContractActivatedTerminationsComponent
  ],
  entryComponents: [
    AttachmentDocumentsDialogComponent,
    ProcessedContractSubjectsDialogComponent,
    NewContractSubjectsDialogComponent
  ],
  exports: [
    TerminationsComponent,
    NewTerminationsComponent,
    NewTerminationComponent,
    ProcessedTerminationComponent,
    IssuedTerminationsComponent,
    RejectedTerminationsComponent,
    AttachmentDocumentsDialogComponent,
    ProcessedContractSubjectsDialogComponent,
    NewContractSubjectsDialogComponent,
    TerminationStatePipe,
    TerminationContractTypePipe,
    ContractActivatedTerminationsComponent
  ]
})
export class TerminationsModule {
}
