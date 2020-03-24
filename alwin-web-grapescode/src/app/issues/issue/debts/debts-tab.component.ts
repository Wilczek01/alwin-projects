import {Component, Input, OnInit} from '@angular/core';
import {ContractService} from '../../../contract/contract.service';
import {ContractFinancialSummary} from '../../../contract/contract-financial-summary';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {Issue} from '../../shared/issue';
import {IssueService} from '../../shared/issue.service';
import {RefreshIssueService} from '../issue/refresh-issue.service';
import {UserAccessService} from '../../../common/user-access.service';
import {BalanceStartAndAdditionalCurrency} from '../../../company/balance-start-and-additional-currency';
import {BalanceStartAndAdditional} from '../../../company/balance-start-and-additional';

/**
 * Komponent z widokiem należności klienta w obsłudze zlecenia
 */
@Component({
  selector: 'alwin-debts-tab',
  templateUrl: './debts-tab.component.html',
  styleUrls: ['./debts-tab.component.css']
})
export class DebtsTabComponent implements OnInit {

  @Input()
  issue: Issue;
  @Input()
  readonly: boolean;

  notPaidOnly = true;
  overdueOnly = false;
  groupByContract = false;

  loading: boolean;
  balanceStartAndAdditionalLoading: boolean;
  balanceUpdating = false;

  activeContractNo: string;

  contractFinancialSummaries: ContractFinancialSummary[];
  balanceStartAndAdditional: BalanceStartAndAdditionalCurrency;

  constructor(private messageService: MessageService, private contractService: ContractService, private issueService: IssueService,
              private refreshIssueService: RefreshIssueService, private userAccessService: UserAccessService) {
  }

  ngOnInit(): void {
    this.loadContractFinancialSummaries(this.notPaidOnly, this.overdueOnly);
    this.loadBalanceStartAndAdditional();
  }

  updateOverdueOnlyFlagAndRefreshContracts(): void {
    if (!this.notPaidOnly) {
      this.overdueOnly = false;
    }
    this.refreshContracts();
  }

  /**
   * Odświeżenie listy kontraktów po kliknięciu "tylko nieopłacone" lub "tylko przedmioty klienta"
   */
  refreshContracts(): void {
    this.loadContractFinancialSummaries(this.notPaidOnly, this.overdueOnly);
  }

  /**
   * Aktualizacja sald zlecenia oraz powiązanych faktur
   */
  updateBalances(): void {
    this.balanceUpdating = true;
    this.issueService.updateBalances(this.issue.id).subscribe(
      response => {
        if (response.status === 202) {
          this.refreshIssueService.refreshIssue();
          this.messageService.showMessage('Salda zostały zaktualizowane');
        } else {
          this.messageService.showMessage('Nie udało się zaktualizować sald');
        }
        this.balanceUpdating = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.balanceUpdating = false;
      }
    );
  }

  /**
   * Pobranie zestawień finansowych kontraktów zlecenia
   */
  private loadContractFinancialSummaries(notPaidOnly, overdueOnly) {
    this.loading = true;
    this.contractService.getContractFinancialSummaries(this.issue.id, overdueOnly, notPaidOnly).subscribe(
      contractFinancialSummaries => {
        if (contractFinancialSummaries == null) {
          this.loading = false;
          return;
        }
        this.contractFinancialSummaries = contractFinancialSummaries;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Pobranie salda wymaganego i niewymaganego
   */
  private loadBalanceStartAndAdditional() {
    this.balanceStartAndAdditionalLoading = true;
    this.issueService.getIssueStartAndAdditionalBalance(this.issue.id).subscribe(
      balanceStartAndAdditional => {
        this.balanceStartAndAdditional = balanceStartAndAdditional;
        this.balanceStartAndAdditionalLoading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.balanceStartAndAdditionalLoading = false;
      });
  }

  /**
   * Sprawdza czy użytkownik jest managerem windykacji telefonicznej
   * @returns {boolean} - true jeżeli użytkownik jest managerem windykacji telefonicznej, false w przeciwnym wypadku
   */
  isPhoneDebtCollectorManager() {
    return this.userAccessService.isPhoneDebtCollectorManager();
  }

  isOpenIssue() {
    const key = this.issue.issueState.key;
    return 'NEW' === key || 'IN_PROGRESS' === key || 'WAITING_FOR_TERMINATION' === key;
  }

  isUpdateBalanceEnable() {
    return (this.isPhoneDebtCollectorManager() && this.isOpenIssue()) || !this.readonly;
  }

  getBalanceStart(currency: string): number {
    const balanceStartAndAdditional = this.getBalanceStartAndAdditional(currency);
    return balanceStartAndAdditional && balanceStartAndAdditional.balanceStart;
  }

  getBalanceAdditional(currency: string): number {
    const balanceStartAndAdditional = this.getBalanceStartAndAdditional(currency);
    return balanceStartAndAdditional && balanceStartAndAdditional.balanceAdditional;
  }

  getIssueBalance(currency: string): number {
    return this.getBalanceStart(currency) + this.getBalanceAdditional(currency);
  }

  private getBalanceStartAndAdditional(currency: string): BalanceStartAndAdditional {
    if (!this.balanceStartAndAdditional) {
      return;
    }
    switch (currency) {
      case 'PLN':
        return this.balanceStartAndAdditional.balanceStartAndAdditionalPLN;
      case 'EUR':
        return this.balanceStartAndAdditional.balanceStartAndAdditionalEUR;
      default:
        return;
    }
  }
}
