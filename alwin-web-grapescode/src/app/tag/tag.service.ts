import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Tag} from './tag';
import {TagInput} from './tag-input';

/**
 * Serwisu dostępu do usług REST dla etykiet
 */
@Injectable()
export class TagService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera etykiety
   *
   * @returns {Observable<Tag[]>} lista etykiet
   */
  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>('tags');
  }

  /**
   * Usuwa wybraną etykietę
   *
   * @param {number} tagId  - identyfikator etykiety
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteTag(tagId: number) {
    return this.http.delete(`tags/${tagId}`);
  }

  /**
   * Tworzy nową etykietę
   *
   * @param {TagInput} tag - szczegóły etykiety
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem utworzenia 201
   */
  createTag(tag: TagInput): any {
    return this.http.postObserve('tags/', tag);
  }

  /**
   * Aktualizuje etykietę o podanym identyfikatorze
   *
   * @param {number} tagId  - identyfikator etykiety
   * @param {TagInput} tag: - szczegóły etykiety
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateTag(tagId: number, tag: TagInput): any {
    return this.http.patch(`tags/${tagId}`, tag);
  }

}
