import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {Tag} from '../tag';
import {TagService} from '../tag.service';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {TagIcon} from '../tag-icon';
import {TagIconService} from '../tag-icon.service';

/**
 * Komponent dla widoku tworzenia nowegj etykiety
 */
@Component({
  selector: 'alwin-create-tag-dialog',
  styleUrls: ['./tag-dialog.component.css'],
  templateUrl: './create-tag-dialog.component.html',
})
export class CreateTagDialogComponent implements OnInit {

  tagIconsLoading: boolean;
  saving: boolean;
  tagIcons: TagIcon[];
  tag = new Tag();

  constructor(public dialogRef: MatDialogRef<CreateTagDialogComponent>,
              private tagService: TagService,
              private tagIconService: TagIconService,
              private messageService: MessageService) {

  }

  ngOnInit(): void {
    this.tagIconsLoading = true;
    this.tagIconService.getTagIcons().subscribe(
      response => {
        this.tagIcons = response;
        if (this.tagIcons.length > 0) {
          this.selectIcon(this.tagIcons[0]);
        }
        this.tagIconsLoading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.tagIconsLoading = false;
      }
    );
  }

  /**
   * Tworzy nową etykietę
   */
  createTag() {
    this.saving = true;
    this.tagService.createTag(this.tag).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Etykieta została utworzona');
          this.saving = false;
          this.dialogRef.close(true);
        } else {
          this.saving = false;
          this.messageService.showMessage('Nie udało się zapisać etykiety');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.saving = false;
      }
    );
  }

  selectIcon(tagIcon: TagIcon) {
    this.tag.tagIcon = tagIcon;
  }

}
