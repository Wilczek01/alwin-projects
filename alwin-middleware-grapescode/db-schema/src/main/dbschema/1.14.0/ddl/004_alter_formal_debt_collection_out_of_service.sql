ALTER TABLE formal_debt_collection_customer_out_of_service
  ALTER COLUMN start_date SET DEFAULT current_timestamp;
ALTER TABLE formal_debt_collection_customer_out_of_service
  ALTER COLUMN start_date SET NOT NULL;
ALTER TABLE formal_debt_collection_customer_out_of_service
  ALTER COLUMN end_date DROP NOT NULL;

ALTER TABLE formal_debt_collection_contract_out_of_service
  ALTER COLUMN start_date SET DEFAULT current_timestamp;

ALTER TABLE formal_debt_collection_contract_out_of_service
  ADD COLUMN ext_company_id BIGINT NOT NULL;
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.ext_company_id
IS 'Identyfikator klienta z zewnętrznego systemu';
