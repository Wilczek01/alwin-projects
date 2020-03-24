import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AlwinMatModule} from '../alwin-mat.module';
import {AlwinPipeModule} from '../alwin-pipe.module';
import {AlwinSharedModule} from '../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TagService} from './tag.service';
import {TagComponent} from './tag.component';
import {CreateTagDialogComponent} from './dialog/create-tag-dialog.component';
import {UpdateTagDialogComponent} from './dialog/update-tag-dialog.component';
import {TagIconService} from './tag-icon.service';

/**
 * Moduł deklarujący komponenty używane w widoku etykiet
 */
@NgModule({
  providers: [
    DatePipe,
    TagService,
    TagIconService
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
    TagComponent,
    CreateTagDialogComponent,
    UpdateTagDialogComponent
  ],
  entryComponents: [
    CreateTagDialogComponent,
    UpdateTagDialogComponent
  ],
  exports: [
    TagComponent,
    CreateTagDialogComponent,
    UpdateTagDialogComponent
  ]
})
export class TagModule {
}
