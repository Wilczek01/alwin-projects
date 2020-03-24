import {AddressPipe} from './address.pipe';

describe('Address pipe', () => {

  let pipe: AddressPipe;

  beforeEach(() => {
    pipe = new AddressPipe();
  });

  it('should build full address', () => {
    expect(pipe.transform('ul.', ['Testowa', '7b', '11', '01-123', 'Warszawa', 'Polska'])).toBe('ul. Testowa 7b/11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip prefix if absent', () => {
    expect(pipe.transform(null, ['Testowa', '7b', '11', '01-123', 'Warszawa', 'Polska'])).toBe('Testowa 7b/11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip prefix if empty', () => {
    expect(pipe.transform('', ['Testowa', '7b', '11', '01-123', 'Warszawa', 'Polska'])).toBe('Testowa 7b/11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip absent house no', () => {
    expect(pipe.transform('ul.', ['Testowa', null, '11', '01-123', 'Warszawa', 'Polska'])).toBe('ul. Testowa 11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip empty house no', () => {
    expect(pipe.transform('ul.', ['Testowa', '', '11', '01-123', 'Warszawa', 'Polska'])).toBe('ul. Testowa 11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip empty flat no', () => {
    expect(pipe.transform('ul.', ['Testowa', '7b', '', '01-123', 'Warszawa', 'Polska'])).toBe('ul. Testowa 7b<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip absent flat no', () => {
    expect(pipe.transform('ul.', ['Testowa', '7b', null, '01-123', 'Warszawa', 'Polska'])).toBe('ul. Testowa 7b<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip empty street', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '01-123', 'Warszawa', 'Polska'])).toBe('ul. 7b/11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should skip absent street', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '01-123', 'Warszawa', 'Polska'])).toBe('ul. 7b/11<br/>01-123, Warszawa<br/>Polska');
  });

  it('should display no data for absent country', () => {
    expect(pipe.transform('ul.', ['Testowa', '7b', '11', '01-123', 'Warszawa', null])).toBe('ul. Testowa 7b/11<br/>01-123, Warszawa<br/>b/d');
  });

  it('should skip absent postal code', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', null, 'Warszawa', 'Polska'])).toBe('ul. 7b/11<br/>Warszawa<br/>Polska');
  });

  it('should skip empty postal code', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '', 'Warszawa', 'Polska'])).toBe('ul. 7b/11<br/>Warszawa<br/>Polska');
  });

  it('should display no data for absent city', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '01-123', null, 'Polska'])).toBe('ul. 7b/11<br/>01-123, b/d<br/>Polska');
  });

  it('should display no data for empty city', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '01-123', '', 'Polska'])).toBe('ul. 7b/11<br/>01-123, b/d<br/>Polska');
  });

  it('should display no data for absent postal code and city', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', null, null, 'Polska'])).toBe('ul. 7b/11<br/>b/d<br/>Polska');
  });

  it('should display no data for absent postal code and empty city', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', null, '', 'Polska'])).toBe('ul. 7b/11<br/>b/d<br/>Polska');
  });

  it('should display no data for empty postal code and absent city', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '', null, 'Polska'])).toBe('ul. 7b/11<br/>b/d<br/>Polska');
  });

  it('should return no data if no parameters passed', () => {
    expect(pipe.transform('ul.', null)).toBe('b/d');
  });

  it('should display no data for empty postal code and city', () => {
    expect(pipe.transform('ul.', ['', '7b', '11', '', '', 'Polska'])).toBe('ul. 7b/11<br/>b/d<br/>Polska');
  });

  it('should return no data if empty parameters passed', () => {
    expect(pipe.transform('ul.', [])).toBe('b/d');
  });

  it('should return no data if invalid parameters number passed', () => {
    expect(pipe.transform('ul.', ['first', 'second'])).toBe('b/d');
  });

  it('should return no data if no data passed', () => {
    expect(pipe.transform(null, [])).toBe('b/d');
  });

});
