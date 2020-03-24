import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {CustomerOutOfService} from '../model/customer-out-of-service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {CustomerOutOfServiceInput} from '../model/customer-out-of-service-input';

/**
 * Komponent dla wiodku aktualizacji istniejącej blokady dla klienta
 */
@Component({
  selector: 'alwin-update-customer-exclusion-dialog',
  styleUrls: ['./customer-exclusion-dialog.component.css'],
  templateUrl: './update-customer-exclusion-dialog.component.html',
})
export class UpdateCustomerExclusionDialogComponent {
  loading: boolean;
  exclusion: CustomerOutOfService;

  constructor(public dialogRef: MatDialogRef<UpdateCustomerExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
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
    this.companyService.updateCustomerOutOfService(new CustomerOutOfServiceInput(this.exclusion), this.exclusion.id).subscribe(
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
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
