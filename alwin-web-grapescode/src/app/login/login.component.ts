import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../shared/authentication/authentication.service';
import {Router} from '@angular/router';
import {User} from '../user/user';
import {MessageService} from '../shared/message.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';

@Component({
  selector: 'alwin-login-form',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  loading = false;

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.authenticationService.logout();
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.user.username, this.user.password)
      .subscribe(result => {
          if (result === true) {
            this.router.navigate(['/']);
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
