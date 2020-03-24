import {Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {UsersDataSource} from './users-datasource';
import {UserService} from '../issues/shared/user.service';
import {MessageService} from '../shared/message.service';
import {PaginatorLabels} from '../shared/pagination/paginator-labels';
import {CreateUserDialogComponent} from './dialog/create-user-dialog.component';


@Component({
  selector: 'alwin-users',
  styleUrls: ['./users.component.css'],
  templateUrl: './users.component.html'
})
export class UsersComponent implements OnInit {
  displayedColumns = ['user'];
  dataSource: UsersDataSource | null;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private userService: UserService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.dataSource = new UsersDataSource(this.userService, this.paginator, this.messageService);
    PaginatorLabels.addAlwinLabels(this.paginator);
  }

  createNewUser() {
    const dialogRef = this.dialog.open(CreateUserDialogComponent);
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.paginator.page.emit();
      }
    });
  }

}
