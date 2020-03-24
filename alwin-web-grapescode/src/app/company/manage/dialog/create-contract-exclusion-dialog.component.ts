import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {ContractOutOfService} from '../model/contract-out-of-service';
import {ContractOutOfServiceInput} from '../model/contract-out-of-service-input';

/**
 * Komponent dla wiodku tworzenia nowej blokady dla umowy klienta
 */
@Component({
  selector: 'alwin-create-contract-exclusion-dialog',
  styleUrls: ['./contract-exclusion-dialog.component.css'],
  templateUrl: './create-contract-exclusion-dialog.component.html',
})
export class CreateContractExclusionDialogComponent {
  loading: boolean;
  extCompanyId: string;
  contractNo: string;
  exclusion: ContractOutOfServiceInput = new ContractOutOfServiceInput();

  constructor(public dialogRef: MatDialogRef<CreateContractExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
  }

  /**
   * Tworzy nową blokadę dla umowy
   */
  createExclusion() {
    if (this.extCompanyId == null || this.contractNo == null) {
      this.messageService.showMessage('Wystąpił błąd zapisu - brak zdefiniowanego klienta lub umowy');
      return;
    }
    this.loading = true;
    this.companyService.addContractOutOfService(this.exclusion, this.extCompanyId, this.contractNo).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Blokada został utworzona');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się utworzyć blokady');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
