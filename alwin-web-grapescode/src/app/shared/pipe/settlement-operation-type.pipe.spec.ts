import {SettlementOperationTypePipe} from './settlement-operation-type.pipe';

describe('Settlement operation type pipe', () => {

  let pipe: SettlementOperationTypePipe;

  beforeEach(() => {
    pipe = new SettlementOperationTypePipe();
  });

  it('should transform CHARGE value', () => {
    expect(pipe.transform('CHARGE', null)).toBe('Obciążenie');
  });

  it('should transform CREDIT value', () => {
    expect(pipe.transform('CREDIT', null)).toBe('Uznanie');
  });

  it('should transform unknown value', () => {
    expect(pipe.transform('unknown', null)).toBe('Nieznany');
  });
});
