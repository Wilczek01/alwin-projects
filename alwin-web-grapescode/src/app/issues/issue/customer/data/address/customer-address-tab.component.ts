import {Component, Input, OnInit} from '@angular/core';
import {Customer} from '../../../../../customer/shared/customer';
import {AddressService} from '../../../../../customer/address/address-service';
import {Address} from '../../../../../customer/shared/address';
import {MessageService} from '../../../../../shared/message.service';
import {HandlingErrorUtils} from '../../../../shared/utils/handling-error-utils';
import {CreateAddressDialogComponent} from '../../../../../customer/address/create-address-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {UpdateAddressDialogComponent} from '../../../../../customer/address/update-address-dialog.component';

/**
 * Komponent z danymi adresowymi klienta w widoku obsługi zlecenia
 */
@Component({
  selector: 'alwin-customer-address-tab',
  templateUrl: './customer-address-tab.component.html',
  styleUrls: ['./customer-address-tab.component.css']
})
export class CustomerAddressTabComponent implements OnInit {

  @Input()
  customer: Customer;
  @Input()
  readonly: boolean;
  addresses: Address[] = [];
  loadingAddresses: boolean;

  constructor(private addressService: AddressService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadAddresses();
  }

  /**
   * Pobiera adresy dla załadowanego klienta
   */
  loadAddresses() {
    this.loadingAddresses = true;
    this.addressService.getCustomerAddresses(this.customer).subscribe(
      addresses => {
        if (addresses != null) {
          this.addresses = addresses;
        }
        this.loadingAddresses = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingAddresses = false;
      });
  }

  /**
   * Tworzy nowy adres dla załadowanego klienta
   */
  createNewAddress() {
    const dialogRef = this.dialog.open(CreateAddressDialogComponent);
    dialogRef.componentInstance.customer = this.customer;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadAddresses();
      }
    });
  }

  /**
   * Aktualizuje istniejący adres dla załadowanego klienta
   */
  updateAddress(address: Address) {
    const dialogRef = this.dialog.open(UpdateAddressDialogComponent);
    dialogRef.componentInstance.address = Object.assign({}, address);
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadAddresses();
      }
    });
  }
}
