import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {TagService} from '../tag.service';
import {Tag} from '../tag';
import {TagInput} from '../tag-input';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {TagIconService} from '../tag-icon.service';
import {TagIcon} from '../tag-icon';

/**
 * Komponent dla widoku edycji istniejącej etykiety
 */
@Component({
  selector: 'alwin-update-tag-dialog',
  styleUrls: ['./tag-dialog.component.css'],
  templateUrl: './update-tag-dialog.component.html',
})
export class UpdateTagDialogComponent implements OnInit {

  tagIconsLoading: boolean;
  saving: boolean;
  tagIcons: TagIcon[];
  tag: Tag;

  constructor(public dialogRef: MatDialogRef<UpdateTagDialogComponent>,
              private tagService: TagService,
              private tagIconService: TagIconService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.tagIconsLoading = true;
    this.tagIconService.getTagIcons().subscribe(
      response => {
        this.tagIcons = response;
        this.tagIconsLoading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.tagIconsLoading = false;
      }
    );
  }

  /**
   * Edytuje istniejącą etykietę
   */
  updateTag() {
    this.saving = true;
    this.tagService.updateTag(this.tag.id, new TagInput(this.tag)).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Etykieta została zaktualizowana');
          this.saving = false;
          this.dialogRef.close(true);
        } else {
          this.saving = false;
          this.messageService.showMessage('Nie udało się zaktualizować etykiety');
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
