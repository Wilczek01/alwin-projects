CREATE INDEX idx_contract_termination_company_id_termination_date_state
  ON contract_termination(company_id, termination_date, state);
