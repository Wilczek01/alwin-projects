import {Component, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import {SchedulerConfiguration} from './scheduler-configuration';
import {SchedulerService} from './scheduler.service';
import {ConfirmationDialogComponent} from '../shared/dialog/confirmation-dialog.component';
import {KeyLabel} from '../shared/key-label';
import {MessageService} from '../shared/message.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {SchedulerEditDialogComponent} from './scheduler-edit-dialog/scheduler-edit-dialog.component';
import {RefreshSchedulerHistoryService} from './refresh-scheduler-history-service';

@Component({
  selector: 'alwin-schedulers-management',
  templateUrl: './schedulers-management.component.html',
  styleUrls: ['./schedulers-management.component.css']
})
export class SchedulersManagementComponent implements OnInit {

  // TODO: zobaczyc czy się to przyda, jest używane w widoku, są ładowane dane poniżej więc może warto poprawnie ustawiać tą flagę?
  loading = false;
  displayedColumns = ['batchProcess', 'hour', 'updateDate', 'actions'];
  dataSource = new MatTableDataSource<KeyLabel>([]);
  configurations: Map<string, SchedulerConfiguration> = new Map<string, SchedulerConfiguration>();

  constructor(private schedulerService: SchedulerService,
              private dialog: MatDialog,
              private messageService: MessageService,
              private refreshSchedulerHistoryService: RefreshSchedulerHistoryService) {
  }

  ngOnInit() {
    this.schedulerService.getAllSchedulerBatchProcessTypes().subscribe(batchProcessTypes => {
      this.dataSource.data = batchProcessTypes;
    });
    this.loadConfigurations();
  }

  startTask(schedulerBatchProcessType: KeyLabel) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Uruchomienie zadania';
    dialogRef.componentInstance.message = `${schedulerBatchProcessType.label}`;
    dialogRef.componentInstance.warning = 'Czy na pewno chcesz uruchomić?';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.schedulerService.startBatchProcess(schedulerBatchProcessType.key)
          .subscribe(() => {
            this.messageService.showMessage(`Uruchomiono proces`);
            this.refreshSchedulerHistoryService.refreshSchedulerHistory();
          }, error => {
            HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, error);
          });
      }
    });
  }

  changeStartHour(schedulerConfiguration: SchedulerConfiguration) {
    const dialogRef = this.dialog.open(SchedulerEditDialogComponent);
    dialogRef.componentInstance.setState(schedulerConfiguration.batchProcess, schedulerConfiguration.hour);
    dialogRef.afterClosed().subscribe(success => {
      if (success) {
        this.loadConfigurations();
      }
    });
  }

  private loadConfigurations() {
    this.schedulerService.getScheduleConfigurations().subscribe(scheduleConfigurations => {
      scheduleConfigurations.forEach(configuration => {
        this.configurations.set(configuration.batchProcess.key, configuration);
      });
    });
  }
}
