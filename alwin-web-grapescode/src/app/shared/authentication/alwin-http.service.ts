import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {EnvironmentSpecificService} from '../environment-specific.service';
import {EnvSpecific} from '../env-specific';

/**
 * Serwis HTTP opakowujący domyślną implementację
 * Ustawia domyślną ścieżkę z dostępem do usług REST oraz przekazuje tokena zaogowanego użytkownika
 */
@Injectable()
export class AlwinHttpService {

  alwinBaseApiUrl: string;

  constructor(private http: HttpClient, envSpecificSvc: EnvironmentSpecificService) {
    envSpecificSvc.subscribe(this, this.setLink);
  }

  setLink(caller: any, es: EnvSpecific) {
    const thisCaller = caller as AlwinHttpService;
    thisCaller.alwinBaseApiUrl = es.url;
  }

  /**
   * Tworzy nagłówek http z tokenem zalogowanego użytkownika
   * @returns {HttpHeaders} - nagłówek http
   */
  createAuthorizationHeader(): HttpHeaders {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    return new HttpHeaders().set('Authorization', currentUser.token);
  }

  /**
   * Wysyła żądanie http z metodą GET i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @returns {Observable<T>} - ciało odpowiedzi http
   */
  get<T>(url) {
    const headers = this.createAuthorizationHeader();
    return this.http.get<T>(this.alwinBaseApiUrl + url, {headers});
  }

  /**
   * Wysyła żądanie http z metodą GET, przekazanymi parametrami i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ściezka zasobu
   * @param myParams - parametry do przekazania
   * @returns {Observable<T>} - ciało odpowiedzi http
   */
  getWithQuery<T>(url, myParams: HttpParams) {
    const headers = this.createAuthorizationHeader();
    const options = {headers, params: myParams};
    return this.http.get<T>(this.alwinBaseApiUrl + url, options);
  }

  /**
   * Wysyła żądanie http z metodą GET, przekazanymi parametrami, nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ściezka zasobu
   * @param myParams - parametry do przekazania
   * @param responseType - typ odpowiedzi
   * @returns {Observable<T>} - ciało odpowiedzi http
   */
  getWithQueryWithTextResponseType<T>(url, myParams: HttpParams, responseType) {
    const headers = this.createAuthorizationHeader();
    const options = {headers, params: myParams, responseType: responseType as 'json'};
    return this.http.get<T>(this.alwinBaseApiUrl + url, options);
  }


  /**
   * Wysyła żądanie http metodą POST z dokumentem zawierającym obiekt data i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @returns {Observable<T>} - ciało odpowiedzi http
   */
  post<T>(url, data) {
    const headers = this.createAuthorizationHeader();
    return this.http.post<T>(this.alwinBaseApiUrl + url, data, {headers});
  }

  /**
   * Wysyła żądanie http metodą POST z dokumentem zawierającym obiekt data i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @returns {Observable<HttpResponse<T>>} - odpowiedź http
   */
  postObserve<T>(url, data) {
    const headers = this.createAuthorizationHeader();
    return this.http.post<T>(this.alwinBaseApiUrl + url, data, {headers, observe: 'response'});
  }

  /**
   * Wysyła żądanie http metodą POST z dokumentem zawierającym obiekt data, parametrami http i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @param myParams - parametry do dołączenia
   * @returns {Observable<HttpResponse<T>>} - odpowiedź http
   */
  postWithQueryObserve<T>(url, data, myParams: HttpParams) {
    const headers = this.createAuthorizationHeader();
    return this.http.post<T>(this.alwinBaseApiUrl + url, data, {headers, observe: 'response', params: myParams});
  }

  /**
   * Wysyła żądanie http metodą POST z dokumentem zawierającym obiekt data, parametrami http i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @param myParams - parametry do dołączenia
   * @returns {Observable<T>} - odpowiedź http
   */
  postWithQuery<T>(url, data, myParams: HttpParams) {
    const headers = this.createAuthorizationHeader();
    return this.http.post<T>(this.alwinBaseApiUrl + url, data, {headers, observe: 'body', params: myParams});
  }

  /**
   * Wysyła żądanie http metodą PUT z dokumentem zawierającym obiekt data, parametrami http i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @param myParams - parametry do dołączenia
   * @returns {Observable<HttpResponse<T>>} - odpowiedź http
   */
  putWithQueryObserve<T>(url, data, myParams: HttpParams) {
    const headers = this.createAuthorizationHeader();
    return this.http.put<T>(this.alwinBaseApiUrl + url, data, {headers, observe: 'response', params: myParams});
  }

  /**
   * Wysyła żądanie http metodą PATCH z dokumentem zawierającym obiekt data i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @returns {Observable<HttpResponse<T>>} - odpowiedź http
   */
  patch<T>(url, data) {
    const headers = this.createAuthorizationHeader();
    return this.http.patch<T>(this.alwinBaseApiUrl + url, data, {headers, observe: 'response'});
  }

  /**
   * Wysyła żądanie http metodą DELETE z nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @returns {Observable<HttpResponse<T>>} - odpowiedź http
   */
  delete<T>(url) {
    const headers = this.createAuthorizationHeader();
    return this.http.delete<T>(this.alwinBaseApiUrl + url, {headers, observe: 'response'});
  }

  /**
   * Wysyła żądanie http metodą PATCH z dokumentem zawierającym obiekt data, parametrami http i nagłówkiem zawierającym token zalogowanego użytkownika
   * @param url - ścieżka zasobu
   * @param data - obiekt do przekazania w żądaniu
   * @param myParams - parametry do dołączenia
   * @returns {Observable<HttpResponse<T>>} - odpowiedź http
   */
  patchWithQuery<T>(url, data, myParams: HttpParams) {
    const headers = this.createAuthorizationHeader();
    return this.http.patch<T>(this.alwinBaseApiUrl + url, data, {headers, observe: 'response', params: myParams});
  }
}
