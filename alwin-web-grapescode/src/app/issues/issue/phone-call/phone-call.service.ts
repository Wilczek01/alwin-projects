import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {PhoneCall} from './phone-call';
import {DeclarationSum} from './declaration-sum';

@Injectable()
export class PhoneCallService {

  private phoneCall = new Subject<PhoneCall>();
  private declarationSum = new Subject<DeclarationSum>();

  phoneCall$ = this.phoneCall.asObservable();
  declarationSum$ = this.declarationSum.asObservable();

  startPhoneCall(phoneCall: PhoneCall) {
    this.phoneCall.next(phoneCall);
  }

  addSumToDeclaration(PLNSum: number, EURSum: number) {
    this.declarationSum.next(new DeclarationSum(PLNSum, EURSum));
  }

}
