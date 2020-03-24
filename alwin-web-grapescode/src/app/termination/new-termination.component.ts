import {Component, Input, OnInit} from '@angular/core';
import {FormalDebtCollectionInvoice} from '../issues/shared/formal-debt-collection-invoice';
import {FormalDebtCollectionInvoicesDialogComponent} from '../invoice/formal-debt-collection-invoices-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {TerminationContractSubject} from './model/termination-contract-subject';
import {NewTermination} from './model/new-termination';
import {TerminationContractTypeConst} from './model/termination-contract-type-const';
import {TerminationContractStateConst} from './model/termination-contract-state-const';
import {NewTerminationContract} from './model/new-termination-contract';
import {NewContractSubjectsDialogComponent} from './dialog/new-contract-subjects-dialog.component';
import {TerminationService} from './termination.service';
import {PostponeContractTerminationDialogComponent} from 'app/termination/dialog/postpone-contract-termination-dialog.component';

/**
 * Komponent odpowiadający za prezentację nowych i odroczonych wypowiedzeń umów klienta
 */
@Component({
  selector: 'alwin-new-termination',
  styleUrls: ['./new-termination.component.css'],
  templateUrl: './new-termination.component.html'
})
export class NewTerminationComponent implements OnInit {

  @Input()
  termination: NewTermination;

  expanded: boolean;
  TYPE = TerminationContractTypeConst;
  STATE = TerminationContractStateConst;

  constructor(private dialog: MatDialog, private terminationService: TerminationService) {
  }

  ngOnInit(): void {
    this.terminationService.refreshSelection$.subscribe(() => {
      this.refreshSelection();
    });
  }

  /**
   * Odświeża checkboxy do zaznaczania wszystkiego
   */
  refreshSelection(): void {
    this.termination.sendAll = this.termination.contracts
      .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING && contract.send).length === this.termination.contracts.length;
    this.termination.postponeAll = this.termination.contracts
      .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING && contract.postpone).length === this.termination.contracts.length;
    this.termination.rejectAll = this.termination.contracts
      .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING && contract.reject).length === this.termination.contracts.length;
  }

  /**
   * Oznacza wszystkie umowy do odrzucenia
   */
  markAllToReject() {
    this.termination.contracts
      .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING)
      .forEach(contract => {
        contract.reject = this.termination.rejectAll;
        this.markToReject(contract);
      });
  }

  /**
   * Oznacza wszystkie umowy do wysłania
   */
  markAllToSend() {
    this.termination.contracts
      .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING)
      .forEach(contract => {
        contract.send = this.termination.sendAll;
        this.markToSend(contract);
      });
  }

  /**
   * Oznacza wszystkie umowy do odroczenia
   */
  markAllToPostpone() {
    if (this.termination.postponeAll) {
      const dialogRef = this.dialog.open(PostponeContractTerminationDialogComponent);
      dialogRef.afterClosed().subscribe(accepted => {
        if (accepted) {
          this.termination.contracts
            .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING)
            .forEach(contract => {
              contract.postpone = this.termination.postponeAll;
              this.markToPostpone(contract, dialogRef.componentInstance.remark, dialogRef.componentInstance.terminationDate);
            });
        } else {
          this.termination.postponeAll = false;
        }
      });
    } else {
      this.termination.contracts
        .filter(contract => contract.terminationContract.state !== TerminationContractStateConst.WAITING)
        .forEach(contract => {
          contract.postpone = this.termination.postponeAll;
          this.markToPostpone(contract, null, null);
        });
    }
  }

  /**
   * Oznacza wypowiedzenie do odrzucenia - automatycznie przez naciśnięcie checkboxa, manualnie odznacza do wysłania i do odroczenia
   * @param termination - wypowiedzenie
   */
  markToReject(termination: NewTerminationContract) {
    if (!termination.reject) {
      this.termination.rejectAll = false;
    } else if (this.termination.contracts
      .filter(contract => !contract.reject && contract.terminationContract.state !== TerminationContractStateConst.WAITING).length === 0) {
      this.termination.rejectAll = true;
      this.termination.sendAll = false;
      this.termination.postponeAll = false;
    }
    termination.terminationContract.remark = null;
    termination.terminationContract.terminationDate = null;
    termination.send = false;
    termination.postpone = false;
    this.termination.sendAll = false;
    this.termination.postponeAll = false;
  }

  /**
   * Oznacza wypowiedzenie do wysłania - automatycznie przez naciśnięcie checkboxa, manualnie odznacza do odrzucenia i do odroczenia
   * @param termination - wypowiedzenie
   */
  markToSend(termination: NewTerminationContract) {
    if (!termination.send) {
      this.termination.sendAll = false;
    } else if (this.termination.contracts
      .filter(contract => !contract.send && contract.terminationContract.state !== TerminationContractStateConst.WAITING).length === 0) {
      this.termination.sendAll = true;
      this.termination.postponeAll = false;
      this.termination.rejectAll = false;
    }
    termination.terminationContract.remark = null;
    termination.terminationContract.terminationDate = null;
    termination.reject = false;
    termination.postpone = false;
    this.termination.postponeAll = false;
    this.termination.rejectAll = false;
  }

  /**
   * Oznacza wypowiedzenie do odroczenia - automatycznie przez naciśnięcie checkboxa, manualnie odznacza do odrzucenia i do wysłania
   * @param termination - wypowiedzenie
   */
  markToPostpone(termination: NewTerminationContract, remark: string, terminationDate: Date) {
    if (!termination.postpone) {
      this.termination.postponeAll = false;
    } else if (this.termination.contracts
      .filter(contract => !contract.postpone && contract.terminationContract.state !== TerminationContractStateConst.WAITING).length === 0) {
      this.termination.postponeAll = true;
      this.termination.sendAll = false;
      this.termination.rejectAll = false;
    }
    termination.terminationContract.remark = remark;
    termination.terminationContract.terminationDate = terminationDate;
    termination.reject = false;
    termination.send = false;
    this.termination.sendAll = false;
    this.termination.rejectAll = false;
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
    const dialogRef = this.dialog.open(NewContractSubjectsDialogComponent);
    dialogRef.componentInstance.terminationSubjects = subjects;
    dialogRef.componentInstance.contractNo = contractNo;
  }

  changeType(contract: NewTerminationContract) {
    this.terminationService.applyContractToTerminationGroup(contract);
  }

}
