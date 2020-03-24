import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {LocationInput} from './model/location-input';
import {HttpParams} from '@angular/common/http';
import {Page} from '../shared/pagination/page';
import {Location} from './model/location';

/**
 * Serwisu dostępu do usług REST dla masek kodów pocztowych
 */
@Injectable()
export class LocationService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera maski kodów pocztowych
   *
   * @returns {Observable<Location[]>} lista masek kodów pocztowych
   */
  getLocations(params: HttpParams): Observable<Page<Location>> {
    return this.http.getWithQuery<Page<Location>>('postalCodes', params);
  }

  /**
   * Usuwa wybraną maskę kodu pocztowego
   *
   * @param {number} postalCodeId  - identyfikator maski kodu pocztowego
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteLocation(postalCodeId: number) {
    return this.http.delete(`postalCodes/${postalCodeId}`);
  }

  /**
   * Tworzy nową maskę kodu pocztowego
   *
   * @param {LocationInput} location - szczegóły maski kodu pocztowego
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem utworzenia 201
   */
  createLocation(location: LocationInput): any {
    return this.http.postObserve('postalCodes/', location);
  }

  /**
   * Aktualizuje maskę kodu pocztowego o podanym identyfikatorze
   *
   * @param {number} postalCodeId  - identyfikator maski kodu pocztowego
   * @param {LocationInput} location: - szczegóły maski kodu pocztowego
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateLocation(postalCodeId: number, location: LocationInput): any {
    return this.http.patch(`postalCodes/${postalCodeId}`, location);
  }

}
