import {ConcatenatePipe} from './concatenate.pipe';

describe('Concatenate pipe', () => {

  let pipe: ConcatenatePipe;

  beforeEach(() => {
    pipe = new ConcatenatePipe();
  });

  it('should concatenate two values', () => {
    expect(pipe.transform('do', ['re'])).toBe('do re');
  });

  it('should concatenate two values skipping null value', () => {
    expect(pipe.transform('do', ['re', null])).toBe('do re');
  });

  it('should concatenate multiple values', () => {
    expect(pipe.transform('do', ['re', 'mi', 'fa'])).toBe('do re mi fa');
  });

  it('should return no data if original value is absent and there is nothing to concatenate', () => {
    expect(pipe.transform(null, null)).toBe('b/d');
  });

  it('should return original value if there is nothing to concatenate', () => {
    expect(pipe.transform('do', null)).toBe('do');
  });

  it('should return only concatenated values if original value is absent', () => {
    expect(pipe.transform(null, ['re', 'mi'])).toBe('re mi');
  });

  it('should return no data if original value is absent and value to concatenate is null', () => {
    expect(pipe.transform(null, [null])).toBe('b/d');
  });
});
