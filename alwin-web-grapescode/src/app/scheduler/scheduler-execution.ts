/**
 * Wykonanie zadania cyklicznego
 */
import {KeyLabel} from '../shared/key-label';

export class SchedulerExecution {
  id: number;
  startDate: Date;
  endDate: Date;
  state: string;
  type: KeyLabel;
  manual: boolean;
}
