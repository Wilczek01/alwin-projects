import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {STREET_PREFIXES} from './street-prefix';
import {KeyLabel} from '../../shared/key-label';
import {Address} from '../shared/address';
import {AddressService} from './address-service';
import {MessageService} from '../../shared/message.service';

export abstract class AddressDialogComponent {
  loading: boolean;
  address = new Address();
  types: KeyLabel[];
  prefixes = STREET_PREFIXES;

  constructor(protected addressService: AddressService, protected messageService: MessageService) {
    this.address.country = 'Polska';
  }

  /**
   * Ładuje listę dostępnych typów adresów
   */
  protected loadAddressTypes() {
    this.loading = true;
    this.addressService.getAddressesTypes().subscribe(
      types => {
        if (types == null) {
          this.loading = false;
          return;
        }
        this.types = types;
        this.loading = false;
        if (this.address.addressType != null) {
          this.address.addressType = this.types.find(obj => obj.key === this.address.addressType.key);
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }
}
