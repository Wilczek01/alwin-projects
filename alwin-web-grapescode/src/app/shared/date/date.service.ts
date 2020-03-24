import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../authentication/alwin-http.service';
import {Observable} from 'rxjs';
import {DateDto} from './DateDto';

@Injectable()
export class DateService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera początek poprzedniego dnia roboczego
   *
   * @returns {Observable<DateDto>} początek poprzedniego dnia roboczego
   */
  getStartOfPreviousWorkingDay(): Observable<DateDto> {
    return this.http.get<DateDto>('dates/previousWorkingDay');
  }

}
