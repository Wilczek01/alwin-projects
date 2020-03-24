import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {AlwinMatModule} from '../../alwin-mat.module';
import {IssueService} from '../shared/issue.service';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {MessageService} from '../../shared/message.service';
import {IssueComponent} from './issue.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivityService} from '../../activity/activity.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AddressService} from '../../customer/address/address-service';
import {ContactDetailService} from '../../customer/contact-detail/contact-detail-service';
import {InvoiceService} from '../shared/invoice.service';
import {HttpClientModule} from '@angular/common/http';
import {NotificationService} from '../../notification/notification.service';
import {Issue} from '../shared/issue';
import {RefreshIssueService} from './issue/refresh-issue.service';
import {EnvironmentSpecificService} from '../../shared/environment-specific.service';
import {ActivatedRoute} from '@angular/router';
import {ActivatedRouteMock} from '../../testing/activated-route.mock';
import {UserAccessService} from '../../common/user-access.service';

describe('Issue component', () => {

  let component: IssueComponent;
  let fixture: ComponentFixture<IssueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, AlwinMatModule, HttpClientModule, BrowserAnimationsModule],
      declarations: [IssueComponent],
      providers: [IssueService, AlwinHttpService, MessageService, ActivityService, AddressService, ContactDetailService, UserAccessService,
        InvoiceService, NotificationService, RefreshIssueService, EnvironmentSpecificService, {provide: ActivatedRoute, useValue: ActivatedRouteMock}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });
    fixture = TestBed.createComponent(IssueComponent);
    component = fixture.componentInstance;
  });

  it('should determine issue progress', () => {
    // given
    spyOn(Date, 'now').and.callFake(function () {
      return new Date('2018-04-28T12:01:04.753Z');
    });

    // and
    component.issue = new Issue();
    component.issue.startDate = new Date('2018-03-28T12:01:04.753Z');
    component.issue.expirationDate = new Date('2018-05-28T12:01:04.753Z');

    // when
    const issueProgress = component.determineIssueProgress();

    // then
    expect(issueProgress).toBe(51);
  });

  it('should set issue progress as 100 because current date is already after expiration', () => {
    // given
    spyOn(Date, 'now').and.callFake(function () {
      return new Date('2018-06-28T12:01:04.753Z');
    });

    // and
    component.issue = new Issue();
    component.issue.startDate = new Date('2018-03-28T12:01:04.753Z');
    component.issue.expirationDate = new Date('2018-05-28T12:01:04.753Z');

    // when
    const issueProgress = component.determineIssueProgress();

    // then
    expect(issueProgress).toBe(100);
  });

  it('should set issue progress as 0 because current date is before start date', () => {
    // given
    spyOn(Date, 'now').and.callFake(function () {
      return new Date('2018-02-28T12:01:04.753Z');
    });

    // and
    component.issue = new Issue();
    component.issue.startDate = new Date('2018-03-28T12:01:04.753Z');
    component.issue.expirationDate = new Date('2018-05-28T12:01:04.753Z');

    // when
    const issueProgress = component.determineIssueProgress();

    // then
    expect(issueProgress).toBe(0);
  });

});
