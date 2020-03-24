import {ActivitySummaryPipe} from './activity-summary.pipe';
import {ActivityDeclaration} from '../../../../activity/activity-declaration';
import {Activity} from '../../../../activity/activity';

describe('ActivitySummary pipe', () => {

  let pipe: ActivitySummaryPipe;
  const dayInMillis = 1000 * 60 * 60 * 24;

  beforeEach(() => {
    pipe = new ActivitySummaryPipe();
  });

  it('should return no summary for no declarations', () => {
    // given
    const activity = new Activity();
    activity.declarations = null;

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('-');
  });

  it('should return no summary for empty declarations', () => {
    // given
    const activity = new Activity();
    activity.declarations = [];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('-');
  });

  it('should return no summary for missing activity', () => {
    // given
    const activity = null;

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('-');
  });

  it('should return declaration paid summary for paid declarations in PLN', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidPLN = 100;
    declaration1.cashPaidEUR = 0;
    declaration1.declaredPaymentAmountPLN = 100;
    declaration1.declaredPaymentAmountEUR = 0;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidPLN = 120;
    declaration2.cashPaidEUR = 0;
    declaration2.declaredPaymentAmountPLN = 110;
    declaration2.declaredPaymentAmountEUR = 0;
    declaration2.declaredPaymentDate = new Date();
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('deklaracja spłacona');
  });

  it('should return declaration paid summary for paid declarations in EUR', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidEUR = 100;
    declaration1.declaredPaymentAmountPLN = 0;
    declaration1.declaredPaymentAmountEUR = 100;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidEUR = 120;
    declaration2.declaredPaymentAmountPLN = 0;
    declaration2.declaredPaymentAmountEUR = 110;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() + dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('deklaracja spłacona');
  });

  it('should return declaration payment pending summary for not paid declaration in PLN with future payment date', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidPLN = 100;
    declaration1.declaredPaymentAmountPLN = 100;
    declaration1.declaredPaymentAmountEUR = 0;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidPLN = 50;
    declaration2.declaredPaymentAmountPLN = 110;
    declaration2.declaredPaymentAmountEUR = 0;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() + dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('oczekiwanie na wpłatę');
  });

  it('should return declaration payment pending summary for not paid declaration in EUR with future payment date', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidEUR = 100;
    declaration1.declaredPaymentAmountPLN = 0;
    declaration1.declaredPaymentAmountEUR = 100;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidEUR = 50;
    declaration2.declaredPaymentAmountPLN = 0;
    declaration2.declaredPaymentAmountEUR = 110;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() + dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('oczekiwanie na wpłatę');
  });

  it('should return declaration not paid summary for not paid declaration in PLN with past payment date', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidPLN = 100;
    declaration1.declaredPaymentAmountPLN = 100;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidPLN = 50;
    declaration2.declaredPaymentAmountPLN = 110;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() - dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('deklaracja niespłacona');
  });

  it('should return declaration payment pending summary for not paid declaration in PLN with payment date today', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidPLN = 50;
    declaration1.declaredPaymentAmountPLN = 100;
    declaration1.declaredPaymentDate = new Date();
    declaration1.declaredPaymentDate.setHours(0, 0, 0, 0);
    activity.declarations = [declaration1];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('oczekiwanie na wpłatę');
  });

  it('should return declaration payment pending summary for not paid declaration in EUR with payment date today', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidEUR = 50;
    declaration1.declaredPaymentAmountEUR = 100;
    declaration1.declaredPaymentDate = new Date();
    declaration1.declaredPaymentDate.setHours(0, 0, 0, 0);
    activity.declarations = [declaration1];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('oczekiwanie na wpłatę');
  });

  it('should return declaration not paid summary for not paid declaration in EUR with past payment date', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidPLN = 100;
    declaration1.declaredPaymentAmountEUR = 100;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidPLN = 50;
    declaration2.declaredPaymentAmountEUR = 110;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() - dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('deklaracja niespłacona');
  });

  it('should return declaration paid summary for paid declaration in PLN considering sum', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidPLN = 160;
    declaration1.declaredPaymentAmountPLN = 100;
    declaration1.declaredPaymentAmountEUR = 0;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidPLN = 50;
    declaration2.declaredPaymentAmountPLN = 110;
    declaration2.declaredPaymentAmountEUR = 0;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() - dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('deklaracja spłacona');
  });

  it('should return declaration paid summary for paid declaration in EUR considering sum', () => {
    // given
    const activity = new Activity();
    const declaration1 = new ActivityDeclaration();
    declaration1.cashPaidEUR = 160;
    declaration1.declaredPaymentAmountPLN = 0;
    declaration1.declaredPaymentAmountEUR = 100;
    declaration1.declaredPaymentDate = new Date();
    const declaration2 = new ActivityDeclaration();
    declaration2.cashPaidEUR = 50;
    declaration2.declaredPaymentAmountPLN = 0;
    declaration2.declaredPaymentAmountEUR = 110;
    declaration2.declaredPaymentDate = new Date(new Date().getTime() - dayInMillis);
    activity.declarations = [declaration1, declaration2];

    // when
    const summary = pipe.transform(activity, null);

    // then
    expect(summary).toBe('deklaracja spłacona');
  });

});
