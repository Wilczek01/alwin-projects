import {Component, OnInit} from '@angular/core';
import {Issue} from '../shared/issue';
import {IssueService} from '../shared/issue.service';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../shared/utils/handling-error-utils';
import {PhoneCallService} from './phone-call/phone-call.service';
import {RefreshIssueService} from './issue/refresh-issue.service';
import {ActivatedRoute} from '@angular/router';
import {UserAccessService} from '../../common/user-access.service';
import {RoleType} from '../shared/role-type';
import {Observable} from 'rxjs';
import {OperatedIssue} from '../shared/operated-issue';

/**
 * Komponent dla widoku obsługi zlecenia
 */
@Component({
  selector: 'alwin-issue',
  templateUrl: './issue.component.html',
  styleUrls: ['../../contract/issue.component.css'],
  providers: [PhoneCallService]
})
export class IssueComponent implements OnInit {
  issue: Issue;
  companyId: number;
  loading: boolean;
  issueProgress: number;
  selectedTabIndex = 0;
  readonly = false;
  role = RoleType;

  constructor(private issueService: IssueService, private messageService: MessageService, private refreshIssueService: RefreshIssueService,
              private route: ActivatedRoute, private userAccessService: UserAccessService) {
    refreshIssueService.issue$.subscribe(() => {
      this.reloadIssue();
    });
    this.readonly = this.route.snapshot.data['readonly'];
  }

  /**
   * Inicjalizacja komponentu przy załadowaniu strony
   */
  ngOnInit() {
    this.loadIssueData();
  }

  /**
   * Załadowanie zlecenia
   */
  private loadIssueData() {
    this.loading = true;
    const issueId = this.route.snapshot.params['issueId'];
    if (issueId != null) {
      this.loadOperatedIssue(this.issueService.getIssueById(issueId));
      return;
    }
    this.loadIssue(this.issueService.getMyWork());
  }

  /**
   * Pobiera zlecenie do wyświetlenia z informacją czy jest dostępna edycja dla zalogowanego operatora
   */
  private loadOperatedIssue(observableIssue: Observable<OperatedIssue>) {
    observableIssue.subscribe(
      operatedIssue => {
        if (operatedIssue == null) {
          this.messageService.showMessage('Nie znaleziono zlecenia');
          this.loading = false;
          return;
        }
        this.updateIssue(operatedIssue.issue);
        this.updateReadOnly(operatedIssue.editable);
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  private updateIssue(loadedIssue: Issue) {
    this.issue = loadedIssue;
    this.companyId = this.issue.customer != null ? this.issue.customer.company.id : this.issue.contract.customer.company.id;
    this.issueProgress = this.determineIssueProgress();
  }

  /**
   * Pobiera zlecenie do wyświetlenia
   */
  private loadIssue(observableIssue: Observable<Issue>) {
    observableIssue.subscribe(
      issue => {
        if (issue == null) {
          this.messageService.showMessage('Nie znaleziono zlecenia');
          this.loading = false;
          return;
        }
        this.updateIssue(issue);
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Aktualizacja flagi readonly po załadowaniu zlecenia
   */
  updateReadOnly(editable: boolean) {
    if (this.isOpenIssue() && editable) {
      this.readonly = false;
    }
  }

  /**
   * Sprawdza czy zlecenie jest otwarte
   * @returns {boolean} true jeśli zlecenie jest otwarte, false - w przeciwnym przypadku
   */
  isOpenIssue() {
    const key = this.issue.issueState.key;
    return 'NEW' === key || 'IN_PROGRESS' === key || 'WAITING_FOR_TERMINATION' === key;
  }

  /**
   * Odświeżenie danych zlecenia
   */
  private reloadIssue() {
    this.loading = true;
    this.issueService.getIssueById(this.issue.id).subscribe(
      operatedIssue => {
        this.issue = operatedIssue.issue;
        this.updateReadOnly(operatedIssue.editable);
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Zwraca postęp zlecenia wyrażonych w procentach
   *
   * @returns {number} - wartość procentowa od 0 do 100 ukończenia zlecenia
   */
  determineIssueProgress(): number {
    const eventStartTime = new Date(this.issue.startDate).valueOf();
    const eventEndTime = new Date(this.issue.expirationDate).valueOf();
    const duration = eventEndTime - eventStartTime;
    const currentTime = Date.now().valueOf();
    const timeLeft = eventEndTime - currentTime;
    if (timeLeft <= 0) {
      return 100;
    } else if (eventStartTime >= currentTime) {
      return 0;
    }
    return Math.round(100 * (duration - timeLeft) / duration);
  }

  /**
   * Sprawdza czy podana rola użytkownika jest taka sama jak rola zalogowanego użytkownika
   * @param {string} role - sprawdzana rola
   * @returns {boolean} - true jeżeli podana rola jest taka sama jak rola zalogowanego użytkownika, false w przeciwnym wypadku
   */
  hasRole(role: string) {
    return this.userAccessService.hasRole(role);
  }
}
