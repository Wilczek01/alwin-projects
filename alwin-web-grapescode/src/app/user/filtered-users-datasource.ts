import { MatPaginator } from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {MessageService} from '../shared/message.service';
import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {UserService} from '../issues/shared/user.service';
import {ParentOperator} from '../operator/parent-operator';

export class FilteredOperatorsDatasource extends AbstractAlwinDataSource<ParentOperator> {

  searchText: string;

  constructor(private userService: UserService, private paginator: MatPaginator, messageService: MessageService) {
    super(messageService);
  }

  connect(): Observable<ParentOperator[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];

    return this.observePage(displayDataChanges);
  }

  protected loadPage(): Observable<Page<ParentOperator>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.userService.getOperatorsFilteredByLoginOrName(firstResult, maxResult, this.searchText);
  }
}
