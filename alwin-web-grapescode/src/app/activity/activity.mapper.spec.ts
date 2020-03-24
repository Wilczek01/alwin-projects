import {ActivityMapper} from './activity.mapper';
import {TEST_ACTIVITY} from '../testing/data/activity.test.data';
import {ActivityUtils} from './activity.utils';

describe('ActivityMapper', () => {

  it('should map activities', () => {
    // given
    const activity = TEST_ACTIVITY;

    // when
    ActivityMapper.mapActivities([activity]);

    // then
    expect(activity.plannedDate instanceof Date).toBeTruthy();
    expect(activity.creationDate instanceof Date).toBeTruthy();
    expect(activity.activityDate instanceof Date).toBeTruthy();

    activity.declarations.forEach(value => {
      expect(value.declarationDate instanceof Date).toBeTruthy();
      expect(value.declaredPaymentDate instanceof Date).toBeTruthy();
    });

    activity.activityDetails.forEach(detail => {
      if (ActivityUtils.isTypeOfDate(detail)) {
        expect(detail.value instanceof Date).toBeTruthy();
      }
      if (ActivityUtils.isTypeOfBoolean(detail)) {
        expect(detail.value).toBeFalsy();
      }
    });
  });

  it('should map activity with null activity date', () => {
    // given
    const activity = TEST_ACTIVITY;
    activity.activityDate = null;

    // when
    ActivityMapper.mapActivities([activity]);

    // then
    expect(activity.activityDate).toBeNull();
  });

});
