import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {SmsTemplate} from './sms-temlate';
import {SmsMessage} from './sms-message';

@Injectable()
export class SmsService {

  constructor(private http: AlwinHttpService) {
  }

  getSmsTemplates(): Observable<SmsTemplate[]> {
    return this.http.get<SmsTemplate[]>('messages/sms/templates');
  }

  sendSmsMessage(smsMessage: SmsMessage): any {
    return this.http.postObserve('messages/sms/send', smsMessage);
  }
}
