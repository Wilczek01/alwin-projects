import { MatPaginator } from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {MessageService} from '../shared/message.service';
import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {Operator} from './operator';
import {OperatorService} from '../issues/shared/operator.service';

export class FieldOperatorsDatasource extends AbstractAlwinDataSource<Operator> {

  constructor(private operatorService: OperatorService, private paginator: MatPaginator, messageService: MessageService) {
    super(messageService);
  }

  connect(): Observable<Operator[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];

    return this.observePage(displayDataChanges);
  }

  protected loadPage(): Observable<Page<Operator>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.operatorService.findFieldOperators(firstResult, maxResult);
  }
}
