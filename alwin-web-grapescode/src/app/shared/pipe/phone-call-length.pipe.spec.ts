import {PhoneCallLengthPipe} from './phone-call-length.pipe';

describe('Phone call length pipe', () => {

  let pipe: PhoneCallLengthPipe;

  beforeEach(() => {
    pipe = new PhoneCallLengthPipe();
  });

  it('should build phone call length with hours', () => {
    expect(pipe.transform(7503, null)).toBe('02:05:03');
  });

  it('should build phone call length without hours', () => {
    expect(pipe.transform(932, null)).toBe('15:32');
  });
});
