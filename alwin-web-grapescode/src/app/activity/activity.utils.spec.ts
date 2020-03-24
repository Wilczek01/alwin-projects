import {ActivityUtils} from './activity.utils';
import {ActivityDetail} from './activity-detail';
import {ActivityDetailProperty} from './activity-detail-property';
import {Operator} from '../operator/operator';

describe('ActivityUtils', () => {

  const runs = [
    {type: 'java.util.Date', expectedTypeOfDate: true, expectedTypeOfString: false, expectedTypeOfBoolean: false, expectedTypeOfInteger: false},
    {type: 'java.lang.String', expectedTypeOfDate: false, expectedTypeOfString: true, expectedTypeOfBoolean: false, expectedTypeOfInteger: false},
    {type: 'java.lang.Boolean', expectedTypeOfDate: false, expectedTypeOfString: false, expectedTypeOfBoolean: true, expectedTypeOfInteger: false},
    {type: 'java.lang.Integer', expectedTypeOfDate: false, expectedTypeOfString: false, expectedTypeOfBoolean: false, expectedTypeOfInteger: true},
  ];

  it('should check types of activity detail', () => {
    runs.forEach(run => {
      // given
      const detail = new ActivityDetail();
      detail.detailProperty = new ActivityDetailProperty({type: run.type});

      // when
      const typeOfDate = ActivityUtils.isTypeOfDate(detail);
      const typeOfString = ActivityUtils.isTypeOfString(detail);
      const typeOfBoolean = ActivityUtils.isTypeOfBoolean(detail);
      const typeOfInteger = ActivityUtils.isTypeOfInteger(detail);

      // then
      expect(typeOfDate).toBe(run.expectedTypeOfDate);
      expect(typeOfString).toBe(run.expectedTypeOfString);
      expect(typeOfBoolean).toBe(run.expectedTypeOfBoolean);
      expect(typeOfInteger).toBe(run.expectedTypeOfInteger);
    });
  });

  it('should get operator', () => {
    expect(ActivityUtils.getOperator(null)).toBe(null);
    expect(ActivityUtils.getOperator('')).toBe(null);
    expect(ActivityUtils.getOperator('Jan Kowalski')).toBe(null);
    const operator = new Operator();
    operator.id = 7;
    expect(ActivityUtils.getOperator(operator)).toBe(operator);
  });
});
