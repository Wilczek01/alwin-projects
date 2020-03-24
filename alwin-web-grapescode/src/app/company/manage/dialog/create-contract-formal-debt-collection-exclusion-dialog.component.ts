import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {FormalDebtCollectionContractOutOfServiceInput} from '../model/formal-debt-collection-contract-out-of-service-input';
import {DemandForPaymentTypeConst} from '../../../demand/model/demand-for-payment-type-const';
import {KeyLabel} from '../../../shared/key-label';

/**
 * Komponent dla wiodku tworzenia nowej blokady w windykacji formalnej dla umowy klienta
 */
@Component({
  selector: 'alwin-create-contract-formal-debt-collection-exclusion-dialog',
  styleUrls: ['./contract-exclusion-dialog.component.css'],
  templateUrl: './create-contract-formal-debt-collection-exclusion-dialog.component.html',
})
export class CreateContractFormalDebtCollectionExclusionDialogComponent {
  loading: boolean;
  extCompanyId: string;
  contractNo: string;
  exclusion: FormalDebtCollectionContractOutOfServiceInput = new FormalDebtCollectionContractOutOfServiceInput();
  demandForPaymentTypes: DemandForPaymentTypeConst[] = [DemandForPaymentTypeConst.FIRST, DemandForPaymentTypeConst.SECOND];
  reasonTypes: KeyLabel[];

  constructor(public dialogRef: MatDialogRef<CreateContractFormalDebtCollectionExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
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
    this.companyService.addFormalDebtCollectionContractOutOfService(this.exclusion, this.extCompanyId, this.contractNo).subscribe(
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
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
