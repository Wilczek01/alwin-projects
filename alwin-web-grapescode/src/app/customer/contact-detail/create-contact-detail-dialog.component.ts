import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../shared/message.service';
import { MatDialogRef } from '@angular/material/dialog';
import {ContactDetailService} from './contact-detail-service';
import {ContactDetailDialogComponent} from './contact-detail-dialog.component';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';

/**
 * Komponent dla widoku dodawania nowego kontaktu dla załadowanego klienta
 */
@Component({
  selector: 'alwin-create-contact-detail-dialog',
  styleUrls: ['./create-contact-detail-dialog.component.css'],
  templateUrl: './create-contact-detail-dialog.component.html',
})
export class CreateContactDetailDialogComponent extends ContactDetailDialogComponent implements OnInit {
  personId: number;
  companyId: number;

  constructor(public dialogRef: MatDialogRef<CreateContactDetailDialogComponent>, contactDetailService: ContactDetailService, messageService: MessageService) {
    super(contactDetailService, messageService);
  }

  ngOnInit() {
    this.loadContactDetailsTypes();
    this.loadContactDetailsStates();
  }

  /**
   * Tworzy nowy kontakt dla załadowanego klienta
   */
  createContactDetail() {
    this.loading = true;
    this.contactDetailService.addContactDetailForCustomer(this.contactDetail, this.companyId, this.personId).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Kontakt został utworzony');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się zapisać kontaktu');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }
}
