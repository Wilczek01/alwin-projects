import {Pipe, PipeTransform} from '@angular/core';
import {Operator} from '../../operator/operator';

@Pipe({
  name: 'withPermission',
  pure: false
})
export class PermissionPipe implements PipeTransform {

  transform(value: Operator[], args: string[]): Operator[] {
    return value.filter(operator => operator.permission != null);
  }

}
