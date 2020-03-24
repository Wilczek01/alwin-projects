import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {ActivityDetailProperty} from '../../../activity/activity-detail-property';
import {ActivityTypeDetailPropertyDictValue} from '../../../activity/activity-type-detail-property-dict-value';

/**
 * Komponent dla wiodku dialogu ustawiającego dane słownikowe cech czynności windykacyjnej
 */
@Component({
  selector: 'alwin-prepare-dict-values-dialog',
  styleUrls: ['./prepare-dict-values-dialog.component.css'],
  templateUrl: './prepare-dict-values-dialog.component.html',
})
export class PrepareDictValuesDialogComponent implements OnInit {
  activityDetailProperty: ActivityDetailProperty;
  dictValue: string;

  constructor(public dialogRef: MatDialogRef<PrepareDictValuesDialogComponent>) {
  }

  ngOnInit(): void {
    if (this.activityDetailProperty.dictionaryValues === null) {
      this.activityDetailProperty.dictionaryValues = [];
    }
  }

  /**
   * Dodaje do słownika nową wartość
   */
  addDictValue() {
    this.activityDetailProperty.dictionaryValues.push(new ActivityTypeDetailPropertyDictValue(null, this.dictValue));
    this.dictValue = null;
  }

  /**
   * Usuwa z listy słownikowych wartości element o podanym indeksie
   * @param index - indeks, kolejność na liście
   */
  removeDictValue(index: number) {
    this.activityDetailProperty.dictionaryValues.splice(index, 1);
  }

  /**
   * Zamyka okno dialogu edycji słownika przekazując właściwość cechy z ustawionymi wartościami slownikowymi
   */
  prepareDict() {
    this.dialogRef.close(this.activityDetailProperty);
  }

}
