ALTER TABLE issue_type
  ADD COLUMN case_length NUMERIC(4) NOT NULL DEFAULT 14; -- TODO ustawienie ilości dni dla zleceń innych niż WT1 i WT2

COMMENT ON COLUMN issue_type.case_length IS 'długość trwania zlecenia w dniach';