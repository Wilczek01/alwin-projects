alter table demand_for_payment
  add column state_change_date TIMESTAMP;

alter table demand_for_payment
  add column state_change_reason TEXT;

alter table demand_for_payment
  add column state_change_operator_id BIGINT;

COMMENT ON COLUMN demand_for_payment.state_change_date
IS 'Data zmiany stanu';

COMMENT ON COLUMN demand_for_payment.state_change_reason
IS 'Przyczyna zmiany stanu';

COMMENT ON COLUMN demand_for_payment.state_change_operator_id
IS 'Identyfikator operatora zmieniającego stan';