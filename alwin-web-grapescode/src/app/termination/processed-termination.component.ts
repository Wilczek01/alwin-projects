import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Termination} from './model/termination';
import {FormalDebtCollectionInvoice} from '../issues/shared/formal-debt-collection-invoice';
import {FormalDebtCollectionInvoicesDialogComponent} from '../invoice/formal-debt-collection-invoices-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {AttachmentDocumentsDialogComponent} from './dialog/attachment-documents-dialog.component';
import {TerminationContractSubject} from './model/termination-contract-subject';
import {ProcessedContractSubjectsDialogComponent} from './dialog/processed-contract-subjects-dialog.component';
import {TerminationContract} from './model/termination-contract';
import {ActivateContractDialogComponent} from './dialog/activate-contract-dialog.component';

/**
 * Komponent odpowiadający za prezentację wysłanych i odrzuconych wypowiedzeń umów klienta
 */
@Component({
  selector: 'alwin-processed-termination',
  styleUrls: ['./processed-termination.component.css'],
  templateUrl: './processed-termination.component.html'
})
export class ProcessedTerminationComponent {

  @Input()
  termination: Termination;
  @Input()
  showActions: boolean;
  @Output()
  terminationChanged: EventEmitter<TerminationContract> = new EventEmitter<TerminationContract>();

  constructor(private dialog: MatDialog) {
  }

  /**
   * Otwiera dialog z tabelą faktur związanych z umową na wypowiedzeniu
   * @param invoices - faktury
   */
  showInvoices(invoices: FormalDebtCollectionInvoice[]) {
    const dialogRef = this.dialog.open(FormalDebtCollectionInvoicesDialogComponent);
    dialogRef.componentInstance.invoices = invoices;
  }

  /**
   * Otwiera dialog z lista przedmiotów związanych z umową na wypowiedzeniu
   * @param subjects - przedmioty na umowie
   * @param contractNo - numer umowy
   */
  showSubjects(subjects: TerminationContractSubject[], contractNo: string) {
    const dialogRef = this.dialog.open(ProcessedContractSubjectsDialogComponent);
    dialogRef.componentInstance.terminationSubjects = subjects;
    dialogRef.componentInstance.contractNo = contractNo;
  }

  /**
   * Otwiera dialog z linkami do dokumentów związanych z wypowiedzeniem
   * @param attachmentRefs - linki do dokumentów
   */
  openTerminationDocuments(attachmentRefs: string[]) {
    const dialogRef = this.dialog.open(AttachmentDocumentsDialogComponent);
    dialogRef.componentInstance.attachmentRefs = attachmentRefs;
  }

  /**
   * Otwiera dialog z formularzem do aktywowania umowy
   * @param terminationContract - wypowiedzenie umowy
   */
  openActivateDialog(terminationContract: TerminationContract) {
    const dialogRef = this.dialog.open(ActivateContractDialogComponent, {data: terminationContract});
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.terminationChanged.emit(terminationContract);
      }
    });
  }

}
