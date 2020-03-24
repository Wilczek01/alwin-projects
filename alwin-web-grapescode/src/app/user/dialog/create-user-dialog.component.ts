import {Component} from '@angular/core';
import {KeyLabel} from '../../shared/key-label';
import {MessageService} from '../../shared/message.service';
import { MatDialogRef } from '@angular/material/dialog';
import {UserService} from '../../issues/shared/user.service';
import {FullUser} from '../full-user';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';

@Component({
  selector: 'alwin-create-user-dialog',
  styleUrls: ['./create-user-dialog.component.css'],
  templateUrl: './create-user-dialog.component.html',
})
export class CreateUserDialogComponent {
  user = new FullUser();
  loading: boolean;
  types: KeyLabel[];

  constructor(public dialogRef: MatDialogRef<CreateUserDialogComponent>,
              private userService: UserService,
              private messageService: MessageService) {
  }

  createUser() {
    this.loading = true;
    this.userService.createUser(this.user).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Użytkownik został utworzony');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się utworzyć użytkownika');
          this.loading = false;
        }
      }
      ,
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
