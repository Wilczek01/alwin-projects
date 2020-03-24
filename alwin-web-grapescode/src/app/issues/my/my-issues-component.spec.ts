import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MyIssuesComponent} from './my-issues.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {AlwinMatModule} from '../../alwin-mat.module';
import {TruncatePipe} from 'app/shared/pipe/truncate.pipe';
import {IssueService} from '../shared/issue.service';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {MessageService} from '../../shared/message.service';
import {DateUtils} from '../shared/utils/date-utils';
import {IssueTypeIconComponent} from '../shared/type/issue-type-icon.component';
import {HttpParams} from '@angular/common/http';
import * as moment from 'moment';
import {EnvironmentSpecificService} from '../../shared/environment-specific.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('Component: My issues', () => {

  let component: MyIssuesComponent;
  let fixture: ComponentFixture<MyIssuesComponent>;

  beforeEach(() => {

    // refine the test module by declaring the test component
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, AlwinMatModule, HttpClientTestingModule, BrowserAnimationsModule],
      declarations: [MyIssuesComponent, IssueTypeIconComponent, TruncatePipe],
      providers: [MatProgressBarModule, AlwinMatModule, IssueService, AlwinHttpService, MessageService, EnvironmentSpecificService]
    });

    // create component and test fixture
    fixture = TestBed.createComponent(MyIssuesComponent);

    // get test component from the fixture
    component = fixture.componentInstance;
    component.ngOnInit();
  });

  it('should form valid when empty', () => {
    expect(component.filterForm.valid).toBeTruthy();
  });

  const formFields = {
    'startStartDate': '09.09.2017',
    'endStartDate': '10.09.2017',
    'startExpirationDate': '09.09.2017',
    'endExpirationDate': '10.09.2017',
    'startActivityDate': '09.09.2017',
    'endActivityDate': '10.09.2017',
    'startPlannedDate': '09.09.2017',
    'endPlannedDate': '10.09.2017'
  };

  for (const fieldName of Object.keys(formFields)) {
    it('should pass validation when empty', () => {
      // given
      const formField = component.filterForm.controls[fieldName];
      // then
      expect(formField.valid).toBeTruthy();
    });

    it('should pass validation when field not required', () => {
      // given
      let errors: {};
      const field = component.filterForm.controls[fieldName];
      // when
      errors = field.errors || {};
      // then
      expect(errors['required']).toBeFalsy();
    });

    it('should not pass validation when field validation return error', () => {
      // given
      const field = component.filterForm.controls[fieldName];

      // when
      field.setValue('test');

      // then
      expect(component.filterForm.valid).toBeFalsy();
    });

    it('should pass validation when field validation return success', () => {
      // given
      const field = component.filterForm.controls[fieldName];

      // when
      field.setValue(formFields[fieldName]);

      // then
      expect(component.filterForm.valid).toBeTruthy();
    });
  }

  it('should prepare date as string in required format', () => {
    // given
    const collectionDate = '2017-09-15T09:00:00';
    const newDate = moment(collectionDate);

    // when
    const params = DateUtils.prepareURLSearchParamFromMoment('testParam', newDate, new HttpParams());

    // then
    expect(params.get('testParam') === '2017-09-15').toBeTruthy();
  });

  it('should not prepare date as string in required format when date is not valid ', () => {
    // given
    const collectionDate = '';
    const newDate = moment(collectionDate);

    // when
    const params = DateUtils.prepareURLSearchParamFromMoment('testParam', newDate, new HttpParams());

    // then
    expect(params.get('testParam') === '2017-09-15').toBeFalsy();
  });

})
;
