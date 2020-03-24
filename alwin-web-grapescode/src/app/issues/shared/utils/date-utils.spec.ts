import {DateUtils} from './date-utils';
import {FormControl} from '@angular/forms';
import {HttpParams} from '@angular/common/http';
import * as moment from 'moment';

describe('Utils: date utils', () => {

  it('should prepare url search parameter from date in required format', () => {
    // given
    const collectionDate = '2017-09-15T09:00:00';
    const newDate = moment(collectionDate);

    // when
    const params = DateUtils.prepareURLSearchParamFromMoment('testParam', newDate, new HttpParams());

    // then
    expect(params.get('testParam') === '2017-09-15').toBeTruthy();
  });

  it('should not prepare url search parameter from date in required format when date is not valid ', () => {
    // given
    const collectionDate = '';
    const newDate = moment(collectionDate);

    // when
    const params = DateUtils.prepareURLSearchParamFromMoment('testParam', newDate, new HttpParams());

    // then
    expect(params.get('testParam') === '2017-09-15').toBeFalsy();
  });

  it('should validateDate return true', () => {
    // given
    const formControl: FormControl = new FormControl();
    formControl.setValue('2017-09-15T09:00:00');

    // when
    const validateDate = DateUtils.validateDate(formControl);

    // then
    expect(validateDate);
  });

  it('should validateDate return false', () => {
    // given
    const formControl: FormControl = new FormControl();

    // when
    const validateDate = DateUtils.validateDate(formControl);

    // then
    expect(validateDate == null);
  });

  it('should add days to date', () => {
    // given
    const date = new Date('2017-09-15T09:00:00');

    // when
    const dateWithAddedDays = DateUtils.addDays(date, 10);

    // then
    const expectedDate = new Date('2017-09-25T09:00:00');
    expect(dateWithAddedDays.getDate()).toBe(expectedDate.getDate());
  });

});
