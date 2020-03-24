/**
 * Właściwość szczegółu czynności windykacyjnej
 */
import {ActivityTypeDetailPropertyDictValue} from './activity-type-detail-property-dict-value';

export class ActivityDetailProperty {
  id: number;
  key: string;
  name: string;
  type: string;
  dictionaryValues: ActivityTypeDetailPropertyDictValue[];

  constructor({id, key, name, type, dictionaryValues}: { id?: number, key?: string, name?: string, type?: string, dictionaryValues?: ActivityTypeDetailPropertyDictValue[] }) {
    this.id = id;
    this.key = key;
    this.name = name;
    this.type = type;
    this.dictionaryValues = dictionaryValues;
  }
}
