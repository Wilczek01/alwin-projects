import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {HttpClient} from '@angular/common/http';
import {EnvironmentSpecificService} from '../shared/environment-specific.service';
import {EnvSpecific} from '../shared/env-specific';
import {VersionResponse} from './version-response';

/**
 * Serwisu dostępu do usług REST dla wersji
 */
@Injectable()
export class VersionService {

  alwinBaseApiUrl: string;

  constructor(private http: HttpClient, envSpecificSvc: EnvironmentSpecificService) {
    envSpecificSvc.subscribe(this, this.setLink);
  }

  setLink(caller: any, es: EnvSpecific) {
    const thisCaller = caller as AlwinHttpService;
    thisCaller.alwinBaseApiUrl = es.url;
  }

  getVersion() {
    return this.http.get<VersionResponse>(`${this.alwinBaseApiUrl}healthCheck`);
  }
}
