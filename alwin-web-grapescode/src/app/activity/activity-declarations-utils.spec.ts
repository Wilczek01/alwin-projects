import {ActivityDeclarationsUtils} from './activity-declarations-utils';

describe('ActivityDeclarationsUtils', () => {

  const runs = [
    {balance: -100, expectedDeclaredPaymentAmount: 100},
    {balance: -1, expectedDeclaredPaymentAmount: 1},
    {balance: 0, expectedDeclaredPaymentAmount: 0},
    {balance: 100, expectedDeclaredPaymentAmount: 0}
  ];

  it('should check types of activity detail', () => {
    runs.forEach(run => {
      // when
      const typeOfDate = ActivityDeclarationsUtils.getDefaultDeclaredPaymentAmount(run.balance);

      // then
      expect(typeOfDate).toBe(run.expectedDeclaredPaymentAmount);
    });
  });
});
