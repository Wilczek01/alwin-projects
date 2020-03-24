import {KeyLabel} from '../../shared/key-label';
import {MessageService} from '../../shared/message.service';
import {ContactDetailService} from './contact-detail-service';
import {ContactDetail} from '../shared/contact-detail';
import {ContactType} from '../shared/contact-type';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';


export abstract class ContactDetailDialogComponent {
  loading: boolean;
  loadingStates: boolean;
  contactDetail = new ContactDetail();
  types: ContactType[];
  states: KeyLabel[];

  constructor(protected contactDetailService: ContactDetailService, protected messageService: MessageService) {
  }

  /**
   * Ładuje możliwe typy dla kontaktów - Numer telefonu, Osoba kontaktowa, E-mail itp.
   */
  loadContactDetailsTypes() {
    this.loading = true;
    this.contactDetailService.getContactDetailsTypes().subscribe(
      types => {
        if (types == null) {
          this.loading = false;
          return;
        }
        this.types = types;
        this.loading = false;
        if (this.contactDetail.contactType != null) {
          this.contactDetail.contactType = this.types.find(obj => obj.key === this.contactDetail.contactType.key);
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Ładuje możliwe stany dla kontaktów - preferowany, aktywny, nieaktwyny
   */
  loadContactDetailsStates() {
    this.loadingStates = true;
    this.contactDetailService.getContactDetailsStates().subscribe(
      states => {
        if (states == null) {
          this.loadingStates = false;
          return;
        }
        this.states = states;
        this.loadingStates = false;
        if (this.contactDetail.state != null) {
          this.contactDetail.state = this.states.find(obj => obj.key === this.contactDetail.state.key);
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingStates = false;
      });
  }
}
