ALTER TABLE INVOICE  ADD COLUMN ISSUE_DATE TIMESTAMP;
ALTER TABLE INVOICE_AUD  ADD COLUMN ISSUE_DATE TIMESTAMP;
COMMENT ON COLUMN INVOICE.ISSUE_DATE IS 'Data wystawienia dokumentu';