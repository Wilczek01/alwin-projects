import {ActivityStateConst} from '../../activity/activity-state-const';
import {FilterActivityTypeDetailPropertiesByStatePipe} from './filter-activity-type-detail-properties-by-state.pipe';
import {ActivityTypeDetailProperty} from '../../activity/activity-type-detail-property';
import {ActivityDetailProperty} from '../../activity/activity-detail-property';

describe('Filter activity type by state pipe', () => {

  let pipe: FilterActivityTypeDetailPropertiesByStatePipe;

  beforeEach(() => {
    pipe = new FilterActivityTypeDetailPropertiesByStatePipe();
  });

  it('should filter activity type by executed state', () => {
    // given
    const activityTypeDetailProperties = [
      new ActivityTypeDetailProperty(1, new ActivityDetailProperty({name: 'Z'}), ActivityStateConst.EXECUTED, null),
      new ActivityTypeDetailProperty(2, new ActivityDetailProperty({name: 'B'}), ActivityStateConst.PLANNED, null),
      new ActivityTypeDetailProperty(3, new ActivityDetailProperty({name: 'A'}), ActivityStateConst.EXECUTED, null),
      new ActivityTypeDetailProperty(4, new ActivityDetailProperty({name: 'D'}), ActivityStateConst.PLANNED, null)
    ];

    // when
    const result = pipe.transform(activityTypeDetailProperties, ActivityStateConst.EXECUTED);

    // then
    expect(result.length).toBe(2);
    expect(result[0].id).toBe(3);
    expect(result[1].id).toBe(1);
  });
});
