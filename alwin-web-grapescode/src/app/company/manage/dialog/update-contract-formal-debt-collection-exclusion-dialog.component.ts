import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {DemandForPaymentTypeConst} from '../../../demand/model/demand-for-payment-type-const';
import {KeyLabel} from '../../../shared/key-label';
import {FormalDebtCollectionContractOutOfServiceInput} from '../model/formal-debt-collection-contract-out-of-service-input';
import {FormalDebtCollectionContractOutOfService} from '../model/formal-debt-collection-contract-out-of-service';

/**
 * Komponent dla wiodku edycji istniejącej blokady dla umowy klienta w windykacji formalnej
 */
@Component({
  selector: 'alwin-update-contract-formal-debt-collection-exclusion-dialog',
  styleUrls: ['./contract-exclusion-dialog.component.css'],
  templateUrl: './update-contract-formal-debt-collection-exclusion-dialog.component.html',
})
export class UpdateContractFormalDebtCollectionExclusionDialogComponent {
  loading: boolean;
  extCompanyId: string;
  contractNo: string;
  exclusion: FormalDebtCollectionContractOutOfService;
  demandForPaymentTypes: DemandForPaymentTypeConst[] = [DemandForPaymentTypeConst.FIRST, DemandForPaymentTypeConst.SECOND];
  reasonTypes: KeyLabel[];

  constructor(public dialogRef: MatDialogRef<UpdateContractFormalDebtCollectionExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
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
    this.companyService.updateFormalDebtCollectionContractOutOfService(new FormalDebtCollectionContractOutOfServiceInput(this.exclusion), this.extCompanyId, this.exclusion.id).subscribe(
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
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
