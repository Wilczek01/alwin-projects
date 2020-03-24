/**
 * Maska kodu pocztowego bez identyfikatora
 */
import {Operator} from '../../operator/operator';

export class LocationInput {

  constructor(location?: LocationInput) {
    if (location != null) {
      this.mask = location.mask;
      this.operator = location.operator;
    }
  }

  mask: string;
  operator: Operator;
}
