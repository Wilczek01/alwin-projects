-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.10.0/db.changelog.xml
-- Ran at: 07.08.18 11:43
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.11 (192.168.0.11)', LOCKGRANTED = '2018-08-07 11:43:30.452' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.10.0/db.changelog.xml::001_create_activity_type_detail_poperty_dict_value::tsliwinski
CREATE TABLE activity_type_detail_property_dict_value (
  id                          BIGSERIAL     NOT NULL,
  activity_detail_property_id BIGINT        NOT NULL,
  value                       VARCHAR(1000) NOT NULL,
  CONSTRAINT pk_activity_type_detail_property_dict_value PRIMARY KEY (id),
  CONSTRAINT idx_activity_detail_value UNIQUE (activity_detail_property_id, value),
  CONSTRAINT fk_activity_detail_property FOREIGN KEY (activity_detail_property_id) REFERENCES activity_detail_property (id)
);

COMMENT ON TABLE activity_type_detail_property_dict_value
IS 'Wartości słownikowe cechy dodatkowej dla czynności';

COMMENT ON COLUMN activity_type_detail_property_dict_value.id
IS 'Identyfikator wartości słownikowej cechy dodatkowej';

COMMENT ON COLUMN activity_type_detail_property_dict_value.activity_detail_property_id
IS 'Link do tabeli typów cech dodatkowych';

COMMENT ON COLUMN activity_type_detail_property_dict_value.value
IS 'Wartość słownikowa dla cechy dodatkowej';

CREATE TABLE activity_type_detail_property_dict_value_aud (
  id                          BIGSERIAL NOT NULl,
  rev                         INTEGER   NOT NULL,
  revtype                     SMALLINT,
  activity_detail_property_id BIGINT,
  dict_value                  VARCHAR(1000),
  CONSTRAINT pk_activity_type_detail_property_dict_value_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE activity_type_detail_property_dict_value_aud
IS 'Tabela audytowa wartości słownikowych cechy dodatkowej dla czynności';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_create_activity_type_detail_poperty_dict_value', 'tsliwinski', 'src/main/dbschema/1.10.0/db.changelog.xml', NOW(), 173, '7:750987c5a44c0f4d750b742a8567238e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3635011124');

-- Changeset src/main/dbschema/1.10.0/db.changelog.xml::002_insert_failed_phone_call_reason_activity_type_detail_property::tsliwinski
INSERT INTO activity_detail_property (property_name, property_type, property_key)
VALUES ('Przyczyna nieudanego kontaktu tel.', 'java.lang.String', 'FAILED_PHONE_CALL_REASON');

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state, required)
VALUES ((SELECT id FROM activity_type WHERE key = 'FAILED_PHONE_CALL_ATTEMPT'),
        (SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'),
        'EXECUTED',
        true);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_insert_failed_phone_call_reason_activity_type_detail_property', 'tsliwinski', 'src/main/dbschema/1.10.0/db.changelog.xml', NOW(), 174, '7:cdb40313bfd21dab98f930103d718d11', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3635011124');

-- Changeset src/main/dbschema/1.10.0/db.changelog.xml::003_insert_activity_type_detail_property_dict_values::tsliwinski
insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Nie odbiera');

insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Tel. Wyłączony');

insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Nie ma takiego numeru');

insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Inna');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_insert_activity_type_detail_property_dict_values', 'tsliwinski', 'src/main/dbschema/1.10.0/db.changelog.xml', NOW(), 175, '7:43a71bb11bc69acb8421f57250bb6b47', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3635011124');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

