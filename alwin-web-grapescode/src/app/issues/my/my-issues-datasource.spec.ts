import {async, inject, TestBed} from '@angular/core/testing';
import {IssueService} from '../shared/issue.service';
import {MessageService} from '../../shared/message.service';
import {AlwinMatModule} from '../../alwin-mat.module';
import { MatPaginator } from '@angular/material/paginator';
import {FIRST_SET_OF_ISSUES, SECOND_SET_OF_ISSUES, TOTAL_VALUES} from '../../testing/data/issue.test.data';
import {IssueServiceMock} from '../../testing/issue.service.mock';
import {MessageServiceMock} from '../../testing/message.service.mock';
import {MyIssuesDataSource} from './my-issues-datasource';
import {ChangeDetectorRef} from '@angular/core';
import {HttpParams} from '@angular/common/http';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('My issues datasource', () => {

  let subject: MyIssuesDataSource;
  let paginator: MatPaginator;
  let result;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        AlwinMatModule,
        HttpClientTestingModule
      ],
      providers: [
        MatPaginator,
        ChangeDetectorRef,
        {
          provide: IssueService,
          useClass: IssueServiceMock
        },
        {
          provide: MessageService,
          useClass: MessageServiceMock
        }
      ]
    });
  });

  beforeEach(inject([IssueService, MatPaginator, MessageService], (issueService, matPaginator, messageService) => {
    subject = new MyIssuesDataSource(issueService, matPaginator, messageService, new HttpParams());
    paginator = matPaginator;
    result = subject.connect();
  }));

  it('should return first page of my not closed issues', async(() => {
    // when
    paginator._pageIndex = 0;
    paginator.pageSize = 2;
    paginator._length = 4;

    // then
    result.subscribe(data => {
      expect(data).toBe(FIRST_SET_OF_ISSUES);
      subject.max = TOTAL_VALUES;
    });
  }));

  it('should return second page of my not closed issues', async(() => {
    // when
    paginator._pageIndex = 1;
    paginator.pageSize = 2;
    paginator._length = 4;

    // then
    result.subscribe(data => {
      expect(data).toBe(SECOND_SET_OF_ISSUES);
      subject.max = TOTAL_VALUES;
    });
  }));

  it('should return empty page of issues if no data was found', async(() => {
    // when
    paginator._pageIndex = 3;
    paginator.pageSize = 2;
    paginator._length = 4;

    // then
    result.subscribe(data => {
      expect(data).toEqual([]);
      subject.max = 0;
    });
  }));

  it('should return empty page of issues if user is not authorized', async(() => {
    // when
    paginator._pageIndex = -1;

    // then
    result.subscribe(data => {
      expect(data).toEqual([]);
      subject.max = 0;
    });
  }));
});
