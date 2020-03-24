import {BooleanToStringPipe} from './boolean-to-string.pipe';

describe('Balance invoice pipe', () => {

  let pipe: BooleanToStringPipe;

  beforeEach(() => {
    pipe = new BooleanToStringPipe();
  });

  it('should transform false to string', () => {
    expect(pipe.transform(false, null)).toBe(BooleanToStringPipe.falseString);
  });

  it('should transform true to string', () => {
    expect(pipe.transform(true, null)).toBe(BooleanToStringPipe.trueString);
  });

  it('should transform null to string', () => {
    expect(pipe.transform(null, null)).toBe(BooleanToStringPipe.noDataString);
  });
});
