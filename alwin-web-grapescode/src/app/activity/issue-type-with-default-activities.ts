import {IssueType} from '../issues/shared/issue-type';
import {DefaultIssueActivity} from './default-issue-activity';

export class IssueTypeWithDefaultActivities {
  issueType: IssueType;
  defaultActivities: DefaultIssueActivity[];
  /*
   *  Ustawia issueType na podstawie już istniejącego
   */
  setIssueType(issueType: IssueType) {
    this.issueType = issueType;
  }
}
