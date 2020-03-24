import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {LocationService} from '../location.service';
import {LocationInput} from '../model/location-input';
import {Location} from '../model/location';
import {MASK_PATTERN} from '../util/mask-utils';
import {FieldOperatorsDatasource} from '../../operator/field-operators-datasource';
import {OperatorService} from '../../issues/shared/operator.service';
import {Operator} from '../../operator/operator';
import {ErrorResponse} from '../../error/error-response';

/**
 * Komponent dla widoku edycji istniejącej maski kodu pocztowego
 */
@Component({
  selector: 'alwin-update-location-dialog',
  styleUrls: ['./location-dialog.component.css'],
  templateUrl: './update-location-dialog.component.html',
})
export class UpdateLocationDialogComponent implements OnInit {

  maskPattern = MASK_PATTERN;
  loading: boolean;
  location = new Location();
  datasource: FieldOperatorsDatasource;
  @ViewChild('paginator', {static: true})
  paginator: MatPaginator;
  errorMessage: string;

  displayedColumns = [
    'name',
    'login',
    'actions'
  ];

  constructor(public dialogRef: MatDialogRef<UpdateLocationDialogComponent>,
              private locationService: LocationService,
              private operatorService: OperatorService,
              private messageService: MessageService,
              private cdRef: ChangeDetectorRef) {

  }

  ngOnInit(): void {
    this.datasource = new FieldOperatorsDatasource(this.operatorService, this.paginator, this.messageService);
    this.cdRef.detectChanges();
  }

  /**
   * Edytuje istniejącą maskę kodu pocztowego
   */
  updateLocation() {
    this.errorMessage = null;
    this.loading = true;
    this.locationService.updateLocation(this.location.id, new LocationInput(this.location)).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Maska została zaktualizowana');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się zaktualizować maski');
        }
      },
      err => {
        this.errorMessage = (err.error as ErrorResponse).message;
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Wybiera operatora dla tworzonej maski kodu pocztowego
   * @param operator
   */
  selectOperator(operator: Operator) {
    this.location.operator = operator;
  }

  /**
   * Usuwa wybranego operatora dla maski kodu pocztowego
   */
  unselect() {
    this.location.operator = null;
    this.cdRef.detectChanges();
  }

}
