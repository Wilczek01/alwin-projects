import { MatPaginator } from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {Issue} from '../shared/issue';
import {IssueService} from '../shared/issue.service';
import {Page} from '../../shared/pagination/page';
import {MessageService} from '../../shared/message.service';
import {AbstractAlwinDataSource} from '../shared/abstract-datasource';
import {HttpParams} from '@angular/common/http';


export class MyIssuesDataSource extends AbstractAlwinDataSource<Issue> {

  constructor(private issueService: IssueService, private paginator: MatPaginator, messageService: MessageService, params: HttpParams) {
    super(messageService);
    this.params = params;
  }

  connect(): Observable<Issue[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];

    return this.observePage(displayDataChanges);
  }

  loadPage(): Observable<Page<Issue>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.issueService.getMyNotClosedIssues(firstResult, maxResult, this.params);
  }
}
