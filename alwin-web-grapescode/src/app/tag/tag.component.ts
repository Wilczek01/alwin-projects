import {Component, OnInit} from '@angular/core';
import {TagService} from './tag.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {Tag} from './tag';
import {MessageService} from '../shared/message.service';
import {ConfirmationDialogComponent} from '../shared/dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {UpdateTagDialogComponent} from './dialog/update-tag-dialog.component';
import {CreateTagDialogComponent} from './dialog/create-tag-dialog.component';
import {TagType} from './tag-type';

@Component({
  selector: 'alwin-tags',
  styleUrls: ['tag.component.css'],
  templateUrl: './tag.component.html'
})
export class TagComponent implements OnInit {

  loading;
  tags: Tag[] = [];
  type = TagType;

  constructor(private messageService: MessageService, private tagService: TagService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.loadTags();
  }

  /**
   * Pobiera listę etykiet
   */
  private loadTags() {
    this.loading = true;
    this.tagService.getTags().subscribe(
      tags => {
        if (tags == null) {
          this.messageService.showMessage('Nie znaleziono etykiet');
          this.loading = false;
          return;
        }
        this.tags = tags;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Otwiera okno wymuszające potwierdzenie usunięcia etykiety o podanym identyfikatorze. Usuwa etykietę w przypadku potwierdzenia.
   *
   * @param {number} tagId - identyfikator etykiety
   */
  confirmDeleteTag(tagId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Usuwanie etykiety';
    dialogRef.componentInstance.message = 'Czy na pewno chcesz usunąć wybraną etykietę?';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.deleteTag(tagId);
      }
    });
  }

  /**
   * Usuwa etykietę o podanym identyfikatorze
   *
   * @param {number} tagId - identyfikator etykiety
   */
  private deleteTag(tagId: number) {
    this.loading = true;
    this.tagService.deleteTag(tagId).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Etykieta została usunięta');
        } else {
          this.messageService.showMessage('Nie udało się usunąć etykiety');
        }
        this.loadTags();
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Otwiera okno do aktualizacji wybranej etykiety
   *
   * @param {Tag} tag - etyketa do aktualizacji
   */
  updateTag(tag: Tag) {
    const dialogRef = this.dialog.open(UpdateTagDialogComponent);
    dialogRef.componentInstance.tag = Object.assign({}, tag);
    dialogRef.afterClosed().subscribe(updated => {
      if (updated) {
        this.loadTags();
      }
    });
  }

  /**
   * Otwiera okno tworzenia nowej etykiety
   */
  createTag() {
    const dialogRef = this.dialog.open(CreateTagDialogComponent);
    dialogRef.afterClosed().subscribe(created => {
      if (created) {
        this.loadTags();
      }
    });
  }

}
