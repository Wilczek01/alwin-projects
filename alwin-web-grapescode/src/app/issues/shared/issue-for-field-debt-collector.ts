import {Issue} from './issue';
import {IssueReminderMarkConst} from './issue-reminder-mark.const';

/**
 * Zlecenie windykacyjne dla listy windykatora terenowego
 */
export class IssueForFieldDebtCollector extends Issue {
  reminderMark: IssueReminderMarkConst;
}
