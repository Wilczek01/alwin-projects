import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import { MatPaginator } from '@angular/material/paginator';
import {MessageService} from '../shared/message.service';
import {HttpParams} from '@angular/common/http';
import {Location} from './model/location';
import {LocationService} from './location.service';

/**
 * Źródło danych dla tabeli wyświetlajacej maski kodów pocztowych
 */
export class LocationDatasource extends AbstractAlwinDataSource<Location> {

  constructor(private locationService: LocationService, private paginator: MatPaginator, messageService: MessageService, params: HttpParams) {
    super(messageService);
    this.params = params;
  }

  /**
   * Pobiera stronę z maskami kodw pocztowych
   * @returns {Observable<Page<Location>>} - strona z maskami kodw pocztowych
   */
  protected loadPage(): Observable<Page<Location>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResults = String(this.paginator.pageSize);
    let params = new HttpParams();
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResults);
    if (this.params.get('mask') != null) {
      params = params.append('mask', this.params.get('mask'));
    }
    return this.locationService.getLocations(params);
  }

  connect(): Observable<Location[]> {
    const displayDataChanges = [
      this.paginator.page
    ];
    return this.observePage(displayDataChanges);
  }
}
