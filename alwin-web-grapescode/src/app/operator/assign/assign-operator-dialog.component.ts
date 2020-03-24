import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {Operator} from '../operator';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {ConfirmationDialogComponent} from '../../shared/dialog/confirmation-dialog.component';
import {OperatorService} from '../../issues/shared/operator.service';
import {MessageService} from '../../shared/message.service';
import {HttpParams} from '@angular/common/http';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {CompanyService} from '../../company/company.service';
import {Customer} from '../../customer/shared/customer';
import { map, startWith } from 'rxjs/operators';
@Component({
  selector: 'alwin-assign-operator-dialog',
  styleUrls: ['./assign-operator-dialog.component.css'],
  templateUrl: './assign-operator-dialog.component.html',
})
export class AssignOperatorDialogComponent implements OnInit {

  loading = false;
  assignToAll: boolean;
  issues: number[];
  currentOperator: Operator;
  operators: Operator[];
  all: number;
  params: HttpParams;
  updateAccountManager: boolean;
  customer: Customer;
  dialogTitle = 'Przypisanie operatora';

  operatorControl: FormControl = new FormControl();
  filteredOptions: Observable<Operator[]>;

  constructor(private dialog: MatDialog,
              public dialogRef: MatDialogRef<AssignOperatorDialogComponent>,
              private operatorService: OperatorService,
              private companyService: CompanyService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    if (this.updateAccountManager) {
      this.dialogTitle = 'Przypisanie opiekuna klienta';
    }

    if (((this.issues != null && this.issues.length === 1) || this.updateAccountManager) && this.currentOperator != null) {
      const matchingOperators = this.operators.filter(operator => operator.id === this.currentOperator.id);
      if (matchingOperators.length !== 0) {
        this.operatorControl.setValue(matchingOperators[0]);
      }
    }

    if (this.assignToAll) {
      let params = new HttpParams();
      // przepisywanie z powodu duplikowania parametrów
      this.params.keys().forEach(key => {
        this.params.getAll(key).forEach(value => params = params.append(key, value));
      });
      this.params = params.append('updateAll', 'true');
    }

    this.filteredOptions = this.operatorControl.valueChanges.pipe(
      startWith(null),
      map(operator => operator && typeof operator === 'object' ? operator.user.firstName : operator),
      map(name => name ? this.filterOperators(name) : this.operators.slice())
    );
  }

  filterOperators(name: string): Operator[] {
    return this.operators.filter(operator =>
      operator.user.firstName.toLowerCase().indexOf(name.toLowerCase()) === 0 ||
      operator.user.lastName.toLowerCase().indexOf(name.toLowerCase()) === 0 ||
      `${operator.user.firstName} ${operator.user.lastName}`.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  displayOperator(operator: Operator): string {
    return operator ? `${operator.user.firstName} ${operator.user.lastName}` : '';
  }

  changeAssignment() {
    if (this.updateAccountManager) {
      this.updateCustomerAccountManager();
    } else if (this.assignToAll || this.issues.length > 1) {
      const confirmationDialogRef = this.dialog.open(ConfirmationDialogComponent);
      const issuesToUpdate = this.assignToAll ? this.all : this.issues.length;
      this.prepareMessage(confirmationDialogRef, issuesToUpdate);
      confirmationDialogRef.componentInstance.title = 'Przypisanie zlecenia';
      confirmationDialogRef.afterClosed().subscribe(confirmed => {
        if (confirmed) {
          this.determineAssignment(issuesToUpdate);
        }
      });
    } else {
      this.determineAssignment(1);
    }
  }

  determineAssignment(issuesToUpdate: number) {
    if (this.operatorControl.value == null || this.operatorControl.value === '') {
      this.unassign(issuesToUpdate);
    } else {
      this.assign(issuesToUpdate);
    }
  }

  private updateCustomerAccountManager() {
    const accountManagerId = this.findAccountManagerId();
    this.companyService.updateAccountManagers(this.customer.id, accountManagerId).subscribe(
      response => this.handleUpdateAccountManagerResponse(response, this.customer.id),
      err => this.handleError(err)
    );
  }

  private findAccountManagerId() {
    let accountManagerId: number = null;
    if (this.operatorControl.value != null && this.operatorControl.value !== '') {
      accountManagerId = this.operatorControl.value.id;
    }
    return accountManagerId;
  }

  private unassign(issuesToUpdate: number) {
    this.operatorService.unassignIssues(this.issues, this.assignToAll ? this.params : null).subscribe(
      response => this.handleUpdateIssuesResponse(response, issuesToUpdate),
      err => this.handleError(err)
    );
  }

  private assign(issuesToUpdate: number) {
    this.operatorService.assignIssues(this.operatorControl.value.id, this.issues, this.assignToAll ? this.params : null).subscribe(
      response => this.handleUpdateIssuesResponse(response, issuesToUpdate),
      err => this.handleError(err)
    );
  }

  private handleError(response) {
    HandlingErrorUtils.handleError(this.messageService, response);
    this.loading = false;
  }

  private handleUpdateAccountManagerResponse(response, customerId: number) {
    const successMessage = `Opiekun klienta (${customerId}) został zaktualizowany`;
    const errorMessage = `Nie udało się zaktualizować opiekuna klienta (${customerId})`;
    this.handleResponse(response, successMessage, errorMessage);
  }

  private handleUpdateIssuesResponse(response, issuesToUpdate: number) {
    const successMessage = `Zlecenia (${issuesToUpdate}) zostały zaktualizowane`;
    const errorMessage = `Nie udało się zaktualizować zleceń (${issuesToUpdate})`;
    this.handleResponse(response, successMessage, errorMessage);
  }

  private handleResponse(response, successMessage: string, errorMessage: string) {
    if (response.status === 202) {
      this.setCustomerAccountManagerAfterSuccessfulUpdate();
      this.messageService.showMessage(successMessage);
      this.loading = false;
      this.dialogRef.close(true);
      return;
    }
    this.messageService.showMessage(errorMessage);
    this.loading = false;
  }

  private setCustomerAccountManagerAfterSuccessfulUpdate() {
    if (!this.updateAccountManager) {
      return;
    }
    const accountManagerId = this.findAccountManagerId();
    let accountManager = null;
    if (accountManagerId != null) {
      const matchingOperators = this.operators.filter(operator => operator.id === accountManagerId);
      if (matchingOperators.length !== 0) {
        accountManager = matchingOperators[0];
      }
    }
    this.customer.accountManager = accountManager;
  }

  prepareMessage(dialogRef: MatDialogRef<ConfirmationDialogComponent>, issuesToUpdate: number) {
    if (this.operatorControl.value != null && this.operatorControl.value !== '') {
      const user = this.operatorControl.value.user;
      dialogRef.componentInstance.message = `Zamierzasz przypisać ${issuesToUpdate} zleceń do ${user.firstName} ${user.lastName}. Czy kontynuować?`;
    } else {
      dialogRef.componentInstance.message = `Zamierzasz usunąć przypisanie w ${issuesToUpdate} zleceniach. Czy kontynuować?`;
    }
  }

  unknownOperatorAssigned(): boolean {
    return this.operatorControl.value === null || !this.operatorControl.value.length;
  }
}
