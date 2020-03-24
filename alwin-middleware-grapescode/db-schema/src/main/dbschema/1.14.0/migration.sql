-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.14.0/db.changelog.xml
-- Ran at: 03.10.18 14:15
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.32 (192.168.0.32)', LOCKGRANTED = '2018-10-03 14:15:44.399' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.14.0/db.changelog.xml::001_add_new_data_to_issue_type_activity_type::pnaroznik
INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id FROM issue_type WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id FROM activity_type WHERE "key" = 'OUTGOING_PHONE_CALL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id FROM issue_type WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id FROM activity_type WHERE "key" = 'FAILED_PHONE_CALL_ATTEMPT'));

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_add_new_data_to_issue_type_activity_type', 'pnaroznik', 'src/main/dbschema/1.14.0/db.changelog.xml', NOW(), 205, '7:633940e8462a9b5f93fc993b9fca8812', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8568945100');

-- Changeset src/main/dbschema/1.14.0/db.changelog.xml::001_create_table_postal_codes::mhorowic
CREATE TABLE postal_code (
  id          BIGSERIAL  NOT NULL,
  mask        VARCHAR(6) NOT NULL,
  operator_id BIGINT     NOT NULL,
  CONSTRAINT pk_postal_code PRIMARY KEY (id),
  CONSTRAINT uq_postal_code_mask UNIQUE (mask),
  CONSTRAINT fk_postal_code_alwin_operator FOREIGN KEY (operator_id) REFERENCES alwin_operator (id)
);

COMMENT ON TABLE postal_code
IS 'Maski kodow pocztowych przypisane do operatorow';

COMMENT ON COLUMN postal_code.id
IS 'Identyfikator przypisania maski';

COMMENT ON COLUMN postal_code.mask
IS 'Maska kodu pocztowego dla operatora';

COMMENT ON COLUMN postal_code.operator_id
IS 'Identyfikator operatora przypisanego do maski kodu pocztowego';

CREATE INDEX idx_postal_code_mask
  ON postal_code (mask);

CREATE TABLE postal_code_aud (
  id          BIGSERIAL NOT NULL,
  mask        VARCHAR(6),
  operator_id BIGINT,
  REV         INTEGER   NOT NULL,
  REVTYPE     SMALLINT,
  CONSTRAINT pk_postal_code_aud PRIMARY KEY (id, REV)
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_create_table_postal_codes', 'mhorowic', 'src/main/dbschema/1.14.0/db.changelog.xml', NOW(), 206, '7:8cf8c430529a1088aa944171beb53de5', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8568945100');

-- Changeset src/main/dbschema/1.14.0/db.changelog.xml::002_alter_formal_debt_collection_add_doc_reference::tsliwinski
-- identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
ALTER TABLE demand_for_payment
  ADD COLUMN preceding_demand_for_payment_id BIGINT,
  ADD CONSTRAINT fk_demand_for_payment_preceding_demand_for_payment_id FOREIGN KEY (preceding_demand_for_payment_id) REFERENCES demand_for_payment (id);

COMMENT ON COLUMN demand_for_payment.preceding_demand_for_payment_id
IS 'Link do poprzedzającego wezwania do zapłaty';

-- identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
ALTER TABLE contract_termination
  ADD COLUMN preceding_demand_for_payment_id BIGINT,
  ADD CONSTRAINT fk_contract_termination_preceding_demand_for_payment_id FOREIGN KEY (preceding_demand_for_payment_id) REFERENCES demand_for_payment (id);

COMMENT ON COLUMN contract_termination.preceding_demand_for_payment_id
IS 'Link do poprzedzającego wezwania do zapłaty';

ALTER TABLE contract_termination_aud
  ADD COLUMN preceding_demand_for_payment_id BIGINT;

-- identyfikator poprzedzającego wypowiedzenia umowy (jeśli istnieje)
ALTER TABLE contract_termination
  ADD COLUMN preceding_contract_termination_id BIGINT,
  ADD CONSTRAINT fk_contract_termination_preceding_contract_termination_id FOREIGN KEY (preceding_contract_termination_id) REFERENCES contract_termination (id);

COMMENT ON COLUMN contract_termination.preceding_contract_termination_id
IS 'Link do poprzedzającego wypowiedzenia umowy';

ALTER TABLE contract_termination_aud
  ADD COLUMN preceding_contract_termination_id BIGINT;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_formal_debt_collection_add_doc_reference', 'tsliwinski', 'src/main/dbschema/1.14.0/db.changelog.xml', NOW(), 207, '7:1048dad9264c556cca119f4c4e61e6be', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8568945100');

-- Changeset src/main/dbschema/1.14.0/db.changelog.xml::003_formal_debt_collection_out_of_service::pnaroznik
CREATE TABLE formal_debt_collection_customer_out_of_service (
  id                   BIGSERIAL    NOT NULL,
  customer_id          BIGINT       NOT NULL,
  start_date           DATE         NOT NULL,
  end_date             DATE         NOT NULL,
  blocking_operator_id BIGINT       NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  reason_type          VARCHAR(50)  NOT NULL,
  remark               TEXT,
  demand_for_payment_type          VARCHAR(50)  NOT NULL,
  CONSTRAINT pk_formal_debt_collection_customer_out_of_service PRIMARY KEY (id)
);

CREATE INDEX idx_formal_debt_collection_customer_out_of_service_customer_id
  ON formal_debt_collection_customer_out_of_service (customer_id);

CREATE INDEX idx_formal_debt_collection_customer_out_of_service_blocking_operator_id
  ON formal_debt_collection_customer_out_of_service (blocking_operator_id);

COMMENT ON TABLE formal_debt_collection_customer_out_of_service
IS 'Dodano tabelę klientów wykluczonych z tworzenia wezwań do zapłaty.';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.customer_id
IS 'ID zablokowanego klienta';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.start_date
IS 'Data wykluczenia';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.end_date
IS 'Data zakończenia wykluczenia.';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.blocking_operator_id
IS 'Operator wykluczający z obsługi';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.reason
IS 'Przyczyna wykluczenia z obsługi';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.reason_type
IS 'Słownikowa wartość przyczyny wykluczenia z obsługi';

COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.demand_for_payment_type
IS 'Typ wezwania dla których obowiązuje wykluczenie z obsługi';

CREATE TABLE formal_debt_collection_contract_out_of_service (
  id                   BIGSERIAL    NOT NULL,
  start_date           DATE,
  end_date             DATE,
  blocking_operator_id BIGINT       NOT NULL,
  contract_no          VARCHAR(30)  NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  reason_type          VARCHAR(50)  NOT NULL,
  remark               TEXT,
  demand_for_payment_type          VARCHAR(50)  NOT NULL,
  CONSTRAINT pk_formal_debt_collection_contract_out_of_service PRIMARY KEY (id),
  CONSTRAINT fk_formal_debt_collection_contract_out_of_service_blocking_operator_id FOREIGN KEY (blocking_operator_id) REFERENCES alwin_operator (id)
);

CREATE INDEX idx_formal_debt_collection_contract_out_of_service_blocking_operator_id
  ON formal_debt_collection_contract_out_of_service (blocking_operator_id);

COMMENT ON TABLE formal_debt_collection_contract_out_of_service
IS 'Kontrakty klientow wykluczone z obslugi';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.start_date
IS 'Data rozpoczecia wykluczenia';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.end_date
IS 'Data zakończenia wykluczenia.';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.blocking_operator_id
IS 'Operator wykluczający kontrakt z obsługi';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.contract_no
IS 'Numer kontraktu';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.reason
IS 'Przyczyna wykluczenia z obsługi';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.remark
IS 'Komentarz';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.reason_type
IS 'Słownikowa wartość przyczyny wykluczenia z obsługi';

COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.demand_for_payment_type
IS 'Typ wezwania dla których obowiązuje wykluczenie z obsługi';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_formal_debt_collection_out_of_service', 'pnaroznik', 'src/main/dbschema/1.14.0/db.changelog.xml', NOW(), 208, '7:c998d94d3c4bf89af1f2006938a2ea53', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8568945100');

-- Changeset src/main/dbschema/1.14.0/db.changelog.xml::004_alter_formal_debt_collection_out_of_service::pnaroznik
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

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_formal_debt_collection_out_of_service', 'pnaroznik', 'src/main/dbschema/1.14.0/db.changelog.xml', NOW(), 209, '7:dd906ecde8b9b53f5b862624fcffaaa8', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8568945100');

-- Changeset src/main/dbschema/1.14.0/db.changelog.xml::005_alter_demand_for_payment_create_manually_flag::tsliwinski
ALTER TABLE demand_for_payment
  ADD COLUMN created_manually BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN demand_for_payment.created_manually
IS 'Flaga określająca, czy wezwanie utworzono ręcznie';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_alter_demand_for_payment_create_manually_flag', 'tsliwinski', 'src/main/dbschema/1.14.0/db.changelog.xml', NOW(), 210, '7:5644c44db77875cc389c6eedd88f0ea0', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8568945100');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

