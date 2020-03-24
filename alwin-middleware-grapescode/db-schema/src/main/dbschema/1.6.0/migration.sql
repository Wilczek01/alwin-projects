-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.6.0/db.changelog.xml
-- Ran at: 02.07.18 16:46
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.11 (192.168.0.11)', LOCKGRANTED = '2018-07-02 16:46:07.727' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::001_alter_issue_type_configuration::astepnowski
ALTER TABLE ISSUE_TYPE_CONFIGURATION ADD COLUMN include_debt_invoices_during_issue BOOLEAN DEFAULT FALSE NOT NULL;

COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.include_debt_invoices_during_issue IS 'Czy dołączać przeterminowane dokumenty w trakcie trwania zlecenia';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_alter_issue_type_configuration', 'astepnowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 159, '7:b000946b8735cc5c3421a4b25903fa89', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::002_alter_issue_add_current_balances::pnaroznik
ALTER TABLE issue
  rename balance_additional_pln to current_balance_pln;

comment on column issue.current_balance_pln
is 'Aktualne saldo dokumentów, które są przedmiotem zlecenia PLN';

ALTER TABLE issue
  rename balance_additional_eur to current_balance_eur;

comment on column issue.current_balance_eur
is 'Aktualne saldo dokumentów, które są przedmiotem zlecenia EUR';

ALTER TABLE issue_aud
  rename balance_additional_pln to current_balance_pln;

ALTER TABLE issue_aud
  rename balance_additional_eur to current_balance_eur;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_alter_issue_add_current_balances', 'pnaroznik', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 160, '7:8b6584d0afd688990b94aaed064be1eb', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::003_create_table_issue_typ_activity_type::mhorowic
CREATE TABLE issue_type_activity_type (
  id              BIGSERIAL,
  issue_type_id   BIGINT,
  activity_type_id BIGINT,
  CONSTRAINT pk_issue_type_activity_type PRIMARY KEY (id),
  CONSTRAINT fk_issue_type_activity_type_issue_type_id FOREIGN KEY (issue_type_id) REFERENCES issue_type (id),
  CONSTRAINT fk_issue_type_activity_type_activity_type_id FOREIGN KEY (activity_type_id) REFERENCES activity_type (id)
);

COMMENT ON TABLE issue_type_activity_type IS 'Typy czynnosci przypisane do typow zlecen';

COMMENT ON COLUMN issue_type_activity_type.issue_type_id IS 'Identyfikator typu zlecenia';

COMMENT ON COLUMN issue_type_activity_type.activity_type_id IS 'Identyfikator typu czynnosci';

CREATE TABLE issue_type_activity_type_aud (
  id              BIGSERIAL NOT NULL,
  rev             INTEGER   NOT NULL,
  revtype         SMALLINT,
  issue_type_id   BIGINT,
  activity_type_id BIGINT,
  CONSTRAINT pk_issue_type_activity_type_aud PRIMARY KEY (id, rev)
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_create_table_issue_typ_activity_type', 'mhorowic', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 161, '7:9d06d853f6dd229d8b09eb0003fcf6d0', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::001_add_issue_typ_activity_type::mhorowic
INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'OUTGOING_PHONE_CALL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'FIRST_DEMAND_PAYMENT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'LAST_DEMAND_PAYMENT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'OUTGOING_SMS'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'INCOMING_PHONE_CALL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'OUTGOING_EMAIL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" =
                                                                                                                                  'FAILED_PHONE_CALL_ATTEMPT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'INCOMING_EMAIL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'INCOMING_SMS'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'COMMENT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_1'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'DATA_UPDATE'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'OUTGOING_PHONE_CALL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'FIRST_DEMAND_PAYMENT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'LAST_DEMAND_PAYMENT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'OUTGOING_SMS'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE
                                                                                                                              "key" = 'INCOMING_PHONE_CALL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'OUTGOING_EMAIL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" =
                                                                                                                                  'FAILED_PHONE_CALL_ATTEMPT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'INCOMING_EMAIL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'INCOMING_SMS'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'COMMENT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id) VALUES ((SELECT id
                                                                                FROM issue_type
                                                                                WHERE "name" = 'PHONE_DEBT_COLLECTION_2'), (SELECT id
                                                                                                                            FROM activity_type
                                                                                                                            WHERE "key" = 'DATA_UPDATE'));

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_add_issue_typ_activity_type', 'mhorowic', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 162, '7:aca1e9d30ca25b22f489c5d0e028785d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::004_alter_invoice_add_exchange_rate::mhorowic
ALTER TABLE invoice ADD COLUMN exchange_rate NUMERIC(10, 4);

ALTER TABLE invoice_aud ADD COLUMN exchange_rate NUMERIC(10, 4);

COMMENT ON COLUMN invoice.exchange_rate IS 'Kurs wymiany waluty';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_alter_invoice_add_exchange_rate', 'mhorowic', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 163, '7:14891c81a42b3a5aa5ef2a8cebebef60', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::002_update_segments.sql::pnaroznik
update ISSUE_TYPE_CONFIGURATION
set segment = 'C'
where segment = 'B';

update ISSUE_TYPE_CONFIGURATION
set segment = 'B'
where segment = 'A';

update ISSUE_TYPE_CONFIGURATION
set segment = 'A'
where segment = 'C';

update COMPANY
set segment = 'C'
where segment = 'B';

update COMPANY
set segment = 'B'
where segment = 'A';

update COMPANY
set segment = 'A'
where segment = 'C';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_update_segments.sql', 'pnaroznik', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 164, '7:6b3187316e4b399de07d6ad5b0e0d4ce', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::005_alter_issue_add_total_balance::drackowski
ALTER TABLE issue ADD COLUMN total_balance_start_pln NUMERIC(18, 2);

ALTER TABLE issue ADD COLUMN total_current_balance_pln NUMERIC(18, 2);

ALTER TABLE issue ADD COLUMN total_payments_pln NUMERIC(18, 2);

ALTER TABLE issue_aud ADD COLUMN total_balance_start_pln NUMERIC(18, 2);

ALTER TABLE issue_aud ADD COLUMN total_current_balance_pln NUMERIC(18, 2);

ALTER TABLE issue_aud ADD COLUMN total_payments_pln NUMERIC(18, 2);

COMMENT ON COLUMN issue.total_balance_start_pln IS 'Całkowite saldo zlecenia w chwili rozpoczęcia (PLN)';

COMMENT ON COLUMN issue.total_current_balance_pln IS 'Całkowite saldo dokumentów, które są przedmiotem zlecenia (PLN)';

COMMENT ON COLUMN issue.total_payments_pln IS 'Całkowita suma dokonanych wpłat (PLN)';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_alter_issue_add_total_balance', 'drackowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 165, '7:194ca5e7405cb29a0c967cf5d0cc4248', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::003_update_issue_total_balance::drackowski
UPDATE issue
SET total_balance_start_pln = 0, total_current_balance_pln = 0, total_payments_pln = 0;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_update_issue_total_balance', 'drackowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 166, '7:4aab706520989ef94a9b0704174e013a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::004_add_default_direct_debt_collection_activities::mhorowic
INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Wizyta terenowa', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'FIELD_VISIT');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Wypowiedzenie warunkowe umowy', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'CONTRACT_TERMINATION');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Wezwanie do zwrotu przedmiotu', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'RETURN_SUBJECT_DEMAND');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Podejrzenie fraudu', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'FRAUD_SUSPECTED');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Odbiór przedmiotu leasingu', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'SUBJECT_COLLECTED');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Potwierdzenie wpłaty klienta', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'PAYMENT_COLLECTED_CONFIRMATION');

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'FIELD_VISIT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'CONTRACT_TERMINATION'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'RETURN_SUBJECT_DEMAND'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'FRAUD_SUSPECTED'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'SUBJECT_COLLECTED'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'PAYMENT_COLLECTED_CONFIRMATION'));

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_add_default_direct_debt_collection_activities', 'mhorowic', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 167, '7:4751a8ab3c911d9b4490b654134a4034', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::005_add_direct_debt_collection_issue_type_operator_type::mhorowic
INSERT INTO OPERATOR_TYPE_ISSUE_TYPE (operator_type_id, issue_type_id) VALUES

  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'FIELD_DEBT_COLLECTOR'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'DIRECT_DEBT_COLLECTION')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'DIRECT_DEBT_COLLECTION_MANAGER'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'DIRECT_DEBT_COLLECTION'));

UPDATE operator_type
SET parent_operator_type_id = (SELECT o2.id
                               FROM operator_type o2
                               WHERE o2.type_name = 'DIRECT_DEBT_COLLECTION_MANAGER')
WHERE type_name = 'FIELD_DEBT_COLLECTOR';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_add_direct_debt_collection_issue_type_operator_type', 'mhorowic', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 168, '7:37df19c98e30919aa83baa64bb1c38bc', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::006_alter_activity_type_has_detail_property::astepnowski
ALTER TABLE activity_type_has_detail_property ADD COLUMN id BIGSERIAL;

ALTER TABLE activity_type_has_detail_property ADD COLUMN state VARCHAR(100) NOT NULL DEFAULT 'EXECUTED';

ALTER TABLE activity_type_has_detail_property ADD COLUMN required BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE activity_type_has_detail_property DROP CONSTRAINT idx_activity_detail_schema;

ALTER TABLE activity_type_has_detail_property ADD CONSTRAINT pk_activity_type_detail_property PRIMARY KEY (id);

ALTER TABLE activity_type_has_detail_property ADD CONSTRAINT idx_activity_type_detail_property UNIQUE (activity_type_id, activity_detail_property_id, state);

COMMENT ON COLUMN activity_type_has_detail_property.state IS 'Stan w odniesieniu do cech dodatkowych dla typu czynności';

COMMENT ON COLUMN activity_type_has_detail_property.required IS 'Czy wymagane w odniesieniu do cech dodatkowych dla typu czynności';

ALTER TABLE activity_type_has_detail_property_aud ADD COLUMN id BIGSERIAL;

ALTER TABLE activity_type_has_detail_property_aud ADD COLUMN state VARCHAR(100);

ALTER TABLE activity_type_has_detail_property_aud ADD COLUMN required BOOLEAN;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_alter_activity_type_has_detail_property', 'astepnowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 169, '7:d30196b375a0cd664343e91e3d786cf3', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::007_alter_issue_add_dpd_start_date::tsliwinski
ALTER TABLE issue
  ADD COLUMN dpd_start_date TIMESTAMP;

ALTER TABLE issue_aud
  ADD COLUMN dpd_start_date TIMESTAMP;

COMMENT ON COLUMN issue.dpd_start_date
IS 'Data, od której jest wyliczane DPD rozpoczęcia dla zlecenia';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_alter_issue_add_dpd_start_date', 'tsliwinski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 170, '7:fff0bd44d5e7fdcea29ccd22d272eb1f', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::006_default_issue_activity_phone_debt_collection_2::drackowski
INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_2'),
  (SELECT id
   FROM activity_type
   WHERE key = 'OUTGOING_PHONE_CALL'),
  0, 1, current_timestamp);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_default_issue_activity_phone_debt_collection_2', 'drackowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 171, '7:cce410a1a333b7649e07fdb48c814660', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::008_alter_activity::astepnowski
INSERT INTO activity_detail_property (property_name, property_type, property_key) VALUES ('Komentarz', 'java.lang.String', 'REMARK');

INSERT INTO activity_detail (activity_detail_property_id, activity_id, property_value)
SELECT (SELECT id FROM activity_detail_property WHERE property_key = 'REMARK'), id, remark FROM activity WHERE remark IS NOT NULL;

ALTER TABLE ACTIVITY DROP COLUMN REMARK;

DELETE FROM activity_type_has_detail_property where activity_detail_property_id in
(SELECT id FROM activity_detail_property WHERE property_name = 'Typ wezwania (podstawowe)' OR property_name = 'Typ wezwania (ostateczne)');

DELETE FROM activity_detail where activity_detail_property_id in
(SELECT id FROM activity_detail_property WHERE property_key in ('BASIC_CALL_TYPE', 'FINAL_CALL_TYPE'));

DELETE FROM activity_detail_property WHERE property_name = 'Typ wezwania (podstawowe)';

DELETE FROM activity_detail_property WHERE property_name = 'Typ wezwania (ostateczne)';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('008_alter_activity', 'astepnowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 172, '7:7ee6d2033a560b204488149a130d5ace', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::009_alter_company_add_short_name::drackowski
ALTER TABLE COMPANY
  ADD COLUMN short_name VARCHAR(30);

ALTER TABLE COMPANY_AUD
  ADD COLUMN short_name VARCHAR(30);

COMMENT ON COLUMN COMPANY.short_name
IS 'Skrócona nazwa klienta';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('009_alter_company_add_short_name', 'drackowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 173, '7:1bdf267b8a33a4df9823b06577d57eef', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::007_update_issue_dpd_start_date::tsliwinski
--------------------------------------------------------------------------------------------
-- update daty dpd start zlecenia dla zleceń utworzonych automatycznie
--  * dla nowych zleceń DPD start date będzie wynosił 5, gdyż z założenia będzie to najstarszy dłużny dokument spełniający warunek progu kwoty (#25363)
--  * dla istniejących danych wyszukujemy najstarszy dokument spełniający warunek kwoty i jego datę płatności przyjmujemy za DPD start date dla zlecenia
--------------------------------------------------------------------------------------------
update issue isu
set dpd_start_date = (
  select min(i.due_date)
  from issue_invoice ii, invoice i
  where
    ii.invoice_id = i.id
    and
    ii.issue_id = isu.id
    and
    ii.excluded = false
    and
    ii.issue_subject = true
    and
    ((i.currency = 'PLN' and i.current_balance < -100) or (i.currency = 'EUR' and i.current_balance * i.exchange_rate < -100))
)
where isu.created_manually = false and isu.dpd_start_date is null;

--------------------------------------------------------------------------------------------
-- update daty dpd start zlecenia dla zleceń utworzonych ręcznie
--  * dla zleceń utworzonych ręcznie ustawiamy datę płatności najstarszego zaległego dokumentu [saldo <0] lub null jeśli nie ma zaległych dokumentów
--------------------------------------------------------------------------------------------
update issue isu
set dpd_start_date = (
  select min(i.due_date)
  from issue_invoice ii, invoice i
  where
    ii.invoice_id = i.id
    and
    ii.issue_id = isu.id
    and
    ii.excluded = false
    and
    ii.issue_subject = true
    and
    i.current_balance < 0
)
where isu.created_manually = true and isu.dpd_start_date is null;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_update_issue_dpd_start_date', 'tsliwinski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 174, '7:da73eb10e297e3df846c0096f641012e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::010_create_activity_remark_view::pnaroznik
create view activity_remark_view AS
  select
    ad.activity_id,
    ad.property_value as remark
  from activity_detail ad inner join activity_detail_property adp on ad.activity_detail_property_id = adp.id
  where adp.property_key = 'REMARK';

COMMENT ON VIEW activity_remark_view
IS 'Widok przechowujący komentarze czynności windykacyjnych';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('010_create_activity_remark_view', 'pnaroznik', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 175, '7:66930070236e62a39ebea1396b1c2e32', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::011_activity_type_has_detail_property_configuration::pnaroznik
--aktualizacja danych klienta
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='DATA_UPDATE'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);

--komentarz
update activity_type set can_be_planned=true where "key" = 'COMMENT';

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='COMMENT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='COMMENT'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', true);

--sms przychodzący

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='INCOMING_SMS'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--email przychodzący
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='EMAIL_ADDRESS' );

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='INCOMING_EMAIL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--nieudana próba kontaktu
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_CALL_PERSON' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_LEFT' );

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--sms wychodzący
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='EMAIL_ADDRESS' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='MESSAGE_CONTENT'), 'PLANNED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='EMAIL_ADDRESS'), 'PLANNED', false);

-- tel przychodzący

-- rozmówca wymagany
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_CALL_PERSON' );

-- Czy pozostawiono wiadomość
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='INCOMING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_LEFT' );

-- Nr tel
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='INCOMING_PHONE_CALL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);

-- sms wychodzący
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

--rozmówca
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='PHONE_CALL_PERSON'), 'PLANNED', false);

--nr tel
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='PHONE_NUMBER'), 'PLANNED', false);

--Treść wiadomości
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='MESSAGE_CONTENT'), 'PLANNED', false);

-- ostateczne wezwanie do zapłaty

--data płatności
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='LAST_DEMAND_PAYMENT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='DATE_OF_PAYMENT' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='LAST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='LAST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

-- pierwsze wezwanie do zapłaty

--data płatności
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='FIRST_DEMAND_PAYMENT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='DATE_OF_PAYMENT' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='FIRST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='FIRST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

--phone call wykonana

-- rozmówca wymagany
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_CALL_PERSON' );

-- Czy pozostawiono wiadomość
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='OUTGOING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_LEFT' );

-- Nr tel
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);

--phone call planowana

--rozmówca
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='PHONE_CALL_PERSON'), 'PLANNED', false);

--nr tel
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='PHONE_NUMBER'), 'PLANNED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('011_activity_type_has_detail_property_configuration', 'pnaroznik', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 176, '7:fbcde5169dbb135a70018c6f04e5358e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::008_alter_column_activity_creation_date_to_timestamp::mhorowic
ALTER TABLE activity
  ALTER COLUMN creation_date SET DATA TYPE TIMESTAMP;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('008_alter_column_activity_creation_date_to_timestamp', 'mhorowic', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 177, '7:d9c8284df65c0e4873192f7909e48e28', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Changeset src/main/dbschema/1.6.0/db.changelog.xml::012_activity_detail_drop_not_null_property_value::drackowski
ALTER TABLE public.activity_detail
  ALTER COLUMN property_value DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('012_activity_detail_drop_not_null_property_value', 'drackowski', 'src/main/dbschema/1.6.0/db.changelog.xml', NOW(), 178, '7:7af5f15ac0a34deceff06550c715da38', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0542768406');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

