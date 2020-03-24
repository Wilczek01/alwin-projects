import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {ContractOutOfService} from '../model/contract-out-of-service';
import {ContractOutOfServiceInput} from '../model/contract-out-of-service-input';

/**
 * Komponent dla wiodku edycji istniejącej blokady dla umowy klienta
 */
@Component({
  selector: 'alwin-update-contract-exclusion-dialog',
  styleUrls: ['./contract-exclusion-dialog.component.css'],
  templateUrl: './update-contract-exclusion-dialog.component.html',
})
export class UpdateContractExclusionDialogComponent {
  loading: boolean;
  extCompanyId: string;
  contractNo: string;
  exclusion: ContractOutOfService;

  constructor(public dialogRef: MatDialogRef<UpdateContractExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
  }

  /**
   * Aktualizuje istniejącą blokadę dla umowy
   */
  updateExclusion() {
    if (this.extCompanyId == null || this.contractNo == null || this.exclusion == null || this.exclusion.id == null) {
      this.messageService.showMessage('Wystąpił błąd zapisu - brak zdefiniowanego klienta lub umowy');
      return;
    }
    this.loading = true;
    this.companyService.updateContractOutOfService(new ContractOutOfServiceInput(this.exclusion), this.extCompanyId, this.exclusion.id).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Blokada został zaktualizowana');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zaktualizować blokady');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
