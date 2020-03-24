import {Issue} from './issue';

/**
 * ZLecenie widnykacyjne z dodatkową informacją czy może być edytowane przez zalogowanego użytkownika
 */
export class OperatedIssue {
  issue: Issue;
  editable: boolean;
}
