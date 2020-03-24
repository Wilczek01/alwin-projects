import {Component, Input, OnInit} from '@angular/core';
import {ContactDetail} from '../../../../../customer/shared/contact-detail';
import {HandlingErrorUtils} from '../../../../shared/utils/handling-error-utils';
import {ContactDetailService} from '../../../../../customer/contact-detail/contact-detail-service';
import { MatDialog } from '@angular/material/dialog';
import {Customer} from '../../../../../customer/shared/customer';
import {MessageService} from '../../../../../shared/message.service';
import {CreateContactDetailDialogComponent} from '../../../../../customer/contact-detail/create-contact-detail-dialog.component';
import {UpdateContactDetailDialogComponent} from '../../../../../customer/contact-detail/update-contact-detail-dialog.component';
import {SendSmsMessageDialogComponent} from '../../../../../message/send-sms-message-dialog.component';
import {Person, UNKNOWN_PERSON} from '../../../../../customer/shared/person';
import {PersonService} from '../../../../../person/person.service';
import {PhoneCallService} from '../../../phone-call/phone-call.service';
import {PhoneCall} from '../../../phone-call/phone-call';

/**
 * Komponent z danymi umów klienta w widoku obsługi zlecenia
 */
@Component({
  selector: 'alwin-customer-contact-tab',
  templateUrl: './customer-contact-tab.component.html',
  styleUrls: ['./customer-contact-tab.component.css']
})
export class CustomerContactTabComponent implements OnInit {

  @Input()
  customer: Customer;
  @Input()
  issueId: number;
  @Input()
  readonly: boolean;
  contacts: ContactDetail[] = [];
  loadingContacts: boolean;

  constructor(private contactService: ContactDetailService, private messageService: MessageService,
              private dialog: MatDialog, private phoneCallService: PhoneCallService, private personService: PersonService) {
  }


  ngOnInit(): void {
    this.loadContacts();
  }

  /**
   * Pobiera kontakty dla załadowanego klienta
   */
  loadContacts() {
    this.loadingContacts = true;
    this.contactService.getCustomerContactDetails(this.customer).subscribe(
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
   * Tworzy nowy kontakt dla załadowanego klienta
   */
  createNewContact() {
    const dialogRef = this.dialog.open(CreateContactDetailDialogComponent);
    dialogRef.componentInstance.companyId = this.customer.company.id;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadContacts();
      }
    });
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
        this.loadContacts();
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

  /**
   * Rozpoczęcie rozmowy telefonicznej i otwarcie formularza
   *
   * @param {ContactDetail} contactDetail - kontakt
   */
  startPhoneCall(contactDetail: ContactDetail) {
    this.loadingContacts = true;
    this.personService.getCompanyPersons(this.customer.company.id).subscribe(
      persons => {
        this.startPhoneCallInternal(persons, contactDetail);
        this.loadingContacts = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingContacts = false;
      });

  }

  /**
   * Rozpoczęcie rozmowy telefonicznej i otwarcie formularza
   *
   * @param {Person} persons - lista osób
   * @param {ContactDetail} contactDetail - kontakt
   */
  startPhoneCallInternal(persons: Person[], contactDetail: ContactDetail) {
    this.openCallTo(contactDetail);
    const phoneCall = new PhoneCall();
    phoneCall.selectedPerson = UNKNOWN_PERSON;
    phoneCall.availablePersons = persons;
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
}
