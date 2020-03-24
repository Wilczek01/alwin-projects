-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.7.0/db.changelog.xml
-- Ran at: 10.07.18 13:14
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.45 (192.168.0.45)', LOCKGRANTED = '2018-07-10 13:14:14.171' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.7.0/db.changelog.xml::013_alter_declaration::astepnowski
ALTER TABLE declaration ADD COLUMN paid BOOLEAN DEFAULT false;

ALTER TABLE declaration_aud ADD COLUMN paid BOOLEAN;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('013_alter_declaration', 'astepnowski', 'src/main/dbschema/1.7.0/db.changelog.xml', NOW(), 158, '7:3cf6def6aa1e67a46afeb07f00be5f03', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '1221254881');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

