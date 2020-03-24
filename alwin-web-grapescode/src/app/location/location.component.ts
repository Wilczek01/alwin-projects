import {Component, OnInit, ViewChild} from '@angular/core';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {MessageService} from '../shared/message.service';
import {ConfirmationDialogComponent} from '../shared/dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {HttpParams} from '@angular/common/http';
import {LocationDatasource} from './location-datasource';
import {LocationService} from './location.service';
import {Location} from './model/location';
import {UpdateLocationDialogComponent} from './dialog/update-location-dialog.component';
import {CreateLocationDialogComponent} from './dialog/create-location-dialog.component';
import {MASK_PATTERN} from './util/mask-utils';

@Component({
  selector: 'alwin-locations',
  styleUrls: ['location.component.css'],
  templateUrl: 'location.component.html'
})
export class LocationComponent implements OnInit {

  maskPattern = MASK_PATTERN;
  loading;
  mask: string;
  locationDataSource: LocationDatasource;
  @ViewChild('paginator', {static: true})
  paginator: MatPaginator;

  displayedColumns = [
    'mask',
    'operator',
    'actions'
  ];

  constructor(private messageService: MessageService, private locationService: LocationService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.refreshLocations();
  }

  /**
   * Otwiera okno wymuszające potwierdzenie usunięcia maski kodu pocztowego o podanym identyfikatorze. Usuwa maskę kodu pocztowego w przypadku potwierdzenia.
   *
   * @param {number} postalCodeId - identyfikator maski kodu pocztowego
   */
  confirmDeleteLocation(postalCodeId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Usuwanie maski kodu pocztowego';
    dialogRef.componentInstance.message = 'Czy na pewno chcesz usunąć wybraną maskę kodu pocztowego?';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.deleteLocation(postalCodeId);
      }
    });
  }

  /**
   * Usuwa maskę kodu pocztowego o podanym identyfikatorze
   *
   * @param {number} postalCodeId - identyfikator maski kodu pocztowego
   */
  private deleteLocation(postalCodeId: number) {
    this.loading = true;
    this.locationService.deleteLocation(postalCodeId).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Maska została usunięta');
        } else {
          this.messageService.showMessage('Nie udało się usunąć maski');
        }
        this.refreshLocations();
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  refreshLocations() {
    let params = new HttpParams();
    if (this.mask != null) {
      params = params.append('mask', this.mask);
    }
    this.locationDataSource = new LocationDatasource(this.locationService, this.paginator, this.messageService, params);
  }

  /**
   * Otwiera okno do aktualizacji wybranej maski kodu pocztowego
   *
   * @param {Location} location - maska kodu pocztowego do aktualizacji
   */
  updateLocation(location: Location) {
    const dialogRef = this.dialog.open(UpdateLocationDialogComponent);
    dialogRef.componentInstance.location = Object.assign({}, location);
    dialogRef.afterClosed().subscribe(updated => {
      if (updated) {
        this.refreshLocations();
      }
    });
  }

  /**
   * Otwiera okno tworzenia nowej maski kodu pocztowego
   */
  createLocation() {
    const dialogRef = this.dialog.open(CreateLocationDialogComponent);
    dialogRef.afterClosed().subscribe(created => {
      if (created) {
        this.refreshLocations();
      }
    });
  }

}
