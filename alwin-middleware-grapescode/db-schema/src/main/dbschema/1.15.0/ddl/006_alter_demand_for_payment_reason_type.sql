alter table demand_for_payment
  add column reason_type VARCHAR(50);

COMMENT ON COLUMN demand_for_payment.reason_type
IS 'Słownikowa wartość przyczyny anulowania wezwania';