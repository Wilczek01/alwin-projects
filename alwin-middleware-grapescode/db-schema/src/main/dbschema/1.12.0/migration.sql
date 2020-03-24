-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.12.0/db.changelog.xml
-- Ran at: 07.09.18 13:30
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.11 (192.168.0.11)', LOCKGRANTED = '2018-09-07 13:30:49.647' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::001_insert_default_issue_activity::astepnowski
INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'DIRECT_DEBT_COLLECTION'),
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  2, 1, current_timestamp);

UPDATE activity_type SET can_be_planned = true WHERE key = 'FIELD_VISIT';

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'REMARK'),
   'PLANNED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'REMARK'),
   'EXECUTED'
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_insert_default_issue_activity', 'astepnowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 176, '7:2487867ab98a8c50b786e9fa701b151a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::001_create_contract_termination::drackowski
CREATE TABLE contract_termination (
  id                                            BIGSERIAL    NOT NULL,
  contract_number                               VARCHAR(255) NOT NULL,
  termination_date                              TIMESTAMP    NOT NULL,
  type                                          VARCHAR(50)  NOT NULL,
  state                                         VARCHAR(100) NOT NULL,
  generated_by                                  VARCHAR(200) NOT NULL,
  operator_phone_number                         VARCHAR(30)  NOT NULL,
  operator_email                                VARCHAR(512) NOT NULL,
  company_id                                    BIGINT,
  company_name                                  VARCHAR(200),
  company_address_id                            BIGINT,
  company_mail_address_id                       BIGINT,
  resumption_cost                               NUMERIC(18, 2),
  due_date_in_demand_for_payment                TIMESTAMP,
  amount_in_demand_for_payment                  NUMERIC(18, 2),
  total_payments_since_demand_for_payment       NUMERIC(18, 2),
  invoice_number_initiating_demand_for_payment  VARCHAR(255),
  invoice_amount_initiating_demand_for_payment  NUMERIC(18, 2),
  invoice_balance_initiating_demand_for_payment NUMERIC(18, 2),
  remark                                        VARCHAR(255),
  CONSTRAINT pk_contract_termination PRIMARY KEY (id)
);

COMMENT ON TABLE contract_termination
IS 'Wypowiedzenia umów';

COMMENT ON COLUMN contract_termination.id
IS 'Identyfikator wypowiedzenia umowy';

COMMENT ON COLUMN contract_termination.contract_number
IS 'Nr umowy';

COMMENT ON COLUMN contract_termination.termination_date
IS 'Data wypowiedzenia';

COMMENT ON COLUMN contract_termination.type
IS 'Typ wypowiedzenia';

COMMENT ON COLUMN contract_termination.generated_by
IS 'Imię i nazwisko operatora generującego dokument';

COMMENT ON COLUMN contract_termination.operator_phone_number
IS 'Nr telefonu operatora obecnie obsługującego klienta';

COMMENT ON COLUMN contract_termination.operator_email
IS 'Email operatora obecnie obsługującego klienta';

COMMENT ON COLUMN contract_termination.company_id
IS 'Nr klienta (z LEO)';

COMMENT ON COLUMN contract_termination.company_name
IS 'Nazwa klienta';

COMMENT ON COLUMN contract_termination.company_address_id
IS 'Adres siedziby';

COMMENT ON COLUMN contract_termination.company_mail_address_id
IS 'Adres korespondencyjny';

COMMENT ON COLUMN contract_termination.resumption_cost
IS 'Wysokość opłaty za wznowienie';

COMMENT ON COLUMN contract_termination.due_date_in_demand_for_payment
IS 'Termin wskazany do zapłaty na wezwaniu';

COMMENT ON COLUMN contract_termination.amount_in_demand_for_payment
IS 'Kwota wskazana do spłaty na wezwaniu';

COMMENT ON COLUMN contract_termination.total_payments_since_demand_for_payment
IS 'Suma wpłat dokonanych od wezwania';

COMMENT ON COLUMN contract_termination.invoice_number_initiating_demand_for_payment
IS 'Nr FV inicjującej wezwanie do zapłaty';

COMMENT ON COLUMN contract_termination.invoice_amount_initiating_demand_for_payment
IS 'Kwota FV inicjującej wystawienie wezwania';

COMMENT ON COLUMN contract_termination.invoice_balance_initiating_demand_for_payment
IS 'Saldo FV inicjującej wystawienie wezwania';

CREATE TABLE contract_termination_aud (
  id                                            BIGSERIAL NOT NULl,
  rev                                           INTEGER   NOT NULL,
  revtype                                       SMALLINT,
  contract_number                               VARCHAR(255),
  termination_date                              TIMESTAMP,
  type                                          VARCHAR(50),
  generated_by                                  VARCHAR(200),
  operator_phone_number                         VARCHAR(30),
  operator_email                                VARCHAR(512),
  company_id                                    BIGINT,
  company_name                                  VARCHAR(200),
  company_address_id                            BIGINT,
  company_mail_address_id                       BIGINT,
  resumption_cost                               NUMERIC(18, 2),
  due_date_in_demand_for_payment                TIMESTAMP,
  amount_in_demand_for_payment                  NUMERIC(18, 2),
  total_payments_since_demand_for_payment       NUMERIC(18, 2),
  invoice_number_initiating_demand_for_payment  VARCHAR(255),
  invoice_amount_initiating_demand_for_payment  NUMERIC(18, 2),
  invoice_balance_initiating_demand_for_payment NUMERIC(18, 2),
  CONSTRAINT pk_contract_termination_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE contract_termination_aud
IS 'Tabela audytowa wypowiedzeń umów';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_create_contract_termination', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 177, '7:0d0859a6dddcb6a3cfb322467fcc5256', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::002_create_contract_termination_subject::drackowski
CREATE TABLE contract_termination_subject (
  id                      BIGSERIAL NOT NULL,
  contract_termination_id BIGINT    NOT NULL,
  subject_id              BIGINT    NOT NULL,
  gps_to_install          BOOLEAN   NOT NULL,
  gps_installation_charge NUMERIC(18, 2),
  CONSTRAINT pk_contract_termination_subject PRIMARY KEY (id),
  CONSTRAINT fk_contract_termination FOREIGN KEY (contract_termination_id) REFERENCES contract_termination (id)
);

COMMENT ON TABLE contract_termination_subject
IS 'Przedmioty leasingu dla wypowiedzeń umów';

COMMENT ON COLUMN contract_termination_subject.id
IS 'Identyfikator przedmiotu leasingu w wypowiedzeniu umowy';

COMMENT ON COLUMN contract_termination_subject.subject_id
IS 'Przedmiot leasingu';

COMMENT ON COLUMN contract_termination_subject.gps_to_install
IS 'Czy ma być zainstalowany GPS?';

COMMENT ON COLUMN contract_termination_subject.gps_installation_charge
IS 'Koszt instalacji GPS';

CREATE TABLE contract_termination_subject_aud (
  id                      BIGSERIAL,
  rev                     INTEGER,
  revtype                 SMALLINT,
  subject_id              BIGINT,
  gps_to_install          BOOLEAN,
  gps_installation_charge BIGINT,
  CONSTRAINT pk_contract_termination_subject_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE contract_termination_subject_aud
IS 'Tabela audytowa przedmiotów leasingu dla wypowiedzeń umów';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_create_contract_termination_subject', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 178, '7:cead0427f7cdec5b071aa3f44d2229fd', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::003_create_formal_debt_collection_invoice::drackowski
CREATE TABLE formal_debt_collection_invoice (
  id              BIGSERIAL      NOT NULL,
  invoice_number  VARCHAR(255)   NOT NULL,
  contract_number VARCHAR(255)   NOT NULL,
  issue_date      TIMESTAMP      NOT NULL,
  due_date        TIMESTAMP      NOT NULL,
  currency        VARCHAR(5)     NOT NULL,
  net_amount      NUMERIC(18, 2) NOT NULL,
  gross_amount    NUMERIC(18, 2) NOT NULL,
  current_balance NUMERIC(18, 2) NOT NULL,
  CONSTRAINT pk_formal_debt_collection_invoice PRIMARY KEY (id),
  CONSTRAINT formal_debt_collection_invoice_invoice_for_contract UNIQUE (invoice_number, contract_number)
);

COMMENT ON TABLE formal_debt_collection_invoice
IS 'Faktury w procesie windykacji formalnej';

COMMENT ON COLUMN formal_debt_collection_invoice.id
IS 'Identyfikator faktury w procesie windykacji formalnej';

COMMENT ON COLUMN formal_debt_collection_invoice.invoice_number
IS 'Nr faktury';

COMMENT ON COLUMN formal_debt_collection_invoice.contract_number
IS 'Nr umowy';

COMMENT ON COLUMN formal_debt_collection_invoice.issue_date
IS 'Data wystawienia';

COMMENT ON COLUMN formal_debt_collection_invoice.due_date
IS 'Termin płatności';

COMMENT ON COLUMN formal_debt_collection_invoice.currency
IS 'Waluta';

COMMENT ON COLUMN formal_debt_collection_invoice.net_amount
IS 'Kwota netto dokumentu';

COMMENT ON COLUMN formal_debt_collection_invoice.gross_amount
IS 'Kwota brutto dokumentu';

COMMENT ON COLUMN formal_debt_collection_invoice.current_balance
IS 'Saldo dokumentu';

CREATE TABLE formal_debt_collection_invoice_aud (
  id              BIGSERIAL      NOT NULl,
  rev             INTEGER        NOT NULL,
  revtype         SMALLINT,
  invoice_number  VARCHAR(255)   NOT NULL,
  contract_number VARCHAR(255)   NOT NULL,
  issue_date      TIMESTAMP      NOT NULL,
  due_date        TIMESTAMP      NOT NULL,
  currency        VARCHAR(5)     NOT NULL,
  net_amount      NUMERIC(18, 2) NOT NULL,
  gross_amount    NUMERIC(18, 2) NOT NULL,
  current_balance NUMERIC(18, 2) NOT NULL,
  CONSTRAINT pk_formal_debt_collection_invoice_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE formal_debt_collection_invoice_aud
IS 'Tabela audytowa dla faktur w procesie windykacji formalnej';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_create_formal_debt_collection_invoice', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 179, '7:57bb7b014683d70b301ec89299012869', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::004_insert_default_issue_activity::mhorowic
ALTER TABLE demand_for_payment
  ADD COLUMN processing_message TEXT;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_insert_default_issue_activity', 'mhorowic', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 180, '7:af0f1af7d86a0c3cb4a6594651ba5fed', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::005_create_contract_termination_invoice::drackowski
CREATE TABLE contract_termination_invoice (
  contract_termination_id           BIGINT NOT NULL,
  formal_debt_collection_invoice_id BIGINT NOT NULL,
  CONSTRAINT pk_contract_termination_invoice PRIMARY KEY (contract_termination_id, formal_debt_collection_invoice_id),
  CONSTRAINT fk_formal_debt_collection_invoice_invoice FOREIGN KEY (formal_debt_collection_invoice_id) REFERENCES formal_debt_collection_invoice (id),
  CONSTRAINT fk_contract_termination_invoice FOREIGN KEY (contract_termination_id) REFERENCES contract_termination (id)
);

COMMENT ON TABLE contract_termination_invoice
IS 'Tabela łącząca wypowiedzenia umów z fakturami w procesie windykacji formalnej';

CREATE TABLE contract_termination_invoice_aud (
  rev                               INTEGER,
  revtype                           SMALLINT,
  contract_termination_id           BIGINT,
  formal_debt_collection_invoice_id BIGINT,
  CONSTRAINT pk_contract_termination_invoice_aud PRIMARY KEY (contract_termination_id, formal_debt_collection_invoice_id, rev)
);

COMMENT ON TABLE contract_termination_invoice_aud
IS 'Tabela audytowa dla połączeń wypowiedzeń umów z fakturami w procesie windykacji formalnej';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_create_contract_termination_invoice', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 181, '7:b5cee4a2dc879e6ddb4650bbd9e342a9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::006_create_demand_for_payment_invoice::tsliwinski
-- łączenie wezwań z fakturami

-- usunięcie starej tabeli
DROP TABLE demand_for_payment_invoices;

CREATE TABLE demand_for_payment_invoice (
  demand_for_payment_id             BIGINT NOT NULL,
  formal_debt_collection_invoice_id BIGINT NOT NULL,
  CONSTRAINT pk_demand_for_payment_invoice PRIMARY KEY (demand_for_payment_id, formal_debt_collection_invoice_id),
  CONSTRAINT fk_demand_for_payment_invoice_invoice FOREIGN KEY (formal_debt_collection_invoice_id) REFERENCES formal_debt_collection_invoice (id),
  CONSTRAINT fk_demand_for_payment_invoice_demand_for_payment FOREIGN KEY (demand_for_payment_id) REFERENCES demand_for_payment (id)
);

COMMENT ON TABLE demand_for_payment_invoice
IS 'Tabela łącząca wezwania do zapłaty z fakturami w procesie windykacji formalnej';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_create_demand_for_payment_invoice', 'tsliwinski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 182, '7:831063cc097a246add85da39816bcc45', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::007_demand_for_payment_type_rename::tsliwinski
-- zmiana nazwy tabeli demand_for_payment_type na demand_for_payment_type_configuration

alter table demand_for_payment
  drop constraint fk_demand_for_payment_type;

alter table demand_for_payment
  rename demand_for_payment_type_id to demand_for_payment_type_configuration_id;

alter table demand_for_payment_type
  rename to demand_for_payment_type_configuration;

alter table demand_for_payment
  add CONSTRAINT fk_demand_for_payment_type_configuration FOREIGN KEY (demand_for_payment_type_configuration_id) REFERENCES demand_for_payment_type_configuration (id);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_demand_for_payment_type_rename', 'tsliwinski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 183, '7:25dc895e68f20ca1a909500557e5922d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::008_add_leo_id_to_formal_debt_collection_invoice::mhorowic
ALTER TABLE formal_debt_collection_invoice
  ADD COLUMN leo_id BIGINT NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud
  ADD COLUMN leo_id BIGINT;

COMMENT ON COLUMN formal_debt_collection_invoice.leo_id
IS 'Id faktury z leo';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('008_add_leo_id_to_formal_debt_collection_invoice', 'mhorowic', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 184, '7:a5a4b84f2d351e1b9745c3768602ee66', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::009_alter_formal_debt_collection_invoice_drop_not_nulls::drackowski
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN invoice_number DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN contract_number DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN issue_date DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN due_date DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN currency DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN net_amount DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN gross_amount DROP NOT NULL;

ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN current_balance DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('009_alter_formal_debt_collection_invoice_drop_not_nulls', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 185, '7:9112f0accb1329ed409601cff0114b3f', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::002_insert_demand_for_payment_type_configuration::tsliwinski
-- wezwanie 1, segment A
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('FIRST', 7, 3, 50.00, 'A', -100.00);

-- wezwanie 2, segment A
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('SECOND', 15, 3, 99.00, 'A', -100.00);

-- wezwanie 1, segment B
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('FIRST', 7, 3, 50.00, 'B', -100.00);

-- wezwanie 2, segment B
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('SECOND', 15, 3, 99.00, 'B', -100.00);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_insert_demand_for_payment_type_configuration', 'tsliwinski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 186, '7:168466e70d98edac94b284d3dce2217c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::010_alter_contract_termination_add_index::drackowski
CREATE INDEX idx_contract_termination_company_id_termination_date_state
  ON contract_termination(company_id, termination_date, state);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('010_alter_contract_termination_add_index', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 187, '7:852335c008e372dec09d2396dab09073', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::011_drop_formal_debt_collection_invoice_invoice_for_contract_constraint::tsliwinski
alter table formal_debt_collection_invoice
  drop constraint formal_debt_collection_invoice_invoice_for_contract;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('011_drop_formal_debt_collection_invoice_invoice_for_contract_constraint', 'tsliwinski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 188, '7:a10312b773c141aa189b4bb03b08d7d2', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::012_alter_contract_termination_rename_mail_address::drackowski
ALTER TABLE contract_termination
  RENAME COLUMN company_mail_address_id TO company_correspondence_address_id;

ALTER TABLE contract_termination_aud
  RENAME COLUMN company_mail_address_id TO company_correspondence_address_id;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('012_alter_contract_termination_rename_mail_address', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 189, '7:8ff0c03db6ba4ebef7797914a3df674f', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::003_change_generate_demand_for_payment_scheduler_job_name::tsliwinski
update scheduler_execution set type = 'GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION' where type = 'GENERATE_DEMAND_FOR_PAYMENT';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_change_generate_demand_for_payment_scheduler_job_name', 'tsliwinski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 190, '7:40f9aeb8e238d911487a422618af532c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::013_alter_contract_termination::drackowski
ALTER TABLE contract_termination
  ALTER COLUMN generated_by DROP NOT NULL;

ALTER TABLE contract_termination
  ALTER COLUMN operator_phone_number DROP NOT NULL;

ALTER TABLE contract_termination
  ALTER COLUMN operator_email DROP NOT NULL;

ALTER TABLE contract_termination_aud
  ADD COLUMN state VARCHAR(100);

ALTER TABLE contract_termination_aud
  ADD COLUMN remark VARCHAR(255);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('013_alter_contract_termination', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 191, '7:185347b86db086eeb1848603d5f66648', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::014_create_contract_termination_attachment_table::drackowski
CREATE TABLE contract_termination_attachment (
  id        BIGSERIAL    NOT NULL,
  reference VARCHAR(255) NOT NULL,
  CONSTRAINT pk_contract_termination_attachment PRIMARY KEY (id)
);

COMMENT ON TABLE contract_termination_attachment
IS 'Dokumenty wypowiedzenia umowy';

COMMENT ON COLUMN contract_termination_attachment.id
IS 'Identyfikator dokumentu wypowiedzenia umowy';

COMMENT ON COLUMN contract_termination_attachment.reference
IS 'Referencja do dokumentu';

CREATE TABLE contract_termination_attachment_aud (
  id        BIGSERIAL NOT NULl,
  rev       INTEGER   NOT NULL,
  revtype   SMALLINT,
  reference VARCHAR(255),
  CONSTRAINT pk_contract_termination_attachment_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE contract_termination_attachment_aud
IS 'Tabela audytowa dla dokumentów wypowiedzeń umów';

CREATE TABLE contract_termination_has_attachment (
  contract_termination_id            BIGINT NOT NULL,
  contract_termination_attachment_id BIGINT NOT NULL,
  CONSTRAINT pk_contract_termination_has_attachment PRIMARY KEY (contract_termination_id, contract_termination_attachment_id),
  CONSTRAINT fk_contract_termination_invoice FOREIGN KEY (contract_termination_id) REFERENCES contract_termination (id),
  CONSTRAINT fk_contract_termination_attachment FOREIGN KEY (contract_termination_attachment_id) REFERENCES contract_termination_attachment (id)
);

COMMENT ON TABLE contract_termination_has_attachment
IS 'Tabela łącząca wypowiedzenia umów z dokumentami wypowiedzeń';

CREATE TABLE contract_termination_has_attachment_aud (
  rev                                INTEGER,
  revtype                            SMALLINT,
  contract_termination_id            BIGINT NOT NULL,
  contract_termination_attachment_id BIGINT NOT NULL,
  CONSTRAINT pk_contract_termination_has_attachment_aud PRIMARY KEY (contract_termination_id, contract_termination_attachment_id, rev)
);

COMMENT ON TABLE contract_termination_has_attachment_aud
IS 'Tabela audytowa dla wypowiedzeń umów z dokumentami wypowiedzeń';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('014_create_contract_termination_attachment_table', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 192, '7:fe7714015ad6104d1d0e272cd935e07e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::002_insert_activity_detail_property::astepnowski
INSERT INTO activity_detail_property (property_name, property_type, property_key) VALUES ('Czy zastano klienta', 'java.lang.Boolean', 'CUSTOMER_WAS_PRESENT');

INSERT INTO activity_detail_property (property_name, property_type, property_key) VALUES ('Zdjęcie', 'java.lang.String', 'ATTACHMENT');

UPDATE activity_type SET may_have_declaration = true WHERE key = 'FIELD_VISIT';

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'CUSTOMER_WAS_PRESENT'),
   'PLANNED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'CUSTOMER_WAS_PRESENT'),
   'EXECUTED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'ATTACHMENT'),
   'PLANNED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'ATTACHMENT'),
   'EXECUTED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'PHONE_CALL_PERSON'),
   'EXECUTED'
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_insert_activity_detail_property', 'astepnowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 193, '7:3f668a0eab96f82d48231d4878819e86', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::015_alter_contract_termination_add_processing_message::drackowski
ALTER TABLE contract_termination
  ADD COLUMN processing_message TEXT;

ALTER TABLE contract_termination_aud
  ADD COLUMN processing_message TEXT;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('015_alter_contract_termination_add_processing_message', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 194, '7:7d53d17c51f14a576d72868754e4c409', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::016_alter_table_contract_termination::mhorowic
ALTER TABLE contract_termination
  DROP COLUMN amount_in_demand_for_payment;

ALTER TABLE contract_termination_aud
  DROP COLUMN amount_in_demand_for_payment;

ALTER TABLE contract_termination
  ADD COLUMN amount_in_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination
  ADD COLUMN amount_in_demand_for_payment_eur NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN amount_in_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN amount_in_demand_for_payment_eur NUMERIC(18, 2);

COMMENT ON COLUMN contract_termination.amount_in_demand_for_payment_pln
IS 'Kwota wskazana do spłaty na wezwaniu PLN';

COMMENT ON COLUMN contract_termination.amount_in_demand_for_payment_eur
IS 'Kwota wskazana do spłaty na wezwaniu EUR';

ALTER TABLE contract_termination
  DROP COLUMN total_payments_since_demand_for_payment;

ALTER TABLE contract_termination_aud
  DROP COLUMN total_payments_since_demand_for_payment;

ALTER TABLE contract_termination
  ADD COLUMN total_payments_since_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination
  ADD COLUMN total_payments_since_demand_for_payment_eur NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN total_payments_since_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN total_payments_since_demand_for_payment_eur NUMERIC(18, 2);

COMMENT ON COLUMN contract_termination.total_payments_since_demand_for_payment_pln
IS 'Suma wpłat dokonanych od wezwania PLN';

COMMENT ON COLUMN contract_termination.total_payments_since_demand_for_payment_eur
IS 'Suma wpłat dokonanych od wezwania EUR';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('016_alter_table_contract_termination', 'mhorowic', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 195, '7:75ea6bcd8d9726b05f5c4e82b780cee0', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::017_alter_table_add_contract_type::drackowski
ALTER TABLE contract_termination
  ADD COLUMN contract_type VARCHAR(50);

ALTER TABLE contract_termination_aud
  ADD COLUMN contract_type VARCHAR(50);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('017_alter_table_add_contract_type', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 196, '7:4ad2c4d1e434f62936b0b6e7b19c9f3c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::018_alter_table_contract_termination_rename_company::drackowski
ALTER TABLE contract_termination
  RENAME COLUMN company_id TO ext_company_id;

ALTER TABLE contract_termination_aud
  RENAME COLUMN company_id TO ext_company_id;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('018_alter_table_contract_termination_rename_company', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 197, '7:9bb5677c412d367875be032bf9d3dc5d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Changeset src/main/dbschema/1.12.0/db.changelog.xml::019_alter_contract_termination_termination_date_and_index::drackowski
ALTER TABLE contract_termination
  ALTER COLUMN termination_date TYPE date;

ALTER TABLE contract_termination_aud
  ALTER COLUMN termination_date TYPE date;

DROP INDEX idx_contract_termination_company_id_termination_date_state;

CREATE INDEX idx_contract_termination_grouping_index
  ON contract_termination (ext_company_id, termination_date, state, type);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('019_alter_contract_termination_termination_date_and_index', 'drackowski', 'src/main/dbschema/1.12.0/db.changelog.xml', NOW(), 198, '7:ff17e53237fd6c4eaa6e96611732469d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '6319850278');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

