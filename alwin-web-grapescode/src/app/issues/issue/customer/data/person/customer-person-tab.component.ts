import {Customer} from '../../../../../customer/shared/customer';
import {Person} from '../../../../../customer/shared/person';
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {CreateContactDetailDialogComponent} from '../../../../../customer/contact-detail/create-contact-detail-dialog.component';
import {PhoneCallService} from '../../../phone-call/phone-call.service';
import {HandlingErrorUtils} from '../../../../shared/utils/handling-error-utils';
import {ContactDetailService} from '../../../../../customer/contact-detail/contact-detail-service';
import {ContactDetail} from '../../../../../customer/shared/contact-detail';
import {MessageService} from '../../../../../shared/message.service';
import {UpdateContactDetailDialogComponent} from '../../../../../customer/contact-detail/update-contact-detail-dialog.component';
import {SendSmsMessageDialogComponent} from '../../../../../message/send-sms-message-dialog.component';
import {PhoneCall} from '../../../phone-call/phone-call';
import {PersonService} from '../../../../../person/person.service';
import {CreatePersonDialogComponent} from '../../../../../person/create-person-dialog.component';

declare let window;

/**
 * Komponent z danymi usób uprawionych klienta w widoku obsługi zlecenia
 */
@Component({
  selector: 'alwin-customer-person-tab',
  templateUrl: './customer-person-tab.component.html',
  styleUrls: ['./customer-person-tab.component.css']
})
export class CustomerPersonTabComponent implements OnInit {
  @Input()
  customer: Customer;

  @Input()
  companyId: number;

  @Input()
  issueId: number;

  @Input()
  readonly: boolean;

  @Output()
  onUpdate = new EventEmitter();

  person: Person;
  loadingContacts: boolean;
  loading = false;
  contacts: ContactDetail[] = [];
  persons: Person[] = [];

  showPersonDetails(person: Person) {
    this.person = person;
    this.refreshContacts(person.id);
  }

  ngOnInit(): void {
    this.loadPersons(this.companyId);
    if (this.person === undefined) {
      this.person = this.customer.company.persons[0];
    }
    if (this.person !== undefined) {
      this.loadContacts(this.person.id);
    }
  }

  constructor(private contactService: ContactDetailService, private messageService: MessageService,
              private dialog: MatDialog, private phoneCallService: PhoneCallService, private personService: PersonService) {
  }

  /**
   * Otweira formularz do dodawania nowej osoby
   */
  createNewPerson() {
    const dialogRef = this.dialog.open(CreatePersonDialogComponent);
    dialogRef.componentInstance.companyId = this.companyId;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadPersons(this.companyId);
      }
    });
  }

  /**
   * Oznacznie lub odznaczenie osoby uprawnionej firmy jako osoba do kontaktu
   */
  updateContactPerson() {
    this.loading = true;
    this.personService.updateContactPerson(this.person.id, this.customer.company.id, this.person.contactPerson).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Osoba do kontaktu została zaktualizowana');
          this.loading = false;
        } else {
          this.messageService.showMessage('Nie udało się zaktualizować użytkownika');
          this.loading = false;
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Rozpoczęcie rozmowy telefonicznej i otwarcie formularza
   *
   * @param {Person} person - osoba
   * @param {ContactDetail} contactDetail - kontakt
   */
  startPhoneCall(person: Person, contactDetail: ContactDetail) {
    this.openCallTo(contactDetail);
    const phoneCall = new PhoneCall();
    phoneCall.selectedPerson = person;
    phoneCall.availablePersons = this.persons;
    phoneCall.contactDetail = contactDetail;
    this.phoneCallService.startPhoneCall(phoneCall);
  }

  /**
   * Uruchamia skrypt do dzwonienia
   *
   * @param {ContactDetail} contactDetail
   */
  private openCallTo(contactDetail: ContactDetail) {
    if (window.location.search.indexOf('qa=true') <= -1) {
      window.location.href = `callto:${contactDetail.contact}`;
    }
  }

  /**
   * Towrzenie nowego kontaku dla osoby
   */
  createNewContractDetail(customer: Customer) {
    const dialogRef = this.dialog.open(CreateContactDetailDialogComponent);
    dialogRef.componentInstance.personId = this.person.id;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadContacts(this.person.id);
      }
    });
  }

  /**
   * Pobiera kontakty dla załadowanej osoby
   */
  loadContacts(personId: number) {
    this.loadingContacts = true;
    this.contactService.getPersonContactDetails(personId).subscribe(
      contacts => {
        if (contacts != null) {
          this.contacts = contacts;
        }
        this.loadingContacts = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingContacts = false;
      });
  }

  /**
   * Pobiera osoby uprawnione dla firmy
   */
  loadPersons(companyId: number) {
    this.loadingContacts = true;
    this.personService.getCompanyPersons(companyId).subscribe(
      persons => {
        if (persons != null) {
          this.persons = persons;
          this.person = persons[0];
        }
        this.loadingContacts = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingContacts = false;
      });
  }

  refreshContacts(personId: number) {
    this.loadContacts(personId);
  }

  /**
   * Aktualizuje podany kontakt dla załadowanego klienta
   *
   * @param {ContactDetail} contact - kontakt do aktualizacji
   */
  updateContact(contact: ContactDetail) {
    const dialogRef = this.dialog.open(UpdateContactDetailDialogComponent);
    dialogRef.componentInstance.contactDetail = Object.assign({}, contact);
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadContacts(this.person.id);
      }
    });
  }

  /**
   * Wysyła wiadomość sms do podanego numeru
   *
   * @param {string} phoneNumber - numer do wysyłki sms
   */
  sendSmsMessage(phoneNumber: string) {
    const dialogRef = this.dialog.open(SendSmsMessageDialogComponent);
    dialogRef.componentInstance.phoneNumber = phoneNumber;
    dialogRef.componentInstance.issueId = this.issueId;
  }

}
