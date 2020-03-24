import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../shared/message.service';
import { MatDialog } from '@angular/material/dialog';
import {ActivityService} from '../../activity/activity.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {ActivityType} from '../../activity/activity-type';
import {DefaultIssueActivity} from "../../activity/default-issue-activity";
import {UpdateActivityTypeDialogComponent} from "./dialog/update-activity-type-dialog.component";

/**
 * Komponent dla widoku obsługi konfiguracji czynności windykacyjnych
 */
@Component({
  selector: 'alwin-issue-activity-type',
  styleUrls: ['activity-type.component.css'],
  templateUrl: './activity-type.component.html'
})
export class ActivityTypeComponent implements OnInit {

  loading = false;
  loadingTypes = false;
  activityTypes: ActivityType[] = [];
  types: Map<number, string> = new Map<number, string>();

  constructor(private activityService: ActivityService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadActivityTypes();
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
        this.activityTypes = types;
        this.loadingTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingTypes = false;
      });
  }

  /**
   * Otwiera okno umożliwiające edycję typu czynności
   * @param {DefaultIssueActivity}  defaultIssueActivity - domyślna czynność windykacyjna
   */
  editActivityType(activityType: ActivityType) {
    const dialogRef = this.dialog.open(UpdateActivityTypeDialogComponent);
    dialogRef.componentInstance.activityType = Object.assign({}, activityType);
    dialogRef.afterClosed().subscribe(saved => {
        if (saved) {
          this.loadActivityTypes();
        }
      }
    );
  }
}
