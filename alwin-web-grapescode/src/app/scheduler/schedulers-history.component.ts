import {Component, OnInit, ViewChild} from '@angular/core';
import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {SchedulerExecution} from './scheduler-execution';
import { MatPaginator } from '@angular/material/paginator';
import {PaginatorLabels} from '../shared/pagination/paginator-labels';
import {SchedulerExecutionDatasource} from './scheduler-execution-datasource';
import {SchedulerService} from './scheduler.service';
import {MessageService} from '../shared/message.service';
import {RefreshSchedulerHistoryService} from './refresh-scheduler-history-service';

@Component({
  selector: 'alwin-schedulers-history',
  styleUrls: ['./schedulers-history.component.css'],
  templateUrl: './schedulers-history.component.html'
})
export class SchedulersHistoryComponent implements OnInit {

  displayedColumns = [
    'startDate',
    'endDate',
    'duration',
    'state',
    'type',
    'manual'
  ];
  dataSource: AbstractAlwinDataSource<SchedulerExecution>;
  loading = false;

  @ViewChild(MatPaginator, {static: true})
  paginator: MatPaginator;

  constructor(private schedulerExecutionService: SchedulerService, private messageService: MessageService,
              private refreshSchedulerHistoryService: RefreshSchedulerHistoryService) {
    this.refreshSchedulerHistoryService.schedulerHistory$.subscribe(() => {
      this.reloadSchedulerHistory();
    });

  }

  ngOnInit(): void {
    this.reloadSchedulerHistory();
  }

  reloadSchedulerHistory() {
    this.dataSource = new SchedulerExecutionDatasource(this.schedulerExecutionService, this.paginator, this.messageService);
    PaginatorLabels.addAlwinLabels(this.paginator);
  }
}
