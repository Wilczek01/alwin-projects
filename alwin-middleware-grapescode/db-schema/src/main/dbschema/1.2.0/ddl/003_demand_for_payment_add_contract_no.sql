ALTER TABLE demand_for_payment
  ADD COLUMN contract_number VARCHAR(150);

COMMENT ON COLUMN demand_for_payment.contract_number IS 'Numer umowy';
