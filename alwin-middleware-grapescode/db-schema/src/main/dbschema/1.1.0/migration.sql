-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.1.0/db.changelog.xml
-- Ran at: 19.03.18 11:54
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.108 (192.168.0.108)', LOCKGRANTED = '2018-03-19 11:54:57.904' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.1.0/db.changelog.xml::001_create_demand_for_payment_type::tsliwinski
CREATE TABLE demand_for_payment_type (
  id                      BIGSERIAL      NOT NULL,
  key                     VARCHAR(10)    NOT NULL,
  dpd_start               NUMERIC(4)     NOT NULL,
  number_of_days_to_pay   NUMERIC(4)     NOT NULL,
  charge                  NUMERIC(18, 2) NOT NULL,
  segment                 VARCHAR(10)    NOT NULL,
  min_due_payment_value   NUMERIC(18, 2) NOT NULL,
  min_due_payment_percent NUMERIC(18, 2),
  CONSTRAINT pk_demand_for_payment_type PRIMARY KEY (id)
);

COMMENT ON COLUMN demand_for_payment_type.id IS 'Identyfikator typu wezwania do zapłaty';

COMMENT ON COLUMN demand_for_payment_type.key IS 'Typ wezwania (pierwsze, drugie)';

COMMENT ON COLUMN demand_for_payment_type.dpd_start IS 'DPD, po przekroczeniu którego dla FV generujemy wezwanie';

COMMENT ON COLUMN demand_for_payment_type.number_of_days_to_pay IS 'Liczba dni na zapłatę';

COMMENT ON COLUMN demand_for_payment_type.charge IS 'Koszt opłaty za wezwanie';

COMMENT ON COLUMN demand_for_payment_type.segment IS 'Segment LB (A, B)';

COMMENT ON COLUMN demand_for_payment_type.min_due_payment_value IS 'Minimalna zaległość';

COMMENT ON COLUMN demand_for_payment_type.min_due_payment_percent IS 'Minimalny % zaległości';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_create_demand_for_payment_type', 'tsliwinski', 'src/main/dbschema/1.1.0/db.changelog.xml', NOW(), 106, '7:8bd3381716d8a7e1bf44b7710b6e9e5b', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '1456898703');

-- Changeset src/main/dbschema/1.1.0/db.changelog.xml::002_alter_issue_debt_collection_action::astepnowski
ALTER TABLE ACTIVITY ALTER COLUMN activity_state SET DEFAULT 'EXECUTED';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_issue_debt_collection_action', 'astepnowski', 'src/main/dbschema/1.1.0/db.changelog.xml', NOW(), 107, '7:71fcb07db72f1e2531395ad6697d9a34', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '1456898703');

-- Changeset src/main/dbschema/1.1.0/db.changelog.xml::003_create_demand_for_payment::tsliwinski
COMMENT ON TABLE demand_for_payment_type IS 'Konfiguracji typów wezwań do zapłaty';

CREATE TABLE demand_for_payment (
  id                         BIGSERIAL   NOT NULL,
  issue_date                 TIMESTAMP   NOT NULL,
  due_date                   TIMESTAMP   NOT NULL,
  initial_invoice_no         VARCHAR(30) NOT NULL,
  ext_company_id             BIGINT      NOT NULL,
  demand_for_payment_type_id BIGINT      NOT NULL,
  charge_invoice_no          VARCHAR(30),
  state                      VARCHAR(20) NOT NULL,
  attachment_ref             VARCHAR(1000),
  CONSTRAINT pk_demand_for_payment PRIMARY KEY (id),
  CONSTRAINT fk_demand_for_payment_type FOREIGN KEY (demand_for_payment_type_id) REFERENCES demand_for_payment_type (id)
);

COMMENT ON TABLE demand_for_payment IS 'Wezwania do zapłaty';

COMMENT ON COLUMN demand_for_payment.id IS 'Identyfikator wezwania do zapłaty';

COMMENT ON COLUMN demand_for_payment.issue_date IS 'Data wystawienia';

COMMENT ON COLUMN demand_for_payment.due_date IS 'Termin zapłaty';

COMMENT ON COLUMN demand_for_payment.initial_invoice_no IS 'Numer faktury inicjującej wystawienie';

COMMENT ON COLUMN demand_for_payment.ext_company_id IS 'Identyfikator klienta w systemie zewtnętrznym';

COMMENT ON COLUMN demand_for_payment.demand_for_payment_type_id IS 'Link do typu wezwania';

COMMENT ON COLUMN demand_for_payment.charge_invoice_no IS 'Nr FV za opłatę';

COMMENT ON COLUMN demand_for_payment.state IS 'Status';

COMMENT ON COLUMN demand_for_payment.attachment_ref IS 'Referencja do pliku z wezwaniem do zapłaty';

-- łączenie wezwań z fakturami

CREATE TABLE demand_for_payment_invoices (
  demand_for_payment_id BIGINT NOT NULL,
  invoice_id            BIGINT NOT NULL,
  CONSTRAINT idx_demand_for_payment_invoices PRIMARY KEY (demand_for_payment_id, invoice_id),
  CONSTRAINT fk_demand_for_payment_invoices_demand_for_payment FOREIGN KEY (demand_for_payment_id) REFERENCES demand_for_payment (id),
  CONSTRAINT fk_demand_for_payment_invoices_invoice FOREIGN KEY (invoice_id) REFERENCES invoice (id)
);

CREATE INDEX idx_demand_for_payment_invoices_demand_for_payment
  ON demand_for_payment_invoices (demand_for_payment_id);

CREATE INDEX idx_demand_for_payment_invoices_invoice
  ON demand_for_payment_invoices (invoice_id);

COMMENT ON TABLE demand_for_payment_invoices IS 'Tabela łącząca wezwania do zapłaty z fakturami';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_create_demand_for_payment', 'tsliwinski', 'src/main/dbschema/1.1.0/db.changelog.xml', NOW(), 108, '7:c38df7f3e87c99ad33fba149c83146aa', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '1456898703');

-- Changeset src/main/dbschema/1.1.0/db.changelog.xml::004_alter_last_data_sync::pnaroznik
ALTER TABLE last_data_sync
  ALTER COLUMN from_date TYPE TIMESTAMP;

ALTER TABLE last_data_sync
  ALTER COLUMN to_date TYPE TIMESTAMP;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_last_data_sync', 'pnaroznik', 'src/main/dbschema/1.1.0/db.changelog.xml', NOW(), 109, '7:d8a8e46cccdc14d5640048500d9cfa65', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '1456898703');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

