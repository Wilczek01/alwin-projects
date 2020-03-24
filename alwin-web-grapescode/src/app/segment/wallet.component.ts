import {Component, Input} from '@angular/core';
import {Wallet} from './wallet';

@Component({
  selector: 'alwin-wallet',
  styleUrls: ['./wallet.component.css'],
  templateUrl: './wallet.component.html'
})
export class WalletComponent {

  @Input()
  wallet: Wallet;
}
