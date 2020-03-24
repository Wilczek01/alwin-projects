-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.9.0/db.changelog.xml
-- Ran at: 7/30/18 11:15 AM
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = 'fda8:c63:2ed9:3400:91cf:df53:7b70:3739%en0 (fda8:c63:2ed9:3400:91cf:df53:7b70:3739%en0)', LOCKGRANTED = '2018-07-30 11:15:48.137' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.9.0/db.changelog.xml::001_alter_activity_type::astepnowski
update activity_type set name = 'Wezwanie do zapłaty (podstawowe)' where name = 'Wezwanie do zapłaty (podsatwowe)';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_alter_activity_type', 'astepnowski', 'src/main/dbschema/1.9.0/db.changelog.xml', NOW(), 169, '7:4c98edbb49d9c258ba68d111fdb7ac2e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '2942148897');

-- Changeset src/main/dbschema/1.9.0/db.changelog.xml::003_alter_default_issue_activity::astepnowski
INSERT INTO default_issue_activity(issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
    (select id from issue_type where name = 'PHONE_DEBT_COLLECTION_1'),
    (select id from activity_type where key = 'OUTGOING_PHONE_CALL'),
    1,
    (select max(version) + 1 from default_issue_activity where issue_type_id = (select id from issue_type where name = 'PHONE_DEBT_COLLECTION_1') and activity_type_id = (select id from activity_type where key = 'OUTGOING_PHONE_CALL')),
    now());

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_default_issue_activity', 'astepnowski', 'src/main/dbschema/1.9.0/db.changelog.xml', NOW(), 170, '7:d4f0827212053c4dc5fe603fcb4cf089', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '2942148897');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

