import {ChoiceWithIconsPipe} from './choice-with-icons.pipe';

describe('ChoiceWithIcons pipe', () => {

  let pipe: ChoiceWithIconsPipe;

  beforeEach(() => {
    pipe = new ChoiceWithIconsPipe();
  });

  it('should return "check_circle" if correct boolean true was provided', () => {
    expect(pipe.transform('true', null)).toBe('check_circle');
  });

  it('should return "radio_button_unchecked" if correct boolean false was provided', () => {
    expect(pipe.transform('false', null)).toBe('radio_button_unchecked');
  });

  it('should return no data if null was provided', () => {
    expect(pipe.transform(null, null)).toBe('radio_button_unchecked');
  });

  it('should return no data if incorrect boolean value was provided', () => {
    expect(pipe.transform('test', null)).toBe('radio_button_unchecked');
  });

});
