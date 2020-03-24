-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.8.0/db.changelog.xml
-- Ran at: 06.08.18 09:59
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.11 (192.168.0.11)', LOCKGRANTED = '2018-08-06 09:59:46.690' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.8.0/db.changelog.xml::001_create_tag_icon::drackowski
CREATE TABLE TAG_ICON (
  id   BIGSERIAL   NOT NULL,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT pk_tag_icon PRIMARY KEY (id)
);

COMMENT ON COLUMN tag_icon.id IS 'Identyfikator symbolu etykiety';

COMMENT ON COLUMN tag_icon.name IS 'Nazwa symbolu etykiety (zgodna z ikonami material design)';

CREATE TABLE TAG_ICON_AUD (
  id      BIGSERIAL,
  rev     INTEGER NOT NULL,
  revtype SMALLINT,
  name    VARCHAR(50),
  CONSTRAINT pk_tag_icon_aud PRIMARY KEY (id, rev)
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_create_tag_icon', 'drackowski', 'src/main/dbschema/1.8.0/db.changelog.xml', NOW(), 173, '7:278f795be0192152a309e11775ffcbc4', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3542387334');

-- Changeset src/main/dbschema/1.8.0/db.changelog.xml::001_insert_tag_icons::drackowski
INSERT INTO TAG_ICON (name) VALUES
  ('bookmark'),
  ('contact_support'),
  ('date_range'),
  ('description'),
  ('euro_symbol'),
  ('face'),
  ('grade'),
  ('hourglass_full'),
  ('lock'),
  ('info'),
  ('pan_tool'),
  ('warning'),
  ('ring_volume'),
  ('mail'),
  ('forward'),
  ('redo');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_insert_tag_icons', 'drackowski', 'src/main/dbschema/1.8.0/db.changelog.xml', NOW(), 174, '7:43efce1ca2537a74600780e066a8168f', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3542387334');

-- Changeset src/main/dbschema/1.8.0/db.changelog.xml::002_add_tag_icon_and_description_to_tag::drackowski
ALTER TABLE TAG ADD COLUMN description VARCHAR(500);

ALTER TABLE TAG ADD COLUMN tag_icon_id BIGINT;

UPDATE TAG SET tag_icon_id = (SELECT min(id) FROM tag_icon) WHERE tag_icon_id IS NULL;

ALTER TABLE TAG ALTER COLUMN tag_icon_id SET NOT NULL;

ALTER TABLE TAG ADD CONSTRAINT fk_tag_tag_icon FOREIGN KEY (tag_icon_id) REFERENCES TAG_ICON (id);

ALTER TABLE TAG_AUD ADD COLUMN description VARCHAR(500);

ALTER TABLE TAG_AUD ADD COLUMN tag_icon_id BIGINT;

COMMENT ON COLUMN tag.description IS 'Opis etykiety';

COMMENT ON COLUMN tag.tag_icon_id IS 'Identyfikator symbolu etykiety';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_add_tag_icon_and_description_to_tag', 'drackowski', 'src/main/dbschema/1.8.0/db.changelog.xml', NOW(), 175, '7:9b14638d43b1374e9974fad172c6a8d6', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3542387334');

-- Changeset src/main/dbschema/1.8.0/db.changelog.xml::003_alter_declaration_aud::astepnowski
ALTER TABLE declaration_aud ADD COLUMN activity_id BIGINT;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_alter_declaration_aud', 'astepnowski', 'src/main/dbschema/1.8.0/db.changelog.xml', NOW(), 176, '7:0e2f4c21bd9da43c0f57fd2796427197', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '3542387334');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

