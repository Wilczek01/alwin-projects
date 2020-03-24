import {EnvironmentSpecificService} from './environment-specific.service';

export function EnvironmentSpecificFactory(service: EnvironmentSpecificService) {
  return () => service.load();
}
