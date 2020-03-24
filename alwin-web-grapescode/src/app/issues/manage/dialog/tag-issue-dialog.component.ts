import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {TagService} from '../../../tag/tag.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {IssueService} from '../../shared/issue.service';
import {map, startWith} from 'rxjs/operators';
import {TagIssueDialog} from './tag-issue-dialog';

/**
 * Komponent dla widoku dodawania i usuwania etykiet dla pojedynczego wybranego zlecenia
 */
@Component({
  selector: 'alwin-tag-issue-dialog',
  styleUrls: ['./tag-issue-dialog.component.css'],
  templateUrl: './tag-issue-dialog.component.html',
})
export class TagIssueDialogComponent extends TagIssueDialog implements OnInit {

  constructor(public dialogRef: MatDialogRef<TagIssueDialogComponent>, tagService: TagService, private issueService: IssueService, messageService: MessageService) {
    super(tagService, messageService);
  }

  ngOnInit() {
    this.loadTags();
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map((tag: string | null) => tag ? this.filter(tag) : this.tags.slice()));
  }

  /**
   * Dodaje etykiety do zlecenia
   */
  changeIssueTags() {
    this.loading = true;
    this.issueService.updateIssueTags(this.issueId, this.issueTags.map(tag => tag.id)).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Etykiety zlecenia zostały zaktualizowane');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zaktualizować etykiet zlecenia');
        }
      },
      err => {
        this.loading = false;
        HandlingErrorUtils.handleError(this.messageService, err);
      }
    );
  }

}
