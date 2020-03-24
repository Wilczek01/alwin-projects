import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HolidaysComponent} from './holidays.component';
import {AlwinMatModule} from '../alwin-mat.module';
import {AlwinPipeModule} from '../alwin-pipe.module';
import {AlwinSharedModule} from '../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CalendarComponent} from './calendar/calendar.component';
import {HolidayService} from './holiday.service';
import {CreateHolidayDialogComponent} from './calendar/dialog/create-holiday-dialog.component';
import {UpdateHolidayDialogComponent} from './calendar/dialog/update-holiday-dialog.component';

/**
 * Moduł deklarujący komponenty używane w widoku dni wolnych
 */
@NgModule({
  providers: [
    DatePipe,
    HolidayService
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
    HolidaysComponent,
    CalendarComponent,
    CreateHolidayDialogComponent,
    UpdateHolidayDialogComponent
  ],
  entryComponents: [
    CreateHolidayDialogComponent,
    UpdateHolidayDialogComponent
  ],
  exports: [
    HolidaysComponent,
    CalendarComponent,
    CreateHolidayDialogComponent,
    UpdateHolidayDialogComponent
  ]
})
export class HolidaysModule {
}
