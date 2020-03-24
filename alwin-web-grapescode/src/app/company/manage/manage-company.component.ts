import {Component, Input, OnInit} from '@angular/core';
import {CompanyService} from '../company.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {CustomerOutOfService} from './model/customer-out-of-service';
import {ContractService} from '../../contract/contract.service';
import {ContractWithExclusions} from '../../contract/contract-with-exclusions';
import {ContractExclusionsDialogComponent} from './dialog/contract-exclusions-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {CreateCustomerExclusionDialogComponent} from './dialog/create-customer-exclusion-dialog.component';
import {CreateContractExclusionDialogComponent} from './dialog/create-contract-exclusion-dialog.component';
import {UpdateCustomerExclusionDialogComponent} from './dialog/update-customer-exclusion-dialog.component';
import {ConfirmationDialogComponent} from '../../shared/dialog/confirmation-dialog.component';

/**
 * Komponent odpowiedzialny za zarządzanie wybranym klientem w windykacji nieformalnej
 */
@Component({
  selector: 'alwin-manage-company',
  styleUrls: ['./manage-company.component.css'],
  templateUrl: './manage-company.component.html'
})
export class ManageCompanyComponent implements OnInit {

  loadingExclusions: boolean;
  loadingContracts: boolean;
  exclusions: CustomerOutOfService[] = [];
  contracts: ContractWithExclusions[] = [];

  @Input()
  extCompanyId: string;

  constructor(private messageService: MessageService, private companyService: CompanyService,
              private contractService: ContractService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadCustomerExclusions();
    this.loadCompanyContracts();
  }


  /**
   * Ładuje blokady dla wybranego klienta
   */
  private loadCustomerExclusions() {
    this.loadingExclusions = true;
    this.companyService.getCustomersOutOfService(this.extCompanyId).subscribe(
      exclusions => {
        if (exclusions != null) {
          this.exclusions = exclusions;
        }
        this.loadingExclusions = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingExclusions = false;
      }
    );
  }

  /**
   * Otwiera okno umożliwiające edycję wybranej blokady wybraneo klienta
   * @param {CustomerOutOfService} exclusion - wybrana blokada
   */
  editExclusion(exclusion: CustomerOutOfService) {
    const dialogRef = this.dialog.open(UpdateCustomerExclusionDialogComponent);
    const exclusionToEdit = new CustomerOutOfService();
    Object.assign(exclusionToEdit, exclusion);
    dialogRef.componentInstance.exclusion = exclusionToEdit;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadCustomerExclusions();
      }
    });
  }

  /**
   * Otwiera okno umożliwiające usunięcie wybranej blokady wybraneo klienta
   * @param {number} exclusionId - identyfikator wybranej blokady
   */
  confirmDeleteExclusion(exclusionId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Usunięcie blokady';
    dialogRef.componentInstance.message = 'Czy na pewno chcesz usunąc tę blokadę?';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.deleteExclusion(exclusionId);
      }
    });
  }

  /**
   * Usuwa blokadę klienta o podanym identyfikatorze, a następnie odświeża listę wszystkich blokad klienta
   * @param {number} exclusionId - identyfikator wybranej blokady
   */
  deleteExclusion(exclusionId: number) {
    this.loadingExclusions = true;
    this.companyService.deleteCustomersOutOfService(exclusionId).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Blokada została usunięta');
          this.loadCustomerExclusions();
        } else {
          this.messageService.showMessage('Nie udało się usunąć blokady');
          this.loadingExclusions = false;
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingExclusions = false;
      }
    );
  }

  /**
   * Otwiera okno umożliwiające dodanie nowej blokady dla wybranego klienta
   */
  createNewExclusion() {
    const dialogRef = this.dialog.open(CreateCustomerExclusionDialogComponent);
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadCustomerExclusions();
      }
    });
  }

  /**
   * Otwiera okno umożliwiające dodanie nowej blokady dla wybranej umowy
   * @param {ContractWithExclusions} contract - wybrana umowa do wykluczenia
   */
  exclude(contract: ContractWithExclusions) {
    const dialogRef = this.dialog.open(CreateContractExclusionDialogComponent);
    dialogRef.componentInstance.contractNo = contract.contractNo;
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadCompanyContracts();
      }
    });
  }

  /**
   * Otwiera okno z blokadami dla wybranej umowy
   * @param {ContractWithExclusions} contract - wybrana umowa
   */
  showExclusions(contract: ContractWithExclusions) {
    const dialogRef = this.dialog.open(ContractExclusionsDialogComponent);
    dialogRef.componentInstance.contract = contract;
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadCompanyContracts();
      }
    });
  }

  /**
   * Ładuje umowy dla wybranego klienta
   */
  private loadCompanyContracts() {
    this.loadingContracts = true;
    this.contractService.getSimpleContracts(this.extCompanyId).subscribe(
      contracts => {
        if (contracts != null) {
          this.contracts = contracts;
        }
        this.loadingContracts = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingContracts = false;
      }
    );
  }

  isLoading() {
    return this.loadingExclusions || this.loadingContracts;
  }
}
