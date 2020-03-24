import { MatPaginator } from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {MessageService} from '../shared/message.service';
import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {FullUser} from './full-user';
import {UserService} from '../issues/shared/user.service';

export class UsersDataSource extends AbstractAlwinDataSource<FullUser> {

  constructor(private userService: UserService, private paginator: MatPaginator, messageService: MessageService) {
    super(messageService);
  }

  connect(): Observable<FullUser[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];

    return this.observePage(displayDataChanges);
  }

  protected loadPage(): Observable<Page<FullUser>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.userService.getUsers(firstResult, maxResult);
  }
}
