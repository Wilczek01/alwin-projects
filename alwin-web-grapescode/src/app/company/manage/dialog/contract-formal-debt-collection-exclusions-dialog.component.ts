import {Component} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {ContractWithFormalDebtCollectionExclusions} from '../../../contract/contract-with-formal-debt-collection-exclusions';
import {UpdateContractFormalDebtCollectionExclusionDialogComponent} from './update-contract-formal-debt-collection-exclusion-dialog.component';
import {FormalDebtCollectionContractOutOfService} from 'app/company/manage/model/formal-debt-collection-contract-out-of-service';
import {KeyLabel} from '../../../shared/key-label';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';

/**
 * Komponent odpowiedzialny za wyświetlanie blokad dla wybranej umowy windykacji formalnej w formie dialogu
 */
@Component({
  selector: 'alwin-contract-formal-debt-collection-exclusions-dialog',
  styleUrls: ['./contract-formal-debt-collection-exclusions-dialog.component.css'],
  templateUrl: './contract-formal-debt-collection-exclusions-dialog.component.html',
})
export class ContractFormalDebtCollectionExclusionsDialogComponent {

  contract: ContractWithFormalDebtCollectionExclusions;
  extCompanyId: string;
  loading: boolean;
  reasonTypes: KeyLabel[];

  constructor(public dialogRef: MatDialogRef<ContractFormalDebtCollectionExclusionsDialogComponent>, private dialog: MatDialog,
              private companyService: CompanyService, private messageService: MessageService) {
  }

  /**
   * Otwiera okno umożliwiające edycję wybranej blokady umowy
   * @param {ContractWithFormalDebtCollectionExclusions} exclusion - wybrana blokada
   */
  editExclusion(exclusion: ContractWithFormalDebtCollectionExclusions) {
    const dialogRef = this.dialog.open(UpdateContractFormalDebtCollectionExclusionDialogComponent);
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.componentInstance.contractNo = this.contract.contractNo;
    dialogRef.componentInstance.reasonTypes = this.reasonTypes;
    const exclusionToEdit = new FormalDebtCollectionContractOutOfService();
    Object.assign(exclusionToEdit, exclusion);
    dialogRef.componentInstance.exclusion = exclusionToEdit;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.dialogRef.close(true);
      }
    });
  }

  /**
   * Otwiera okno umożliwiające usunięcie wybranej blokady umowy wybraneo klienta
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
   * Usuwa blokadę umowy klienta o podanym identyfikatorze, a następnie odświeża listę wszystkich umów klienta
   * @param {number} exclusionId - identyfikator wybranej blokady
   */
  deleteExclusion(exclusionId: number) {
    this.loading = true;
    this.companyService.deleteFormalDebtCollectionContractOutOfService(this.extCompanyId, exclusionId).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Blokada została usunięta');
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się usunąć blokady');
        }
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
