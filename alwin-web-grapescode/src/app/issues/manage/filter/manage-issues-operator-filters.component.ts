import {Component, Input} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'alwin-manage-issues-operator-filters',
  styleUrls: [
    './../../shared/issue-table.css',
    './../manage-issues.component.scss'
  ],
  templateUrl: './manage-issues-operator-filters.component.html'
})
export class ManageIssuesOperatorFiltersComponent {

  @Input()
  group: FormGroup;

}

