-- data aktywacji wypowiedzenia umowy
ALTER TABLE contract_termination
  ADD COLUMN activation_date TIMESTAMP;

COMMENT ON COLUMN contract_termination.activation_date
IS 'Data aktywacji wypowiedzenia umowy';

ALTER TABLE contract_termination_aud
  ADD COLUMN activation_date TIMESTAMP;
