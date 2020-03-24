ALTER TABLE contract_termination
  RENAME COLUMN company_id TO ext_company_id;


ALTER TABLE contract_termination_aud
  RENAME COLUMN company_id TO ext_company_id;

