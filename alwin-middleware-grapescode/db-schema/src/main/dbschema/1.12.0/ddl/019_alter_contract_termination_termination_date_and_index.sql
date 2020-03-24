ALTER TABLE contract_termination
  ALTER COLUMN termination_date TYPE date;

ALTER TABLE contract_termination_aud
  ALTER COLUMN termination_date TYPE date;

DROP INDEX idx_contract_termination_company_id_termination_date_state;

CREATE INDEX idx_contract_termination_grouping_index
  ON contract_termination (ext_company_id, termination_date, state, type);
