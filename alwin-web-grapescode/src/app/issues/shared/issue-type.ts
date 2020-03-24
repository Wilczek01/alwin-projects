import {OperatorType} from '../../operator/operator-type';

/**
 * Typ zlecenia windykacyjnego
 */
export class IssueType {
  /*
   *  Tworzy nowe issueType na podstawie już istniejącego
   */
  constructor(id: number, name: string, label: string) {
    this.id = id;
    this.name = name;
    this.label = label;
  }
  id: number;
  name: string;
  label: string;
  operatorTypes: OperatorType[];
}
