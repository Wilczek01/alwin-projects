import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {SmsService} from './sms.service';
import {SmsTemplate} from './sms-temlate';
import {MessageService} from '../shared/message.service';
import {SmsMessage} from './sms-message';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';

/**
 * Komponent dla widoku wysyłania wiadomości sms na podany numer dla załadowanego klienta
 */
@Component({
  selector: 'alwin-send-sms-message-dialog',
  styleUrls: ['send-sms-message-dialog.component.css'],
  templateUrl: 'send-sms-message-dialog.component.html',
})
export class SendSmsMessageDialogComponent implements OnInit {
  loading: boolean;
  issueId: number;
  smsMessage: String;
  phoneNumber: String;
  smsTemplates: SmsTemplate[];

  constructor(public dialogRef: MatDialogRef<SendSmsMessageDialogComponent>,
              private smsService: SmsService, private messageService: MessageService) {
  }

  ngOnInit() {
    this.loadSmsTemplates();
  }

  /**
   * Ładuje dostępne szablony wiadomości sms
   */
  loadSmsTemplates() {
    this.loading = true;
    this.smsService.getSmsTemplates().subscribe(
      templates => {
        if (templates == null) {
          this.loading = false;
          return;
        }
        this.smsTemplates = templates;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Wysyła wiadomość sms
   */
  sendSmsMessage() {
    this.loading = true;
    const smsMessage = new SmsMessage(this.issueId, this.smsMessage, this.phoneNumber);
    this.smsService.sendSmsMessage(smsMessage).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Wiadomość sms została wysłana');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się wysłać wiadomości sms');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }
}
