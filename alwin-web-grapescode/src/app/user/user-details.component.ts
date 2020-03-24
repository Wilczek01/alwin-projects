import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {FullUser} from './full-user';
import {UserService} from '../issues/shared/user.service';
import {OperatorService} from '../issues/shared/operator.service';
import {MessageService} from '../shared/message.service';
import {OperatorType} from '../operator/operator-type';
import {Operator} from '../operator/operator';
import {AddPermissionDialogComponent} from './dialog/add-permission-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {StringUtils} from '../issues/shared/utils/string-utils';
import {SelectManagerDialogComponent} from './dialog/select-manager-dialog.component';
import {ConfirmationDialogComponent} from '../shared/dialog/confirmation-dialog.component';
import {PasswordUtils} from './util/password-utils';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import { switchMap } from 'rxjs/operators';
@Component({
  selector: 'alwin-user-details',
  styleUrls: ['./user-details.component.css'],
  templateUrl: './user-details.component.html'
})
export class UserDetailsComponent implements OnInit {
  user: FullUser;
  types: OperatorType[];
  loading: boolean;
  errorMessage: string;

  constructor(private userService: UserService,
              private operatorService: OperatorService,
              private messageService: MessageService,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.loading = true;
    this.errorMessage = null;
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.userService.getUser(params.get('userId'))),
    ).subscribe(
      user => {
        if (user == null) {
          this.messageService.showMessage('Nie odnaleziono użytkownika');
          this.loading = false;
          return;
        }
        this.operatorService.getOperatorTypes().subscribe(
          types => {
            this.types = types;
            this.user = user;
            this.loading = false;
          },
          err => {
            this.handleError(err);
          }
        );
      },
      err => {
        this.handleError(err);
      }
    );
  }

  compareTypes(type1: OperatorType, type2: OperatorType): boolean {
    return type1 && type2 ? type1.id === type2.id : type1 === type2;
  }

  hasAnyPermission() {
    return this.user.operators && this.user.operators.find(operator => operator.permission != null);
  }

  hasAnyOperatorWithoutPermission() {
    return this.user.operators && this.user.operators.find(operator => operator.permission == null && !StringUtils.isNullOrEmptyOrUndefined(operator.login));
  }

  addNewPermission() {
    const dialogRef = this.dialog.open(AddPermissionDialogComponent);
    dialogRef.componentInstance.operators =
      this.user.operators.filter(operator => operator.permission == null && !StringUtils.isNullOrEmptyOrUndefined(operator.login));
  }

  updateUser() {
    this.loading = true;
    this.errorMessage = null;
    this.userService.updateUser(this.user).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Użytkownik został zaktualizowany');
          this.refreshUserData();
          this.loading = false;
        } else {
          this.messageService.showMessage('Nie udało się zaktualizować użytkownika');
          this.loading = false;
        }
      }
      ,
      err => {
        this.handleErrorWithValidation(err);
      }
    );
  }

  refreshUserData() {
    this.loading = true;
    this.errorMessage = null;
    this.userService.getUser(String(this.user.id)).subscribe(
      user => {
        if (user == null) {
          this.messageService.showMessage('Nie odnaleziono użytkownika');
          this.loading = false;
          return;
        }
        this.user = user;
        this.loading = false;
      },
      err => {
        this.handleError(err);
      }
    );
  }

  private handleError(error) {
    if (error.status === 404) {
      this.messageService.showMessage('Nie odnaleziono użytkownika');
    } else {
      HandlingErrorUtils.handleError(this.messageService, error);
    }
    this.loading = false;
  }

  private handleErrorWithValidation(error) {
    if (error.status === 404) {
      this.messageService.showMessage('Nie odnaleziono użytkownika');
    } else {
      HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, error);
    }
    this.loading = false;
  }

  addNewOperator() {
    if (this.user.operators == null) {
      this.user.operators = [];
    }
    this.user.operators.push(new Operator());
  }

  /**
   * Otwiera okno potwierdzające czy zresetować hasło podanego operatora. Resetuje hasło po potwierdzeniu.
   * @param {Operator} operator
   */
  confirmResetPassword(operator: Operator) {
    const newPassword = PasswordUtils.generateNewPassword(operator.id);
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Reset hasła';
    dialogRef.componentInstance.message = 'Czy na pewno zresetować hasło użytkownika?';
    dialogRef.componentInstance.warning = `UWAGA: zamierzasz zmienić hasło operatora na ${newPassword}`;
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.resetPassword(operator.id, newPassword);
      }
    });
  }

  /**
   * Resetuje hasło na podane dla operatora o podanym identyfikatorze
   *
   * @param {number} operatorId - identyfikator operatora
   * @param {string} newPassword - nowe hasło
   */
  resetPassword(operatorId: number, newPassword: string) {
    this.loading = true;
    this.operatorService.resetPassword(operatorId, newPassword).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Hasło zostało zresetowane');
          this.loading = false;
        } else {
          this.messageService.showMessage('Nie udało się zresetować hasła');
          this.loading = false;
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Usuwa podanego operatora
   *
   * @param {Operator} operator - operator do usunięcia
   */
  removeOperator(operator: Operator) {
    const index = this.user.operators.indexOf(operator, 0);
    if (index > -1) {
      this.user.operators.splice(index, 1);
    }
  }

  /**
   * Otwiera okno przypisania managera dla podanego operatora
   *
   * @param {Operator} operator - operator dla którego zostanie ustawiony manager
   */
  assignManager(operator: Operator) {
    const dialogRef = this.dialog.open(SelectManagerDialogComponent);
    dialogRef.afterClosed().subscribe(manager => {
      if (manager === false) {
        return;
      }
      if (manager != null) {
        operator.parentOperator = manager;
      }
    });
  }

}
