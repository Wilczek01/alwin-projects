-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.5.0/db.changelog.xml
-- Ran at: 08.06.18 15:47
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.12 (192.168.0.12)', LOCKGRANTED = '2018-06-08 15:47:16.811' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.5.0/db.changelog.xml::001_alter_invoice_add_issue_date::astepnowski
ALTER TABLE INVOICE  ADD COLUMN ISSUE_DATE TIMESTAMP;

ALTER TABLE INVOICE_AUD  ADD COLUMN ISSUE_DATE TIMESTAMP;

COMMENT ON COLUMN INVOICE.ISSUE_DATE IS 'Data wystawienia dokumentu';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_alter_invoice_add_issue_date', 'astepnowski', 'src/main/dbschema/1.5.0/db.changelog.xml', NOW(), 138, '7:c6cdc636735f4c191401931cf04d8581', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8465637506');

-- Changeset src/main/dbschema/1.5.0/db.changelog.xml::002_alter_issue_type_configuration.sql::pnaroznik
update ISSUE_TYPE_CONFIGURATION set duration= 10 where segment = 'B';

update ISSUE_TYPE_CONFIGURATION set duration= 15 where segment = 'A';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_issue_type_configuration.sql', 'pnaroznik', 'src/main/dbschema/1.5.0/db.changelog.xml', NOW(), 139, '7:5bf8b909a76fd5efaec0e3030820ca07', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8465637506');

-- Changeset src/main/dbschema/1.5.0/db.changelog.xml::003_update_invoice_issue_subject.sql::pnaroznik
--update scheduler execution time
update scheduler_configuration set hour=1,update_date=now() where id = 1;

-- update issue subject invoice
UPDATE ISSUE_INVOICE
SET
  issue_subject     = (inclusion_balance < 0) and (invoice.due_date < issue.start_date)
FROM INVOICE invoice, ISSUE issue
WHERE
  invoice_id = invoice.id
  and issue_id = issue.id
  and issue.issue_state not in('DONE', 'CANCELED')
  and inclusion_balance is not null;

-- update issue balance_start_pln
UPDATE issue i
SET
  balance_start_pln  = t.balance
FROM (
       select issue.id, sum(ii.inclusion_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'PLN'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue balance_start_eur
UPDATE issue i
SET
  balance_start_eur  = t.balance
FROM (
       select issue.id, sum(ii.inclusion_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'EUR'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue balance_additional_pln
UPDATE issue i
SET
  balance_additional_pln  = t.balance
FROM (
       select issue.id, sum(ii.final_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'PLN'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue balance_additional_eur
UPDATE issue i
SET
  balance_additional_eur  = t.balance
FROM (
       select issue.id, sum(ii.final_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'EUR'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue payments_pln
UPDATE issue SET payments_pln = balance_additional_pln - balance_start_pln
where  issue_state not in('DONE', 'CANCELED');

-- update issue payments_eur
UPDATE issue SET payments_eur = balance_additional_eur - balance_start_eur
where  issue_state not in('DONE', 'CANCELED');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_update_invoice_issue_subject.sql', 'pnaroznik', 'src/main/dbschema/1.5.0/db.changelog.xml', NOW(), 140, '7:51f9de08670f32f85ba90d76c704cad8', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8465637506');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

