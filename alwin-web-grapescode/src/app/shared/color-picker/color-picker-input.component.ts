import {Component, Input, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';

/**
 * Input umożliwiający pobranie koloru
 */
@Component({
  selector: 'alwin-color-picker-input',
  styleUrls: ['./color-picker-input.component.css'],
  templateUrl: './color-picker-input.component.html',
  exportAs: 'colorField'
})
export class ColorPickerInputComponent {

  /**
   * Obiekt z atrybutem 'color'
   */
  @Input()
  objectWithColor: any;

  @ViewChild('colorFieldForm', {static: true})
  form: NgForm;

  colors = [
    '#00BCD4',
    '#009688',
    '#4CAF50',
    '#8BC34A',
    '#CDDC39',
    '#FFEB3B',
    '#FFC107',
    '#FF9800',
    '#795548',
    '#607D8B',
    '#9E9E9E',
    '#000000',
    '#FF5722',
    '#F44336',
    '#9C27B0',
    '#673AB7',
    '#2196F3',
    '#03A9F4',
    '#3F51B5',
    '#E91E63'];

  fillColor(color: string) {
    this.objectWithColor.color = color;
  }

}
