import {APP_INITIALIZER} from '@angular/core';
import {EnvironmentSpecificFactory} from './environment-specific.factory';
import {EnvironmentSpecificService} from './environment-specific.service';

export const EnvironmentSpecificProvider = {
  provide: APP_INITIALIZER,
  useFactory: EnvironmentSpecificFactory,
  deps: [EnvironmentSpecificService],
  multi: true
};
