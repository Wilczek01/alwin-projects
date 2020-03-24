import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {ContractWithSubjectsAndSchedules} from '../../../contract/contract-with-subjects-and-schedules';
import {ContractService} from '../../../contract/contract.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../utils/handling-error-utils';

/**
 * Komponent typu dialog do wyświetlania umów firmy wraz z przedmiotami i harmonogramami
 */
@Component({
  selector: 'alwin-customer-contracts-dialog',
  styleUrls: ['./customer-contracts-dialog.component.css'],
  templateUrl: './customer-contracts-dialog.component.html'
})
export class CustomerContractsDialogComponent implements OnInit {

  extCompanyId: number;
  contracts: ContractWithSubjectsAndSchedules[];

  loading: boolean;

  constructor(public dialogRef: MatDialogRef<CustomerContractsDialogComponent>, private messageService: MessageService,
              private contractService: ContractService) {
  }

  ngOnInit() {
    this.loadContracts();
  }

  loadContracts() {
    this.loading = true;
    this.contractService.getContractsWithSubjectsAndSchedules(this.extCompanyId).subscribe(
      contracts => {
        if (contracts == null) {
          this.loading = false;
          return;
        }
        this.contracts = contracts;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }
}
