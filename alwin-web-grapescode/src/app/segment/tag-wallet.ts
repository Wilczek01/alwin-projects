import {Wallet} from './wallet';
import {Tag} from '../tag/tag';

/**
 * Portfele zleceń liczone dla zleceń przypisanych do etykiety.
 * Portfele są liczone z otwartych zleceń windykacyjnych.
 */
export class TagWallet extends Wallet {

  /**
   * Etykieta dla której liczony jest portfel
   */
  tag: Tag;
}
