import {NgModule} from '@angular/core';
import {SchedulersManagementComponent} from './schedulers-management.component';
import {SchedulerEditDialogComponent} from './scheduler-edit-dialog/scheduler-edit-dialog.component';
import {CommonModule, DatePipe} from '@angular/common';
import {AlwinMatModule} from '../alwin-mat.module';
import {AlwinPipeModule} from '../alwin-pipe.module';
import {RouterModule} from '@angular/router';
import {AlwinSharedModule} from '../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SchedulersHistoryComponent} from './schedulers-history.component';
import {SchedulerService} from './scheduler.service';

/**
 * Moduł deklarujący komponenty używane do zarządzania zadaniami cyklicznymi
 */
@NgModule({
  providers: [
    DatePipe,
    SchedulerService
  ],
  imports: [
    CommonModule,
    AlwinMatModule,
    AlwinPipeModule,
    AlwinSharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [
    SchedulersHistoryComponent,
    SchedulersManagementComponent,
    SchedulerEditDialogComponent
  ],
  entryComponents: [
    SchedulerEditDialogComponent
  ],
  exports: [
    SchedulersHistoryComponent,
    SchedulersManagementComponent,
    SchedulerEditDialogComponent
  ]
})
export class SchedulerModule {
}
