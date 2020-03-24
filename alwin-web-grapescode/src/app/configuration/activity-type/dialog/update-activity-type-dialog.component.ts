import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {MessageService} from '../../../shared/message.service';
import {OperatorService} from '../../../issues/shared/operator.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {ActivityService} from '../../../activity/activity.service';
import {ActivityDetailProperty} from '../../../activity/activity-detail-property';
import {ActivityType} from '../../../activity/activity-type';
import {UpdateActivityType} from './update-activity-type';
import {ActivityTypeDetailProperty} from '../../../activity/activity-type-detail-property';
import {ActivityStateConst} from '../../../activity/activity-state-const';
import {ActivityDetailPropertyKey} from '../../../activity/activity-detail-property-key';
import {PrepareDictValuesDialogComponent} from './prepare-dict-values-dialog.component';
import {ActivityTypeWithDetailProperties} from '../../../activity/activity-type-with-detail-properties';

/**
 * Komponent dla wiodku aktualizacji typu czynności zlecenia windykacyjnego
 */
@Component({
  selector: 'alwin-activity-type-dialog',
  styleUrls: ['./update-activity-type-dialog.component.css'],
  templateUrl: './update-activity-type-dialog.component.html',
})
export class UpdateActivityTypeDialogComponent implements OnInit {
  loading: boolean;
  loadingProperties: boolean;
  activityType: ActivityType;

  activityDetailProperties: ActivityDetailProperty[];

  selectedActivityDetailProperties: UpdateActivityType[] = [];
  activityDetailPropertyKey = ActivityDetailPropertyKey;
  activityDetailPropertiesToUpdate: ActivityDetailProperty[] = [];

  constructor(public dialogRef: MatDialogRef<UpdateActivityTypeDialogComponent>,
              private operatorService: OperatorService,
              private messageService: MessageService,
              private activityService: ActivityService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadActivityTypes();
  }

  /**
   * Aktualizuje istniejący typ czynności
   */
  updateActivityType() {
    this.loading = true;

    const activityTypeWithDetailProperties = <ActivityTypeWithDetailProperties>this.activityType;
    activityTypeWithDetailProperties.detailProperties = this.activityDetailPropertiesToUpdate;
    activityTypeWithDetailProperties.activityTypeDetailProperties = this.applyActivityTypeDetailProperties();
    this.activityService.updateActivityType(activityTypeWithDetailProperties, activityTypeWithDetailProperties.id).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Konfiguracja została zaktualizowana');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zaktualizować konfiguracji');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Buduje listę cech z ich atrybutami wymagalności dla wybranego typu czynności windykacyjnej
   */
  private applyActivityTypeDetailProperties(): ActivityTypeDetailProperty[] {
    const properties: ActivityTypeDetailProperty[] = [];
    this.selectedActivityDetailProperties.forEach(selected => {
      if (selected.selected) {
        properties.push(new ActivityTypeDetailProperty(selected.id, selected.activityDetailProperty, ActivityStateConst.EXECUTED, selected.required));
      }
      if (this.activityType.canBePlanned && selected.selectedPlanned) {
        properties.push(new ActivityTypeDetailProperty(selected.idPlanned, selected.activityDetailProperty, ActivityStateConst.PLANNED, selected.requiredPlanned));
      }
    });
    return properties;
  }

  /**
   * Pobieranie wszystkich typów czynności
   */
  loadActivityTypes() {
    this.loadingProperties = true;
    this.activityService.getActivityDetailProperties().subscribe(
      properties => {
        if (properties == null) {
          this.loadingProperties = false;
          return;
        }
        this.activityDetailProperties = properties;

        this.activityDetailProperties.forEach(adp => {
          const updateActivityType = new UpdateActivityType();

          this.activityType.activityTypeDetailProperties.forEach(selectedDetailProperty => {
            if (selectedDetailProperty.activityDetailProperty.id === adp.id) {
              if (selectedDetailProperty.state === ActivityStateConst.EXECUTED) {
                updateActivityType.required = selectedDetailProperty.required;
                updateActivityType.selected = true;
                updateActivityType.id = selectedDetailProperty.id;
              } else if (selectedDetailProperty.state === ActivityStateConst.PLANNED) {
                updateActivityType.requiredPlanned = selectedDetailProperty.required;
                updateActivityType.selectedPlanned = true;
                updateActivityType.idPlanned = selectedDetailProperty.id;
              }
            }
          });

          updateActivityType.activityDetailProperty = adp;
          this.selectedActivityDetailProperties.push(updateActivityType);
        });

        this.loadingProperties = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingProperties = false;
      });
  }

  chooseDictionaryValues(activityDetailProperty: ActivityDetailProperty) {
    const dialogRef = this.dialog.open(PrepareDictValuesDialogComponent);
    const activityDetailPropertyToUpdate = this.activityDetailPropertiesToUpdate.find(adp => adp.id === activityDetailProperty.id)
    dialogRef.componentInstance.activityDetailProperty = activityDetailPropertyToUpdate == null ? Object.assign({}, activityDetailProperty) : activityDetailPropertyToUpdate;
    dialogRef.afterClosed().subscribe(property => {
        if (property !== null) {
          if (activityDetailPropertyToUpdate == null) {
            this.activityDetailPropertiesToUpdate.push(property);
          } else {
            activityDetailPropertyToUpdate.dictionaryValues = property.dictionaryValues;
          }
        }
      }
    );
  }

}
