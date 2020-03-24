-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.3.0/db.changelog.xml
-- Ran at: 18.05.18 12:21
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.14 (192.168.0.14)', LOCKGRANTED = '2018-05-18 12:21:04.215' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::001_insert_operator_type::pnaroznik
INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('CUSTOMER_ASSISTANT', 'Opiekun klienta');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_insert_operator_type', 'pnaroznik', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 130, '7:57f90fdee67bac3cb8571a028390fd4c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::002_update_operator_type::pnaroznik
UPDATE operator_type
set type_name = 'ACCOUNT_MANAGER'
where type_name = 'CUSTOMER_ASSISTANT';

UPDATE operator_type_aud
set type_name = 'ACCOUNT_MANAGER'
where type_name = 'CUSTOMER_ASSISTANT';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_update_operator_type', 'pnaroznik', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 131, '7:5de8797380e447f31e01501530536940', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::001_add_customer_account_manager::pnaroznik
ALTER TABLE customer
  ADD COLUMN account_manager_id BIGINT;

ALTER TABLE customer
  ADD CONSTRAINT fk_customer_alwin_operator FOREIGN KEY (account_manager_id) REFERENCES alwin_operator (id);

COMMENT ON COLUMN customer.account_manager_id
IS 'Opiekun klienta';

ALTER TABLE customer_aud
  ADD COLUMN account_manager_id BIGINT;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_add_customer_account_manager', 'pnaroznik', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 132, '7:3e1c259ea21982613b80a5637d34f7e4', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::003_alter_declaration::astepnowski
ALTER TABLE DECLARATION RENAME COLUMN DECLARED_PAYMENT_AMOUNT TO DECLARED_PAYMENT_AMOUNT_PLN;

ALTER TABLE DECLARATION ADD COLUMN DECLARED_PAYMENT_AMOUNT_EUR NUMERIC(18, 2);

COMMENT ON COLUMN DECLARATION.DECLARED_PAYMENT_AMOUNT_PLN IS 'Deklaracja spłaty (PLN)';

COMMENT ON COLUMN DECLARATION.DECLARED_PAYMENT_AMOUNT_EUR IS 'Deklaracja spłaty (EUR)';

ALTER TABLE DECLARATION_AUD RENAME COLUMN DECLARED_PAYMENT_AMOUNT TO DECLARED_PAYMENT_AMOUNT_PLN;

ALTER TABLE DECLARATION_AUD ADD COLUMN DECLARED_PAYMENT_AMOUNT_EUR NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_declaration', 'astepnowski', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 133, '7:8d3eab75eaf9f4ace3f0814faa3f958e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::004_alter_declaration::astepnowski
ALTER TABLE DECLARATION RENAME COLUMN CASH_PAID TO CASH_PAID_PLN;

ALTER TABLE DECLARATION ADD COLUMN CASH_PAID_EUR NUMERIC(18, 2);

COMMENT ON COLUMN DECLARATION.CASH_PAID_PLN IS 'Kwota dokonanej wpłaty (PLN)';

COMMENT ON COLUMN DECLARATION.CASH_PAID_EUR IS 'Kwota dokonanej wpłaty (EUR)';

ALTER TABLE DECLARATION_AUD RENAME COLUMN CASH_PAID TO CASH_PAID_PLN;

ALTER TABLE DECLARATION_AUD ADD COLUMN CASH_PAID_EUR NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_declaration', 'astepnowski', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 134, '7:09683dc4a933a0f5ea9584409f96bd16', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::002_create_scheduler_execution::drackowski
CREATE TABLE SCHEDULER_EXECUTION (
  id         BIGSERIAL                           NOT NULL,
  start_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  end_date   TIMESTAMP,
  state      VARCHAR(500),
  type       VARCHAR(100)                        NOT NULL,
  manual     BOOLEAN                             NOT NULL DEFAULT FALSE,
  CONSTRAINT pk_scheduler_execution PRIMARY KEY (id)
);

COMMENT ON TABLE SCHEDULER_EXECUTION IS 'informacje o wykonywaniu zadań cyklicznych';

COMMENT ON COLUMN SCHEDULER_EXECUTION.id IS 'identyfikator wykonania';

COMMENT ON COLUMN SCHEDULER_EXECUTION.start_date IS 'data rozpoczecia';

COMMENT ON COLUMN SCHEDULER_EXECUTION.end_date IS 'data zakonczenia';

COMMENT ON COLUMN SCHEDULER_EXECUTION.state IS 'status - dodatkowa informacja w przypadku niepowodzenia';

COMMENT ON COLUMN SCHEDULER_EXECUTION.type IS 'typ zadania';

COMMENT ON COLUMN SCHEDULER_EXECUTION.manual IS 'czy zostalo wystartowane recznie';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_create_scheduler_execution', 'drackowski', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 135, '7:368589b2fae10f543b4b24bf61aa4ec9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::004_alter_invoice::pnaroznik
alter table invoice
  alter column current_balance drop not null;

alter table invoice
  alter column paid drop not null;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_invoice', 'pnaroznik', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 136, '7:8f42b769ea0728d0edcc893f182ba7c4', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6638864911');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

