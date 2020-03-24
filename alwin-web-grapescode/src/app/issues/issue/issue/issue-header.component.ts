import {Component, Input} from '@angular/core';
import {Issue} from '../../shared/issue';

/**
 * Komponent dla widoku sczegółowych danych zlecenia
 */
@Component({
  selector: 'alwin-issue-header',
  templateUrl: './issue-header.component.html',
  styleUrls: ['./issue-header.component.css']
})
export class IssueHeaderComponent {
  @Input()
  issue: Issue;

  @Input()
  issueProgress: number;

  @Input()
  readonly: boolean;

  /**
   * Zwraca ogólny postęp spłat zlecenia wyrażone w procentach
   *
   * @returns {number} - wartość procentowa od 0 do 100 ogólnej sumy wpłat
   */
  determineIssuePaymentProgress(): number {
    if (!this.isTotalPaymentsCorrect()) {
      return 0;
    }
    if (this.issue.totalBalanceStartPLN >= 0) {
      return 100;
    }
    const totalBalanceStartPLN = Math.abs(this.issue.totalBalanceStartPLN);
    const totalPaymentsPLN = Math.abs(this.issue.totalPaymentsPLN);
    return Math.min(100, Math.round(100 * totalPaymentsPLN / totalBalanceStartPLN));
  }

  isTotalPaymentsCorrect(): boolean {
    return this.issue.totalBalanceStartPLN != null && this.issue.totalPaymentsPLN != null;
  }

}
