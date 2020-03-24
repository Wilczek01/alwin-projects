import {browser, By, by, element, ElementFinder, ExpectedConditions} from 'protractor';
import * as fs from 'fs';

export abstract class AbstractPage {

  confirmConfirmationDialog() {
    browser.waitForAngular();
    this.clickElementById(`confirmation-dialog-confirm-button`);
  }

  sleep(milliseconds: number) {
    browser.sleep(milliseconds);
  }

  snackBar() {
    return element(by.className('mat-snack-bar-container'));
  }

  snackBarErrorReportButton() {
    return element(by.className('mat-simple-snackbar-action'));
  }

  errorReportDialog() {
    return element(by.id('report-error-dialog'));
  }

  clickElementById(elementId: string) {
    browser.driver.executeScript('arguments[0].click()', this.getElementById(elementId));
  }

  clickElementByClassName(className: string) {
    browser.driver.executeScript('arguments[0].click()', this.getElementByClassName(className));
  }

  slideToggle(elementId: string) {
    element(by.id(elementId)).click();
  }

  getElementById(elementId: string) {
    return browser.driver.findElement(By.xpath(`//*[@id="${elementId}"]`));
  }

  getTextFromElementById(elementId: string) {
    return element(by.id(elementId)).getText();
  }

  getElementByClassName(className: string) {
    return element(by.className(className));
  }

  getTextFromElementByClassName(className: string) {
    return this.getElementByClassName(className).getText();
  }

  getAttributeFromElementByClassName(className: string, attributeName: string) {
    return this.getElementByClassName(className).getAttribute(attributeName);
  }

  /**
   * Kliknięcie elementu z oczekiwaniem na jego wyświetlenie
   * @param {ElementFinder} element - element do kliknięcia
   */
  clickElementWithWait(element: ElementFinder) {
    browser.wait(() => element.isPresent(), 10000).then(() => {
      element.click();
    });
  }

  expectUrlToBe(url: string, failureMessage: string) {
    this.expectUrlToBeWithTimeout(url, failureMessage, 500);
  }

  expectUrlToBeWithTimeout(url: string, failureMessage: string, timeout: number) {
    browser.wait(ExpectedConditions.urlIs(this.getBrowserBaseUrl() + url), timeout, failureMessage);
  }

  /**
   * Sprawdza czy aktualny adres jest taki jak podano z uwzględnieniem podstawowej części adresu oraz flagi wyłączającej animację
   *
   * @param {string} url - oczekiwany adres
   * @param {string} failureMessage - komunikat, jeżeli adres jest inny niż oczekiwany
   */
  expectUrlInQaModeToBe(url: string, failureMessage: string) {
    browser.wait(ExpectedConditions.urlIs(this.getBrowserBaseUrl() + url + '?qa=true'), 500, failureMessage);
  }

  /**
   * Zwraca url bazowy zakończony "/"
   */
  getBrowserBaseUrl() {
    let url = browser.baseUrl;
    if (url.endsWith('/')) {
      return url;
    }
    return url + '/';
  }

  expectNewTabWithUrl(url: string, failureMessage: string) {
    browser.getAllWindowHandles().then(handles => {
      const firstWindowHandle = handles[0];
      const newWindowHandle = handles[1];
      browser.switchTo().window(newWindowHandle).then(function () {
        browser.wait(ExpectedConditions.urlIs(browser.baseUrl + url), 500, failureMessage);
      }).then(() => {
        browser.close();
      }).then(() => {
        browser.switchTo().window(firstWindowHandle);
      });
    });
  }

  protected getSelect(selectName: string) {
    return element.all(by.name(selectName));
  }

  protected chooseFromSelectByBrowser(selectName: string, index: number) {
    this.getSelect(selectName).each(eachElement => {
      eachElement.click();
      browser.waitForAngular();
      this.clickElementById(`mat-option-${index}`);
      browser.waitForAngular();
    });
  }

  protected chooseFromSelect(selectName: string, index: number) {
    this.getSelect(selectName).each(eachElement => {
      eachElement.click();
      browser.waitForAngular();
      const selectOption = element.all(by.css('mat-option')).get(index);
      this.clickElementWithWait(selectOption);
      browser.waitForAngular();
    });
  }

  protected chooseFromSelectById(selectId: string, index: number) {
    element.all(by.id(selectId)).each(eachElement => {
      eachElement.click();
      browser.waitForAngular();
      const selectOption = element.all(by.css('mat-option')).get(index);
      this.clickElementWithWait(selectOption);
      browser.waitForAngular();
    });
  }

  clickErrorReportButton() {
    browser.wait(ExpectedConditions.elementToBeClickable(this.snackBarErrorReportButton()), 1000);
    this.snackBarErrorReportButton().click();
  }

  expectSnackBarWithErrorReportButtonToBePresented() {
    expect(this.snackBar().isPresent()).toBeTruthy();
    expect(this.snackBarErrorReportButton().isPresent()).toBeTruthy();
  }

  expectErrorReportDialogWithErrorIdToBePresented(errorId: string) {
    browser.wait(ExpectedConditions.presenceOf(this.errorReportDialog()), 1000);
    expect(this.errorReportDialog().isPresent()).toBeTruthy();
    expect(this.getTextFromElementById('error-details')).toContain(errorId);
  }

  /**
   * Ustawia wartość tekstową dla inputów - sendKeys niestety nie zawsze działa w trybie headless
   * https://github.com/angular/protractor/issues/2019
   *
   * @param {ElementFinder} inputElement - element input
   * @param {string} value - wartość do ustawienia
   */
  setValueOnInputElement(inputElement: ElementFinder, value: string) {
    browser.wait(() => inputElement.isPresent(), 10000).then(() => {
      inputElement.clear();
      inputElement.sendKeys(value);
      return inputElement.getAttribute('value')
        .then(insertedValue => {
          if (insertedValue !== value) {
            return this.setValueOnInputElement(inputElement, value);
          } else {
            return null;
          }
        });
    });
  }

  /**
   * Do celów debugu możliwość zrobienia zrzutu ekranu w formacie png
   *
   * @param {string} path - ścieżka zapisu zrzutu kończaca się znakiem '/' np: /Users/me/Downloads/
   * @param {string} name - nazwa pliku bez rozszerzenia np: screenshot_1
   */
  takeScreenshot(path: string, name: string) {
    browser.takeScreenshot().then(png => {
      const stream = fs.createWriteStream(`${path}${name}.png`);
      stream.write(new Buffer(png, 'base64'));
      stream.end();
    });
  }

  /**
   * Zwraca tabelę z danymi
   * @returns {ElementFinder}
   */
  getTable() {
    return element.all(by.tagName('mat-table')).get(0);
  }

  /**
   * Zwraca listę wszystkich rekordów w tabeli
   * @returns {ElementArrayFinder}
   */
  getTableRows() {
    return this.getTable().all(by.tagName('mat-row'));
  }

  /**
   * Zwraca listę wszystkich wartości(kolumn) dla rekordu o podanym numerze z tabeli
   *
   * @param {number} no - numer rekordu
   * @returns {ElementArrayFinder}
   */
  getTableRowCells(no: number) {
    return this.getTableRow(no).all(by.tagName('mat-cell'));
  }

  /**
   * Zwraca rekord o podanym numerze z tabeli
   *
   * @param {number} no - numer rekordu
   * @returns {ElementArrayFinder}
   */
  getTableRow(no: number) {
    return this.getTableRows().get(no);
  }

  /**
   * Zwraca komórkę o podanym numerze dla rekordu o podanym numerze z tabeli
   *
   * @param {number} rowNo - numer rekordu
   * @param {number} cellNo - numer komórki
   * @returns {ElementArrayFinder}
   */
  getTableRowCell(rowNo: number, cellNo: number) {
    return this.getTableRowCells(rowNo).get(cellNo);
  }

  /**
   * Zwraca wartość tesktową z komórki o podanym numerze dla rekordu o podanym numerze z tabeli
   *
   * @param {number} rowNo - numer rekordu
   * @param {number} cellNo - numer komórki
   * @returns {ElementArrayFinder}
   */
  getTableRowCellText(rowNo: number, cellNo: number) {
    return this.getTableRowCell(rowNo, cellNo).getText();
  }
}
