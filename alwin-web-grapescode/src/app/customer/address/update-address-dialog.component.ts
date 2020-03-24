import {Component, OnInit} from '@angular/core';
import {Customer} from '../shared/customer';
import {Address} from '../shared/address';
import {AddressService} from './address-service';
import {KeyLabel} from '../../shared/key-label';
import {MessageService} from '../../shared/message.service';
import { MatDialogRef } from '@angular/material/dialog';
import {STREET_PREFIXES} from './street-prefix';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {AddressDialogComponent} from './address-dialog.component';

/**
 * Komponent dla wiodku aktualizacji adresu dla klienta
 */
@Component({
  selector: 'alwin-update-address-dialog',
  styleUrls: ['./update-address-dialog.component.css'],
  templateUrl: './update-address-dialog.component.html',
})
export class UpdateAddressDialogComponent extends AddressDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<UpdateAddressDialogComponent>, addressService: AddressService, messageService: MessageService) {
    super(addressService, messageService);
  }

  ngOnInit() {
    this.loading = true;
    const prefix = this.prefixes.find(obj => obj === this.address.streetPrefix);
    if (prefix == null && this.address.streetPrefix != null) {
      this.prefixes.push(this.address.streetPrefix);
    } else {
      this.address.streetPrefix = prefix;
    }
    this.loadAddressTypes();
  }

  /**
   * Aktualizuje załadowany adres
   */
  updateAddress() {
    this.loading = true;
    this.addressService.updateAddress(this.address).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Adres został zapisany');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się zapisać adresu');
          this.loading = false;
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
