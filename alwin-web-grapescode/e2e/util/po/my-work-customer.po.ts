import {browser, by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';

export class MyWorkCustomerPage extends AbstractPage {

  expectBasicCustomerData(pkd: string, accountManager: string) {
    expect(element(by.id('customer-name')).getText()).toBe('Klient: ADH253');
    const headerElements = element.all(by.className('customer-data-list-item'));
    expect(headerElements.get(1).getText()).toBe(`PKD: ${pkd}`);
    expect(headerElements.get(2).getText()).toBe('Nr klienta: 253 | NIP: 9876253 | Regon: 1234253 | KRS: 3456253 | Forma prawna: Spółka cywilna');
    expect(headerElements.get(3).getText()).toBe('Nazwa pełna: ADHNazwa253');
    expect(headerElements.get(4).getText()).toBe(`Opiekun: ${accountManager}`);
  }

  expectUpdateAccountManagerDialog() {
    element(by.id('update-account-manager')).click();
    expect(element(by.id('dialog-title')).getText()).toBe('Przypisanie opiekuna klienta');
  }

  expectAddresses(readonly: boolean) {
    this.openAddressTab();
    expect(element(by.id('address-type-8')).getText()).toBe('Siedziby/zamieszkania');
    expect(element(by.id('address-value-8')).getText()).toBe('ADHUlica2253\n253, ADHPoczta253\nb/d');
    expect(element(by.id('address-remark-8')).getText()).toBe('');
    if (readonly) {
      expect(element(by.id('edit-address-8-button')).isPresent()).toBeFalsy();
    } else {
      expect(element(by.id('edit-address-8-button')).isEnabled()).toBeFalsy();
    }
    expect(element(by.id('address-type-7')).getText()).toBe('Korespondencyjny');
    expect(element(by.id('address-value-7')).getText()).toBe('ADHUlica2253_2\n253_2, ADHPoczta253_2\nb/d');
    expect(element(by.id('address-remark-7')).getText()).toBe('');
    if (readonly) {
      expect(element(by.id('edit-address-7-button')).isPresent()).toBeFalsy();
    } else {
      expect(element(by.id('edit-address-7-button')).isEnabled()).toBeTruthy();
    }
  }

  expectContacts() {
    this.openContactTab();
    this.expectContact(5, 'Numer telefonu', '0048123456791', 'Preferowany', 'Adam - w godzinach 10-18', 'Nie');
    this.expectContact(2, 'Osoba kontaktowa', 'Adam Mickiewicz', 'Preferowany', 'w godzinach 10-18', 'b/d');
    this.expectContact(10, 'E-mail', 'contat@company.pl', 'Aktywny', '', 'b/d');
    this.expectContact(7, 'Numer telefonu', '220987654', 'Aktywny', '', 'Nie');
    this.expectContact(8, 'Numer telefonu', '221234567', 'Aktywny', '', 'Nie');
    this.expectContact(9, 'Numer telefonu', '225678901', 'Aktywny', '', 'Nie');
    this.expectContact(4, 'Numer telefonu', '0048123456790', 'Aktywny', 'Jan - trudno się dodzownić', 'Tak');
    this.expectContact(1, 'Osoba kontaktowa', 'Jan Kochanowski', 'Aktywny', 'trudno się dodzownić', 'b/d');
    this.expectContact(12, 'E-mail do biura', 'test@test.pl', 'Aktywny', '', 'b/d');
    this.expectContact(11, 'E-mail dla dokumentów', 'documents@email.pl', 'Aktywny', '', 'b/d');
    this.expectContact(13, 'Adres internetowy', 'http://www.google.pl', 'Aktywny', '', 'b/d');
    this.expectContact(6, 'Numer telefonu', '0048123456789', 'Nieaktywny', 'Juliusz - nie odbiera', 'Tak');
    this.expectContact(3, 'Osoba kontaktowa', 'Juliusz Słowacki', 'Nieaktywny', 'nie odbiera', 'b/d');
  }

  private expectContact(id: number, type: string, value: string, priority: string, remark: string, autoSms: string) {
    expect(element(by.id(`contact-type-${id}`)).getText()).toBe(type);
    expect(element(by.id(`contact-value-${id}`)).getText()).toBe(value);
    expect(element(by.id(`contact-priority-${id}`)).getText()).toBe(priority);
    expect(element(by.id(`contact-remark-${id}`)).getText()).toBe(remark);
    expect(element(by.id(`contact-sms-${id}`)).getText()).toBe(autoSms);
  }

  openAddressTab() {
    element(by.id('mat-tab-label-1-0')).click();
  }

  openContactTab() {
    this.clickElementWithWait(element(by.id('mat-tab-label-1-1')));
    const contactTab = element(by.tagName('alwin-customer-contact-tab'));
    expect(contactTab.isPresent()).toBe(true);
  }

  openPersonTab() {
    this.clickElementWithWait(element(by.id('mat-tab-label-1-2')));
    const contactTab = element(by.tagName('alwin-customer-person-tab'));
    expect(contactTab.isPresent()).toBe(true);
  }

  openContractTab() {
    this.clickElementWithWait(element(by.id('mat-tab-label-1-3')));
    const contactTab = element(by.tagName('alwin-customer-contract-tab'));
    expect(contactTab.isPresent()).toBe(true);
  }

  clickContractSchedule(id: number) {
    element(by.id(`contract-schedule-${id}`)).click();
  }

  expectSchedule2474730() {
    this.expectScheduleName(2474730, 'Przedmiotu Klienta')
    this.expectScheduleRow(1, '1 16.03.2017 22 134,15 66 402,44');
    this.expectScheduleRow(2, '1 24.04.2017 1 544,18 65 139,80');
    this.expectScheduleRow(3, '2 24.05.2017 1 544,18 63 871,81');
    this.expectScheduleRow(4, '3 24.06.2017 1 544,18 62 598,44');
    this.expectScheduleRow(5, '4 24.07.2017 1 544,18 61 319,67');

  }

  expectScheduleName(id: number, name: string) {
    expect(element(by.id(`schedule-${id}`)).getText()).toBe(name);
  }

  expectScheduleRow(row: number, value: string) {
    expect(element(by.css(`#schedules-table > tbody > tr:nth-child(${row})`)).getText()).toBe(value);
  }

  clickPersonSecondRow() {
    this.clickElementWithWait(element(by.id('contact-person-2')));
  }

  sendSms() {
    this.clickElementById('send-sms-5-button');
    browser.waitForAngular();
    expect(element(by.name('phoneNumber')).getAttribute('value')).toBe(element(by.id(`contact-value-5`)).getText());
    this.chooseSmsTemplate();
    expect(element(by.name('smsMessage')).getAttribute('value')).toBe('testowa treść wiadomości 2');
    element(by.id('send-sms-button')).click();
  }

  startCall() {
    element(by.id('call-5-button')).click();
  }

  endCall() {
    element(by.className('end-phone-call')).click();
  }

  createNewAddress() {
    element(by.id('create-new-address-button')).click();
    this.chooseStreetPrefix();
    this.setNewAddressData();
    element(by.id('save-address-button')).click();
  }

  editExistingAddress() {
    element(by.id('edit-address-7-button')).click();
    browser.waitForAngular();
    this.expectAddressToEdit();
    element(by.id('update-address-button')).click();
  }

  private expectAddressToEdit() {
    expect(element(by.name('addressType')).getText()).toBe('Korespondencyjny');
    expect(element(by.name('streetPrefix')).getText()).toBe('');
    expect(element(by.name('streetName')).getAttribute('value')).toBe('ADHUlica2253_2');
    expect(element(by.name('houseNumber')).getAttribute('value')).toBe('');
    expect(element(by.name('flatNumber')).getAttribute('value')).toBe('');
    expect(element(by.name('city')).getAttribute('value')).toBe('ADHPoczta253_2');
    expect(element(by.name('postalCode')).getAttribute('value')).toBe('253_2');
    expect(element(by.name('country')).getAttribute('value')).toBe('');
    expect(element(by.name('remark')).getAttribute('value')).toBe('');
  }

  private expectContactToEdit() {
    expect(element(by.name('contactType')).getText()).toBe('Numer telefonu');
    expect(element(by.name('state')).getText()).toBe('Aktywny');
    expect(element(by.name('contactValue')).getAttribute('value')).toBe('0048123456790');
    expect(element(by.name('remark')).getAttribute('value')).toBe('Jan - trudno się dodzownić');
  }

  createNewContact() {
    this.clickElementWithWait(element(by.id('create-new-contact-button')));
    this.chooseContactType();
    this.chooseContactState();
    this.setNewContactData();
    this.clickElementById('save-contact-button');
  }

  editExistingContact() {
    this.clickElementWithWait(element(by.id('edit-contact-4-button')));
    this.expectContactToEdit();
    element(by.id('update-contact-button')).click();
  }

  private setNewContactData() {
    element(by.name('contactValue')).sendKeys('123123123');
    element(by.name('remark')).sendKeys('t-e-s-t');
  }

  private setNewAddressData() {
    element(by.name('streetName')).sendKeys('Testowa');
    element(by.name('houseNumber')).sendKeys('5a');
    element(by.name('flatNumber')).sendKeys('2');
    element(by.name('city')).sendKeys('Testowe');
    element(by.name('postalCode')).sendKeys('01-012');
    element(by.name('country')).sendKeys('Polska');
    element(by.name('remark')).sendKeys('t-e-s-t');
  }

  chooseStreetPrefix() {
    this.chooseFromSelect('streetPrefix', 2);
  }

  private chooseContactType() {
    element(by.name('contactType')).sendKeys('Osoba kontaktowa');
  }

  private chooseContactState() {
    element(by.name('state')).sendKeys('Preferowany');
  }

  private chooseSmsTemplate() {
    this.chooseFromSelect('smsTemplate', 1);
  }

  expectDefaultPersonDetails() {
    expect(element(by.id('contact-first-last-name-1')).getText()).toBe('person Jan Kowalsky\nOsoba do kontaktu');
    expect(element(by.id('person-detail-first-line-1')).getText()).toBe('Pesel: 88010101234 | Data urodzenia: 17.11.1982 | Funkcja: Konserwator nawierzchni');
    expect(element(by.id('person-detail-second-line-1')).getText())
      .toBe('Reprezentant: \nTak | \n Beneficjent rzeczywisty: \nNie | \n Osoba eksponowana politycznie: \nNie');
  }

  expectSecondRowPersonDetails() {
    expect(element(by.id('contact-first-last-name-2')).getText()).toBe('person Zuzana Fialová\nOsoba do kontaktu');
    expect(element(by.id('person-detail-first-line-2')).getText()).toBe('Pesel: 81011101234 | Data urodzenia: 12.01.1981 | Funkcja: Všeobecná zdravotná');
    expect(element(by.id('person-detail-second-line-2')).getText())
      .toBe('Reprezentant: \nNie | \n Beneficjent rzeczywisty: \nNie | \n Osoba eksponowana politycznie: \nNie');
  }

  writeCommentInCall() {
    this.setValueOnInputElement(element(by.id('mat-input-2')), 'komentarz 123');
  }

  clickFailedActionButton() {
    this.clickElementWithWait(element(by.id('failed-action-button')));
  }

  expectFailedActionDialogVisible() {
    expect(element(by.className('mat-dialog-title')).getText()).toBe('Nowa czynność');
    expect(element(by.css('[name="activityType"]')).getText()).toBe('Nieudana próba kontaktu tel.');
    expect(element(by.css('[placeholder="Nr tel."]')).getAttribute('value')).toBe('0048123456791');
    expect(element(by.id('remark')).getAttribute('value')).toBe('komentarz 123');
  }

}
