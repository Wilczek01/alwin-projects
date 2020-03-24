import {Directive, forwardRef} from '@angular/core';
import {ControlContainer, NgForm, NgModelGroup} from '@angular/forms';

@Directive({
  selector: '[alwinProvideParentForm]',
  providers: [
    {
      provide: ControlContainer,
      useFactory: ngFormFunction,
      deps: [NgForm]
    }
  ]
})
export class ProvideParentFormDirective {
}

export function ngFormFunction(form: NgForm) {
  return form;
}

export const modelGroupProvider: any = {
  provide: ControlContainer,
  useExisting: forwardRef(() => NgModelGroup)
};
