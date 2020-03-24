import {Component, OnInit, ViewChild} from '@angular/core';
import {MessageService} from '../../shared/message.service';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {UserService} from '../../issues/shared/user.service';
import {PaginatorLabels} from '../../shared/pagination/paginator-labels';
import {FilteredOperatorsDatasource} from '../filtered-users-datasource';
import {ParentOperator} from '../../operator/parent-operator';

@Component({
  selector: 'alwin-select-manager-dialog',
  styleUrls: ['./select-manager-dialog.component.css'],
  templateUrl: './select-manager-dialog.component.html',
})
export class SelectManagerDialogComponent implements OnInit {
  loading: boolean;
  searchText: string;
  displayedColumns = ['login', 'name', 'type', 'action'];
  dataSource: FilteredOperatorsDatasource | null;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(public dialogRef: MatDialogRef<SelectManagerDialogComponent>,
              private userService: UserService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.dataSource = new FilteredOperatorsDatasource(this.userService, this.paginator, this.messageService);
    PaginatorLabels.addAlwinLabels(this.paginator);
  }

  filterUsers() {
    this.dataSource = new FilteredOperatorsDatasource(this.userService, this.paginator, this.messageService);
    this.paginator.pageIndex = 0;
    this.dataSource.searchText = this.searchText;

    return !this.dataSource.loading;
  }

  selectManager(operator: ParentOperator) {
    this.dialogRef.close(operator);
  }

}
