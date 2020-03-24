import {Issue} from './issue';

/**
 * Klasa pomocniczna sprawdzająca parametry zlecenia windykacyjnego
 */
export class IssueUtils {

  /**
   * Sprawdza czy zlecenie windykacyjne ma wysoki priorytet
   * @param {Issue} issue - zlecenie windykacyjne
   * @returns {boolean} true jeżeli zlecenie windykacyjne ma wysoki priorytet
   */
  static isIssuePriorityHigh(issue: Issue) {
    const result = issue
      && issue.priority
      && issue.priority.key === 'HIGH';
    return result;
  }

}
