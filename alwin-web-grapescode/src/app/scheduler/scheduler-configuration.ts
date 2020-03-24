/**
 * Konfiguracja zadania cyklicznego
 */
import {KeyLabel} from '../shared/key-label';

export class SchedulerConfiguration {
  id: number;
  batchProcess: KeyLabel;
  updateDate: Date;
  hour: number;
}
