import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Operator} from '../../../operator/operator';
import {IssueType} from '../../shared/issue-type';
import {KeyLabel} from '../../../shared/key-label';
import {Tag} from '../../../tag/tag';

@Component({
  selector: 'alwin-manage-issues-manager-filters',
  styleUrls: [
    './../../shared/issue-table.css',
    './../manage-issues.component.scss'
  ],
  templateUrl: './manage-issues-manager-filters.component.html'
})
export class ManageIssuesManagerFiltersComponent {

  @Input()
  group: FormGroup;

  @Input()
  operators: Operator[] = [];

  @Input()
  tags: Tag[] = [];

  @Input()
  states: KeyLabel[] = [];

  @Input()
  priorities: KeyLabel[] = [];

  @Input()
  issueTypes: IssueType[] = [];

  @Input()
  segments: KeyLabel[] = [];

  @Input()
  readonly: boolean;

  @Output()
  changeManagement: EventEmitter<boolean> = new EventEmitter<boolean>();

  changeAllManagement() {
    this.changeManagement.emit(this.group.get('disableAllManagementControl').value);
  }
}

