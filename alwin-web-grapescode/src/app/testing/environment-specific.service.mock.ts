import {EnvironmentSpecificService} from '../shared/environment-specific.service';
import {EnvSpecific} from '../shared/env-specific';


export class EnvironmentSpecificServiceMock extends EnvironmentSpecificService {

  public subscribe(caller: any, callback: (caller: any, es: EnvSpecific) => void) {
  }
}
