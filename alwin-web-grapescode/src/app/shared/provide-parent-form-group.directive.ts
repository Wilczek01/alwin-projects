import {Directive} from '@angular/core';
import {modelGroupProvider} from './provide-parent-form.directive';

@Directive({
  selector: '[alwinProvideParentFormGroup]',
  providers: [
    modelGroupProvider
  ]
})
export class ProvideParentFormGroupDirective {}
