import {Component, Input, OnInit} from '@angular/core';
import {CompanyService} from '../company.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {ContractService} from '../../contract/contract.service';
import { MatDialog } from '@angular/material/dialog';
import {FormalDebtCollectionCustomerOutOfService} from './model/formal-debt-collection-customer-out-of-service';
import {ContractWithFormalDebtCollectionExclusions} from '../../contract/contract-with-formal-debt-collection-exclusions';
import {ContractFormalDebtCollectionExclusionsDialogComponent} from 'app/company/manage/dialog/contract-formal-debt-collection-exclusions-dialog.component';
import {CreateCustomerFormalDebtCollectionExclusionDialogComponent} from './dialog/create-customer-formal-debt-collection-exclusion-dialog.component';
import {KeyLabel} from '../../shared/key-label';
import {UpdateCustomerFormalDebtCollectionExclusionDialogComponent} from './dialog/update-customer-formal-debt-collection-exclusion-dialog.component';
import {ConfirmationDialogComponent} from '../../shared/dialog/confirmation-dialog.component';
import {CreateContractFormalDebtCollectionExclusionDialogComponent} from './dialog/create-contract-formal-debt-collection-exclusion-dialog.component';

/**
 * Komponent odpowiedzialny za zarządzanie wybranym klientem w windykacji formalnej
 */
@Component({
  selector: 'alwin-manage-formal-debt-collection-company',
  styleUrls: ['./manage-formal-debt-collection-company.component.css'],
  templateUrl: './manage-formal-debt-collection-company.component.html'
})
export class ManageFormalDebtCollectionCompanyComponent implements OnInit {

  loadingReasonTypes: boolean;
  loadingExclusions: boolean;
  loadingContracts: boolean;
  reasonTypes: KeyLabel[] = [];
  exclusions: FormalDebtCollectionCustomerOutOfService[] = [];
  contracts: ContractWithFormalDebtCollectionExclusions[] = [];

  @Input()
  extCompanyId: string;

  constructor(private messageService: MessageService, private companyService: CompanyService,
              private contractService: ContractService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadReasonTypes();
    this.loadFormalDebtCollectionCustomerExclusions();
    this.loadFormalDebtCollectionCompanyContracts();
  }

  /**
   * Ładuje typy przyczyn zablokowania
   */
  private loadReasonTypes() {
    this.loadingReasonTypes = true;
    this.companyService.getReasonTypes().subscribe(
      reasonTypes => {
        if (reasonTypes != null) {
          this.reasonTypes = reasonTypes;
        }
        this.loadingReasonTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingReasonTypes = false;
      }
    );
  }

  /**
   * Ładuje blokady dla wybranego klienta
   */
  private loadFormalDebtCollectionCustomerExclusions() {
    this.loadingExclusions = true;
    this.companyService.getFormalDebtCollectionCustomerOutOfService(this.extCompanyId).subscribe(
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
   * @param {FormalDebtCollectionCustomerOutOfService} exclusion - wybrana blokada
   */
  editExclusion(exclusion: FormalDebtCollectionCustomerOutOfService) {
    const dialogRef = this.dialog.open(UpdateCustomerFormalDebtCollectionExclusionDialogComponent);
    const exclusionToEdit = new FormalDebtCollectionCustomerOutOfService();
    Object.assign(exclusionToEdit, exclusion);
    dialogRef.componentInstance.exclusion = exclusionToEdit;
    dialogRef.componentInstance.reasonTypes = this.reasonTypes;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadFormalDebtCollectionCustomerExclusions();
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
    this.companyService.deleteFormalDebtCollectionCustomersOutOfService(exclusionId).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Blokada została usunięta');
          this.loadFormalDebtCollectionCustomerExclusions();
        } else {
          this.messageService.showMessage('Nie udało się usunąć blokady');
          this.loadingExclusions = false;
        }
      },
      err => {
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loadingExclusions = false;
      }
    );
  }

  /**
   * Otwiera okno umożliwiające dodanie nowej blokady dla wybranego klienta
   */
  createNewExclusion() {
    const dialogRef = this.dialog.open(CreateCustomerFormalDebtCollectionExclusionDialogComponent);
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.componentInstance.reasonTypes = this.reasonTypes;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadFormalDebtCollectionCustomerExclusions();
      }
    });
  }

  /**
   * Otwiera okno umożliwiające dodanie nowej blokady dla wybranej umowy
   * @param {ContractWithFormalDebtCollectionExclusions} contract - wybrana umowa do wykluczenia
   */
  exclude(contract: ContractWithFormalDebtCollectionExclusions) {
    const dialogRef = this.dialog.open(CreateContractFormalDebtCollectionExclusionDialogComponent);
    dialogRef.componentInstance.contractNo = contract.contractNo;
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.componentInstance.reasonTypes = this.reasonTypes;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadFormalDebtCollectionCompanyContracts();
      }
    });
  }

  /**
   * Otwiera okno z blokadami dla wybranej umowy
   * @param {ContractWithFormalDebtCollectionExclusions} contract - wybrana umowa
   */
  showExclusions(contract: ContractWithFormalDebtCollectionExclusions) {
    const dialogRef = this.dialog.open(ContractFormalDebtCollectionExclusionsDialogComponent);
    dialogRef.componentInstance.contract = contract;
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.componentInstance.reasonTypes = this.reasonTypes;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadFormalDebtCollectionCompanyContracts();
      }
    });
  }

  /**
   * Ładuje umowy dla wybranego klienta
   */
  private loadFormalDebtCollectionCompanyContracts() {
    this.loadingContracts = true;
    this.contractService.getSimpleFormalDebtCollectionContracts(this.extCompanyId).subscribe(
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
    return this.loadingExclusions || this.loadingContracts || this.loadingReasonTypes;
  }
}
