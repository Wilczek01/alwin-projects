import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {TagService} from '../../../tag/tag.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {IssueService} from '../../shared/issue.service';
import {map, startWith} from 'rxjs/operators';
import {HttpParams} from '@angular/common/http';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';
import {TagIssueDialog} from './tag-issue-dialog';

/**
 * Komponent dla widoku dodawania etykiet dla listy zleceń
 */
@Component({
  selector: 'alwin-tag-issues-dialog',
  styleUrls: ['./tag-issue-dialog.component.css'],
  templateUrl: './tag-issues-dialog.component.html',
})
export class TagIssuesDialogComponent extends TagIssueDialog implements OnInit {

  updateAll: boolean;
  searchCriteria: HttpParams;
  issueIds: number[];
  all: number;

  constructor(private dialog: MatDialog, public dialogRef: MatDialogRef<TagIssuesDialogComponent>,
              tagService: TagService, private issueService: IssueService, messageService: MessageService) {
    super(tagService, messageService);
  }

  ngOnInit() {
    this.loadTags();
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map((tag: string | null) => tag ? this.filter(tag) : this.tags.slice()));

    if (this.updateAll) {
      let params = new HttpParams();
      // przepisywanie z powodu duplikowania parametrów
      this.searchCriteria.keys().forEach(key => {
        this.searchCriteria.getAll(key).forEach(value => params = params.append(key, value));
      });
      this.searchCriteria = params.append('updateAll', 'true');
    }
  }

  /**
   * Dodaje etykiety do zlecenia po uprzednim pokazaniu okienka z prośba o potwierdzenie
   */
  changeIssueTagsWithConfirmation() {
    if (this.updateAll || this.issueIds.length > 1) {
      const confirmationDialogRef = this.dialog.open(ConfirmationDialogComponent);
      const issuesToUpdate = this.updateAll ? this.all : this.issueIds.length;
      confirmationDialogRef.componentInstance.message = `Zamierzasz przypisać etykiety do ${issuesToUpdate} zleceń. Czy kontynuować?`;
      confirmationDialogRef.componentInstance.title = 'Przypisanie etykiety';
      confirmationDialogRef.afterClosed().subscribe(confirmed => {
        if (confirmed) {
          this.changeIssueTags();
        }
      });
    } else {
      this.changeIssueTags();
    }
  }

  /**
   * Dodaje etykiety do zlecenia
   */
  changeIssueTags() {
    this.loading = true;
    this.issueService.updateIssuesTags(this.issueIds, this.issueTags.map(tag => tag.id), this.updateAll, this.searchCriteria).subscribe(
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
