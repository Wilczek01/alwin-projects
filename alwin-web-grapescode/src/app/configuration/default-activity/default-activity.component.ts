import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import { MatDialog } from '@angular/material/dialog';
import {DefaultIssueActivity} from '../../activity/default-issue-activity';
import {ActivityService} from '../../activity/activity.service';
import {UpdateDefaultActivityDialogComponent} from './dialog/update-default-activity-dialog.component';
import {IssueTypeWithDefaultActivities} from '../../activity/issue-type-with-default-activities';

/**
 * Komponent dla widoku obsługi konfiguracji czynności windykacyjnych
 */
@Component({
  selector: 'alwin-default-issue-activity',
  styleUrls: ['default-activity.component.css'],
  templateUrl: './default-activity.component.html'
})
export class DefaultActivityComponent implements OnInit {

  loading = false;
  loadingTypes = false;
  issueTypesWithDefaultActivities: IssueTypeWithDefaultActivities[] = [];
  types: Map<number, string> = new Map<number, string>();

  constructor(private activityService: ActivityService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadDefaultActivities();
    this.loadActivityTypes();
  }

  /**
   * Pobieranie wszystkich konfiguracji zlecenia windykacyjnego
   */
  private loadDefaultActivities() {
    this.loading = true;
    this.activityService.getAllDefaultIssueActivities().subscribe(
      defaultActivities => {
        if (defaultActivities == null) {
          this.messageService.showMessage('Nie znaleziono domyślnych aktywności');
          this.loading = false;
          return;
        }
        this.issueTypesWithDefaultActivities = [];
        this.issueTypesWithDefaultActivities = defaultActivities;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Pobieranie wszystkich typów czynności
   */
  loadActivityTypes() {
    this.loadingTypes = true;
    this.activityService.getAllActivityTypes().subscribe(
      types => {
        if (types == null) {
          this.loadingTypes = false;
          return;
        }
        types.forEach(activityType => this.types.set(activityType.id, activityType.name));
        this.loadingTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingTypes = false;
      });
  }

  /**
   * Otwiera okno umożliwiające edycję czynności
   * @param {DefaultIssueActivity}  defaultIssueActivity - domyślna czynność windykacyjna
   */
  editDefaultIssueActivity(defaultIssueActivity: DefaultIssueActivity, defaultActivityNameLabel: string) {
    const dialogRef = this.dialog.open(UpdateDefaultActivityDialogComponent);
    dialogRef.componentInstance.defaultIssueActivity = Object.assign({}, defaultIssueActivity);
    dialogRef.componentInstance.defaultActivityNameLabel = defaultActivityNameLabel;
    dialogRef.afterClosed().subscribe(saved => {
        if (saved) {
          this.loadDefaultActivities();
        }
      }
    );
  }

  /**
   * Sprawdza, czy w podanym parametrze znajdują się jakieś domyślne czynności
   */
  containsDefaultActivities(issueTypesWithDefaultActivities: IssueTypeWithDefaultActivities): boolean {
    return issueTypesWithDefaultActivities
      && issueTypesWithDefaultActivities.defaultActivities
      && issueTypesWithDefaultActivities.defaultActivities.length !== 0;
  }
}
