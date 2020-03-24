import { MatPaginator } from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {Issue} from '../shared/issue';
import {IssueService} from '../shared/issue.service';
import {Page} from '../../shared/pagination/page';
import {MessageService} from '../../shared/message.service';
import {AbstractAlwinDataSource} from '../shared/abstract-datasource';
import {SelectionModel} from '@angular/cdk/collections';
import {HttpParams} from '@angular/common/http';


export class ManageIssuesDataSource extends AbstractAlwinDataSource<Issue> {

  constructor(private issueService: IssueService,
              private paginator: MatPaginator,
              messageService: MessageService,
              params: HttpParams,
              private selection: SelectionModel<number>) {
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
    this.selection.clear();
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.issueService.getManagedIssues(firstResult, maxResult, this.params);
  }
}
