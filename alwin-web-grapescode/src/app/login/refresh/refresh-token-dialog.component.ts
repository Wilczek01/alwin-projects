import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {User} from '../../user/user';
import {AuthenticationService} from '../../shared/authentication/authentication.service';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';

@Component({
  selector: 'alwin-refresh-token-dialog',
  styleUrls: ['./refresh-token-dialog.component.css'],
  templateUrl: './refresh-token-dialog.component.html',
})
export class RefreshTokenDialogComponent {
  user: User = new User();
  loading = false;
  refreshSessionToken: Boolean = true;

  constructor(private authenticationService: AuthenticationService,
              private messageService: MessageService,
              private dialogRef: MatDialogRef<RefreshTokenDialogComponent>) {
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.user.username, this.user.password)
      .subscribe(result => {
          if (result === true) {
            this.messageService.showMessage('Sesja została odświeżona');
            this.loading = false;
            this.dialogRef.close(true);
          } else {
            this.messageService.showMessage('Wystapił błąd połączenia');
            this.loading = false;
          }
        },
        err => {
          if (err.status === 401) {
            this.messageService.showMessage('Niepoprawny login lub hasło');
          } else {
            HandlingErrorUtils.handleError(this.messageService, err);
          }
          this.loading = false;
        }
      );
  }
}
