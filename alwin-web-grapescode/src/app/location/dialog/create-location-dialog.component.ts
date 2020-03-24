import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {LocationService} from '../location.service';
import {LocationInput} from '../model/location-input';
import {Operator} from '../../operator/operator';
import {FieldOperatorsDatasource} from '../../operator/field-operators-datasource';
import {OperatorService} from '../../issues/shared/operator.service';
import {ErrorResponse} from '../../error/error-response';
import {MASK_PATTERN} from '../util/mask-utils';

/**
 * Komponent dla widoku tworzenia noweej maski kodu pocztowego
 */
@Component({
  selector: 'alwin-create-location-dialog',
  styleUrls: ['./location-dialog.component.css'],
  templateUrl: './create-location-dialog.component.html',
})
export class CreateLocationDialogComponent implements OnInit {

  maskPattern = MASK_PATTERN;
  loading: boolean;
  location = new LocationInput();
  datasource: FieldOperatorsDatasource;
  @ViewChild('paginator', {static: true})
  paginator: MatPaginator;
  errorMessage: string;

  displayedColumns = [
    'name',
    'login',
    'actions'
  ];

  constructor(public dialogRef: MatDialogRef<CreateLocationDialogComponent>,
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
   * Tworzy nową maskę kodu pocztowego
   */
  createLocation() {
    this.errorMessage = null;
    this.loading = true;
    this.locationService.createLocation(this.location).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Maska została utworzona');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zapisać maski');
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
