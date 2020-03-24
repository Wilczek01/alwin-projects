alter table contract_termination
  add column state_change_date TIMESTAMP;

alter table contract_termination_aud
  add column state_change_date TIMESTAMP;

alter table contract_termination
  add column state_change_reason TEXT;

alter table contract_termination_aud
  add column state_change_reason TEXT;


alter table contract_termination
  add column state_change_operator_id BIGINT;

alter table contract_termination_aud
  add column state_change_operator_id BIGINT;

COMMENT ON COLUMN contract_termination.state_change_date
IS 'Data zmiany stanu';

COMMENT ON COLUMN contract_termination.state_change_reason
IS 'Przyczyna zmiany stanu';

COMMENT ON COLUMN contract_termination.state_change_operator_id
IS 'Identyfikator operatora zmieniającego stan';