import {inject, TestBed} from '@angular/core/testing';
import {IssueService} from './issue.service';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {AlwinHttpServiceMock} from '../../testing/alwin-http.service.mock';
import {EXPECTED_FIRST_PAGE} from '../../testing/data/issue.test.data';
import {FIRST_RESULT, MAX_RESULTS} from '../../testing/data/http.test.data';
import {HttpParams} from '@angular/common/http';

describe('Issue Service', () => {

  let subject: IssueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        IssueService,
        {
          provide: AlwinHttpService,
          useClass: AlwinHttpServiceMock
        }
      ]
    });
  });

  beforeEach(inject([IssueService], (issueService) => {
    subject = issueService;
  }));

  it('should return page of issues with total size of available issues', () => {
    // when
    const result = subject.getIssues(FIRST_RESULT, MAX_RESULTS, new HttpParams());

    // then
    result.subscribe(data => {
      expect(data).toEqual(EXPECTED_FIRST_PAGE);
    });
  });

});



