import {ContractDpdPipe} from './contract-dpd.pipe';

describe('Contract DPD pipe', () => {

  let pipe: ContractDpdPipe;

  beforeEach(() => {
    pipe = new ContractDpdPipe();
  });

  it('should transform null DPD to "---"', () => {
    expect(pipe.transform(null, null)).toBe('---');
  });

  it('should transform positive DPD', () => {
    expect(pipe.transform(19, null)).toBe('19');
  });

  it('should transform negative DPD', () => {
    expect(pipe.transform(-19, null)).toBe('-19');
  });

});
