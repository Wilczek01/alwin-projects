ALTER TABLE contract_termination
  ALTER COLUMN generated_by DROP NOT NULL;
ALTER TABLE contract_termination
  ALTER COLUMN operator_phone_number DROP NOT NULL;
ALTER TABLE contract_termination
  ALTER COLUMN operator_email DROP NOT NULL;

ALTER TABLE contract_termination_aud
  ADD COLUMN state VARCHAR(100);
ALTER TABLE contract_termination_aud
  ADD COLUMN remark VARCHAR(255);
