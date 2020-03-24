-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.4.0/db.changelog.xml
-- Ran at: 04.06.18 11:47
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.12 (192.168.0.12)', LOCKGRANTED = '2018-06-04 11:47:26.760' WHERE ID = 1 AND LOCKED = FALSE;


-- Changeset src/main/dbschema/1.3.0/db.changelog.xml::003_alter_issue_type_duration::astepnowski
ALTER TABLE ISSUE_TYPE_DURATION RENAME TO ISSUE_TYPE_CONFIGURATION;

ALTER TABLE ISSUE_TYPE_CONFIGURATION
  ADD COLUMN create_automatically BOOLEAN NOT NULL DEFAULT FALSE,
  ADD COLUMN dpd_start NUMERIC(4),
  ADD COLUMN min_balance_start NUMERIC(18, 2);

COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.create_automatically IS 'Czy dany typ zlecenia jest tworzony automatycznie';

COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.dpd_start IS 'DPD, w którym zlecenie jest automatycznie tworzone';

COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.min_balance_start IS 'Minimalny próg zadłużenia';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_issue_type_duration', 'astepnowski', 'src/main/dbschema/1.3.0/db.changelog.xml', NOW(), 144, '7:3ca87cb4fc04e7d73dbeaed56bb7a0f2', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::001_create_scheduler_configuration::drackowski
CREATE TABLE SCHEDULER_CONFIGURATION (
  id            BIGSERIAL                           NOT NULL,
  batch_process VARCHAR(500)                        NOT NULL,
  update_date   TIMESTAMP DEFAULT current_timestamp NOT NULL,
  hour          INT                                 NOT NULL,
  CONSTRAINT pk_scheduler_process PRIMARY KEY (id),
  CONSTRAINT idx_scheduler_configuration_process UNIQUE (batch_process)
);

COMMENT ON TABLE SCHEDULER_CONFIGURATION IS 'configuracja zadań cyklicznych';

COMMENT ON COLUMN SCHEDULER_CONFIGURATION.id IS 'identyfikator konfiguracji';

COMMENT ON COLUMN SCHEDULER_CONFIGURATION.batch_process IS 'identyfikator grupy zadań cyklicznych ';

COMMENT ON COLUMN SCHEDULER_CONFIGURATION.update_date IS 'data ostatniej zmiany konfiguracji';

COMMENT ON COLUMN SCHEDULER_CONFIGURATION.hour IS 'godzina startu procesu';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_create_scheduler_configuration', 'drackowski', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 138, '7:d8911b4f3009b12b2c968838818f61d8', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::001_insert_base_schedule_tasks_configuration::drackowski
INSERT INTO SCHEDULER_CONFIGURATION (batch_process, hour)
VALUES ('BASE_SCHEDULE_TASKS', 1);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_insert_base_schedule_tasks_configuration', 'drackowski', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 139, '7:26cf13d1323a542fae3cddce27b30a62', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::002_alter_table_tag::mhorowic
ALTER TABLE TAG ADD COLUMN predefined BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE TAG ADD COLUMN tag_type VARCHAR(100) NOT NULL DEFAULT 'CUSTOM';

COMMENT ON COLUMN tag.predefined IS 'Okresla czy etykieta jest zdefiniowana przez system';

COMMENT ON COLUMN tag.tag_type IS 'Typ etykiety';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_table_tag', 'mhorowic', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 140, '7:7b06b14993414d281aa5685af188f2a1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::002_insert_predefined_tag::mhorowic
INSERT INTO TAG(name, color, predefined, tag_type) VALUES ('Zaległe', '#E53935', true, 'OVERDUE');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_insert_predefined_tag', 'mhorowic', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 141, '7:ecfd94b82eb594e8e680566e94883a4c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::003_alter_table_tag_aud::mhorowic
ALTER TABLE TAG_AUD
  ADD COLUMN predefined BOOLEAN;

ALTER TABLE TAG_AUD
  ADD COLUMN tag_type VARCHAR(100);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_table_tag_aud', 'mhorowic', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 142, '7:d39823f75d11da2616c578c55b834b75', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::004_alter_issue_invoice_issue_subject::tsliwinski
ALTER TABLE ISSUE_INVOICE
  ADD COLUMN inclusion_balance NUMERIC(18, 2);

ALTER TABLE ISSUE_INVOICE
  ADD COLUMN issue_subject BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN ISSUE_INVOICE.inclusion_balance
IS 'Saldo faktury na dzień utworzenia zlecenia';

COMMENT ON COLUMN ISSUE_INVOICE.issue_subject
IS 'Czy faktura stanowi przedmiot zlecenia';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_issue_invoice_issue_subject', 'tsliwinski', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 143, '7:ba2eec63a9c894739b719c6388a2f6e9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Changeset src/main/dbschema/1.4.0/db.changelog.xml::003_update_issue_invoice_issue_subject::tsliwinski
UPDATE ISSUE_INVOICE
SET
  inclusion_balance = i.current_balance,
  final_balance     = i.current_balance,
  issue_subject     = true
FROM INVOICE I
WHERE
  invoice_id = i.id
  AND
  i.current_balance < 0;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_update_issue_invoice_issue_subject', 'tsliwinski', 'src/main/dbschema/1.4.0/db.changelog.xml', NOW(), 144, '7:a132ccc50f204497ecc0a4c944ad2e4d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '8105647643');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

