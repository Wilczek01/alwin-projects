import {Component, Input, OnInit} from '@angular/core';
import {Issue} from '../../shared/issue';
import {Customer} from '../../../customer/shared/customer';
import {Operator} from '../../../operator/operator';
import {AssignOperatorDialogComponent} from '../../../operator/assign/assign-operator-dialog.component';
import {MessageService} from '../../../shared/message.service';
import {OperatorService} from '../../shared/operator.service';
import { MatDialog } from '@angular/material/dialog';
import {FormControl} from '@angular/forms';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {UserAccessService} from '../../../common/user-access.service';
import {ExtCompanyService} from '../../../company/ext-company.service';

/**
 * Komponent z widokiem wszystkich danych klienta w obsłudze zlecenia
 */
@Component({
  selector: 'alwin-customer-tab',
  templateUrl: './customer-tab.component.html',
  styleUrls: ['./customer-tab.component.css']
})
export class CustomerTabComponent implements OnInit {

  @Input()
  issue: Issue;
  @Input()
  readonly: boolean;
  customer: Customer;
  loading = false;

  operatorControl: FormControl = new FormControl();
  operators: Operator[] = [];

  constructor(private messageService: MessageService,
              private dialog: MatDialog,
              private operatorService: OperatorService,
              private userAccessService: UserAccessService,
              private extCompanyService: ExtCompanyService) {
  }

  ngOnInit(): void {
    this.customer = this.issue.customer != null ? this.issue.customer : this.issue.contract.customer;
    this.fillCompanyLegalForm();
  }

  /**
   * Pobieranie opiekunów klientów
   */
  private loadOperators() {
    if (!this.isPhoneDebtCollectorManager()) {
      return;
    }
    this.loading = true;
    this.operatorService.getAccountManagers().subscribe(
      operators => {
        if (operators == null) {
          this.messageService.showMessage('Nie znaleziono opiekunów klientów');
          this.loading = false;
          return;
        }
        this.operators = operators;
        const emptyOperator = new Operator();
        this.operators.unshift(emptyOperator);
        this.loading = false;
      },
      err => {
        this.handleError(err);
      }
    );
  }

  /**
   * Uzupełnia formę prawną dla firmy
   *
   * TODO: naprawić poprzez zaciąganie do alwina formy prawnej podczas importu klienta (#24481)
   */
  private fillCompanyLegalForm() {
    this.loading = true;
    this.extCompanyService.findCompany(String(this.customer.company.extCompanyId)).subscribe(
      extCompany => {
        if (extCompany == null) {
          this.messageService.showMessage('Nie znaleziono klienta');
          this.loadOperators();
          return;
        }
        if (extCompany.legalForm != null) {
          this.customer.company.legalForm = extCompany.legalForm.symbol;
        }
        this.loadOperators();
      },
      err => {
        this.loadOperators();
        this.handleError(err);
      }
    );
  }

  /**
   * Obsługa błedu http
   *
   * @param err - błąd http
   */
  private handleError(err) {
    HandlingErrorUtils.handleError(this.messageService, err);
    this.loading = false;
  }

  /**
   * Otwarcie okna to modyfikacji opiekuna klienta
   *
   * @param {Operator} currentAccountManager - aktualny opiekun klienta
   */
  openAssignment(currentAccountManager: Operator) {
    const dialogRef = this.dialog.open(AssignOperatorDialogComponent);
    dialogRef.componentInstance.updateAccountManager = true;
    dialogRef.componentInstance.customer = this.customer;
    dialogRef.componentInstance.currentOperator = currentAccountManager;
    dialogRef.componentInstance.operators = this.operators.filter(operator => operator.id != null);
  }

  /**
   * Sprawdza czy użytkownik jest managerem windykacji telefonicznej
   * @returns {boolean} - true jeżeli użytkownik jest managerem windykacji telefonicznej, false w przeciwnym wypadku
   */
  isPhoneDebtCollectorManager() {
    return this.userAccessService.isPhoneDebtCollectorManager();
  }
}
