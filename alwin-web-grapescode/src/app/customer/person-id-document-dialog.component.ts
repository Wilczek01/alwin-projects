import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {Person} from './shared/person';

@Component({
  selector: 'alwin-person-id-document-dialog',
  templateUrl: './person-id-document-dialog.component.html',
})
export class PersonIdDocumentDialogComponent {
  person: Person;

  constructor(public dialogRef: MatDialogRef<PersonIdDocumentDialogComponent>) {
  }
}
