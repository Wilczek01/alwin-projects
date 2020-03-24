import {Pipe, PipeTransform} from '@angular/core';

/**
 * Pipe do budowy adresu. Wymagana kolejność parametrów:
 *
 * prefix jako wartość
 * ulica, numer domu, numer mieszkania, kod pocztowy, miasto, kraj jako argumenty
 */
@Pipe({
  name: 'buildAddress'
})
export class AddressPipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (args == null || args.length !== 6) {
      return 'b/d';
    }

    let address = value != null ? value : '';

    address = this.buildFirstAddressLine(args, address);
    address += '<br/>';
    address = this.buildSecondAddressLine(args, address);
    address += '<br/>';
    return this.buildThirdAddressLine(address, args);
  }

  private buildThirdAddressLine(address: string, args: string[]) {
    address += args[5] != null && args[5].length > 0 ? args[5] : 'b/d';
    return address;
  }

  private buildSecondAddressLine(args: string[], address: string) {
    const city = args[4] != null && args[4].length > 0 ? args[4] : 'b/d';

    if (args[3] != null && args[3].length > 0) {
      address += `${args[3]}, ${city}`;
    } else {
      address += city;
    }
    return address;
  }

  private buildFirstAddressLine(args: string[], address: string) {
    if (args[0] != null && args[0].length > 0) {
      address += address.length > 0 ? ` ${args[0]}` : args[0];
    }

    let addressNumber = args[1] != null ? args[1] : '';

    if (args[2] != null && args[2].length > 0) {
      addressNumber = addressNumber.length > 0 ? `${addressNumber}/${args[2]}` : args[2];
    }

    address += address.length > 0 ? ` ${addressNumber}` : addressNumber;

    address = address.length > 0 ? address.trim() : 'b/d';
    return address;
  }
}
