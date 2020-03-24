-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.15.0/db.changelog.xml
-- Ran at: 18.10.18 07:28
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.38 (192.168.0.38)', LOCKGRANTED = '2018-10-18 07:28:20.405' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::001_alter_contract_termination_add_activation_date::drackowski
-- data aktywacji wypowiedzenia umowy
ALTER TABLE contract_termination
  ADD COLUMN activation_date TIMESTAMP;

COMMENT ON COLUMN contract_termination.activation_date
IS 'Data aktywacji wypowiedzenia umowy';

ALTER TABLE contract_termination_aud
  ADD COLUMN activation_date TIMESTAMP;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_alter_contract_termination_add_activation_date', 'drackowski', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 217, '7:4f0998669a6bca6132ddbfdacc6c6059', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::002_alter_contract_termination_subject::pnaroznik
alter table contract_termination_subject
  add column gps_installed bool not null default false;

alter table contract_termination_subject_aud
  add column gps_installed bool;

COMMENT ON COLUMN contract_termination_subject.gps_installed
IS 'Czy przedmiot umowy ma już zainstalowany GPS';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_contract_termination_subject', 'pnaroznik', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 218, '7:ac7d1e625cc86b2ae56bee82d11bd7d5', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::003_alter_contract_termination_subject_increased_fee::pnaroznik
alter table contract_termination_subject
  add column gps_increased_fee bool not null default false;

alter table contract_termination_subject
  add column gps_increased_installation_charge NUMERIC(18, 2);

alter table contract_termination_subject_aud
  add column gps_increased_fee bool;

alter table contract_termination_subject_aud
  add column gps_increased_installation_charge NUMERIC(18, 2);

COMMENT ON COLUMN contract_termination_subject.gps_increased_fee
IS 'Czy powiększona opłata za instalację GPS';

COMMENT ON COLUMN contract_termination_subject.gps_increased_installation_charge
IS 'Powiększony koszt instalacji GPS';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_contract_termination_subject_increased_fee', 'pnaroznik', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 219, '7:3ba3c9789a86284db11a6b954251e17c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::004_alter_contract_termination::astepnowski
alter table contract_termination
  add column state_change_date TIMESTAMP;

alter table contract_termination_aud
  add column state_change_date TIMESTAMP;

alter table contract_termination
  add column state_change_reason TEXT;

alter table contract_termination_aud
  add column state_change_reason TEXT;

alter table contract_termination
  add column state_change_operator_id BIGINT;

alter table contract_termination_aud
  add column state_change_operator_id BIGINT;

COMMENT ON COLUMN contract_termination.state_change_date
IS 'Data zmiany stanu';

COMMENT ON COLUMN contract_termination.state_change_reason
IS 'Przyczyna zmiany stanu';

COMMENT ON COLUMN contract_termination.state_change_operator_id
IS 'Identyfikator operatora zmieniającego stan';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_contract_termination', 'astepnowski', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 220, '7:13ffd3a2f68bbb2e3b2aa0ad1f069767', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::005_alter_demand_for_payment::astepnowski
alter table demand_for_payment
  add column state_change_date TIMESTAMP;

alter table demand_for_payment
  add column state_change_reason TEXT;

alter table demand_for_payment
  add column state_change_operator_id BIGINT;

COMMENT ON COLUMN demand_for_payment.state_change_date
IS 'Data zmiany stanu';

COMMENT ON COLUMN demand_for_payment.state_change_reason
IS 'Przyczyna zmiany stanu';

COMMENT ON COLUMN demand_for_payment.state_change_operator_id
IS 'Identyfikator operatora zmieniającego stan';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_alter_demand_for_payment', 'astepnowski', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 221, '7:bf18d8765235a18805629e8d4634630d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::006_alter_demand_for_payment_reason_type::pnaroznik
alter table demand_for_payment
  add column reason_type VARCHAR(50);

COMMENT ON COLUMN demand_for_payment.reason_type
IS 'Słownikowa wartość przyczyny anulowania wezwania';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_alter_demand_for_payment_reason_type', 'pnaroznik', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 222, '7:62ffdefd462d571f4b43f531c5eb4e85', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::007_alter_demand_for_payment_add_abort_flag::tsliwinski
ALTER TABLE demand_for_payment
  ADD COLUMN aborted BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN demand_for_payment.aborted
IS 'Flaga określająca, czy wezwanie zastąpione w procesie wezwaniem utworzonym ręcznie (wyłączone z procesu)';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_alter_demand_for_payment_add_abort_flag', 'tsliwinski', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 223, '7:c8a4a708ea701108814679d9f91d0bfa', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::001_add_operator_types::pnaroznik
INSERT INTO operator_type (type_name, type_label, parent_operator_type_id)
VALUES ('PHONE_DEBT_COLLECTOR_1', 'Windykator telefoniczny sekcja 1', (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR'));

INSERT INTO operator_type (type_name, type_label, parent_operator_type_id)
VALUES ('PHONE_DEBT_COLLECTOR_2', 'Windykator telefoniczny sekcja 2', (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR'));

INSERT INTO operator_type_issue_type (issue_type_id, operator_type_id)
VALUES ((select id from issue_type where "name" = 'PHONE_DEBT_COLLECTION_1'), (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR_1'));

INSERT INTO operator_type_issue_type (issue_type_id, operator_type_id)
VALUES ((select id from issue_type where "name" = 'PHONE_DEBT_COLLECTION_2'), (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR_2'));

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_add_operator_types', 'pnaroznik', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 224, '7:0dbb02fe8d191e4e27e75c43d3cf73b5', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::002_update_operator_types::pnaroznik
update operator_type
set parent_operator_type_id = (select id
                               from operator_type
                               where type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
where type_name = 'PHONE_DEBT_COLLECTOR_1';

update operator_type
set parent_operator_type_id = (select id
                               from operator_type
                               where type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
where type_name = 'PHONE_DEBT_COLLECTOR_2';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_update_operator_types', 'pnaroznik', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 225, '7:111363b69d5f3f1e1e5a9e87715fc78d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Changeset src/main/dbschema/1.15.0/db.changelog.xml::008_alter_permission_add_demand_for_payment::drackowski
ALTER TABLE PERMISSION ADD COLUMN allowed_to_manage_demands_for_payment BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE PERMISSION_AUD ADD COLUMN allowed_to_manage_demands_for_payment BOOLEAN;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('008_alter_permission_add_demand_for_payment', 'drackowski', 'src/main/dbschema/1.15.0/db.changelog.xml', NOW(), 226, '7:01bea8209fa3206e1c0bbb75daff01b7', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '9840501020');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

