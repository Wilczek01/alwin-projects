import {TruncatePipe} from './truncate.pipe';

describe('Truncate pipe', () => {

  let pipe: TruncatePipe;

  beforeEach(() => {
    pipe = new TruncatePipe();
  });

  it('should cut text longer than provided length', () => {
    expect(pipe.transform('12345678', ['5'])).toBe('12345...');
  });

  it('should not cut text shorter or equal to provided length', () => {
    expect(pipe.transform('12345', ['5'])).toBe('12345');
  });
});
