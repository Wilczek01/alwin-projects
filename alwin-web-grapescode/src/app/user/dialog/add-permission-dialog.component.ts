import {Component, Input} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {Operator} from '../../operator/operator';
import {Permission} from '../../operator/permission';

@Component({
  selector: 'alwin-add-permission-dialog',
  templateUrl: './add-permission-dialog.component.html'
})
export class AddPermissionDialogComponent {

  @Input()
  operators: Operator[];

  constructor(public dialogRef: MatDialogRef<AddPermissionDialogComponent>) {
  }

  addPermission(operator: Operator) {
    operator.permission = new Permission();
    this.dialogRef.close(false);
  }

}
