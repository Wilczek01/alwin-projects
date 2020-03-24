import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {OperatorService} from '../../issues/shared/operator.service';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {NavigationUtil} from '../../shared/authentication/navigation-util';

/**
 * Komponent dla widoku zmiany hasła
 */
@Component({
  selector: 'alwin-change-password-form',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  newPassword: string;
  repeatPassword: string;
  loading = false;

  constructor(private router: Router,
              private operatorService: OperatorService,
              private messageService: MessageService) {
  }

  /**
   * Zmienia hasło dla zalogowanego operatora. Aktualizuje flagę wymuszającą zmianę hasła na false w przypadku poprawnej zmiany.
   */
  changePassword() {
    this.loading = true;
    this.operatorService.changePassword(this.newPassword).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Hasło zostało zmienione');
          const user = JSON.parse(localStorage.getItem('currentUser'));
          localStorage.setItem('currentUser', JSON.stringify({
            username: user.username,
            role: user.role,
            roleLabel: user.roleLabel,
            forceUpdatePassword: false,
            token: user.token
          }));
          NavigationUtil.defaultRedirectPage(this.router, user.role);
          this.loading = false;
        } else {
          this.messageService.showMessage('Nie udało się zmienić hasła');
          this.loading = false;
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }
}
