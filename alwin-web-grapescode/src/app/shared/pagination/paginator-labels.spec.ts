import {async, TestBed} from '@angular/core/testing';
import {PaginatorLabels} from './paginator-labels';
import {AlwinHttpService} from '../authentication/alwin-http.service';
import {MessageService} from '../message.service';
import {AlwinMatModule} from '../../alwin-mat.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {IssueService} from '../../issues/shared/issue.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {TruncatePipe} from '../pipe/truncate.pipe';
import {IssueTypeIconComponent} from '../../issues/shared/type/issue-type-icon.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ConcatenatePipe} from '../pipe/concatenate.pipe';
import {OperatorService} from '../../issues/shared/operator.service';
import {EnvironmentSpecificService} from '../environment-specific.service';
import {ActivatedRouteMock} from '../../testing/activated-route.mock';
import {ManageIssuesComponent} from '../../issues/manage/manage-issues.component';
import {ActivatedRoute} from '@angular/router';
import {TagService} from '../../tag/tag.service';
import {CompanyService} from '../../company/company.service';
import {ManageIssuesOperatorFiltersComponent} from '../../issues/manage/filter/manage-issues-operator-filters.component';
import {ManageIssuesManagerFiltersComponent} from '../../issues/manage/filter/manage-issues-manager-filters.component';

describe('Paginator labels', () => {

  const currentUserKey = 'currentUser';
  const storedUser = {username: 'Adam Mickiewicz', role: 'ADMIN', token: 'Barer asdasewqesdfvfwerew'};
  let subject: MatPaginator;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, AlwinMatModule, HttpClientTestingModule, BrowserAnimationsModule],
      declarations: [ManageIssuesComponent, IssueTypeIconComponent, TruncatePipe, ConcatenatePipe, ManageIssuesOperatorFiltersComponent, ManageIssuesManagerFiltersComponent],
      providers: [MatProgressBarModule, AlwinMatModule, IssueService, AlwinHttpService, MessageService, OperatorService, EnvironmentSpecificService, TagService,
        {provide: ActivatedRoute, useValue: ActivatedRouteMock}, CompanyService]
    });

    localStorage.setItem(currentUserKey, JSON.stringify(storedUser));
    this.fixture = TestBed.createComponent(ManageIssuesComponent);
    const component = this.fixture.componentInstance;
    this.fixture.detectChanges();
    subject = component.paginator;
  });


  it('should be set', async(() => {
    // given
    this.fixture.detectChanges();

    // when
    PaginatorLabels.addAlwinLabels(subject);

    // then
    expect(subject._intl.nextPageLabel).toBe('Następna strona');
    expect(subject._intl.previousPageLabel).toBe('Poprzednia strona');
    expect(subject._intl.itemsPerPageLabel).toBe('Ilość na stronie:');
    expect(subject._intl.getRangeLabel(0, 10, 20)).toBe('1 - 10 z 20');
    expect(subject._intl.getRangeLabel(0, 0, 20)).toBe('0 z 20');
    expect(subject._intl.getRangeLabel(0, 10, 0)).toBe('0 z 0');
  }));

});
