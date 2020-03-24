import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Tag} from './tag';
import {TagIcon} from './tag-icon';

/**
 * Serwis dostępu do usług REST dla symboli etykiet
 */
@Injectable()
export class TagIconService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera symbole etykiet
   *
   * @returns {Observable<Tag[]>} lista symboli etykiet
   */
  getTagIcons(): Observable<TagIcon[]> {
    return this.http.get<Tag[]>('tags/icons');
  }

}
