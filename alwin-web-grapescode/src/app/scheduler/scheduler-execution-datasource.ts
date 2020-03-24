import { MatPaginator } from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {SchedulerService} from './scheduler.service';
import {SchedulerExecution} from './scheduler-execution';
import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {MessageService} from '../shared/message.service';
import {Page} from '../shared/pagination/page';

/**
 * Źródło danych dla stronicowanej listy uruchomień zadań cyklicznych
 */
export class SchedulerExecutionDatasource extends AbstractAlwinDataSource<SchedulerExecution> {

  constructor(private schedulerService: SchedulerService, private paginator: MatPaginator, messageService: MessageService) {
    super(messageService);
  }

  connect(): Observable<SchedulerExecution[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];

    return this.observePage(displayDataChanges);
  }

  protected loadPage(): Observable<Page<SchedulerExecution>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResults = String(this.paginator.pageSize);
    return this.schedulerService.getSchedulersExecutions(firstResult, maxResults);
  }
}
