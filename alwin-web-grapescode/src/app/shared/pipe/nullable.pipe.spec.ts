import {NullablePipe} from './nullable.pipe';

describe('Nullable pipe', () => {

  let pipe: NullablePipe;

  beforeEach(() => {
    pipe = new NullablePipe();
  });

  it('should return default data if value is absent', () => {
    expect(pipe.transform(null, ['default value'])).toBe('default value');
  });

  it('should return no data if value is absent', () => {
    expect(pipe.transform(null, null)).toBe('b/d');
  });

  it('should return original value if present', () => {
    expect(pipe.transform('example', null)).toBe('example');
  });

});
