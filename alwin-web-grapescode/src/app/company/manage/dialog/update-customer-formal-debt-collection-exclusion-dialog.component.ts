import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {DemandForPaymentTypeConst} from '../../../demand/model/demand-for-payment-type-const';
import {KeyLabel} from '../../../shared/key-label';
import {FormalDebtCollectionCustomerOutOfService} from '../model/formal-debt-collection-customer-out-of-service';
import {FormalDebtCollectionCustomerOutOfServiceInput} from 'app/company/manage/model/formal-debt-collection-customer-out-of-service-input';

/**
 * Komponent dla wiodku aktualizacji istniejącej blokady dla klienta w windykacji formalnej
 */
@Component({
  selector: 'alwin-update-customer-formal-debt-collection-exclusion-dialog',
  styleUrls: ['./customer-exclusion-dialog.component.css'],
  templateUrl: './update-customer-formal-debt-collection-exclusion-dialog.component.html',
})
export class UpdateCustomerFormalDebtCollectionExclusionDialogComponent {
  loading: boolean;
  exclusion: FormalDebtCollectionCustomerOutOfService;
  demandForPaymentTypes: DemandForPaymentTypeConst[] = [DemandForPaymentTypeConst.FIRST, DemandForPaymentTypeConst.SECOND];
  reasonTypes: KeyLabel[];

  constructor(public dialogRef: MatDialogRef<UpdateCustomerFormalDebtCollectionExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
  }

  /**
   * Aktualizuje istniejącą blokadę dla klienta
   */
  updateExclusion() {
    if (this.exclusion == null || this.exclusion.id == null) {
      this.messageService.showMessage('Wystąpił błąd aktualizacji - brak istniejacej blokady');
      return;
    }
    this.loading = true;
    this.companyService.updateFormalDebtCollectionCustomerOutOfService(new FormalDebtCollectionCustomerOutOfServiceInput(this.exclusion), this.exclusion.id).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Blokada została zaktualizowana');
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
