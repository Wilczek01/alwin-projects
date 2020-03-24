import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../shared/message.service';
import { MatDialogRef } from '@angular/material/dialog';
import {ContactDetailService} from './contact-detail-service';
import {ContactDetailDialogComponent} from './contact-detail-dialog.component';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';

/**
 * Komponent dla widoku aktualizacji załadowanego kontaktu dla klienta
 */
@Component({
  selector: 'alwin-update-contact-detail-dialog',
  styleUrls: ['./update-contact-detail-dialog.component.css'],
  templateUrl: './update-contact-detail-dialog.component.html',
})
export class UpdateContactDetailDialogComponent extends ContactDetailDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<UpdateContactDetailDialogComponent>, contactDetailService: ContactDetailService, messageService: MessageService) {
    super(contactDetailService, messageService);
  }

  ngOnInit() {
    this.loadContactDetailsTypes();
    this.loadContactDetailsStates();
  }

  /**
   * Aktualizuje załadowany kontakt
   */
  updateContactDetail() {
    this.loading = true;
    this.contactDetailService.updateContactDetail(this.contactDetail).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Kontakt został zapisany');
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
