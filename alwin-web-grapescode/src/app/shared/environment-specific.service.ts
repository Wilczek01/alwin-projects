import {Injectable} from '@angular/core';
import {EnvSpecific} from './env-specific';
import {BehaviorSubject} from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class EnvironmentSpecificService {

  public envSpecific: EnvSpecific;
  private envSpecificSubject: BehaviorSubject<EnvSpecific> = new BehaviorSubject<EnvSpecific>(null);

  public load() {
    return new Promise((resolve, reject) => {
      this.setEnvSpecific(new EnvSpecific(environment.apiUrl, environment.production));
      resolve();
    });
  }

  private setEnvSpecific(es: EnvSpecific) {
    if (es === null || es === undefined) {
      return;
    }

    this.envSpecific = es;
    if (this.envSpecificSubject) {
      this.envSpecificSubject.next(this.envSpecific);
    }
  }

  public subscribe(caller: any, callback: (caller: any, es: EnvSpecific) => void) {
    this.envSpecificSubject
      .subscribe((es) => {
        if (es === null) {
          return;
        }
        callback(caller, es);
      });
  }
}
