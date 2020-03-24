import {Component, OnInit} from '@angular/core';
import {Customer} from '../shared/customer';
import {AddressService} from './address-service';
import {MessageService} from '../../shared/message.service';
import { MatDialogRef } from '@angular/material/dialog';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {AddressDialogComponent} from './address-dialog.component';

/**
 * Komponent dla widoku tworzenia nowego adresu dla klienta
 */
@Component({
  selector: 'alwin-create-address-dialog',
  styleUrls: ['./create-address-dialog.component.css'],
  templateUrl: './create-address-dialog.component.html',
})
export class CreateAddressDialogComponent extends AddressDialogComponent implements OnInit {
  customer: Customer;

  constructor(public dialogRef: MatDialogRef<CreateAddressDialogComponent>, addressService: AddressService, messageService: MessageService) {
    super(addressService, messageService);
  }

  ngOnInit() {
    this.loadAddressTypes();
  }

  /**
   * Tworzy nowy adres dla załadowanego klienta
   */
  createAddress() {
    this.loading = true;
    this.addressService.addAddressesForCustomer(this.address, this.customer).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Adres został utworzony');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zapisać adresu');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
