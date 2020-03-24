import {TestBed} from '@angular/core/testing';
import {ManageIssuesComponent} from './manage-issues.component';
import {MessageService} from '../../shared/message.service';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {OperatorService} from '../shared/operator.service';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {IssueService} from '../shared/issue.service';
import {TruncatePipe} from '../../shared/pipe/truncate.pipe';
import {AlwinMatModule} from '../../alwin-mat.module';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {ConcatenatePipe} from '../../shared/pipe/concatenate.pipe';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FIRST_OPERATOR} from '../../testing/data/operator.test.data';
import {IssueTypeIconComponent} from '../shared/type/issue-type-icon.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import * as moment from 'moment';
import {EnvironmentSpecificService} from '../../shared/environment-specific.service';
import {ActivatedRouteMock} from '../../testing/activated-route.mock';
import {ActivatedRoute} from '@angular/router';
import {TagService} from '../../tag/tag.service';
import {CompanyService} from '../../company/company.service';
import {ManageIssuesOperatorFiltersComponent} from './filter/manage-issues-operator-filters.component';
import {ManageIssuesManagerFiltersComponent} from './filter/manage-issues-manager-filters.component';

describe('Manage issues component', () => {

  let component: ManageIssuesComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, AlwinMatModule, HttpClientTestingModule, BrowserAnimationsModule],
      declarations: [ManageIssuesComponent, IssueTypeIconComponent, TruncatePipe, ConcatenatePipe, ManageIssuesOperatorFiltersComponent, ManageIssuesManagerFiltersComponent],
      providers: [MatProgressBarModule, AlwinMatModule, IssueService, AlwinHttpService, MessageService, OperatorService, EnvironmentSpecificService, TagService,
        {provide: ActivatedRoute, useValue: ActivatedRouteMock}, CompanyService]
    });

    const fixture = TestBed.createComponent(ManageIssuesComponent);

    component = fixture.componentInstance;
    component.ngOnInit();
  });

  it('should pass filter parameters to table datasource', () => {
    // given
    component.startStartDateControl = new FormControl(moment('2017-09-15T09:00:00'));
    component.endStartDateControl = new FormControl(moment('2017-10-22T09:08:00'));
    component.startContactDateControl = new FormControl(moment('2017-03-08T07:00:00'));
    component.endContactDateControl = new FormControl(moment('2017-05-11T06:00:00'));
    component.totalCurrentBalancePLNMinControl = new FormControl(100);
    component.totalCurrentBalancePLNMaxControl = new FormControl(300);
    component.operatorControl.setValue([FIRST_OPERATOR]);

    // when
    component.filterIssues();

    // then
    expect(component.dataSource.params.get('startStartDate')).toEqual('2017-09-15');
    expect(component.dataSource.params.get('endStartDate')).toEqual('2017-10-22');
    expect(component.dataSource.params.get('startContactDate')).toEqual('2017-03-08');
    expect(component.dataSource.params.get('endContactDate')).toEqual('2017-05-11');
    expect(component.dataSource.params.get('totalCurrentBalancePLNMin')).toEqual(String(100));
    expect(component.dataSource.params.get('totalCurrentBalancePLNMax')).toEqual(String(300));
    expect(component.dataSource.params.get('operatorId')).toEqual(String(FIRST_OPERATOR.id));
  });

});



