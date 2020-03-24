import {browser, by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';

export class MyWorkPhoneCallPage extends AbstractPage {

  expectEmptyPhoneCall() {
    const phoneCall = this.findPhoneCallTag();
    expect(phoneCall.isPresent()).toBe(true);
    expect(phoneCall.getText()).toBe('');
  }

  expectPhoneCall() {
    // TODO: assercje nie działają po naciśnięciu przycisku callto
    // const phoneCall = this.findPhoneCallTag();
    // expect(phoneCall.isPresent()).toBe(true);
    // browser.switchTo().alert().accept();
  }

  startPhoneCall() {
    const phoneCallButton = element(by.id('call-5-button'));
    expect(phoneCallButton.isPresent()).toBe(true);
    phoneCallButton.click();
  }

  findPhoneCallTag() {
    return element(by.tagName('alwin-phone-call'));
  }
}
