import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {FormalDebtCollectionCustomerOutOfServiceInput} from '../model/formal-debt-collection-customer-out-of-service-input';
import {KeyLabel} from '../../../shared/key-label';
import {DemandForPaymentTypeConst} from '../../../demand/model/demand-for-payment-type-const';

/**
 * Komponent dla wiodku tworzenia nowej blokady dla klienta w windykacji formalnej
 */
@Component({
  selector: 'alwin-create-customer-formal-debt-collection-exclusion-dialog',
  styleUrls: ['./customer-exclusion-dialog.component.css'],
  templateUrl: './create-customer-formal-debt-collection-exclusion-dialog.component.html',
})
export class CreateCustomerFormalDebtCollectionExclusionDialogComponent {
  extCompanyId: string;
  loading: boolean;
  exclusion: FormalDebtCollectionCustomerOutOfServiceInput = new FormalDebtCollectionCustomerOutOfServiceInput();
  demandForPaymentTypes: DemandForPaymentTypeConst[] = [DemandForPaymentTypeConst.FIRST, DemandForPaymentTypeConst.SECOND];
  reasonTypes: KeyLabel[];

  constructor(public dialogRef: MatDialogRef<CreateCustomerFormalDebtCollectionExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
  }

  /**
   * Tworzy nową blokadę dla klienta
   */
  createExclusion() {
    if (this.extCompanyId == null) {
      this.messageService.showMessage('Wystąpił błąd zapisu - brak zdefiniowanego klienta');
      return;
    }
    this.loading = true;
    this.companyService.addFormalDebtCollectionCustomerOutOfService(this.extCompanyId, this.exclusion).subscribe(
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
