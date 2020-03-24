import {Component, Input, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {MessageService} from '../shared/message.service';
import {PersonService} from './person.service';
import {Person} from '../customer/shared/person';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {PersonValidation} from '../customer/shared/person-validation';
import {KeyLabel} from '../shared/key-label';

/**
 * Komponent odpowiedzialny za tworzenie osoby uprawnionej firmy w formie dialogu
 */
@Component({
  selector: 'alwin-create-person-dialog',
  styleUrls: ['./create-person-dialog.component.css'],
  templateUrl: './create-person-dialog.component.html',
})
export class CreatePersonDialogComponent implements OnInit {
  loading: boolean;
  loadingTypes: boolean;
  person: Person = new Person();
  validationResult: PersonValidation = new PersonValidation();
  maritalStatuses: KeyLabel[];
  idDocTypes: KeyLabel[];

  @Input()
  companyId: number;

  constructor(public dialogRef: MatDialogRef<CreatePersonDialogComponent>,
              private personService: PersonService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.loading = true;
    this.loadingTypes = true;
    this.getMaritalStatuses();
    this.getDocumentTypes();
  }

  /**
   * Pobieranie statusów cywilnych
   */
  private getMaritalStatuses() {
    this.personService.getMaritalStatuses().subscribe(
      types => {
        this.maritalStatuses = types;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Pobieranie typów dokumentu tożsamości
   */
  private getDocumentTypes() {
    this.personService.getDocumentTypes().subscribe(
      types => {
        this.idDocTypes = types;
        this.loadingTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingTypes = false;
      });
  }

  /**
   * Tworzy osobę uprawnioną firmy
   */
  createPerson() {
    this.loading = true;
    this.personService.createPerson(this.companyId, this.person).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Osoba uprawniona została utworzona');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się utworzyć osoby uprawnionej');
        }
      },
      err => {
        if (err.status === 400) {
          this.validationResult = err.error;
          this.messageService.showMessage('Błąd walidacji poprawności przesyłanych danych');
        } else {
          HandlingErrorUtils.handleError(this.messageService, err);
        }
        this.loading = false;
      }
    );
  }
}
