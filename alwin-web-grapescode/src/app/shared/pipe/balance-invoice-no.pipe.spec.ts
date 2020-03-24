import {BalanceInvoiceNoPipe} from './balance-invoice-no.pipe';

describe('Balance invoice pipe', () => {

  let pipe: BalanceInvoiceNoPipe;

  beforeEach(() => {
    pipe = new BalanceInvoiceNoPipe();
  });

  it('should cut text longer than 14 chars', () => {
    expect(pipe.transform('000875/12/2016/RL/LO/PLN', null)).toBe('000875/12/2016');
  });

  it('should not cut text shorter or equal to 14 chars', () => {
    expect(pipe.transform('000875/12', null)).toBe('000875/12');
  });

  it('should display no data if invoice no is absent', () => {
    expect(pipe.transform(null, null)).toBe('b/d');
  });
});
