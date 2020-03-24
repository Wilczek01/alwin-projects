import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AlwinMatModule} from '../alwin-mat.module';
import {AlwinPipeModule} from '../alwin-pipe.module';
import {AlwinSharedModule} from '../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LocationComponent} from './location.component';
import {CreateLocationDialogComponent} from './dialog/create-location-dialog.component';
import {UpdateLocationDialogComponent} from './dialog/update-location-dialog.component';
import {LocationService} from './location.service';

/**
 * Moduł deklarujący komponenty używane w widoku etykiet
 */
@NgModule({
  providers: [
    DatePipe,
    LocationService
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
    LocationComponent,
    CreateLocationDialogComponent,
    UpdateLocationDialogComponent
  ],
  entryComponents: [
    CreateLocationDialogComponent,
    UpdateLocationDialogComponent
  ],
  exports: [
    LocationComponent,
    CreateLocationDialogComponent,
    UpdateLocationDialogComponent
  ]
})
export class LocationModule {
}
