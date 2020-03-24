ALTER TABLE issue_invoice RENAME type TO type_id;
ALTER TABLE issue_invoice ALTER COLUMN type_id TYPE BIGINT;