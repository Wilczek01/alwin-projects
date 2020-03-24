ALTER TABLE ISSUE_TYPE_CONFIGURATION ADD COLUMN include_debt_invoices_during_issue BOOLEAN DEFAULT FALSE NOT NULL;

COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.include_debt_invoices_during_issue IS 'Czy dołączać przeterminowane dokumenty w trakcie trwania zlecenia';
