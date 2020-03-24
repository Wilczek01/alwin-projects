import {Component} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {ContractWithExclusions} from '../../../contract/contract-with-exclusions';
import {ContractOutOfService} from '../model/contract-out-of-service';
import {UpdateContractExclusionDialogComponent} from './update-contract-exclusion-dialog.component';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';

/**
 * Komponent odpowiedzialny za wyświetlanie blokad dla wybranej umowy w formie dialogu
 */
@Component({
  selector: 'alwin-contract-exclusions-dialog',
  styleUrls: ['./contract-exclusions-dialog.component.css'],
  templateUrl: './contract-exclusions-dialog.component.html',
})
export class ContractExclusionsDialogComponent {

  contract: ContractWithExclusions;
  extCompanyId: string;
  loading: boolean;

  constructor(public dialogRef: MatDialogRef<ContractExclusionsDialogComponent>, private dialog: MatDialog,
              private companyService: CompanyService, private messageService: MessageService) {
  }

  /**
   * Otwiera okno umożliwiające edycję wybranej blokady umowy
   * @param {ContractOutOfService} exclusion - wybrana blokada
   */
  editExclusion(exclusion: ContractOutOfService) {
    const dialogRef = this.dialog.open(UpdateContractExclusionDialogComponent);
    dialogRef.componentInstance.extCompanyId = this.extCompanyId;
    dialogRef.componentInstance.contractNo = this.contract.contractNo;
    const exclusionToEdit = new ContractOutOfService();
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
    this.companyService.deleteContractOutOfService(this.extCompanyId, exclusionId).subscribe(
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
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
