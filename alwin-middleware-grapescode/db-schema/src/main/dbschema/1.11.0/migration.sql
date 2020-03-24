-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.11.0/db.changelog.xml
-- Ran at: 16.08.18 09:14
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '2a02:a319:8244:5f80:44ac:1d70:c1a6:af79 (2a02:a319:8244:5f80:44ac:1d70:c1a6:af79)', LOCKGRANTED = '2018-08-16 09:14:59.518' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.11.0/db.changelog.xml::001_update_issue_type_configuration::tsliwinski
-- Windykacja telefoniczna sekcja 1, segment B
UPDATE issue_type_configuration
SET dpd_start         = 1,
    min_balance_start = -100.00,
    duration          = 9
WHERE segment = 'B'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1');

-- Windykacja telefoniczna sekcja 1, segment A
UPDATE issue_type_configuration
SET dpd_start         = 1,
    min_balance_start = -100.00,
    duration          = 14
WHERE segment = 'A'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1');

-- Windykacja telefoniczna sekcja 2, segment B
UPDATE issue_type_configuration
SET dpd_start         = 11,
    min_balance_start = -100.00,
    duration          = 12
WHERE segment = 'B'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_2');

-- Windykacja telefoniczna sekcja 2, segment A
UPDATE issue_type_configuration
SET dpd_start         = 16,
    min_balance_start = -100.00,
    duration          = 17
WHERE segment = 'A'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_2');

-- wyłączenie automatycznego tworzenia zleceń innych niż WT1 i WT2
UPDATE issue_type_configuration
SET create_automatically = false
WHERE issue_type_id not in (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1'
                                                         OR name = 'PHONE_DEBT_COLLECTION_2');

-- włączenie automatycznego tworzenia zleceń WT1 i WT2
UPDATE issue_type_configuration
SET create_automatically = true
WHERE issue_type_id in (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1'
                                                     OR name = 'PHONE_DEBT_COLLECTION_2');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_update_issue_type_configuration', 'tsliwinski', 'src/main/dbschema/1.11.0/db.changelog.xml', NOW(), 175, '7:56cc1a1ffa0f0f6e094877ed59b60ad6', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4403700162');

-- Changeset src/main/dbschema/1.11.0/db.changelog.xml::001_drop_not_null_activity_type_has_detail_property_aud::mhorowic
ALTER TABLE activity_type_has_detail_property_AUD
  DROP CONSTRAINT activity_type_has_detail_property_aud_pkey;

ALTER TABLE activity_type_has_detail_property_AUD
  ADD PRIMARY KEY (id, REV);

ALTER TABLE activity_type_has_detail_property_AUD
  ALTER COLUMN activity_detail_property_id DROP NOT NULL;

ALTER TABLE activity_type_has_detail_property_AUD
  ALTER COLUMN activity_type_id DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_drop_not_null_activity_type_has_detail_property_aud', 'mhorowic', 'src/main/dbschema/1.11.0/db.changelog.xml', NOW(), 176, '7:c5bc00f47b8e715fc64302cd6ae86dfd', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4403700162');

-- Changeset src/main/dbschema/1.11.0/db.changelog.xml::002_alter_value_name_activity_type_detail_property_dict_value_aud::mhorowic
ALTER TABLE activity_type_detail_property_dict_value_aud
  RENAME COLUMN dict_value to value;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_value_name_activity_type_detail_property_dict_value_aud', 'mhorowic', 'src/main/dbschema/1.11.0/db.changelog.xml', NOW(), 177, '7:cda50716505b1325df514cb2e8cda7a2', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4403700162');

-- Changeset src/main/dbschema/1.11.0/db.changelog.xml::003_alter_holiday::mhorowic
ALTER TABLE holiday
  ADD COLUMN alwin_user_id BIGINT;

ALTER TABLE holiday
  ADD CONSTRAINT fk_holiday_alwin_user_id FOREIGN KEY (alwin_user_id) REFERENCES alwin_user (id);

CREATE TABLE holiday_aud (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  description   VARCHAR(150),
  holiday_day   SMALLINT,
  holiday_month SMALLINT,
  holiday_year  SMALLINT,
  alwin_user_id BIGINT,
  PRIMARY KEY (id, REV)
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_holiday', 'mhorowic', 'src/main/dbschema/1.11.0/db.changelog.xml', NOW(), 178, '7:f0a74eea8c67155ef8f1e62e539c6103', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '4403700162');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

