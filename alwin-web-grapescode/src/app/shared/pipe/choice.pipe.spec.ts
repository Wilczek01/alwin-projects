import {ChoicePipe} from './choice.pipe';

describe('Choice pipe', () => {

  let pipe: ChoicePipe;

  beforeEach(() => {
    pipe = new ChoicePipe();
  });

  it('should return "Tak" if correct boolean true was provided', () => {
    expect(pipe.transform('true', null)).toBe('Tak');
  });

  it('should return "Nie" if correct boolean false was provided', () => {
    expect(pipe.transform('false', null)).toBe('Nie');
  });

  it('should return no data if null was provided', () => {
    expect(pipe.transform(null, null)).toBe('b/d');
  });

  it('should return no data if incorrect boolean value was provided', () => {
    expect(pipe.transform('test', null)).toBe('b/d');
  });

});
