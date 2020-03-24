import {Directive, Input} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from '@angular/forms';

@Directive({
  selector: '[alwinDateBefore]',
  providers: [{provide: NG_VALIDATORS, useExisting: DateBeforeValidatorDirective, multi: true}]
})
export class DateBeforeValidatorDirective implements Validator {

  @Input() dateAfter: string;

  validate(control: AbstractControl): { [key: string]: any } {
    const date = control.value;

    if (date > this.dateAfter) {
      return {
        alwinDateBefore: false
      };
    }

    return null;
  }
}
