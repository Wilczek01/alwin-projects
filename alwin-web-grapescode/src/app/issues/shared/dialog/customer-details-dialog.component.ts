import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {Customer} from '../../../customer/shared/customer';

@Component({
  selector: 'alwin-customer-details-dialog',
  styleUrls: ['./customer-details-dialog.component.css'],
  templateUrl: './customer-details-dialog.component.html'
})
export class CustomerDetailsDialogComponent {
  customer: Customer;

  constructor(public dialogRef: MatDialogRef<CustomerDetailsDialogComponent>) {
  }
}
