import {AbstractPage} from '../abstract.po';
import {by, element, ElementArrayFinder, ElementFinder} from 'protractor';

export class TagsPage extends AbstractPage {

  /**
   * Zwraca listę wszystkich rekordów w tabeli etykiet
   * @returns {ElementArrayFinder}
   */
  getTagsTableRows() {
    return element.all(by.className('tags-table')).get(0).all(by.tagName('tr'));
  }

  /**
   * Zwraca listę wszystkich wartości(kolumn) dla rekordu o podanym numerze z tabeli etykiet
   *
   * @param {number} no - numer rekordu
   * @returns {ElementArrayFinder}
   */
  getTagsTableRow(no: number) {
    return this.getTagsTableRows().get(no).all(by.tagName('td'));
  }

  /**
   * Zwraca kolor pierwszej ikony z podanej komórki
   *
   * @param {ElementFinder} td - komórka w tabeli
   * @returns {any}
   */
  getBookmarkColor(td: ElementFinder) {
    return td.all(by.className('mat-icon')).get(0).getCssValue('color');
  }

  /**
   * Zwraca wartość atrybutu disabled dla przycisku edycji w podanej komórce dla etykiety o podanym identyfikatorze
   * @param {ElementFinder} td - komórka
   * @param {number} tagId - identyfikator etykiety
   * @returns {any} wartość atrybutu disabled
   */
  getEditDisabled(td: ElementFinder, tagId: number) {
    return td.element(by.id(`edit-tag-${tagId}-button`)).getAttribute('disabled');
  }

  /**
   * Zwraca wartość atrybutu disabled dla przycisku usuwania w podanej komórce dla etykiety o podanym identyfikatorze
   * @param {ElementFinder} td - komórka
   * @param {number} tagId - identyfikator etykiety
   * @returns {any} wartość atrybutu disabled
   */
  getDeleteDisabled(td: ElementFinder, tagId: number) {
    return td.element(by.id(`delete-tag-${tagId}-button`)).getAttribute('disabled');
  }

  /**
   * Klika w przycisk dodawania etykiety
   */
  clickAddTagButton() {
    element(by.id('add-tag-button')).click();
  }

  /**
   * Zwraca dialog dodawania etykiety
   * @returns {ElementFinder}
   */
  createTagDialog() {
    return element(by.id('new-tag-dialog'));
  }

  /**
   * Zwraca dialog edycji etykiety
   * @returns {ElementFinder}
   */
  updateTagDialog() {
    return element(by.id('update-tag-dialog'));
  }

  /**
   * Zwraca treść nagłówka dialogu
   * @returns {ElementFinder}
   */
  getHeaderTitle() {
    return element(by.id('dialog-title')).getText();
  }

  /**
   * Zwraca listę dostępnych symboli etykiet do wyboru
   * @returns {ElementArrayFinder}
   */
  getTagIconsInTagIconPicker(): ElementArrayFinder {
    return element.all(by.css('.tag-icon-picker .mat-icon'));
  }

  /**
   * Zwraca zaznaczone symbole etykiet
   * @returns {ElementArrayFinder}
   */
  getSelectedTagIconsInTagIconPicker(): ElementArrayFinder {
    return element.all(by.css('.tag-icon-picker .mat-icon.selected'));
  }

  /**
   * Klika w przycisk edycji etykiet
   */
  clickEditTagButton() {
    element(by.id('edit-tag-3-button')).click();
  }

}
