ALTER TABLE ISSUE_INVOICE DROP COLUMN id;
ALTER TABLE ISSUE_INVOICE add primary key (issue_id,invoice_id);

