import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {FieldDebtIssuesComponent} from './field-debt-issues.component';
import {ReactiveFormsModule} from '@angular/forms';
import {AlwinMatModule} from '../../alwin-mat.module';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {IssueService} from '../shared/issue.service';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {MessageService} from '../../shared/message.service';
import {ActivityService} from '../../activity/activity.service';
import {AddressService} from '../../customer/address/address-service';
import {ContactDetailService} from '../../customer/contact-detail/contact-detail-service';
import {UserAccessService} from '../../common/user-access.service';
import {InvoiceService} from '../shared/invoice.service';
import {NotificationService} from '../../notification/notification.service';
import {RefreshIssueService} from '../issue/issue/refresh-issue.service';
import {EnvironmentSpecificService} from '../../shared/environment-specific.service';
import {ActivatedRoute} from '@angular/router';
import {ActivatedRouteMock} from '../../testing/activated-route.mock';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {TruncatePipe} from '../../shared/pipe/truncate.pipe';
import { MatDialog } from '@angular/material/dialog';
import {OperatorService} from '../shared/operator.service';
import {TagService} from '../../tag/tag.service';
import {CompanyService} from '../../company/company.service';

describe('FieldDebtIssuesComponent', () => {
  let component: FieldDebtIssuesComponent;
  let fixture: ComponentFixture<FieldDebtIssuesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, AlwinMatModule, HttpClientModule, BrowserAnimationsModule],
      declarations: [FieldDebtIssuesComponent, TruncatePipe],
      providers: [IssueService, AlwinHttpService, MessageService, ActivityService, AddressService, ContactDetailService, UserAccessService,
        InvoiceService, NotificationService, RefreshIssueService, EnvironmentSpecificService, {provide: ActivatedRoute, useValue: ActivatedRouteMock}, OperatorService, TagService,
        CompanyService, MatDialog],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FieldDebtIssuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
