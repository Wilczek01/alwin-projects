-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.2.0/db.changelog.xml
-- Ran at: 20.04.18 10:46
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.108 (192.168.0.108)', LOCKGRANTED = '2018-04-20 10:46:38.959' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::001_alter_invoice_drop_invoice_id::tsliwinski
-- zduplikowana kolumna (invoice.number)
ALTER TABLE invoice
  DROP COLUMN invoice_id;

ALTER TABLE invoice_aud
  DROP COLUMN invoice_id;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_alter_invoice_drop_invoice_id', 'tsliwinski', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 119, '7:4874ea41e63fa8c0a58f0ec0b9f00c71', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::001_update_activity_details_proprty::pnaroznik
UPDATE activity_detail_property
SET property_key = 'PHONE_CALL_PERSON', property_type = 'java.lang.String'
WHERE property_key = 'DECISION_MAKER_CALL';

UPDATE activity_detail
SET property_value = 'Inny'
WHERE activity_detail_property_id IN
      (SELECT id
       FROM activity_detail_property
       WHERE property_key = 'PHONE_CALL_PERSON');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_update_activity_details_proprty', 'pnaroznik', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 120, '7:5a92e3f99758506cd94a4df612529fad', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::002_alter_demand_for_payment::tsliwinski
ALTER TABLE demand_for_payment
  ALTER COLUMN issue_date DROP NOT NULL;

ALTER TABLE demand_for_payment
  ALTER COLUMN due_date DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_demand_for_payment', 'tsliwinski', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 121, '7:beba0e20ba1efb574793b71b69eb1380', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::003_demand_for_payment_add_contract_no::tsliwinski
ALTER TABLE demand_for_payment
  ADD COLUMN contract_number VARCHAR(150);

COMMENT ON COLUMN demand_for_payment.contract_number IS 'Numer umowy';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_demand_for_payment_add_contract_no', 'tsliwinski', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 122, '7:28bf538edc01f48ab49d08e796de62a8', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::002_update_activity_details_proprty_name::pnaroznik
UPDATE activity_detail_property
SET property_name = 'Rozmówca'
WHERE property_key = 'PHONE_CALL_PERSON';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_update_activity_details_proprty_name', 'pnaroznik', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 123, '7:7182f71b45ed977cc90b6356bfa8cf35', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::004_create_holidays::mhorowic
CREATE TABLE holiday (
  id            BIGSERIAL    NOT NULL,
  description   VARCHAR(150) NOT NULL,
  holiday_day   SMALLINT     NOT NULL,
  holiday_month SMALLINT     NOT NULL,
  holiday_year  SMALLINT,
  CONSTRAINT pk_holiday PRIMARY KEY (id)
);

COMMENT ON TABLE holiday IS 'Dni wolne od pracy';

COMMENT ON COLUMN holiday.id IS 'Identyfikator dnia wolnego';

COMMENT ON COLUMN holiday.description IS 'Nazwa dnia wolnego';

COMMENT ON COLUMN holiday.holiday_day IS 'Dzien miesiaca dnia wolnego';

COMMENT ON COLUMN holiday.holiday_month IS 'Miesiac dnia wolnego';

COMMENT ON COLUMN holiday.holiday_year IS 'Rok dnia wolnego - brak oznacza powtarzajacy sie dzien wolny co roku';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_create_holidays', 'mhorowic', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 124, '7:b2b03326490d66ff315588365142e19b', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::003_insert_holidays::mhorowic
INSERT INTO holiday (holiday_day, holiday_month, holiday_year, description) VALUES
  (1, 1, NULL, 'Nowy Rok, Świętej Bożej Rodzicielki'),
  (6, 1, NULL, 'Trzech Króli (Objawienie Pańskie)'),
  (1, 5, NULL, 'Święto Pracy'),
  (3, 5, NULL, 'Święto Konstytucji 3 Maja'),
  (15, 8, NULL, 'Święto Wojska Polskiego, Wniebowzięcie Najświętszej Maryi Panny'),
  (1, 11, NULL, 'Wszystkich Świętych'),
  (11, 11, NULL, 'Święto Niepodległości'),
  (25, 12, NULL, 'Boże Narodzenie (pierwszy dzień)'),
  (26, 12, NULL, 'Boże Narodzenie (drugi dzień)');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_insert_holidays', 'mhorowic', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 125, '7:d51a3a62d234e452b3244c5d0f31365e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::005_force_update_password::mhorowic
ALTER TABLE alwin_operator ADD COLUMN force_update_password boolean NOT NULL default false;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_force_update_password', 'mhorowic', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 126, '7:080e3989f95dc36ad46eeafda15d79bd', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::006_force_update_password_aud::mhorowic
ALTER TABLE alwin_operator_aud
  ADD COLUMN force_update_password BOOLEAN;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_force_update_password_aud', 'mhorowic', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 127, '7:36f39d7a90aeb7c59e0f8a0eb2ddb797', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::004_update_contact_detail_phone_number::tsliwinski
UPDATE contact_detail
SET contact_type = 'PHONE_NUMBER'
WHERE contact_type in ('MOBILE_PHONE', 'PHONE_NUMBER_1', 'PHONE_NUMBER_2', 'PHONE_NUMBER_3');

UPDATE contact_detail_aud
SET contact_type = 'PHONE_NUMBER'
WHERE contact_type in ('MOBILE_PHONE', 'PHONE_NUMBER_1', 'PHONE_NUMBER_2', 'PHONE_NUMBER_3');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_update_contact_detail_phone_number', 'tsliwinski', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 128, '7:65aec192792e1b065a916dd661f9c355', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::008_create_tags::mhorowic
CREATE TABLE tag (
  id    BIGSERIAL   NOT NULL,
  name  VARCHAR(50) NOT NULL,
  color VARCHAR(7)  NOT NULL,
  CONSTRAINT pk_tag PRIMARY KEY (id)
);

COMMENT ON TABLE tag IS 'Etykiety dla zlecen';

COMMENT ON COLUMN tag.id IS 'Identyfikator etykiety';

COMMENT ON COLUMN tag.name IS 'Nazwa etykiety';

COMMENT ON COLUMN tag.color IS 'Kolor etykiety';

CREATE TABLE tag_aud (
  id      BIGSERIAL   NOT NULL,
  rev     INTEGER     NOT NULL,
  revtype SMALLINT,
  name    VARCHAR(50) NOT NULL,
  color   VARCHAR(7)  NOT NULL,
  CONSTRAINT pk_tag_aud PRIMARY KEY (id, rev)
);

CREATE TABLE issue_tag (
  id       BIGSERIAL,
  issue_id BIGINT,
  tag_id   BIGINT,
  CONSTRAINT pk_issue_tag PRIMARY KEY (id),
  CONSTRAINT fk_issue_tag_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id),
  CONSTRAINT fk_issue_tag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id)
);

COMMENT ON TABLE issue_tag IS 'Etykiety nadane zleceniom';

COMMENT ON COLUMN issue_tag.issue_id IS 'Identyfikator zlecenia';

COMMENT ON COLUMN issue_tag.tag_id IS 'Identyfikator etykiety';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('008_create_tags', 'mhorowic', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 129, '7:d6258878630c175bce4afb4dbe3448a8', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::007_update_invoice_add_corrections::pnaroznik
ALTER TABLE invoice
  ADD COLUMN correction BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE invoice
  ADD COLUMN parent_id BIGINT;

ALTER TABLE invoice
  ADD COLUMN correction_order INTEGER;

COMMENT ON COLUMN invoice.correction IS 'Określa czy faktura jest fakturą korygującą';

COMMENT ON COLUMN invoice.parent_id IS 'Identyfikator skorygowanej faktury';

COMMENT ON COLUMN invoice.correction_order IS 'Kolejność wystawianych korekt dla jednej faktury';

ALTER TABLE invoice_aud
  ADD COLUMN correction BOOLEAN;

ALTER TABLE invoice_aud
  ADD COLUMN parent_id BIGINT;

ALTER TABLE invoice_aud
  ADD COLUMN correction_order INTEGER;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_update_invoice_add_corrections', 'pnaroznik', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 130, '7:b05e8816ed1e92619ac816a398f045ad', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::009_alter_declaration::astepnowski
ALTER TABLE DECLARATION RENAME COLUMN CURRENT_BALANCE TO CURRENT_BALANCE_PLN;

ALTER TABLE DECLARATION ADD COLUMN current_balance_eur NUMERIC(18, 2);

COMMENT ON COLUMN DECLARATION.CURRENT_BALANCE_PLN IS 'Saldo bieżące w chwili utworzenia lub aktualizacji deklaracji (PLN)';

COMMENT ON COLUMN DECLARATION.CURRENT_BALANCE_EUR IS 'Saldo bieżące w chwili utworzenia lub aktualizacji deklaracji (EUR)';

ALTER TABLE DECLARATION_AUD RENAME COLUMN CURRENT_BALANCE TO CURRENT_BALANCE_PLN;

ALTER TABLE DECLARATION_AUD ADD COLUMN current_balance_eur NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('009_alter_declaration', 'astepnowski', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 131, '7:a72fa34724997bca0b7433d34408893a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Changeset src/main/dbschema/1.2.0/db.changelog.xml::010_fix_issue_tags::mhorowic
ALTER TABLE tag_aud
  ALTER COLUMN name DROP NOT NULL;

ALTER TABLE tag_aud
  ALTER COLUMN color DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('010_fix_issue_tags', 'mhorowic', 'src/main/dbschema/1.2.0/db.changelog.xml', NOW(), 132, '7:2ffa42870e965e1686b79442bf97b7f2', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4213999671');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

