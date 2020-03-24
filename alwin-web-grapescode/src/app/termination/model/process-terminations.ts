import {Termination} from './termination';

/**
 * Obiekt żądania przetworzenia przesłanych wypowiedzeń umów
 */
export class ProcessTerminations {
  terminationsToReject: Termination[] = [];
  terminationsToSend: Termination[] = [];
  terminationsToPostpone: Termination[] = [];
}
