import {Component, Input, OnInit} from '@angular/core';
import {ContractService} from '../../../../../contract/contract.service';
import {ContractWithSubjectsAndSchedules} from '../../../../../contract/contract-with-subjects-and-schedules';
import {HandlingErrorUtils} from '../../../../shared/utils/handling-error-utils';
import {MessageService} from '../../../../../shared/message.service';
import {ContractScheduleDialogComponent} from './schedule/contract-schedule-dialog.component';
import { MatDialog } from '@angular/material/dialog';

/**
 * Komponent z danymi harmonogramu dla wybranego kontraktu w widoku obsługi zlecenia
 */
@Component({
  selector: 'alwin-customer-contract-tab',
  templateUrl: './customer-contract-tab.component.html',
  styleUrls: ['./customer-contract-tab.component.css']
})
export class CustomerContractTabComponent implements OnInit {

  @Input()
  extCompanyId: number;
  contracts: ContractWithSubjectsAndSchedules[] = [];
  selectedContract: ContractWithSubjectsAndSchedules;
  loadingContracts: boolean;

  constructor(private contractService: ContractService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadContracts();
  }

  /**
   * Ładuje kontrakty dla załadowanego klienta
   */
  private loadContracts() {
    this.loadingContracts = true;
    this.contractService.getContractsWithSubjectsAndSchedules(this.extCompanyId).subscribe(
      contracts => {
        if (contracts != null) {
          this.contracts = contracts;
        }
        this.selectFirstContract();
        this.loadingContracts = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingContracts = false;
      });
  }

  /**
   * Ustawia w widoku szczegółów pierwszy kontrakt z listy
   */
  private selectFirstContract() {
    if (this.contracts.length > 0) {
      this.selectContract(0);
    }
  }

  /**
   * Ustawia w widoku szczegółów kontrakt z listy o wybranym indeksie
   * @param {number} index - indeks, numer porzadkowy na liście kontraktów
   */
  selectContract(index: number) {
    this.selectedContract = this.contracts[index];
  }

  /**
   * Sprawdza czy kontrakt o wybranym identyfikatorze jest wybrany
   * @param {number} contractId - identyfikator wybranego kontraktu
   * @returns {boolean} - true, jeżeli kontrakt jest już wybrany, false w przeciwnym razie
   */
  isContractSelected(contractId: number) {
    return this.selectedContract != null && this.selectedContract.id === contractId;
  }

  /**
   * Wyświetla dialog z harmonogramem dla wybranej umowy
   */
  showSchedule() {
    const dialogRef = this.dialog.open(ContractScheduleDialogComponent);
    dialogRef.componentInstance.paymentSchedules = this.selectedContract.paymentSchedules;
  }

  /**
   * Sprawdza czy wybrana umowa posiada jakikolwiek harmonogram ze ratami
   *  @returns {boolean} - true, jeżeli  wybrana umowa posiada jakikolwiek harmonogram ze ratami, false w przeciwnym razie
   */
  hasInstalments(): boolean {
    return this.selectedContract.paymentSchedules.filter(schedule => schedule.instalments != null && schedule.instalments.length > 0).length > 0;
  }

}
