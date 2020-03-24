import {NumberUtils} from './number-utils';

describe('Utils: number utils', () => {

  it('should get string from number with additional prefix', () => {
    // given
    const number = 9;
    const prefix = '0';

    // when
    const formattedNumber = NumberUtils.getFormattedNumber(number);

    // then
    expect(formattedNumber === (prefix + number));
  });

  it('should get string from number without additional prefix', () => {
    // given
    const number = 10;
    const prefix = '';

    // when
    const formattedNumber = NumberUtils.getFormattedNumber(number);

    // then
    expect(formattedNumber === (prefix + number));
  });
})
;
