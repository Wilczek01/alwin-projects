import {Attribute, Directive} from '@angular/core';
import {FormControl, NG_VALIDATORS, Validator} from '@angular/forms';

/**
 * Dyrektywa do sprawdzania czy komponent o podanej nazwie ma taką samą wartość jak ten, do której dyrektywa zotała użyta
 */
@Directive({
  selector: '[alwinRepeatValue]',
  providers: [{provide: NG_VALIDATORS, useExisting: CompareDirective, multi: true}]
})
export class CompareDirective implements Validator {

  constructor(@Attribute('alwinRepeatValue') public comparer: string) {
  }

  /**
   * Waliduje komponent do którego została podpięta dyrektywa
   * @param {FormControl} c - komponent
   * @returns {{[p: string]: any}} - odpowiedź walidacji
   */
  validate(c: FormControl): { [key: string]: any } {
    const e = c.root.get(this.comparer);
    if (e && c.value !== e.value) {
      return {'compare': true};
    }
    return null;
  }
}
