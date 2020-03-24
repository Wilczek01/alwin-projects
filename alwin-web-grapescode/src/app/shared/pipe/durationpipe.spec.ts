import {DurationPipe} from './duration.pipe';

describe('Duration pipe', () => {

  let pipe: DurationPipe;

  beforeEach(() => {
    pipe = new DurationPipe();
  });

  it('for number input should contains hours and 0 minutes and 0 seconds when only days different', () => {
    expect(pipe.transform(new Date(2018, 6, 6).getTime(), [new Date(2018, 6, 9).getTime()])).toBe('72h 0m 0s');
  });

  it('for number input should contains minutes and 0 seconds when only minutes different', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 0).getTime(), [new Date(2018, 6, 6, 12, 34).getTime()])).toBe('34m 0s');
  });

  it('for number input should contains minutes and seconds when minutes and seconds different', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 3, 6).getTime(), [new Date(2018, 6, 6, 12, 34, 56).getTime()])).toBe('31m 50s');
  });

  it('for number input should contains only seconds when only seconds different', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 3, 6).getTime(), [new Date(2018, 6, 6, 12, 3, 40).getTime()])).toBe('34s');
  });

  it('for number input should contains only 0 seconds when dates are same', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 3, 6).getTime(), [new Date(2018, 6, 6, 12, 3, 6).getTime()])).toBe('0s');
  });

  it('for Date input should contains hours and 0 minutes and 0 seconds when only days different', () => {
    expect(pipe.transform(new Date(2018, 6, 6), [new Date(2018, 6, 9)])).toBe('72h 0m 0s');
  });

  it('for Date input should contains minutes and 0 seconds when only minutes different', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 0), [new Date(2018, 6, 6, 12, 34)])).toBe('34m 0s');
  });

  it('for Date input should contains minutes and seconds when minutes and seconds different', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 3, 6), [new Date(2018, 6, 6, 12, 34, 56)])).toBe('31m 50s');
  });

  it('for Date input should contains only seconds when only seconds different', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 3, 6), [new Date(2018, 6, 6, 12, 3, 40)])).toBe('34s');
  });

  it('for Date input should contains only 0 seconds when dates are same', () => {
    expect(pipe.transform(new Date(2018, 6, 6, 12, 3, 6), [new Date(2018, 6, 6, 12, 3, 6)])).toBe('0s');
  });

});
