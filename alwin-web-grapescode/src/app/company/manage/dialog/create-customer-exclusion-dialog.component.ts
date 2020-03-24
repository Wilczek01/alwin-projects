import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {CompanyService} from '../../company.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {CustomerOutOfServiceInput} from '../model/customer-out-of-service-input';

/**
 * Komponent dla wiodku tworzenia nowej blokady dla klienta
 */
@Component({
  selector: 'alwin-create-customer-exclusion-dialog',
  styleUrls: ['./customer-exclusion-dialog.component.css'],
  templateUrl: './create-customer-exclusion-dialog.component.html',
})
export class CreateCustomerExclusionDialogComponent {
  extCompanyId: string;
  loading: boolean;
  exclusion: CustomerOutOfServiceInput = new CustomerOutOfServiceInput();

  constructor(public dialogRef: MatDialogRef<CreateCustomerExclusionDialogComponent>, private companyService: CompanyService, private messageService: MessageService) {
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
    this.companyService.addCustomerOutOfService(this.extCompanyId, this.exclusion).subscribe(
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
