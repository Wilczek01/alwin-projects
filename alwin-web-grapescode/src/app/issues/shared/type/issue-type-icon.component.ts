import {Component, Input} from '@angular/core';
import {IssueType} from '../issue-type';
import {IssueTypeConst} from '../issue-type.const';

@Component({
  selector: 'alwin-issue-type-icon',
  templateUrl: './issue-type-icon.component.html'
})
export class IssueTypeIconComponent {

  @Input()
  issueType: IssueType;
  ISSUE_TYPE = IssueTypeConst;

}
