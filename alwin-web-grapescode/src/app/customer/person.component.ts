import {Component, Input} from '@angular/core';
import {Person} from './shared/person';
import {PersonIdDocumentDialogComponent} from './person-id-document-dialog.component';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'alwin-person',
  templateUrl: './person.component.html'
})
export class PersonComponent {

  @Input()
  person: Person;


  constructor(private dialog: MatDialog) {
  }

  openPersonIdDocument(person: Person) {
    const dialogRef = this.dialog.open(PersonIdDocumentDialogComponent);
    dialogRef.componentInstance.person = person;
  }
}
