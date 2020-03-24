import {StringUtils} from './string-utils';
import {HttpParams} from '@angular/common/http';

describe('Utils: string utils', () => {

  it('should prepare url search parameter from string', () => {
    // given
    const balance = '12.21';
    const params: HttpParams = new HttpParams();

    // when
    StringUtils.prepareURLSearchParamFromString('testParam', balance, params);

    // then
    expect(params.get('testParam') === balance);
  });

  it('should not prepare url search parameter from string when value is not valid', () => {
    // given
    const params: HttpParams = new HttpParams();

    // when
    StringUtils.prepareURLSearchParamFromString('testParam', null, params);

    // then
    expect(params.get('testParam') == null);
  });

  it('should remove new lines from string', () => {
    // given
    const stringWithNewLines = '\rtest line 1 \n test \r\nline 2';

    // when
    const result = StringUtils.removeNewLines(stringWithNewLines);

    // then
    expect(result).toBe('test line 1  test line 2');
  });

  it('should not remove empty lines from empty string', () => {
    // given
    const emptyString = '';

    // when
    const result = StringUtils.removeNewLines(emptyString);

    // then
    expect(result).toBe('');
  });

  it('should undefined be blank string', () => {
    // given
    const emptyString = undefined;

    // when
    const result = StringUtils.isNullOrEmptyOrUndefined(emptyString);

    // then
    expect(result).toBe(true);
  });

  it('should null be blank string', () => {
    // given
    const emptyString = null;

    // when
    const result = StringUtils.isNullOrEmptyOrUndefined(emptyString);

    // then
    expect(result).toBe(true);
  });

  it('should empty string be blank string', () => {
    // given
    const emptyString = '';

    // when
    const result = StringUtils.isNullOrEmptyOrUndefined(emptyString);

    // then
    expect(result).toBe(true);
  });

  it('should string with value be not blank string', () => {
    // given
    const emptyString = 'test';

    // when
    const result = StringUtils.isNullOrEmptyOrUndefined(emptyString);

    // then
    expect(result).toBe(false);
  });

  it('should prepare extracted url search parameter from string with spaces', () => {
    // given
    const testValue = '123 456 78 90';
    const testExtractedValue = '1234567890';
    const params: HttpParams = new HttpParams();

    // when
    StringUtils.prepareURLSearchParamFromExtractedString('testParam', testValue, params);

    // then
    expect(params.get('testParam') === testExtractedValue);
  });

  it('should prepare extracted url search parameter from string with dashes', () => {
    // given
    const testValue = '123-456-78-90';
    const testExtractedValue = '1234567890';
    const params: HttpParams = new HttpParams();

    // when
    StringUtils.prepareURLSearchParamFromExtractedString('testParam', testValue, params);

    // then
    expect(params.get('testParam') === testExtractedValue);
  });

});
