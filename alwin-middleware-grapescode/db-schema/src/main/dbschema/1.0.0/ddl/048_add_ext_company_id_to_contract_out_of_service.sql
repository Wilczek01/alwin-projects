ALTER TABLE contract_out_of_service ADD COLUMN ext_company_id BIGINT NOT NULL;

COMMENT ON COLUMN contract_out_of_service.ext_company_id IS 'Identyfikator klienta z zewnętrznego systemu';
