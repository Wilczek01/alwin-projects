-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.0.0/db.changelog.xml
-- Ran at: 13.03.18 15:52
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

CREATE TABLE databasechangeloglock (
  id int4 NOT NULL,
  locked bool NOT NULL,
  lockgranted timestamp NULL,
  lockedby varchar(255) NULL,
  PRIMARY KEY (id)
);

CREATE TABLE databasechangelog (
  id varchar(255) NOT NULL,
  author varchar(255) NOT NULL,
  filename varchar(255) NOT NULL,
  dateexecuted timestamp NOT NULL,
  orderexecuted int4 NOT NULL,
  exectype varchar(10) NOT NULL,
  md5sum varchar(35) NULL,
  description varchar(255) NULL,
  comments varchar(255) NULL,
  tag varchar(255) NULL,
  liquibase varchar(20) NULL,
  contexts varchar(255) NULL,
  labels varchar(255) NULL,
  deployment_id varchar(10) NULL
);

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.0.16 (192.168.0.16)', LOCKGRANTED = '2018-03-13 15:52:01.618' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::001_initial-schema::mhorowic
CREATE TABLE ALWIN_ROLE (
  id    BIGSERIAL    NOT NULL,
  name  VARCHAR(100) NOT NULL,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_alwin_role PRIMARY KEY (id),
  CONSTRAINT idx_alwin_role_name UNIQUE (name)
);

COMMENT ON TABLE ALWIN_ROLE IS 'role uzytkownikow';

COMMENT ON COLUMN ALWIN_ROLE.id IS 'identyfikator roli';

COMMENT ON COLUMN ALWIN_ROLE.name IS 'nazwa roli';

COMMENT ON COLUMN ALWIN_ROLE.label IS 'opis roli';

CREATE TABLE ALWIN_USER (
  id            BIGSERIAL                           NOT NULL,
  alwin_role_id BIGINT                              NOT NULL,
  first_name    VARCHAR(100)                        NOT NULL,
  last_name     VARCHAR(100)                        NOT NULL,
  login         VARCHAR(40)                         NOT NULL,
  email         VARCHAR(512)                        NOT NULL,
  "password"    VARCHAR(255)                        NOT NULL,
  salt          VARCHAR(255)                        NOT NULL,
  status        VARCHAR(40)                         NOT NULL,
  creation_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  update_date   TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_alwin_user PRIMARY KEY (id),
  CONSTRAINT idx_alwin_user_login UNIQUE (login),
  CONSTRAINT fk_alwin_user_alwin_role FOREIGN KEY (alwin_role_id) REFERENCES ALWIN_ROLE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_alwin_user_role
  ON ALWIN_USER (alwin_role_id);

COMMENT ON TABLE ALWIN_USER IS 'uzytkownicy systemu';

COMMENT ON COLUMN ALWIN_USER.id IS 'identyfikator uzytkownika';

COMMENT ON COLUMN ALWIN_USER.alwin_role_id IS 'identyfikator roli uzytkownika';

COMMENT ON COLUMN ALWIN_USER.first_name IS 'imie uzytkownika';

COMMENT ON COLUMN ALWIN_USER.last_name IS 'nazwisko uzytkownika';

COMMENT ON COLUMN ALWIN_USER.login IS 'login uzytkownika';

COMMENT ON COLUMN ALWIN_USER.email IS 'e-mail uzytkownika';

COMMENT ON COLUMN ALWIN_USER."password" IS 'haslo uzytkownika';

COMMENT ON COLUMN ALWIN_USER.salt IS 'sol do hasla uzytkownika';

COMMENT ON COLUMN ALWIN_USER.status IS 'status konta uzytkownika';

COMMENT ON COLUMN ALWIN_USER.creation_date IS 'data utworzenia konta uzytkownika';

COMMENT ON COLUMN ALWIN_USER.update_date IS 'data aktualizacji konta uzytkownika';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_initial-schema', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 103, '7:8a19845981eeae64b73c1e41eb4d10a3', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::001_initial-roles-table::mhorowic
INSERT INTO ALWIN_ROLE (name, label) VALUES ('ADMIN', 'Administrator systemu');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('PHONE_DEBT_COLLECTOR', 'Windykator telefoniczny');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('FIELD_DEBT_COLLECTOR', 'Windykator terenowy');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('RESTRUCTURING_SPECIALIST', 'Specjalista ds. restrukturyzacji');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('RENUNCIATION_COORDINATOR', 'Koordynator wypowiedzeń');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('SECURITY_SPECIALIST', 'Specjalista ds. realizacji zabezpieczeń');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('PHONE_DEBT_COLLECTOR_MANAGER', 'Menedżer windykacji telefonicznej');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('DIRECT_DEBT_COLLECTION_MANAGER', 'Menedżer windykacji bezpośredniej');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('ANALYST', 'Analityk');

INSERT INTO ALWIN_ROLE (name, label) VALUES ('DEPARTMENT_MANAGER', 'Menedżer departamentu');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_initial-roles-table', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 104, '7:f7e935598cf56ea04593930a530d31f3', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::002_debt-collection-action::mhorowic
CREATE TABLE DEBT_COLLECTION_ACTION (
  id            BIGSERIAL                           NOT NULL,
  name          VARCHAR(100)                        NOT NULL,
  label         VARCHAR(100)                        NOT NULL,
  default_day   INTEGER                             NOT NULL,
  version       INTEGER                             NOT NULL,
  creating_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_debt_collection_action PRIMARY KEY (id)
);

COMMENT ON TABLE DEBT_COLLECTION_ACTION IS 'czynnosci windykacyjne';

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.id IS 'identyfikator czynnosci';

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.name IS 'nazwa czynnosci';

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.label IS 'opis czynnosci';

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.default_day IS 'standardowy dzien obslugi';

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.version IS 'wersja czynnosci';

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.creating_date IS 'data utworzenia podanej wersji czynnosci';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_debt-collection-action', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 105, '7:ae87b12d8ff028d895a53c4ac4f6dc44', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::002_debt-collection-actions::mhorowic
INSERT INTO DEBT_COLLECTION_ACTION (name, label, default_day, version, creating_date)
VALUES ('FIRST_PHONE_CALL_ATTEMPT', 'Próba kontaktu telefonicznego', 1, 1, current_timestamp);

INSERT INTO DEBT_COLLECTION_ACTION (name, label, default_day, version, creating_date)
VALUES ('PAYMENT_CALL', 'Wezwanie do zapłaty', 2, 1, current_timestamp);

INSERT INTO DEBT_COLLECTION_ACTION (name, label, default_day, version, creating_date)
VALUES ('SECOND_PHONE_CALL_ATTEMPT', 'Próba kontaktu telefonicznego', 7, 1, current_timestamp);

INSERT INTO DEBT_COLLECTION_ACTION (name, label, default_day, version, creating_date)
VALUES ('LAST_PAYMENT_CALL', 'Ostateczne wezwanie do zapłaty', 10, 1, current_timestamp);

INSERT INTO DEBT_COLLECTION_ACTION (name, label, default_day, version, creating_date)
VALUES ('THIRD_PHONE_CALL_ATTEMPT', 'Próba kontaktu telefonicznego', 11, 1, current_timestamp);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_debt-collection-actions', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 106, '7:31f78a3100786c78ad389b735af5cc67', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::003_customer::astepnowski
CREATE TABLE company (
  id           BIGSERIAL       NOT NULL,
  company_id   INTEGER      NOT NULL,
  company_name VARCHAR(200) NOT NULL,
  nip          VARCHAR(10)  NOT NULL,
  regon        VARCHAR(9)   NOT NULL,
  CONSTRAINT pk_company PRIMARY KEY (id)
);

COMMENT ON TABLE company IS 'Tabela firm';

COMMENT ON COLUMN company.company_id IS 'ID  kontrahenta w systemie AL';

COMMENT ON COLUMN company.company_name IS 'Nazwa firmy';

COMMENT ON COLUMN company.nip IS 'Nr nip';

COMMENT ON COLUMN company.regon IS 'Nr regon';

CREATE TABLE address (
  id           BIGSERIAL       NOT NULL,
  street       VARCHAR(100) NOT NULL,
  house_no     VARCHAR(10)  NOT NULL,
  flat_no      VARCHAR(10)  NOT NULL,
  post_code    VARCHAR(10)  NOT NULL,
  city         VARCHAR(100),
  country      VARCHAR(100) NOT NULL,
  address_type VARCHAR(100) NOT NULL,
  remark       TEXT,
  CONSTRAINT pk_adsdress PRIMARY KEY (id)
);

COMMENT ON TABLE address IS 'Tabela adresów';

COMMENT ON COLUMN address.street IS 'Ulica';

COMMENT ON COLUMN address.house_no IS 'Numer domu';

COMMENT ON COLUMN address.flat_no IS 'Nr lokalu';

COMMENT ON COLUMN address.post_code IS 'Kod pocztowy';

COMMENT ON COLUMN address.city IS 'Miasto';

COMMENT ON COLUMN address.country IS 'Państwo';

COMMENT ON COLUMN address.address_type IS 'Typ adresu: -Siedziby/zamieszkania -Korespondencyjny -Prowadzenia działalności -Inny';

COMMENT ON COLUMN address.remark IS 'Komentarz do adresu';

CREATE TABLE contact_detail (
  id           BIGSERIAL       NOT NULL,
  contact      VARCHAR(100) NOT NULL,
  contact_type VARCHAR(100) NOT NULL,
  is_active    BOOL         NOT NULL,
  CONSTRAINT pk_contact_detail PRIMARY KEY (id)
);

COMMENT ON TABLE contact_detail IS 'Tabela danych kontaktowych';

COMMENT ON COLUMN contact_detail.contact IS 'nr telefonu / adres email / nr gołębia pocztowego';

COMMENT ON COLUMN contact_detail.contact_type IS 'Rodzaj danej kontaktowej';

COMMENT ON COLUMN contact_detail.is_active IS 'Czy kontakt aktywny';

CREATE TABLE person (
  id         BIGSERIAL       NOT NULL,
  person_id  INTEGER      NOT NULL,
  pesel      NUMERIC(11)  NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name  VARCHAR(100) NOT NULL,
  CONSTRAINT pk_person PRIMARY KEY (id)
);

COMMENT ON COLUMN person.person_id IS 'ID  osoby w systemie AL';

COMMENT ON COLUMN person.pesel IS 'Nr pesel';

COMMENT ON COLUMN person.first_name IS 'Imię';

COMMENT ON COLUMN person.last_name IS 'Nazwisko';

CREATE TABLE person_address (
  person_id  BIGINT NOT NULL,
  address_id BIGINT NOT NULL,
  CONSTRAINT idx_person_address PRIMARY KEY (person_id, address_id),
  CONSTRAINT fk_person_address_person FOREIGN KEY (person_id) REFERENCES person (id),
  CONSTRAINT fk_person_address_address FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE INDEX idx_person_address_person
  ON person_address (person_id);

CREATE INDEX idx_person_address_address
  ON person_address (address_id);

CREATE TABLE person_contact_detail (
  person_id         BIGINT NOT NULL,
  contact_detail_id BIGINT NOT NULL,
  CONSTRAINT idx_person_contact_detail PRIMARY KEY (person_id, contact_detail_id),
  CONSTRAINT fk_person_contact_detail_contact_detail FOREIGN KEY (contact_detail_id) REFERENCES contact_detail (id),
  CONSTRAINT fk_person_contact_detail_person FOREIGN KEY (person_id) REFERENCES person (id)
);

CREATE INDEX idx_person_contact_detail_person
  ON person_contact_detail (person_id);

CREATE INDEX idx_person_contact_detail_contact_detail
  ON person_contact_detail (contact_detail_id);

CREATE TABLE company_person (
  company_id BIGINT NOT NULL,
  person_id  BIGINT NOT NULL,
  CONSTRAINT idx_company_person PRIMARY KEY (company_id, person_id),
  CONSTRAINT fk_company_person_company FOREIGN KEY (company_id) REFERENCES company (id),
  CONSTRAINT fk_company_person_person FOREIGN KEY (person_id) REFERENCES person (id)
);

CREATE INDEX idx_company_person_company
  ON company_person (company_id);

CREATE INDEX idx_company_person_person
  ON company_person (person_id);

COMMENT ON TABLE company_person IS 'Tabela wiazaca osoby z firma';

CREATE TABLE customer (
  id           BIGSERIAL NOT NULL,
  company_id   BIGINT,
  person_id    BIGINT,
  debt_segment VARCHAR(30),
  CONSTRAINT pk_customer PRIMARY KEY (id),
  CONSTRAINT fk_customer_company FOREIGN KEY (company_id) REFERENCES company (id),
  CONSTRAINT fk_customer_person FOREIGN KEY (person_id) REFERENCES person (id)
);

COMMENT ON COLUMN customer.debt_segment IS 'Segment klienta.';

CREATE INDEX idx_customer_company
  ON customer (company_id);

CREATE INDEX idx_customer_person
  ON customer (person_id);

COMMENT ON TABLE customer IS 'Tabela klientow';

CREATE TABLE company_address (
  company_id BIGINT NOT NULL,
  address_id BIGINT NOT NULL,
  CONSTRAINT idx_company_address PRIMARY KEY (company_id, address_id),
  CONSTRAINT fk_company_address_company FOREIGN KEY (company_id) REFERENCES company (id),
  CONSTRAINT fk_company_address_address FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE INDEX idx_company_address_company
  ON company_address (company_id);

CREATE INDEX idx_company_address_address
  ON company_address (address_id);

COMMENT ON TABLE company_address IS 'Tabela laczaca adresy z firmami';

CREATE TABLE company_contact_detail (
  company_id        BIGINT NOT NULL,
  contact_detail_id BIGINT NOT NULL,
  CONSTRAINT idx_company_contact_detail PRIMARY KEY (company_id, contact_detail_id),
  CONSTRAINT fk_company_contact_detail_company FOREIGN KEY (company_id) REFERENCES company (id),
  CONSTRAINT fk_company_contact_detail_contact_detail FOREIGN KEY (contact_detail_id) REFERENCES contact_detail (id)
);

CREATE INDEX idx_company_contact_detail_company
  ON company_contact_detail (company_id);

CREATE INDEX idx_company_contact_detail_detail
  ON company_contact_detail (contact_detail_id);

CREATE TABLE customer_out_of_service (
  id                   BIGSERIAL       NOT NULL,
  customer_id          BIGINT       NOT NULL,
  start_date           DATE         NOT NULL,
  end_date             DATE         NOT NULL,
  blocking_operator_id BIGINT       NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  remark               TEXT,
  CONSTRAINT pk_customers_out_of_service PRIMARY KEY (id)
);

CREATE INDEX idx_customers_out_of_service_customer_id
  ON customer_out_of_service (customer_id);

CREATE INDEX idx_customers_out_of_service_blocking_operator_id
  ON customer_out_of_service (blocking_operator_id);

COMMENT ON TABLE customer_out_of_service IS 'Dodano tabelę klientów wykluczonych z obsługi.';

COMMENT ON COLUMN customer_out_of_service.customer_id IS 'ID zablokowanego klienta';

COMMENT ON COLUMN customer_out_of_service.start_date IS 'Data wykluczenia';

COMMENT ON COLUMN customer_out_of_service.end_date IS 'Data zakończenia wykluczenia.';

COMMENT ON COLUMN customer_out_of_service.blocking_operator_id IS 'Operator wykluczający z obsługi';

COMMENT ON COLUMN customer_out_of_service.reason IS 'Przyczyna wykluczenia z obsługi';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_customer', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 107, '7:4faefdc015f7239617e18d8fbe473635', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::i004_ssue::mhorowic
CREATE TABLE CONTRACT (
  id          BIGSERIAL   NOT NULL,
  customer_id BIGINT      NOT NULL,
  ext_contract_id VARCHAR(30) NOT NULL,
  CONSTRAINT pk_contract PRIMARY KEY (id),
  CONSTRAINT fk_contract_customer FOREIGN KEY (customer_id) REFERENCES CUSTOMER (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_contract
  ON CONTRACT (customer_id);

COMMENT ON TABLE CONTRACT IS 'umowy klientow';

COMMENT ON COLUMN CONTRACT.customer_id IS 'Indentyfikator klienta';

COMMENT ON COLUMN CONTRACT.ext_contract_id IS 'Numer umowy w zewnętrzynm systemie';

CREATE TABLE ISSUE (
  id                    BIGSERIAL      NOT NULL,
  operator_id           BIGINT,
  customer_id           BIGINT,
  contract_id           BIGINT,
  start_date            TIMESTAMP      NOT NULL,
  expiration_date       TIMESTAMP      NOT NULL,
  termination_cause     VARCHAR(1000),
  issue_type            VARCHAR(100)   NOT NULL,
  issue_state           VARCHAR(100)   NOT NULL DEFAULT 'NEW',
  rpb                   NUMERIC(18, 2) NOT NULL,
  balance_start         NUMERIC(18, 2) NOT NULL,
  balance_additional    NUMERIC(18, 2),
  payments              NUMERIC(18, 2),
  priority              NUMERIC(1),
  excluded_from_stats   BOOL           NOT NULL,
  exclusion_cause       VARCHAR(500),
  excluding_operator_id BIGINT,
  parent_issue_id       BIGINT,
  CONSTRAINT pk_issue PRIMARY KEY (id),
  CONSTRAINT fk_issue_contract FOREIGN KEY (contract_id) REFERENCES CONTRACT (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_alwin_user FOREIGN KEY (operator_id) REFERENCES ALWIN_USER (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_issue FOREIGN KEY (parent_issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_excluding_alwin_user FOREIGN KEY (excluding_operator_id) REFERENCES ALWIN_USER (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_issue_alwin_user
  ON ISSUE (operator_id);

CREATE INDEX idx_issue_customer
  ON ISSUE (customer_id);

CREATE INDEX idx_issue_contract
  ON ISSUE (contract_id);

CREATE INDEX idx_issue_excluding_alwin_user
  ON issue (excluding_operator_id);

CREATE INDEX idx_issue_parent_issue
  ON issue (parent_issue_id);

COMMENT ON TABLE ISSUE IS 'zlecenia windykacyjne';

COMMENT ON COLUMN ISSUE.operator_id IS 'Identyfikator uzytkownika zajmujacego sie zleceniem';

COMMENT ON COLUMN ISSUE.customer_id IS 'Identyfikator klienta. UWAGA: zlecenie moze być laczone z klientem lub z umowa (XOR)';

COMMENT ON COLUMN ISSUE.contract_id IS 'Identyfikator umowy. UWAGA: zlecenie moze być laczone z klientem lub z umowa (XOR)';

COMMENT ON COLUMN ISSUE.start_date IS 'Data rozpoczecia zlecenia';

COMMENT ON COLUMN ISSUE.expiration_date IS 'Data wygasniecia zlecenia.';

COMMENT ON COLUMN ISSUE.termination_cause IS 'Przyczyna przerwania realizacji zlecenia (jesli przerwane recznie)';

COMMENT ON COLUMN ISSUE.issue_type IS 'Typ zlecenia';

COMMENT ON COLUMN ISSUE.issue_state IS 'Stan zlecenia: NEW(Nowe) - domyslna wartosc, ustawiana w momencie utworzenia przez system, IN_PROGRESS(Realizowane), DONE(Zakonczone), CANCELED(Anulowane)';

COMMENT ON COLUMN ISSUE.rpb IS 'Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia';

COMMENT ON COLUMN ISSUE.balance_start IS 'Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane';

COMMENT ON COLUMN ISSUE.balance_additional IS 'Saldo dokumentow, ktore staly sie wymagalne w trakcie obslugi zlecenia';

COMMENT ON COLUMN ISSUE.payments IS 'Suma dokonanych wplat';

COMMENT ON COLUMN issue.priority IS 'Priorytet obsługi zlecenia';

COMMENT ON COLUMN issue.excluded_from_stats IS 'Czy zlecenie wykluczone z liczenia statystyk';

COMMENT ON COLUMN issue.exclusion_cause IS 'Przyczyna wykluczenia ze statystyk';

COMMENT ON COLUMN issue.excluding_operator_id IS 'Identyfikator uzytkownika wykluczającego zlecenie z ujęcia w statystykach';

COMMENT ON COLUMN issue.parent_issue_id IS 'Identyfikator zlecenia poprzedzajacego';

CREATE TABLE ISSUE_INVOICE (
  id         BIGSERIAL   NOT NULL,
  issue_id   BIGINT      NOT NULL,
  invoice_id VARCHAR(30) NOT NULL,
  CONSTRAINT pk_issue_invoice PRIMARY KEY (id),
  CONSTRAINT fk_issue_invoice_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_issue_invoice_issue
  ON ISSUE_INVOICE (issue_id);

COMMENT ON TABLE ISSUE_INVOICE IS 'Zestawienie dokumentow obciazeniowych, ktore obejmuje zlecenie - relacja do systemu ksiegowego';

COMMENT ON COLUMN ISSUE_INVOICE.invoice_id IS 'Nr faktury - relacja do systemu ksiegowego';

CREATE TABLE ISSUE_DEBT_COLLECTION_ACTION (
  id                        BIGSERIAL      NOT NULL,
  issue_id                  BIGINT         NOT NULL,
  debt_collection_action_id BIGINT         NOT NULL,
  day                       INTEGER,
  assignee_id               BIGINT,
  comment                   VARCHAR(1000),
  handling_fee              NUMERIC(18, 2) NOT NULL,
  state                     VARCHAR(100)   NOT NULL DEFAULT 'PLANNED',
  start_date                TIMESTAMP      NOT NULL,
  end_date                  TIMESTAMP,
  creating_date             TIMESTAMP      NOT NULL,
  updating_date             TIMESTAMP      NOT NULL,
  CONSTRAINT pk_issue_debt_collection_action PRIMARY KEY (id),
  CONSTRAINT fk_issue_debt_collection_action_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_debt_collection_action_debt_collection_action FOREIGN KEY (debt_collection_action_id) REFERENCES DEBT_COLLECTION_ACTION (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_debt_collection_action_alwin_user FOREIGN KEY (assignee_id) REFERENCES ALWIN_USER (id)
);

CREATE INDEX idx_issue_debt_collection_action_issue
  ON ISSUE_DEBT_COLLECTION_ACTION (issue_id);

COMMENT ON TABLE ISSUE_DEBT_COLLECTION_ACTION IS 'Zaplanowane czynnosci windykacyjne dla danego zlecenia';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.id IS 'Identyfikator czynnosci windykacyjnej w danym zleceniu';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.issue_id IS 'Identyfikator zlecenia dla ktorego podejmujemy czynnosc windykacyjna';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.debt_collection_action_id IS 'Identyfikator czynnosci windykacyjnej podejmowanej dla zlecenia';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.day IS 'Dzien w ktorym nalezy podjac czynnosc windykacyjna, jesli wartosc pusta to uzywamy domyslny dzien dla tej czynnosci';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.comment IS 'Komentarz do realizowanej czynnosci windykacyjnej';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.handling_fee IS 'Oplata manipulacyjna';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.state IS 'Stan wykonywanej czynnosci dla zlecenia: PLANNED(Zaplanowana) - domyslna wartosc, EXECUTED(Wykonana), POSTPONED(Przelozona), CANCELED(Anulowana)';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.start_date IS 'Data rozpoczecia czynnosci windykacyjnej dla zlecenia';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.end_date IS 'Data zakonczenia czynnosci windykacyjnej dla zlecenia';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.creating_date IS 'Data utworzenia wpisu';

COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.updating_date IS 'Data aktualizacji wpisu';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('i004_ssue', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 108, '7:945e530c0303a0734faf88bc754a44d9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::005_debt-collection-action-handling-fee::tsliwinski
ALTER TABLE DEBT_COLLECTION_ACTION
  ADD COLUMN HANDLING_FEE NUMERIC(18, 2) NOT NULL DEFAULT 0;

COMMENT ON COLUMN DEBT_COLLECTION_ACTION.HANDLING_FEE IS 'Wysokość opłaty manipulacyjnej';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_debt-collection-action-handling-fee', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 109, '7:5a40db64c43d9f922a74e16821fe398a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::006_drop-not-null-end-date-issue-deb-col-action::tsliwinski
ALTER TABLE ISSUE_DEBT_COLLECTION_ACTION ALTER COLUMN END_DATE DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_drop-not-null-end-date-issue-deb-col-action', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 110, '7:31dedc90f23736d48bcd07c9db23e339', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::007_company_fix_for_external_company_id::mhorowic
ALTER TABLE COMPANY RENAME COLUMN COMPANY_ID TO EXT_COMPANY_ID;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_company_fix_for_external_company_id', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 111, '7:a1c20afac80d2836278ab561bc39a6ca', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::change-person-pesel-type::mhorowic
ALTER TABLE person ALTER COLUMN pesel TYPE VARCHAR(20);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('change-person-pesel-type', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 112, '7:0ddd1eb110442219d4cb364d96d347c9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::009_change-issue-priority-not-null::pnaroznik
UPDATE issue SET priority=0 WHERE issue.priority IS NULL;

ALTER TABLE issue ALTER COLUMN priority SET NOT NULL;

ALTER TABLE issue ALTER COLUMN priority SET DEFAULT 0;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('009_change-issue-priority-not-null', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 113, '7:797953ad96a84904597fe73e3c7ad85d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::010_change-update-sequences-min-value.sql::pnaroznik
ALTER SEQUENCE issue_id_seq start 20000 restart 20100 MINVALUE 20000;

ALTER SEQUENCE issue_debt_collection_action_id_seq start 20000 restart 20100 MINVALUE 20000;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('010_change-update-sequences-min-value.sql', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 114, '7:da258b98441c8ab8681790bc31c55bd1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::011_extending_person_and_company_model::mhorowic
CREATE TABLE country (
  id        SERIAL NOT NULL,
  long_name VARCHAR(150),
  CONSTRAINT idx_country PRIMARY KEY (id)
);

COMMENT ON TABLE country IS 'Tabla krajow';

COMMENT ON COLUMN country.id IS 'Identyfikator kraju';

COMMENT ON COLUMN country.long_name IS 'Pelna nazwa';

ALTER TABLE person
  ADD COLUMN representative BOOLEAN,
  ADD COLUMN address VARCHAR(1024),
  ADD COLUMN martial_status INTEGER,
  ADD COLUMN id_doc_country VARCHAR(2),
  ADD COLUMN id_doc_number VARCHAR(30),
  ADD COLUMN id_doc_signed_by VARCHAR(50),
  ADD COLUMN id_doc_sign_date TIMESTAMP,
  ADD COLUMN politician BOOLEAN,
  ADD COLUMN job_function VARCHAR(30),
  ADD COLUMN birth_date TIMESTAMP,
  ADD COLUMN real_beneficiary BOOLEAN,
  ADD COLUMN id_doc_type INTEGER,
  ADD COLUMN country_id BIGINT,
  ADD CONSTRAINT fk_person_country FOREIGN KEY (country_id) REFERENCES country (id);

COMMENT ON COLUMN person.representative IS 'Czy osoba jest reprezentantem';

COMMENT ON COLUMN person.address IS 'Adres osoby';

COMMENT ON COLUMN person.martial_status IS 'Status cywilny osoby: 0 - nieznany,1 - wolny/wolna,2 - zonaty/zamezna ze wspolnota majatkowa,3 - zonaty/zamezna bez wspolnoty majatkowej';

COMMENT ON COLUMN person.id_doc_country IS 'Kraj wystawienia dokumentu tozsamosci';

COMMENT ON COLUMN person.id_doc_number IS 'Numer dowodu';

COMMENT ON COLUMN person.id_doc_signed_by IS 'Wystawca dokumentu tozsamosci';

COMMENT ON COLUMN person.id_doc_sign_date IS 'Data wystawienia dokumentu tozsamosci';

COMMENT ON COLUMN person.politician IS 'Czy osoba jest politykiem';

COMMENT ON COLUMN person.job_function IS 'Stanowisko osoby';

COMMENT ON COLUMN person.birth_date IS 'Data urodzenia';

COMMENT ON COLUMN person.real_beneficiary IS 'Czy osoba jest beneficjentem rzeczywistym transakcji';

COMMENT ON COLUMN person.id_doc_type IS 'Typ dokumentu tozsamosci: 0 - nieznany,1 - Dowod osobisty,2 - Paszport,5 - Inne';

COMMENT ON COLUMN person.country_id IS 'Identyfikator kraju z ktorego pochodzi osoba';

ALTER TABLE company
  ADD COLUMN postal_code VARCHAR(10),
  ADD COLUMN city VARCHAR(255),
  ADD COLUMN prefix VARCHAR(5),
  ADD COLUMN street VARCHAR(255),
  ADD COLUMN email VARCHAR(60),
  ADD COLUMN correspondence_postal_code VARCHAR(10),
  ADD COLUMN correspondence_city VARCHAR(255),
  ADD COLUMN correspondence_prefix VARCHAR(5),
  ADD COLUMN correspondence_street VARCHAR(255),
  ADD COLUMN krs VARCHAR(150),
  ADD COLUMN recipient_name VARCHAR(150),
  ADD COLUMN rating VARCHAR(100),
  ADD COLUMN rating_date TIMESTAMP,
  ADD COLUMN contact_person VARCHAR(50),
  ADD COLUMN phone_number_1 VARCHAR(30),
  ADD COLUMN phone_number_2 VARCHAR(30),
  ADD COLUMN phone_number_3 VARCHAR(30),
  ADD COLUMN mobile_phone_number VARCHAR(20),
  ADD COLUMN documents_email VARCHAR(60),
  ADD COLUMN office_email VARCHAR(60),
  ADD COLUMN website_address VARCHAR(100),
  ADD COLUMN external_db_agreement BOOLEAN,
  ADD COLUMN external_db_agreement_date TIMESTAMP;

COMMENT ON COLUMN company.postal_code IS 'Kod pocztowy';

COMMENT ON COLUMN company.city IS 'Miasto';

COMMENT ON COLUMN company.prefix IS 'Prefiks dla ulicy np. ul. albo aleja';

COMMENT ON COLUMN company.street IS 'Nazwa ulicy';

COMMENT ON COLUMN company.email IS 'Adres e-mail';

COMMENT ON COLUMN company.correspondence_postal_code IS 'Kod pocztowy do adresu korespondencyjnego';

COMMENT ON COLUMN company.correspondence_city IS 'Miasto adresu korespondencyjnego';

COMMENT ON COLUMN company.correspondence_prefix IS 'Prefiks dla ulicy do adresu korespondencyjnego np. ul. albo aleja';

COMMENT ON COLUMN company.correspondence_street IS 'Nazwa ulicy do adresu korespondencyjnego';

COMMENT ON COLUMN company.krs IS 'KRS';

COMMENT ON COLUMN company.recipient_name IS 'Nazwa odbiorcy';

COMMENT ON COLUMN company.contact_person IS 'Osoba kontaktowa';

COMMENT ON COLUMN company.phone_number_1 IS 'Pierwszy numer telefonu';

COMMENT ON COLUMN company.phone_number_2 IS 'Drugi numer telefonu';

COMMENT ON COLUMN company.phone_number_3 IS 'Trzeci numer telefonu';

COMMENT ON COLUMN company.mobile_phone_number IS 'Numer telefonu komorkowego';

COMMENT ON COLUMN company.office_email IS 'Adres e-mail do biura';

COMMENT ON COLUMN company.website_address IS 'Adres strony www';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('011_extending_person_and_company_model', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 115, '7:6e4b6840be718687fca9f42181dc0b95', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::012_update-sequences::mhorowic
ALTER SEQUENCE country_id_seq start 20000 restart 20100 MINVALUE 20000;

ALTER SEQUENCE customer_id_seq start 20000 restart 20100 MINVALUE 20000;

ALTER SEQUENCE company_id_seq start 20000 restart 20100 MINVALUE 20000;

ALTER SEQUENCE person_id_seq start 20000 restart 20100 MINVALUE 20000;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('012_update-sequences', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 116, '7:79eb412ff85b359b5908233b2eca184a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::013_add_balance_update_date_to_issue.sql::tsliwinski
ALTER TABLE ISSUE
  ADD COLUMN BALANCE_UPDATE_DATE TIMESTAMP;

COMMENT ON COLUMN ISSUE.BALANCE_UPDATE_DATE IS 'Data ostaniej akutalizacji sald';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('013_add_balance_update_date_to_issue.sql', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 117, '7:90de0f6c712dfc540234ebfd18c80f9a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::014_rename_column_for_marital_status::mhorowic
ALTER TABLE PERSON
  RENAME COLUMN martial_status TO marital_status;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('014_rename_column_for_marital_status', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 118, '7:ad1a0412546c88b2b6fb1883117ac96e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::015_issue_type.sql::pnaroznik
CREATE TABLE ISSUE_TYPE (
  id    BIGSERIAL    NOT NULL,
  name  VARCHAR(100) NOT NULL,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_issue_type PRIMARY KEY (id),
  CONSTRAINT idx_issue_type_name UNIQUE (name)
);

COMMENT ON TABLE ISSUE_TYPE IS 'typ zlecenia';

COMMENT ON COLUMN ISSUE_TYPE.id IS 'identyfikator roli';

COMMENT ON COLUMN ISSUE_TYPE.name IS 'nazwa roli';

COMMENT ON COLUMN ISSUE_TYPE.label IS 'opis roli';

CREATE TABLE ISSUE_TYPE_TRANSITION (
  id                   BIGSERIAL    NOT NULL,
  closed_issue_type_id BIGINT       NOT NULL,
  child_issue_type_id  BIGINT       NOT NULL,
  condition            VARCHAR(100) NOT NULL,
  CONSTRAINT pk_issue_type_transition PRIMARY KEY (id),
  CONSTRAINT fk_closed_issue_type_id FOREIGN KEY (closed_issue_type_id) REFERENCES ISSUE_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_child_issue_type_id FOREIGN KEY (child_issue_type_id) REFERENCES ISSUE_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE ISSUE_TYPE_TRANSITION IS 'przepływ zlecen';

COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.id IS 'identyfikator przepływu zlecenia';

COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.closed_issue_type_id IS 'konczone zlecenie';

COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.child_issue_type_id IS 'nastepne zlecenie';

COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.condition IS 'dodatkowe warunki do przepływu zlecenia';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('015_issue_type.sql', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 119, '7:f5e5382f79dfd0e36b12a7e2723f0595', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::003_issue-type.sql::pnaroznik
INSERT INTO ISSUE_TYPE (name, label) VALUES ('PHONE_DEBT_COLLECTION_1', 'Windykacja telefoniczna sekcja 1');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('PHONE_DEBT_COLLECTION_2', 'Windykacja telefoniczna sekcja 2');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('DIRECT_DEBT_COLLECTION', 'Windykacja bezpośrednia');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM', 'Windykacja prawna - pozew o wydanie przedmiotu');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('SUBJECT_TRANSPORT', 'Transport przedmiotu');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('REALIZATION_OF_COLLATERAL', 'Realizacja zabezpieczenia');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('LAW_DEBT_COLLECTION_MOTION_TO_PAY', 'Windykacja prawna - pozew o zapłatę');

INSERT INTO ISSUE_TYPE (name, label) VALUES ('RESTRUCTURING', 'Restrukturyzacja');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'PHONE_DEBT_COLLECTION_1'), (SELECT it.ID
                                                 FROM ISSUE_TYPE it
                                                 WHERE it.NAME = 'PHONE_DEBT_COLLECTION_2'), 'none');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'PHONE_DEBT_COLLECTION_2'), (SELECT it.ID
                                                 FROM ISSUE_TYPE it
                                                 WHERE it.NAME = 'DIRECT_DEBT_COLLECTION'), 'none');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'DIRECT_DEBT_COLLECTION'), (SELECT it.ID
                                                FROM ISSUE_TYPE it
                                                WHERE it.NAME = 'SUBJECT_TRANSPORT'), 'securedItem');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'DIRECT_DEBT_COLLECTION'), (SELECT it.ID
                                                FROM ISSUE_TYPE it
                                                WHERE it.NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM'), 'nonSecuredItem');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM'), (SELECT it.ID
                                                                    FROM ISSUE_TYPE it
                                                                    WHERE it.NAME = 'SUBJECT_TRANSPORT'), 'securedItem');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'SUBJECT_TRANSPORT'), (SELECT it.ID
                                           FROM ISSUE_TYPE it
                                           WHERE it.NAME = 'REALIZATION_OF_COLLATERAL'), 'none');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'REALIZATION_OF_COLLATERAL'), (SELECT it.ID
                                                   FROM ISSUE_TYPE it
                                                   WHERE it.NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_PAY'), 'none');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('003_issue-type.sql', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 120, '7:b9d7aafdd3263a1b5c2c2022c046a0f0', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::016_update_issue_type_in_issue.sql::pnaroznik
ALTER TABLE ISSUE
  ADD COLUMN ISSUE_TYPE_ID BIGINT;

COMMENT ON COLUMN ISSUE.ISSUE_TYPE_ID IS 'Typ zlecenia';

UPDATE ISSUE
SET ISSUE_TYPE_ID = ((SELECT it.ID
                      FROM ISSUE_TYPE it
                      WHERE it.NAME = 'PHONE_DEBT_COLLECTION_1'));

ALTER TABLE ISSUE
  ADD CONSTRAINT fk_issue_issue_type_id
FOREIGN KEY (ISSUE_TYPE_ID)
REFERENCES ISSUE_TYPE (ID);

ALTER TABLE ISSUE
  ALTER ISSUE_TYPE_ID SET NOT NULL;

ALTER TABLE ISSUE
  DROP COLUMN ISSUE_TYPE;

UPDATE ISSUE_TYPE_TRANSITION
SET condition = 'NONE'
WHERE condition = 'none';

UPDATE ISSUE_TYPE_TRANSITION
SET condition = 'SECURED_ITEM'
WHERE condition = 'securedItem';

UPDATE ISSUE_TYPE_TRANSITION
SET condition = 'NON_SECURED_ITEM'
WHERE condition = 'nonSecuredItem';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('016_update_issue_type_in_issue.sql', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 121, '7:fc477c10627b46c1cdbc3a69b58161be', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::017_activity::tsliwinski
--------------------------------------------------------------------------------------
-- activity_type
--------------------------------------------------------------------------------------

CREATE TABLE activity_type (
  id                   BIGSERIAL          NOT NULL,
  name                 VARCHAR(100)       NOT NULL,
  can_be_planned       BOOL               NOT NULL,
  charge_min           NUMERIC(18, 2)     NOT NULL,
  charge_max           NUMERIC(18, 2),
  may_be_automated     BOOL DEFAULT FALSE NOT NULL,
  may_have_declaration BOOL               NOT NULL,
  specific             BOOL               NOT NULL,
  CONSTRAINT pk_activity_type PRIMARY KEY (id)
);

COMMENT ON TABLE activity_type IS 'Tabela typów czynności';

COMMENT ON COLUMN activity_type.name IS 'Nazwa typu czynności';

COMMENT ON COLUMN activity_type.can_be_planned IS 'Czy czynność może zostać zaplanowana.';

COMMENT ON COLUMN activity_type.charge_min IS 'Minimalna wartość opłaty dodatkowej';

COMMENT ON COLUMN activity_type.charge_max IS 'Maksymalna wartość opłaty dodatkowej. Jeżeli opłata stała - nie w przedziale - null.';

COMMENT ON COLUMN activity_type.may_be_automated IS 'Czy czynność może być wykonana przez system.';

COMMENT ON COLUMN activity_type.may_have_declaration IS 'Czy do czynności można dodać deklarację.';

COMMENT ON COLUMN activity_type.specific IS 'Czy czynność szczególna.';

--------------------------------------------------------------------------------------
-- activity
--------------------------------------------------------------------------------------

CREATE TABLE activity (
  id               BIGSERIAL   NOT NULL,
  issue_id         BIGINT      NOT NULL,
  operator_id      BIGINT      NOT NULL,
  activity_type_id BIGINT      NOT NULL,
  activity_date    DATE,
  planned_date     DATE,
  creation_date    DATE        NOT NULL,
  activity_state   VARCHAR(50) NOT NULL DEFAULT 'PLANNED',
  remark           TEXT,
  charge           NUMERIC(18, 2),
  invoice_id       VARCHAR(30),
  CONSTRAINT pk_activity PRIMARY KEY (id),
  CONSTRAINT fk_activity_alwin_user FOREIGN KEY (operator_id) REFERENCES alwin_user (id),
  CONSTRAINT fk_activity_issue FOREIGN KEY (issue_id) REFERENCES issue (id),
  CONSTRAINT fk_activity_activity_type FOREIGN KEY (activity_type_id) REFERENCES activity_type (id)
);

CREATE INDEX idx_activity_issue
  ON activity (issue_id);

CREATE INDEX idx_activity_operator
  ON activity (operator_id);

CREATE INDEX idx_activity_activity_type
  ON activity (activity_type_id);

COMMENT ON TABLE activity IS 'Tabela czynności windykacyjnych';

COMMENT ON COLUMN activity.issue_id IS 'Link do tabeli zleceń';

COMMENT ON COLUMN activity.operator_id IS 'Link do operatora wykonującego czynność';

COMMENT ON COLUMN activity.activity_type_id IS 'Link do typu czynności';

COMMENT ON COLUMN activity.activity_date IS 'Data wykonania czynności';

COMMENT ON COLUMN activity.planned_date IS 'Zaplanowana data wykonania czynności';

COMMENT ON COLUMN activity.creation_date IS 'Data utworzenia czynności';

COMMENT ON COLUMN activity.activity_state IS 'Stan czynności';

COMMENT ON COLUMN activity.remark IS 'Uwaga/komentarz do czynności';

COMMENT ON COLUMN activity.charge IS 'Opłata naliczona za zdarzenie';

COMMENT ON COLUMN activity.invoice_id IS 'Nr faktury za opłate dodatkową - odniesienie do systemu księgowego.';

--------------------------------------------------------------------------------------
-- activity_detail_property
--------------------------------------------------------------------------------------

CREATE TABLE activity_detail_property (
  id            BIGSERIAL    NOT NULL,
  property_name VARCHAR(100) NOT NULL,
  property_type VARCHAR      NOT NULL,
  CONSTRAINT pk_activity_detail_type PRIMARY KEY (id)
);

COMMENT ON TABLE activity_detail_property IS 'Lista cech dodatkowych zdarzeń';

COMMENT ON COLUMN activity_detail_property.property_name IS 'Nazwa typu cechy dodatkowej';

COMMENT ON COLUMN activity_detail_property.property_type IS 'Typ danych cechy';

--------------------------------------------------------------------------------------
-- activity_detail
--------------------------------------------------------------------------------------

CREATE TABLE activity_detail (
  id                          BIGSERIAL     NOT NULL,
  activity_detail_property_id BIGINT        NOT NULL,
  activity_id                 BIGINT        NOT NULL,
  property_value              VARCHAR(1000) NOT NULL,
  CONSTRAINT pk_activity_detail PRIMARY KEY (id),
  CONSTRAINT fk_activity_detail_property FOREIGN KEY (activity_detail_property_id) REFERENCES activity_detail_property (id),
  CONSTRAINT fk_activity_detail_activity FOREIGN KEY (activity_id) REFERENCES activity (id)
);

CREATE INDEX idx_activity_detail_property
  ON activity_detail (activity_detail_property_id);

CREATE INDEX idx_activity_detail_activity
  ON activity_detail (activity_id);

COMMENT ON TABLE activity_detail IS 'Tabela szczegółów zdarzeń';

COMMENT ON COLUMN activity_detail.property_value IS 'Wartość cechy dodatkowej';

COMMENT ON COLUMN activity_detail.activity_detail_property_id IS 'Link do tabeli cech dodatkowych zdarzeń';

COMMENT ON COLUMN activity_detail.activity_id IS 'Link do tabeli czynności windykacyjnych';

--------------------------------------------------------------------------------------
-- activity_type_has_detail_property
--------------------------------------------------------------------------------------

CREATE TABLE activity_type_has_detail_property (
  activity_type_id            BIGINT NOT NULL,
  activity_detail_property_id BIGINT NOT NULL,
  CONSTRAINT idx_activity_detail_schema PRIMARY KEY (activity_type_id, activity_detail_property_id),
  CONSTRAINT fk_activity_detail_schema_activity_type FOREIGN KEY (activity_type_id) REFERENCES activity_type (id),
  CONSTRAINT fk_activity_detail_schema_activity_detail_property FOREIGN KEY (activity_detail_property_id) REFERENCES activity_detail_property (id)
);

CREATE INDEX idx_activity_detail_schema_activity_type
  ON activity_type_has_detail_property (activity_type_id);

CREATE INDEX idx_activity_detail_schema_activity_detail_property
  ON activity_type_has_detail_property (activity_detail_property_id);

COMMENT ON TABLE activity_type_has_detail_property IS 'Tabela asocjacyjna - schemat cech dodatkowych dla typu czynności';

COMMENT ON COLUMN activity_type_has_detail_property.activity_type_id IS 'Link do tabeli typów czynności';

COMMENT ON COLUMN activity_type_has_detail_property.activity_detail_property_id IS 'Link do tabeli cech dodatkowych zdarzeń';

--------------------------------------------------------------------------------------
-- declaration
--------------------------------------------------------------------------------------

CREATE TABLE declaration (
  id                      BIGSERIAL      NOT NULL,
  activity_id             BIGINT         NOT NULL,
  declaration_date        DATE           NOT NULL,
  declared_payment_date   DATE           NOT NULL,
  declared_payment_amount NUMERIC(18, 2) NOT NULL,
  cash_paid               NUMERIC(18, 2),
  CONSTRAINT pk_declaration PRIMARY KEY (id),
  CONSTRAINT fk_declaration_activity FOREIGN KEY (activity_id) REFERENCES activity (id)
);

CREATE INDEX idx_declaration_activity
  ON declaration (activity_id);

COMMENT ON TABLE declaration IS 'Tabela deklaracji splaty';

COMMENT ON COLUMN declaration.activity_id IS 'Link do tabeli czynności windykacyjnych';

COMMENT ON COLUMN declaration.declaration_date IS 'Data złożenia deklaracji';

COMMENT ON COLUMN declaration.declared_payment_date IS 'Zadeklarowana data zapłaty';

COMMENT ON COLUMN declaration.cash_paid IS 'Kwota faktycznie zapłacona';

--------------------------------------------------------------------------------------
-- default_issue_action
--------------------------------------------------------------------------------------

CREATE TABLE default_issue_activity (
  id               BIGSERIAL                           NOT NULL,
  issue_type_id    BIGINT                              NOT NULL,
  activity_type_id BIGINT                              NOT NULL,
  default_day      INTEGER                             NOT NULL,
  version          INTEGER                             NOT NULL,
  creating_date    TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_default_issue_activity PRIMARY KEY (id),
  CONSTRAINT fk_issue_type FOREIGN KEY (issue_type_id) REFERENCES issue_type (id),
  CONSTRAINT fk_activity_type FOREIGN KEY (activity_type_id) REFERENCES activity_type (id)
);

COMMENT ON TABLE default_issue_activity IS 'Domyślne czynności windykacyjne dla zlecenia';

COMMENT ON COLUMN default_issue_activity.id IS 'Identyfikator';

COMMENT ON COLUMN default_issue_activity.issue_type_id IS 'Identyfikator typu zlecenia';

COMMENT ON COLUMN default_issue_activity.activity_type_id IS 'Identyfikator typu czynności';

COMMENT ON COLUMN default_issue_activity.default_day IS 'Standardowy dzień obsługi';

COMMENT ON COLUMN default_issue_activity.version IS 'Wersja czynności';

COMMENT ON COLUMN default_issue_activity.creating_date IS 'Data utworzenia podanej wersji czynności';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('017_activity', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 122, '7:98d20e5031fd3e55cd0413f0eb016515', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::004_activity_type::tsliwinski
--------------------------------------------------------------------------------------
-- activity_type
--------------------------------------------------------------------------------------

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Telefon wychodzący', FALSE, 0, NULL, TRUE, TRUE, TRUE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wezwanie do zapłaty (podsatwowe)', TRUE, 50, NULL, FALSE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wezwanie do zapłaty (ostateczne)', TRUE, 50, NULL, FALSE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wiadomość SMS wychodząca', TRUE, 0, NULL, FALSE, TRUE, TRUE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Telefon przychodzący', FALSE, 0, NULL, TRUE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Email wychodzący', TRUE, 0, NULL, FALSE, TRUE, TRUE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Nieudana próba kontaktu tel.', FALSE, 0, NULL, FALSE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Email przychodzący', FALSE, 0, NULL, TRUE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wiadomość SMS przychodząca', FALSE, 0, NULL, TRUE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Komentarz', FALSE, 0, NULL, FALSE, FALSE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Uaktualnienie danych klienta', FALSE, 0, NULL, FALSE, FALSE, FALSE);

--------------------------------------------------------------------------------------
-- activity_detail_property
--------------------------------------------------------------------------------------

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Nr tel.', 'java.lang.String');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Czy pozostawiono wiadomość', 'java.lang.Boolean');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Długość rozmowy', 'java.lang.Integer');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Czy rozmowa z osobą decyzyjną', 'java.lang.Boolean');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Termin zapłaty', 'java.util.Date');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Typ wezwania (podstawowe)', 'java.lang.String');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Typ wezwania (ostateczne)', 'java.lang.String');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Treść wiadomości', 'java.lang.String');

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Adres email', 'java.lang.String');

--------------------------------------------------------------------------------------
-- activity_type_has_detail_property
--------------------------------------------------------------------------------------

-- Telefon wychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy pozostawiono wiadomość')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Długość rozmowy')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy rozmowa z osobą decyzyjną')
);

-- Wezwanie do zapłaty (podsatwowe)
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (podsatwowe)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Termin zapłaty')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (podsatwowe)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Typ wezwania (podstawowe)')
);

-- Wezwanie do zapłaty (ostateczne)
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (ostateczne)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Termin zapłaty')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (ostateczne)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Typ wezwania (ostateczne)')
);

-- Wiadomość SMS wychodząca
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS wychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS wychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

-- Telefon przychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Długość rozmowy')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy rozmowa z osobą decyzyjną')
);

-- Email wychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Adres email')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

-- Nieudana próba kontaktu tel
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Nieudana próba kontaktu tel.'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Nieudana próba kontaktu tel.'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy pozostawiono wiadomość')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Nieudana próba kontaktu tel.'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy rozmowa z osobą decyzyjną')
);

-- Email przychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Adres email')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

-- Wiadomość SMS przychodząca
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS przychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS przychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('004_activity_type', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 123, '7:dcbf87d1c7a0cf874615cb176215eede', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::005_default_issue_activity::tsliwinski
INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  1, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (podsatwowe)')
  , 2, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  7, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (ostateczne)'),
  10, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'), 11, 1, current_timestamp);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('005_default_issue_activity', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 124, '7:1bccfb40d0e3c62114b248391302bb42', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::018_activity_operator_nullable::tsliwinski
ALTER TABLE activity
  ALTER COLUMN operator_id DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('018_activity_operator_nullable', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 125, '7:7c0bb4807c044aba94715f3a1bbc6330', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::019_cleaning-activity::pnaroznik
DROP TABLE issue_debt_collection_action;

DROP TABLE debt_collection_action;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('019_cleaning-activity', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 126, '7:9962f3de861c6d0ffdb651d17dd200b7', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::020_declaration_monitoring::mhorowic
ALTER TABLE declaration
  ADD COLUMN monitored BOOLEAN;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('020_declaration_monitoring', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 127, '7:dbefe4901f0d011585252cde53a822f9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::021_reorganizing_user_model::mhorowic
CREATE TABLE PERMISSION (
  id                                         BIGSERIAL NOT NULL,
  allowed_to_externally_plan_activities      BOOLEAN   NOT NULL,
  allowed_to_change_activity_charge          BOOLEAN   NOT NULL,
  allowed_to_change_activity_charge_percent  BOOLEAN   NOT NULL,
  allowed_to_mark_own_attachment_deleted     BOOLEAN   NOT NULL,
  allowed_to_mark_others_attachments_deleted BOOLEAN   NOT NULL,
  allowed_to_transfer_issues_internally      BOOLEAN   NOT NULL,
  allowed_to_transfer_issues_externally      BOOLEAN   NOT NULL,
  allowed_to_exclude_issue                   BOOLEAN   NOT NULL,
  allowed_to_set_customer_out_of_service     BOOLEAN   NOT NULL,
  CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE OPERATOR_TYPE (
  id         BIGSERIAL    NOT NULL,
  type_name  VARCHAR(100) NOT NULL,
  type_label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_operator_type PRIMARY KEY (id),
  CONSTRAINT idx_operator_type_name UNIQUE (type_name)
);

CREATE TABLE ALWIN_OPERATOR (
  id                       BIGSERIAL    NOT NULL,
  operator_type_id         BIGINT       NOT NULL,
  alwin_user_id            BIGINT       NOT NULL,
  parent_alwin_operator_id BIGINT,
  permission_id           BIGINT,
  active                   BOOLEAN      NOT NULL,
  login                    VARCHAR(40)  NOT NULL,
  "password"               VARCHAR(255) NOT NULL,
  salt                     VARCHAR(255) NOT NULL,
  CONSTRAINT pk_alwin_operator PRIMARY KEY (id),
  CONSTRAINT fk_alwin_operator_operator_type FOREIGN KEY (operator_type_id) REFERENCES OPERATOR_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alwin_operator_alwin_user FOREIGN KEY (alwin_user_id) REFERENCES alwin_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alwin_operator_alwin_operator FOREIGN KEY (parent_alwin_operator_id) REFERENCES ALWIN_OPERATOR (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alwin_operator_permission FOREIGN KEY (permission_id) REFERENCES PERMISSION (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE OPERATOR_TYPE_ISSUE_TYPE (
  id               BIGSERIAL NOT NULL,
  operator_type_id BIGINT    NOT NULL,
  issue_type_id    BIGINT    NOT NULL,
  CONSTRAINT pk_operator_type_issue_type PRIMARY KEY (id),
  CONSTRAINT fk_operator_type_issue_type_operator_type FOREIGN KEY (operator_type_id) REFERENCES OPERATOR_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_operator_type_issue_type_issue_type FOREIGN KEY (issue_type_id) REFERENCES ISSUE_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('021_reorganizing_user_model', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 128, '7:3c46a302f7b9f939b85f1906a57bd4b9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::006_operator-types::mhorowic
INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('ADMIN', 'Administrator systemu');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('PHONE_DEBT_COLLECTOR', 'Windykator telefoniczny');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('FIELD_DEBT_COLLECTOR', 'Windykator terenowy');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('RESTRUCTURING_SPECIALIST', 'Specjalista ds. restrukturyzacji');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('RENUNCIATION_COORDINATOR', 'Koordynator wypowiedzeń');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('SECURITY_SPECIALIST', 'Specjalista ds. realizacji zabezpieczeń');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('PHONE_DEBT_COLLECTOR_MANAGER', 'Menedżer windykacji telefonicznej');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('DIRECT_DEBT_COLLECTION_MANAGER', 'Menedżer windykacji bezpośredniej');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('ANALYST', 'Analityk');

INSERT INTO OPERATOR_TYPE (type_name, type_label) VALUES ('DEPARTMENT_MANAGER', 'Menedżer departamentu');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('006_operator-types', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 129, '7:d720ef265631afe9443d919d6f6b369c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::007_operator-and-issue-types::mhorowic
INSERT INTO OPERATOR_TYPE_ISSUE_TYPE (operator_type_id, issue_type_id) VALUES

  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_1')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_2')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'DIRECT_DEBT_COLLECTION')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'SUBJECT_TRANSPORT')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'REALIZATION_OF_COLLATERAL')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_PAY')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'RESTRUCTURING')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_1')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_2')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR_MANAGER'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_1')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR_MANAGER'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_2'));

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('007_operator-and-issue-types', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 130, '7:b28bab6537520c920665434e6a1eab2e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::022_operator_foreign_keys::mhorowic
DROP INDEX idx_issue_alwin_user;

DROP INDEX idx_issue_excluding_alwin_user;

ALTER TABLE ACTIVITY
  DROP CONSTRAINT fk_activity_alwin_user;

ALTER TABLE ACTIVITY
  ADD CONSTRAINT fk_activity_alwin_operator FOREIGN KEY (operator_id) REFERENCES ALWIN_OPERATOR (id);

ALTER TABLE ISSUE
  DROP CONSTRAINT fk_issue_alwin_user;

ALTER TABLE ISSUE
  DROP CONSTRAINT fk_issue_excluding_alwin_user;

UPDATE ISSUE i
SET operator_id = (SELECT o.id
                   FROM ALWIN_OPERATOR o
                     JOIN ALWIN_USER u ON o.alwin_user_id = u.id
                   WHERE u.id = i.operator_id
                   LIMIT 1);

ALTER TABLE ISSUE
  ADD CONSTRAINT fk_issue_alwin_operator FOREIGN KEY (operator_id) REFERENCES ALWIN_OPERATOR (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ISSUE
  ADD CONSTRAINT fk_issue_excluding_alwin_operator FOREIGN KEY (excluding_operator_id) REFERENCES ALWIN_OPERATOR (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE INDEX idx_issue_alwin_operator
  ON ISSUE (operator_id);

CREATE INDEX idx_issue_excluding_alwin_operator
  ON ISSUE (excluding_operator_id);

ALTER TABLE ALWIN_USER
  DROP CONSTRAINT fk_alwin_user_alwin_role;

ALTER TABLE ALWIN_USER
  DROP COLUMN alwin_role_id;

ALTER TABLE ALWIN_USER
  DROP COLUMN login;

ALTER TABLE ALWIN_USER
  DROP COLUMN "password";

ALTER TABLE ALWIN_USER
  DROP COLUMN salt;

DROP TABLE ALWIN_ROLE;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('022_operator_foreign_keys', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 131, '7:129d88285039f1a821c2a88616d83ab1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::023_update-copany-relations::pnaroznik
ALTER TABLE contact_detail
  ADD COLUMN remark TEXT;

ALTER TABLE contact_detail
  ADD COLUMN state VARCHAR(100) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE contact_detail
  ALTER COLUMN state DROP DEFAULT;

ALTER TABLE contact_detail
  DROP COLUMN is_active;

COMMENT ON COLUMN contact_detail.remark IS 'Komentarz do danych kontaktowych';

COMMENT ON COLUMN contact_detail.state IS 'Status danych kontaktowych';

ALTER TABLE address
  ADD COLUMN street_prefix VARCHAR(5);

ALTER TABLE address
  ALTER COLUMN house_no DROP NOT NULL;

ALTER TABLE address
  ALTER COLUMN flat_no DROP NOT NULL;

ALTER TABLE address
  ALTER COLUMN country DROP NOT NULL;

ALTER TABLE company DROP COLUMN postal_code;

ALTER TABLE company DROP COLUMN city;

ALTER TABLE company DROP COLUMN prefix;

ALTER TABLE company DROP COLUMN street;

ALTER TABLE company DROP COLUMN email;

ALTER TABLE company DROP COLUMN correspondence_postal_code;

ALTER TABLE company DROP COLUMN correspondence_city;

ALTER TABLE company DROP COLUMN correspondence_prefix;

ALTER TABLE company DROP COLUMN correspondence_street;

ALTER TABLE company DROP COLUMN contact_person;

ALTER TABLE company DROP COLUMN phone_number_1;

ALTER TABLE company DROP COLUMN phone_number_2;

ALTER TABLE company DROP COLUMN phone_number_3;

ALTER TABLE company DROP COLUMN mobile_phone_number;

ALTER TABLE company DROP COLUMN documents_email;

ALTER TABLE company DROP COLUMN office_email;

ALTER TABLE company DROP COLUMN website_address;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('023_update-copany-relations', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 132, '7:a8a63a9cc01ff512cec209c03ee9a557', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::024_customer_contact_for_detail_type::mhorowic
ALTER TABLE activity_type
  ADD COLUMN customer_contact BOOLEAN NOT NULL DEFAULT FALSE;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('024_customer_contact_for_detail_type', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 133, '7:f24995f1c3dcc06e89d60a07c99c2dd6', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::008_update-customer-contact-for-activity-types::mhorowic
UPDATE activity_type
SET customer_contact = TRUE
WHERE
  name IN ('Telefon wychodzący', 'Wiadomość SMS wychodząca', 'Telefon przychodzący', 'Email wychodzący', 'Email przychodzący', 'Wiadomość SMS przychodząca');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('008_update-customer-contact-for-activity-types', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 134, '7:d55aa8e4dfed012ccf85893ceccee3ec', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::025_alter_issue_invoice::astepnowski
DELETE FROM issue_invoice;

ALTER TABLE issue_invoice
  ADD COLUMN last_payment_date  TIMESTAMP,
  ADD COLUMN net_amount         NUMERIC(18, 2)  NOT NULL,
  ADD COLUMN gross_amount       NUMERIC(18, 2)  NOT NULL,
  ADD COLUMN number             VARCHAR(30)     NOT NULL,
  ADD COLUMN currency           VARCHAR(5)      NOT NULL,
  ADD COLUMN current_balance    NUMERIC(18, 2)  NOT NULL,
  ADD COLUMN contract_number    VARCHAR(150)    NOT NULL,
  ADD COLUMN type               INT;

COMMENT ON COLUMN issue_invoice.last_payment_date IS 'Data ostatniej wplaty';

COMMENT ON COLUMN issue_invoice.net_amount IS 'Wartosc netto';

COMMENT ON COLUMN issue_invoice.gross_amount IS 'Wartosc brutto';

COMMENT ON COLUMN issue_invoice.number IS 'Numer faktury';

COMMENT ON COLUMN issue_invoice.currency IS 'Waluta';

COMMENT ON COLUMN issue_invoice.current_balance IS 'Saldo biezace';

COMMENT ON COLUMN issue_invoice.contract_number IS 'Numer umowy';

COMMENT ON COLUMN issue_invoice.type IS 'Typ faktury';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('025_alter_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 135, '7:287f51a5c661b5a4b175c74b475632e3', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::026_parent_operator_type::mhorowic
ALTER TABLE operator_type
  ADD COLUMN parent_operator_type_id BIGINT,
  ADD CONSTRAINT fk_operator_type_parent_operator_type_id FOREIGN KEY (parent_operator_type_id) REFERENCES operator_type (id);

COMMENT ON COLUMN operator_type.parent_operator_type_id IS 'Typ operatora, ktoremu podlega ten typ';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('026_parent_operator_type', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 136, '7:50a26799107c91bba7be528f83f69b01', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::009_parent_operator_types::mhorowic
UPDATE operator_type
SET parent_operator_type_id = (SELECT o.id
                               FROM operator_type o
                               WHERE o.type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
WHERE type_name = 'PHONE_DEBT_COLLECTOR';

UPDATE alwin_operator
SET parent_alwin_operator_id = (SELECT o.id
                                FROM alwin_operator o
                                  JOIN operator_type t ON o.operator_type_id = t.id
                                WHERE t.type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
WHERE
  id IN (SELECT o.id
         FROM alwin_operator o
           JOIN operator_type t ON o.operator_type_id = t.id
         WHERE t.type_name = 'PHONE_DEBT_COLLECTOR');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('009_parent_operator_types', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 137, '7:5c598229c6d50c0ca9eccb4239456579', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::027_alter_issue_invoice::astepnowski
ALTER TABLE issue_invoice
  ADD COLUMN due_date TIMESTAMP DEFAULT current_timestamp NOT NULL;

COMMENT ON COLUMN issue_invoice.due_date IS 'Termin wymagalności';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('027_alter_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 138, '7:413848c585280b38329a0b1624f20ac9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::028_alter_issue_invoice::astepnowski
ALTER TABLE issue_invoice RENAME type TO type_id;

ALTER TABLE issue_invoice ALTER COLUMN type_id TYPE BIGINT;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('028_alter_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 139, '7:8444f78cfb531d6eeb0d033e9862a498', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::029_alter_user_add_phone::mhorowic
ALTER TABLE alwin_user
  ADD COLUMN phone_number VARCHAR(30);

COMMENT ON COLUMN alwin_user.phone_number IS 'Numer telefonu uzytkownika';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('029_alter_user_add_phone', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 140, '7:de19054764d8daa40c0733c3e77ea298', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::030_alter_user_drop_status::mhorowic
ALTER TABLE alwin_user
  DROP COLUMN status;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('030_alter_user_drop_status', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 141, '7:577f9e1d103636ff76d9005b0a078106', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::031_audit_schema::pnaroznik
CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE address_AUD (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  address_type  VARCHAR(100),
  city          VARCHAR(100),
  country       VARCHAR(100),
  flat_no       VARCHAR(100),
  house_no      VARCHAR(100),
  post_code     VARCHAR(100),
  remark        VARCHAR(100),
  street        VARCHAR(100),
  street_prefix VARCHAR(100),
  PRIMARY KEY (id, REV)
);

CREATE TABLE company_address_AUD (
  REV        INTEGER NOT NULL,
  company_id BIGINT  NOT NULL,
  address_id BIGINT  NOT NULL,
  REVTYPE    SMALLINT,
  PRIMARY KEY (REV, company_id, address_id)
);

CREATE TABLE company_AUD (
  id                         BIGINT  NOT NULL,
  REV                        INTEGER NOT NULL,
  REVTYPE                    SMALLINT,
  company_name               VARCHAR(255),
  ext_company_id             BIGINT,
  external_db_agreement      BOOLEAN,
  external_db_agreement_date TIMESTAMP,
  krs                        VARCHAR(255),
  nip                        VARCHAR(255),
  rating                     VARCHAR(255),
  rating_date                TIMESTAMP,
  recipient_name             VARCHAR(255),
  regon                      VARCHAR(255),
  PRIMARY KEY (id, REV)
);

CREATE TABLE company_contact_detail_AUD (
  REV               INTEGER NOT NULL,
  company_id        BIGINT  NOT NULL,
  contact_detail_id BIGINT  NOT NULL,
  REVTYPE           SMALLINT,
  PRIMARY KEY (REV, company_id, contact_detail_id)
);

CREATE TABLE company_person_AUD (
  REV        INTEGER NOT NULL,
  company_id BIGINT  NOT NULL,
  person_id  BIGINT  NOT NULL,
  REVTYPE    SMALLINT,
  PRIMARY KEY (REV, company_id, person_id)
);

CREATE TABLE contact_detail_AUD (
  id           BIGINT  NOT NULL,
  REV          INTEGER NOT NULL,
  REVTYPE      SMALLINT,
  contact      VARCHAR(100),
  contact_type VARCHAR(100),
  remark       VARCHAR(255),
  state        VARCHAR(255),
  PRIMARY KEY (id, REV)
);

CREATE TABLE country_AUD (
  id        BIGINT  NOT NULL,
  REV       INTEGER NOT NULL,
  REVTYPE   SMALLINT,
  long_name VARCHAR(255),
  PRIMARY KEY (id, REV)
);

CREATE TABLE person_address_AUD (
  REV        INTEGER NOT NULL,
  person_id  BIGINT  NOT NULL,
  address_id BIGINT  NOT NULL,
  REVTYPE    SMALLINT,
  PRIMARY KEY (REV, person_id, address_id)
);

CREATE TABLE person_AUD (
  id               BIGINT  NOT NULL,
  REV              INTEGER NOT NULL,
  REVTYPE          SMALLINT,
  address          VARCHAR(255),
  birth_date       TIMESTAMP,
  first_name       VARCHAR(255),
  id_doc_country   VARCHAR(255),
  id_doc_number    VARCHAR(255),
  id_doc_sign_date TIMESTAMP,
  id_doc_signed_by VARCHAR(255),
  id_doc_type      INTEGER,
  job_function     VARCHAR(255),
  last_name        VARCHAR(255),
  marital_status   INTEGER,
  person_id        BIGINT,
  pesel            VARCHAR(255),
  politician       BOOLEAN,
  real_beneficiary BOOLEAN,
  representative   BOOLEAN,
  country_id       BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE person_contact_detail_AUD (
  REV               INTEGER NOT NULL,
  person_id         BIGINT  NOT NULL,
  contact_detail_id BIGINT  NOT NULL,
  REVTYPE           SMALLINT,
  PRIMARY KEY (REV, person_id, contact_detail_id)
);

CREATE TABLE REVINFO (
  id          INTEGER NOT NULL,
  timestamp   BIGINT  NOT NULL,
  operator_id BIGINT,
  PRIMARY KEY (id)
);

ALTER TABLE address_AUD
  ADD CONSTRAINT FK8pavhn1oejo2hbfyi0wwxlhk7 FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE company_address_AUD
  ADD CONSTRAINT FKh6sdqrkg988wd54nuik8vqnrr FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE company_AUD
  ADD CONSTRAINT FKavlit4ky8rlvoiku0y2ew9o7 FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE company_contact_detail_AUD
  ADD CONSTRAINT FKjbw8cjcnf11nimv2dscutfnwg FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE company_person_AUD
  ADD CONSTRAINT FK535vd1m064efi76q5tm2wn9qa FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE contact_detail_AUD
  ADD CONSTRAINT FKeshw37x9pafh93xdx81gb6vno FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE country_AUD
  ADD CONSTRAINT FKa507c9e014j598v5etq25ctxi FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE person_address_AUD
  ADD CONSTRAINT FKp9pmriia53sqqbywkd1oy4vje FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE person_AUD
  ADD CONSTRAINT FK6m0ndyymbd03jgiq8o9qa3e65 FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE person_contact_detail_AUD
  ADD CONSTRAINT FK8cbl5m5tdm61jsw65ffcm1j3m FOREIGN KEY (REV) REFERENCES REVINFO;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('031_audit_schema', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 142, '7:cc1f5a5888dc5b469d938a4e88a47f9c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::032_last_data_sync_schema::pnaroznik
CREATE TABLE last_data_sync (
  id        BIGSERIAL          NOT NULL,
  type      VARCHAR(30) UNIQUE NOT NULL,
  to_date   DATE,
  from_date DATE,
  CONSTRAINT pk_last_data_sync PRIMARY KEY (id)
);

COMMENT ON TABLE last_data_sync IS 'informacje o ostatniej synchronizacji dancyh';

COMMENT ON COLUMN last_data_sync.id IS 'identyfikator wpisu';

COMMENT ON COLUMN last_data_sync.type IS 'rodzaj synchronizowanych danych';

COMMENT ON COLUMN last_data_sync.to_date IS 'przedzial czasu, do którego były synchronizowane dane ostatnio';

COMMENT ON COLUMN last_data_sync.from_date IS 'przedzial czasu, od którego były zsynchronizowane dane ostatnio';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('032_last_data_sync_schema', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 143, '7:06f69514815c066a9379c0bbe14c4018', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::033_update_address_and_contact_schema::pnaroznik
ALTER TABLE address
  ADD COLUMN imported_from_aida BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN address.imported_from_aida IS 'czy wpis został zaimportowany z systemu AIDA';

ALTER TABLE address
  ADD COLUMN imported_type VARCHAR(100);

COMMENT ON COLUMN address.imported_type IS 'typ zaimportowany z systemu AIDA';

ALTER TABLE contact_detail
  ADD COLUMN imported_from_aida BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN contact_detail.imported_from_aida IS 'czy wpis został zaimportowany z systemu AIDA';

ALTER TABLE contact_detail
  ADD COLUMN imported_type VARCHAR(100);

COMMENT ON COLUMN contact_detail.imported_type IS 'typ zaimportowany z systemu AIDA';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('033_update_address_and_contact_schema', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 144, '7:2dc7d33cf1ed63f1d7b70eea38deee21', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::034_alter_issue_add_created_manually.sql::tsliwinski
ALTER TABLE issue
  ADD COLUMN created_manually BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN issue.created_manually IS 'czy zlecenie zostało utworzone manualnie';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('034_alter_issue_add_created_manually.sql', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 145, '7:c588be74fc4535f2558dec84a30ea8ce', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::035_alter_issue_type_add_case_length::tsliwinski
ALTER TABLE issue_type
  ADD COLUMN case_length NUMERIC(4) NOT NULL DEFAULT 14;

-- TODO ustawienie ilości dni dla zleceń innych niż WT1 i WT2

COMMENT ON COLUMN issue_type.case_length IS 'długość trwania zlecenia w dniach';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('035_alter_issue_type_add_case_length', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 146, '7:532e2fbdd23cb9f12e5453480873576a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::010_issue_type_case_length::tsliwinski
UPDATE ISSUE_TYPE
SET case_length = 15
WHERE name = 'PHONE_DEBT_COLLECTION_2';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('010_issue_type_case_length', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 147, '7:11c89c299b32241f06c8c76a64962eab', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::036_message_template_schema::pnaroznik
CREATE TABLE message_template (
  id    BIGSERIAL        NOT NULL,
  type  VARCHAR(10)   NOT NULL,
  name  VARCHAR(100)  NOT NULL,
  body  TEXT NOT NULL,
  topic VARCHAR(100),
  PRIMARY KEY (id),
  UNIQUE (type, name)
);

COMMENT ON COLUMN message_template.type IS 'typ wiadomości';

COMMENT ON COLUMN message_template.name IS 'nazwa szablonu';

COMMENT ON COLUMN message_template.body IS 'treść szablonu';

COMMENT ON COLUMN message_template.topic IS 'temat wiadomości (e-mail)';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('036_message_template_schema', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 148, '7:c2a963f5f10a97e05971b36f88010d1c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::037_unique_operator_login::mhorowic
ALTER TABLE alwin_operator
  ADD CONSTRAINT idx_alwin_operator_login UNIQUE (login);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('037_unique_operator_login', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 149, '7:3d0ded262fe742c02da80b7022677f5d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::038_activity_type_key::pnaroznik
ALTER TABLE activity_type
  ADD COLUMN key VARCHAR(100);

ALTER TABLE activity_detail_property
  ADD COLUMN key VARCHAR(100);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('038_activity_type_key', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 150, '7:c0575dd97b3a4aa7e4240e73ea498db5', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::011_activity_type_key::pnaroznik
-- activity_type
UPDATE activity_type
SET key = 'OUTGOING_PHONE_CALL'
WHERE name = 'Telefon wychodzący';

UPDATE activity_type
SET key = 'FIRST_DEMAND_PAYMENT'
WHERE name = 'Wezwanie do zapłaty (podsatwowe)';

UPDATE activity_type
SET key = 'LAST_DEMAND_PAYMENT'
WHERE name = 'Wezwanie do zapłaty (ostateczne)';

UPDATE activity_type
SET key = 'OUTGOING_SMS'
WHERE name = 'Wiadomość SMS wychodząca';

UPDATE activity_type
SET key = 'INCOMING_PHONE_CALL'
WHERE name = 'Telefon przychodzący';

UPDATE activity_type
SET key = 'OUTGOING_EMAIL'
WHERE name = 'Email wychodzący';

UPDATE activity_type
SET key = 'FAILED_PHONE_CALL_ATTEMPT'
WHERE name = 'Nieudana próba kontaktu tel.';

UPDATE activity_type
SET key = 'INCOMING_EMAIL'
WHERE name = 'Email przychodzący';

UPDATE activity_type
SET key = 'INCOMING_SMS'
WHERE name = 'Wiadomość SMS przychodząca';

UPDATE activity_type
SET key = 'COMMENT'
WHERE name = 'Komentarz';

UPDATE activity_type
SET key = 'DATA_UPDATE'
WHERE name = 'Uaktualnienie danych klienta';

-- activity_detail_property
UPDATE activity_detail_property
SET key = 'PHONE_NUMBER'
WHERE property_name = 'Nr tel.';

UPDATE activity_detail_property
SET key = 'MESSAGE_LEFT'
WHERE property_name = 'Czy pozostawiono wiadomość';

UPDATE activity_detail_property
SET key = 'PHONE_CALL_LENGTH'
WHERE property_name = 'Długość rozmowy';

UPDATE activity_detail_property
SET key = 'DECISION_MAKER_CALL'
WHERE property_name = 'Czy rozmowa z osobą decyzyjną';

UPDATE activity_detail_property
SET key = 'DATE_OF_PAYMENT'
WHERE property_name = 'Termin zapłaty';

UPDATE activity_detail_property
SET key = 'BASIC_CALL_TYPE'
WHERE property_name = 'Typ wezwania (podstawowe)';

UPDATE activity_detail_property
SET key = 'FINAL_CALL_TYPE'
WHERE property_name = 'Typ wezwania (ostateczne)';

UPDATE activity_detail_property
SET key = 'MESSAGE_CONTENT'
WHERE property_name = 'Treść wiadomości';

UPDATE activity_detail_property
SET key = 'EMAIL_ADDRESS'
WHERE property_name = 'Adres email';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('011_activity_type_key', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 151, '7:731c84bf650bd983659b3cb988d239dd', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::039_activity_type_key_unique::pnaroznik
ALTER TABLE activity_type
  ALTER COLUMN key SET NOT NULL;

CREATE UNIQUE INDEX uactivity_type_key_idx
  ON activity_type (key);

ALTER TABLE activity_detail_property
  ALTER COLUMN key SET NOT NULL;

CREATE UNIQUE INDEX activity_detail_property_idx
  ON activity_detail_property (key);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('039_activity_type_key_unique', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 152, '7:6be51d9912bc024033568f78b73a523a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::040_column_name_fix::pnaroznik
ALTER TABLE activity_detail_property
  RENAME COLUMN key to property_key;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('040_column_name_fix', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 153, '7:365f59475d86fb3069c082260f2e26fc', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::041_company_drop_not_null::pnaroznik
ALTER TABLE company
  ALTER COLUMN company_name DROP NOT NULL;

ALTER TABLE company
  ALTER COLUMN nip DROP NOT NULL;

ALTER TABLE company
  ALTER COLUMN regon DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('041_company_drop_not_null', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 154, '7:7a1cd578bf08edc185e479a61cd56f55', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::042_create_notification::astepnowski
CREATE TABLE NOTIFICATION (
  id            BIGSERIAL NOT NULL,
  issue_id      BIGINT    NOT NULL,
  creation_date TIMESTAMP NOT NULL,
  read_date     TIMESTAMP,
  message       TEXT      NOT NULL,
  sender_id     BIGINT    NOT NULL,
  recipient_id  BIGINT    NOT NULL,
  CONSTRAINT pk_notification PRIMARY KEY (id)
);

COMMENT ON TABLE NOTIFICATION IS 'Powiadomienia';

COMMENT ON COLUMN NOTIFICATION.id IS 'Identyfikator powiadomienia';

COMMENT ON COLUMN NOTIFICATION.issue_id IS 'Identyfikator zlecenia';

COMMENT ON COLUMN NOTIFICATION.creation_date IS 'Data utworzenia';

COMMENT ON COLUMN NOTIFICATION.read_date IS 'Data odczytania';

COMMENT ON COLUMN NOTIFICATION.message IS 'Treść wiadomości';

COMMENT ON COLUMN NOTIFICATION.sender_id IS 'Identyfikator autora powiadomienia';

COMMENT ON COLUMN NOTIFICATION.recipient_id IS 'Identyfikator odbiorcy powiadomienia';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('042_create_notification', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 155, '7:5cc1b93d3aeed77cc295ef61e175d3ac', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::043_update_user_sequencec::pnaroznik
ALTER SEQUENCE alwin_user_id_seq
START 20000 RESTART 20100
MINVALUE 20000;

ALTER SEQUENCE permission_id_seq
START 20000 RESTART 20100
MINVALUE 20000;

ALTER SEQUENCE alwin_operator_id_seq
START 20000 RESTART 20100
MINVALUE 20000;

ALTER TABLE issue_invoice
  ALTER COLUMN currency DROP NOT NULL;

ALTER TABLE address
  ALTER COLUMN street DROP NOT NULL;

ALTER TABLE address
  ALTER COLUMN post_code DROP NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('043_update_user_sequencec', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 156, '7:91d9b483e0c36ba5fa8b22031c433d27', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::044_alter_notification::astepnowski
alter table notification drop column recipient_id;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('044_alter_notification', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 157, '7:0502d348a8171f6c5cc6b57c537e15e4', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::045_update_issue_termination_data::pnaroznik
ALTER TABLE issue
  ADD COLUMN termination_date TIMESTAMP;

ALTER TABLE issue
  ADD COLUMN termination_operator_id BIGINT;

ALTER TABLE issue
  ADD CONSTRAINT fk_issue_termination_operator_id FOREIGN KEY (termination_operator_id) REFERENCES ALWIN_OPERATOR (id);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('045_update_issue_termination_data', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 158, '7:a455a21b6dddb578938481f1115b8fe3', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::046_alter_declaration::astepnowski
ALTER TABLE declaration ADD COLUMN current_balance NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('046_alter_declaration', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 159, '7:0057fae2fed73320ad1acf6951eb31e1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::047_create_contract_out_of_service::mhorowic
CREATE TABLE contract_out_of_service (
  id                   BIGSERIAL    NOT NULL,
  start_date           DATE,
  end_date             DATE,
  blocking_operator_id BIGINT       NOT NULL,
  contract_no          VARCHAR(30)  NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  remark               TEXT,
  CONSTRAINT pk_contract_out_of_service PRIMARY KEY (id),
  CONSTRAINT fk_contract_out_of_service_blocking_operator_id FOREIGN KEY (blocking_operator_id) REFERENCES alwin_operator (id)
);

CREATE INDEX idx_contract_out_of_service_blocking_operator_id
  ON contract_out_of_service (blocking_operator_id);

COMMENT ON TABLE contract_out_of_service IS 'Kontrakty klientow wykluczone z obslugi';

COMMENT ON COLUMN contract_out_of_service.start_date IS 'Data rozpoczecia wykluczenia';

COMMENT ON COLUMN contract_out_of_service.end_date IS 'Data zakończenia wykluczenia.';

COMMENT ON COLUMN contract_out_of_service.blocking_operator_id IS 'Operator wykluczający kontrakt z obsługi';

COMMENT ON COLUMN contract_out_of_service.contract_no IS 'Numer kontraktu';

COMMENT ON COLUMN contract_out_of_service.reason IS 'Przyczyna wykluczenia z obsługi';

COMMENT ON COLUMN contract_out_of_service.remark IS 'Komentarz';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('047_create_contract_out_of_service', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 160, '7:4ba4f202f3187aeaaf10babd984f6381', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::048_add_ext_company_id_to_contract_out_of_service::mhorowic
ALTER TABLE contract_out_of_service ADD COLUMN ext_company_id BIGINT NOT NULL;

COMMENT ON COLUMN contract_out_of_service.ext_company_id IS 'Identyfikator klienta z zewnętrznego systemu';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('048_add_ext_company_id_to_contract_out_of_service', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 161, '7:c353981fe21f4de4fd32dc89f96afcd1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::049_drop_not_null_on_dates_for_customer_out_of_service::mhorowic
ALTER TABLE customer_out_of_service
  ALTER COLUMN start_date SET DEFAULT current_timestamp;

ALTER TABLE customer_out_of_service
  ALTER COLUMN end_date DROP NOT NULL;

ALTER TABLE contract_out_of_service
  ALTER COLUMN start_date SET DEFAULT current_timestamp;

ALTER TABLE customer_out_of_service
  ALTER COLUMN start_date SET NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('049_drop_not_null_on_dates_for_customer_out_of_service', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 162, '7:809e6250dbd4116893970e0de0db1947', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::050_changing_country_id_to_country::mhorowic
ALTER TABLE person
  ADD COLUMN country VARCHAR(100);

UPDATE person
SET country = (SELECT c.long_name
               FROM country c
               WHERE c.id = country_id);

ALTER TABLE person
  DROP COLUMN country_id;

DROP TABLE country;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('050_changing_country_id_to_country', 'mhorowic', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 163, '7:59f60a371986c22dafb36a4e19c06318', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::051_alter_issue_invoice::astepnowski
ALTER TABLE issue_invoice RENAME TO invoice;

ALTER SEQUENCE issue_invoice_id_seq RENAME TO invoice_id_seq;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('051_alter_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 164, '7:c55f44c41288eb2b83ae9bd1002e8fba', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::052_create_audit_tables::tsliwinski
CREATE TABLE Activity_ActivityDetail_AUD (
  REV         INTEGER NOT NULL,
  activity_id BIGINT  NOT NULL,
  id          BIGINT  NOT NULL,
  REVTYPE     SMALLINT,
  PRIMARY KEY (REV, activity_id, id)
);

CREATE TABLE Activity_Declaration_AUD (
  REV         INTEGER NOT NULL,
  activity_id BIGINT  NOT NULL,
  id          BIGINT  NOT NULL,
  REVTYPE     SMALLINT,
  PRIMARY KEY (REV, activity_id, id)
);

CREATE TABLE activity_AUD (
  id               BIGINT  NOT NULL,
  REV              INTEGER NOT NULL,
  REVTYPE          SMALLINT,
  activity_date    TIMESTAMP,
  charge           DECIMAL(19, 2),
  creation_date    TIMESTAMP,
  invoice_id       VARCHAR(255),
  planned_date     TIMESTAMP,
  remark           VARCHAR(255),
  activity_state   VARCHAR(50),
  activity_type_id BIGINT,
  issue_id         BIGINT,
  operator_id      BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_detail_AUD (
  id                          BIGINT  NOT NULL,
  REV                         INTEGER NOT NULL,
  REVTYPE                     SMALLINT,
  property_value              VARCHAR(1000),
  activity_detail_property_id BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_detail_property_AUD (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  property_key  VARCHAR(100),
  property_name VARCHAR(100),
  property_type VARCHAR(255),
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_type_AUD (
  id                   BIGINT  NOT NULL,
  REV                  INTEGER NOT NULL,
  REVTYPE              SMALLINT,
  can_be_planned       BOOLEAN,
  charge_max           DECIMAL(19, 2),
  charge_min           DECIMAL(19, 2),
  customer_contact     BOOLEAN,
  key                  VARCHAR(100),
  may_be_automated     BOOLEAN,
  may_have_declaration BOOLEAN,
  name                 VARCHAR(100),
  specific             BOOLEAN,
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_type_has_detail_property_AUD (
  REV                         INTEGER NOT NULL,
  activity_type_id            BIGINT  NOT NULL,
  activity_detail_property_id BIGINT  NOT NULL,
  REVTYPE                     SMALLINT,
  PRIMARY KEY (REV, activity_type_id, activity_detail_property_id)
);

CREATE TABLE alwin_operator_AUD (
  id                       BIGINT  NOT NULL,
  REV                      INTEGER NOT NULL,
  REVTYPE                  SMALLINT,
  active                   BOOLEAN,
  login                    VARCHAR(40),
  password                 VARCHAR(255),
  salt                     VARCHAR(255),
  parent_alwin_operator_id BIGINT,
  permission_id            BIGINT,
  operator_type_id         BIGINT,
  alwin_user_id            BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE alwin_user_AUD (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  creation_date TIMESTAMP,
  email         VARCHAR(512),
  first_name    VARCHAR(100),
  last_name     VARCHAR(100),
  phone_number  VARCHAR(30),
  PRIMARY KEY (id, REV)
);

CREATE TABLE contract_AUD (
  id              BIGINT  NOT NULL,
  REV             INTEGER NOT NULL,
  REVTYPE         SMALLINT,
  ext_contract_id VARCHAR(30),
  customer_id     BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE customer_AUD (
  id           BIGINT  NOT NULL,
  REV          INTEGER NOT NULL,
  REVTYPE      SMALLINT,
  debt_segment VARCHAR(255),
  company_id   BIGINT,
  person_id    BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE declaration_AUD (
  id                      BIGINT  NOT NULL,
  REV                     INTEGER NOT NULL,
  REVTYPE                 SMALLINT,
  cash_paid               DECIMAL(19, 2),
  current_balance         DECIMAL(19, 2),
  declaration_date        TIMESTAMP,
  declared_payment_amount DECIMAL(19, 2),
  declared_payment_date   TIMESTAMP,
  monitored               BOOLEAN,
  PRIMARY KEY (id, REV)
);

CREATE TABLE issue_AUD (
  id                      BIGINT  NOT NULL,
  REV                     INTEGER NOT NULL,
  REVTYPE                 SMALLINT,
  balance_additional      DECIMAL(19, 2),
  balance_start           DECIMAL(19, 2),
  balance_update_date     TIMESTAMP,
  created_manually        BOOLEAN,
  excluded_from_stats     BOOLEAN,
  exclusion_cause         VARCHAR(500),
  expiration_date         TIMESTAMP,
  issue_state             VARCHAR(100),
  payments                DECIMAL(19, 2),
  priority                INTEGER,
  rpb                     DECIMAL(19, 2),
  start_date              TIMESTAMP,
  termination_cause       VARCHAR(1000),
  termination_date        TIMESTAMP,
  operator_id             BIGINT,
  contract_id             BIGINT,
  customer_id             BIGINT,
  excluding_operator_id   BIGINT,
  issue_type_id           BIGINT,
  parent_issue_id         BIGINT,
  termination_operator_id BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE issue_invoice_AUD (
  id                BIGINT  NOT NULL,
  REV               INTEGER NOT NULL,
  REVTYPE           SMALLINT,
  contract_number   VARCHAR(255),
  currency          VARCHAR(255),
  current_balance   DECIMAL(19, 2),
  due_date          TIMESTAMP,
  gross_amount      DECIMAL(19, 2),
  invoice_id        VARCHAR(30),
  last_payment_date TIMESTAMP,
  net_amount        DECIMAL(19, 2),
  number            VARCHAR(255),
  type_id           BIGINT,
  issue_id          BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE issue_type_AUD (
  id          BIGINT  NOT NULL,
  REV         INTEGER NOT NULL,
  REVTYPE     SMALLINT,
  case_length INTEGER,
  label       VARCHAR(100),
  name        VARCHAR(100),
  PRIMARY KEY (id, REV)
);

CREATE TABLE operator_type_AUD (
  id                      BIGINT  NOT NULL,
  REV                     INTEGER NOT NULL,
  REVTYPE                 SMALLINT,
  type_label              VARCHAR(100),
  type_name               VARCHAR(100),
  parent_operator_type_id BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE operator_type_issue_type_AUD (
  REV              INTEGER NOT NULL,
  issue_type_id    BIGINT  NOT NULL,
  operator_type_id BIGINT  NOT NULL,
  REVTYPE          SMALLINT,
  PRIMARY KEY (REV, issue_type_id, operator_type_id)
);

CREATE TABLE permission_AUD (
  id                                         BIGINT  NOT NULL,
  REV                                        INTEGER NOT NULL,
  REVTYPE                                    SMALLINT,
  allowed_to_change_activity_charge          BOOLEAN,
  allowed_to_change_activity_charge_percent  BOOLEAN,
  allowed_to_exclude_issue                   BOOLEAN,
  allowed_to_externally_plan_activities      BOOLEAN,
  allowed_to_mark_others_attachments_deleted BOOLEAN,
  allowed_to_mark_own_attachment_deleted     BOOLEAN,
  allowed_to_set_customer_out_of_service     BOOLEAN,
  allowed_to_transfer_issues_externally      BOOLEAN,
  allowed_to_transfer_issues_internally      BOOLEAN,
  PRIMARY KEY (id, REV)
);

ALTER TABLE Activity_ActivityDetail_AUD
  ADD CONSTRAINT FKp25f0qwc1ss5tjx86bdkp274s FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE Activity_Declaration_AUD
  ADD CONSTRAINT FK98fo92y4m3lg1fno6ncpcvgdy FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE activity_AUD
  ADD CONSTRAINT FKaj3jrmqx0w728g0ueq4orx35c FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE activity_detail_AUD
  ADD CONSTRAINT FKrp74up6w4fj2yaiugck26ybcj FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE activity_detail_property_AUD
  ADD CONSTRAINT FKgfdldd57t34nbv0afk259oncb FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE activity_type_AUD
  ADD CONSTRAINT FK5kahvwyqtjg1lsf6nd77w0q6s FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE activity_type_has_detail_property_AUD
  ADD CONSTRAINT FK2hdmkbnit7lf7amkxtig2bbbo FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE alwin_operator_AUD
  ADD CONSTRAINT FKsj2com32ttjd08bfv9h28p87f FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE alwin_user_AUD
  ADD CONSTRAINT FKih515lnkismggrtfoq7hdnvne FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE contract_AUD
  ADD CONSTRAINT FKf9tfq0prxe8vbsau3bpbmlcya FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE customer_AUD
  ADD CONSTRAINT FKmeoyvv2wyms32m3q8xrby1lox FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE declaration_AUD
  ADD CONSTRAINT FKnyupmn62uvjwcpb85jrjxl8ox FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE issue_AUD
  ADD CONSTRAINT FK99r0ko1ns7art19gyloh1tsrc FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE issue_invoice_AUD
  ADD CONSTRAINT FK2vp072py05tlvikbnoa17pi6t FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE issue_type_AUD
  ADD CONSTRAINT FK1skel0aisimn0sje8mf3jetf6 FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE operator_type_AUD
  ADD CONSTRAINT FKran0g8nleqr6b22slrcnq3859 FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE operator_type_issue_type_AUD
  ADD CONSTRAINT FKlc8glcfria7kd3dj0n5rdcm3g FOREIGN KEY (REV) REFERENCES REVINFO;

ALTER TABLE permission_AUD
  ADD CONSTRAINT FKhgrao1tyqi2ai2pyrq7n9r09q FOREIGN KEY (REV) REFERENCES REVINFO;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('052_create_audit_tables', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 165, '7:2864ca67dbf42832b892e04ddcee51f6', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::053_create_issue_termination_request_table::pnaroznik
CREATE TABLE issue_termination_request (
  id                         BIGSERIAL     NOT NULL,
  issue_id                   BIGINT        NOT NULL,
  request_cause              VARCHAR(1000) NOT NULL,
  request_operator_id        BIGINT        NOT NULL,
  excluded_from_stats        BOOLEAN       NOT NULL,
  exclusion_from_stats_cause VARCHAR(500),
  state                      VARCHAR(20)   NOT NULL,
  manager_operator_id        BIGINT,
  comment                    VARCHAR(500),
  created                    TIMESTAMP     NOT NULL,
  updated                    TIMESTAMP     NOT NULL,
  CONSTRAINT pk_issue_termination_request PRIMARY KEY (id),
  CONSTRAINT fk_issue_termination_request_issue FOREIGN KEY (issue_id) REFERENCES issue (id),
  CONSTRAINT fk_issue_termination_request_request_operator FOREIGN KEY (request_operator_id) REFERENCES alwin_operator (id),
  CONSTRAINT fk_issue_termination_request_manager_operator FOREIGN KEY (manager_operator_id) REFERENCES alwin_operator (id)
);

COMMENT ON TABLE issue_termination_request IS 'Tabela do przechowywania żądań przedterminowego zakończenia zlecenia';

COMMENT ON COLUMN issue_termination_request.id IS 'Identyfikaotr żądania';

COMMENT ON COLUMN issue_termination_request.issue_id IS 'Identyfikator zlecenia do zamknięcia';

COMMENT ON COLUMN issue_termination_request.request_cause IS 'Przyczyna przedterminowego zamknięcia';

COMMENT ON COLUMN issue_termination_request.request_operator_id IS 'Identyfikator operatora tworzącego żądanie';

COMMENT ON COLUMN issue_termination_request.excluded_from_stats IS 'Czy zlecenie powinno być pominięte w statystykach';

COMMENT ON COLUMN issue_termination_request.state IS 'Status żądania';

COMMENT ON COLUMN issue_termination_request.manager_operator_id IS 'Identyfikator operatora, który obsłużył żądanie';

COMMENT ON COLUMN issue_termination_request.comment IS 'Komentarz dotyczący obsługi żądania';

COMMENT ON COLUMN issue_termination_request.created IS 'Data utworzenia żądania';

COMMENT ON COLUMN issue_termination_request.updated IS 'Data aktulaizacji żądania';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('053_create_issue_termination_request_table', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 166, '7:b2a5b72ee9522bdf9f3904cde9e7ab33', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::054_alter_issue_aud_table_name::tsliwinski
ALTER TABLE issue_invoice_AUD
  RENAME TO invoice_AUD;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('054_alter_issue_aud_table_name', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 167, '7:9788baa47c5e6505342004f3fbb61d6e', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::055_create_issue_invoice::astepnowski
ALTER TABLE invoice DROP CONSTRAINT pk_issue_invoice;

ALTER TABLE invoice DROP CONSTRAINT fk_issue_invoice_issue;

ALTER INDEX idx_issue_invoice_issue RENAME TO idx_invoice_issue;

ALTER TABLE invoice add CONSTRAINT pk_invoice PRIMARY KEY (id);

ALTER TABLE invoice add CONSTRAINT fk_issue_invoice_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE ISSUE_INVOICE (
  id            BIGSERIAL   NOT NULL,
  issue_id      BIGINT      NOT NULL,
  invoice_id    BIGINT      NOT NULL,
  addition_date TIMESTAMP   NOT NULL,
  CONSTRAINT pk_issue_invoice PRIMARY KEY (id),
  CONSTRAINT fk_issue_invoice_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id),
  CONSTRAINT fk_issue_invoice_invoice FOREIGN KEY (invoice_id) REFERENCES INVOICE (id)
);

CREATE INDEX idx_issue_invoice_issue ON ISSUE_INVOICE (issue_id);

COMMENT ON TABLE ISSUE_INVOICE IS 'Powiązanie dokumentów obciążeniowych ze zleceniem w celu uniknięcia powielaniu danych w tabeli faktur';

COMMENT ON COLUMN ISSUE_INVOICE.issue_id IS 'Identyfikator zlecenia';

COMMENT ON COLUMN ISSUE_INVOICE.invoice_id IS 'Identyfikator faktury';

COMMENT ON COLUMN ISSUE_INVOICE.addition_date IS 'Data dodania powiązania';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('055_create_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 168, '7:171800b3a3317c48c2631432dbcac673', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::056_alter_issue_invoice::astepnowski
ALTER TABLE ISSUE_INVOICE DROP COLUMN id;

ALTER TABLE ISSUE_INVOICE add primary key (issue_id,invoice_id);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('056_alter_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 169, '7:9b9001a0f3f200ab1bec1393958290c1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::057_alter_company::pnaroznik
ALTER TABLE company
  ADD COLUMN segment VARCHAR(10) NOT NULL DEFAULT 'B';

ALTER TABLE company
  ALTER COLUMN segment DROP DEFAULT;

ALTER TABLE company
  ADD COLUMN involvement NUMERIC(18, 2) NOT NULL DEFAULT 0.00;

ALTER TABLE company
  ALTER COLUMN involvement DROP DEFAULT;

COMMENT ON COLUMN company.segment IS 'segment firmy';

COMMENT ON COLUMN company.involvement IS 'zaangażowanie firmy';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('057_alter_company', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 170, '7:d44f507cc08e2a181ecabdfc3db595a9', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::058_alter_issue_invoice::astepnowski
ALTER TABLE ISSUE_INVOICE ADD COLUMN final_balance NUMERIC(18, 2);

ALTER TABLE ISSUE_INVOICE ADD COLUMN excluded BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN ISSUE_INVOICE.final_balance IS 'Aktualna wartość salda przy zamykaniu zlecenia - wartość w PLN';

COMMENT ON COLUMN ISSUE_INVOICE.excluded IS 'Czy faktura wykluczona z obsługi';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('058_alter_issue_invoice', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 171, '7:cb2b8e9355b24023d09541f67c509ed6', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::059_alter_company::pnaroznik
ALTER TABLE company
  ALTER COLUMN segment DROP NOT NULL;

UPDATE company
SET segment = NULL;

ALTER TABLE company
  ALTER COLUMN involvement DROP NOT NULL;

UPDATE company
SET involvement = NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('059_alter_company', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 172, '7:f63eaa1aa6c694cd4b1280850b846761', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::060_alter_issue_type::pnaroznik
CREATE TABLE issue_type_duration (
  id            BIGSERIAL   NOT NULL,
  issue_type_id BIGINT      NOT NULL,
  segment       VARCHAR(10) NOT NULL,
  duration      NUMERIC(4)  NOT NULL,
  CONSTRAINT pk_issue_type_duration PRIMARY KEY (id),
  CONSTRAINT idx_issue_type_length_type_id_and_segment UNIQUE (issue_type_id, segment)
);

COMMENT ON COLUMN issue_type_duration.segment IS 'segment zlecenia';

COMMENT ON COLUMN issue_type_duration.duration IS 'długość trwania zlecenia w dniach';

COMMENT ON COLUMN issue_type_duration.issue_type_id IS 'identyfikaotr typu zlecenia';

ALTER TABLE company_aud
  ADD COLUMN segment VARCHAR(10);

ALTER TABLE company_aud
  ADD COLUMN involvement NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('060_alter_issue_type', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 173, '7:a38f04e508d658d66645fcd4dfa39730', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::061_alter_issue_type::pnaroznik
INSERT INTO issue_type_duration (issue_type_id, segment, duration)
  SELECT id, 'B', 15 from issue_type;

INSERT INTO issue_type_duration (issue_type_id, segment, duration)
  SELECT id, 'A', 10 from issue_type;

ALTER TABLE issue_type
  DROP COLUMN case_length;

ALTER TABLE issue_type_aud
  DROP COLUMN case_length;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('061_alter_issue_type', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 174, '7:f4715ad997bd07e0793948e9ba531d2c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::062_alter_permission::astepnowski
ALTER TABLE PERMISSION ADD COLUMN allowed_to_terminate_issue BOOLEAN NOT NULL;

ALTER TABLE PERMISSION ADD COLUMN allowed_to_exclude_invoice BOOLEAN NOT NULL;

ALTER TABLE PERMISSION_AUD ADD COLUMN allowed_to_terminate_issue BOOLEAN NOT NULL;

ALTER TABLE PERMISSION_AUD ADD COLUMN allowed_to_exclude_invoice BOOLEAN NOT NULL;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('062_alter_permission', 'astepnowski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 175, '7:a024e3cf4303935f7ac469cf27f3d90f', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::063_alter_company_person::pnaroznik
DROP TABLE company_person_AUD;

ALTER TABLE company_person
  ADD COLUMN contactPerson BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE company_person
  ALTER COLUMN contactPerson DROP DEFAULT;

COMMENT ON COLUMN company_person.contactPerson IS 'Czy osoba jest osobą do kontaktu w firmie?';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('063_alter_company_person', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 176, '7:cc9b065f51a57db5585a52e703261f2c', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::064_alter_person::pnaroznik
ALTER TABLE person
  ALTER COLUMN pesel DROP NOT NULL;

ALTER TABLE person
  ALTER COLUMN person_id DROP NOT NULL;

ALTER TABLE person_aud
  ADD COLUMN country VARCHAR(100);

UPDATE person_aud
SET country = (SELECT c.long_name
               FROM country_aud c
               WHERE c.id = country_id);

ALTER TABLE person_aud
  DROP COLUMN country_id;

DROP TABLE country_aud;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('064_alter_person', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 177, '7:c72c45c0fc60f68053231b9a23ce52c0', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::065_alter_contact_detail::pnaroznik
ALTER TABLE contact_detail
  ADD COLUMN send_automatic_sms BOOLEAN;

COMMENT ON COLUMN contact_detail.send_automatic_sms IS 'Czy system może wysyłać automatyczne smsy na ten numer?';

ALTER TABLE contact_detail_aud
  ADD COLUMN send_automatic_sms BOOLEAN;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('065_alter_contact_detail', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 178, '7:b7c2c46bd070d89df8372085945f39ad', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::066_alter_comapny_with_pkd::pnaroznik
ALTER TABLE company
  ADD COLUMN pk_code VARCHAR(50);

ALTER TABLE company
  ADD COLUMN pk_name VARCHAR(200);

COMMENT ON COLUMN company.pk_code IS 'Kod pkd firmy';

COMMENT ON COLUMN company.pk_name IS 'Nazwa pkd firmy';

ALTER TABLE company_aud
  ADD COLUMN pk_code VARCHAR(50);

ALTER TABLE company_aud
  ADD COLUMN pk_name VARCHAR(200);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('066_alter_comapny_with_pkd', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 179, '7:82cc9ba580d278d6b9f41024c8e0065d', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::067_alter_comapny_with_pkd::pnaroznik
ALTER TABLE company
  RENAME COLUMN pk_code TO pkd_code;

ALTER TABLE company
  RENAME COLUMN pk_name TO pkd_name;

ALTER TABLE company_aud
  RENAME COLUMN pk_code TO pkd_code;

ALTER TABLE company_aud
  RENAME COLUMN pk_name TO pkd_name;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('067_alter_comapny_with_pkd', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 180, '7:bc9eaec2a7cdc151c9d1fab3d44a31d1', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::068_invoice_add_paid::tsliwinski
ALTER TABLE invoice
  ADD COLUMN paid NUMERIC(18, 2) NOT NULL DEFAULT 0;

COMMENT ON COLUMN invoice.paid IS 'Kwota wpłat';

ALTER TABLE invoice_AUD
  ADD COLUMN paid NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('068_invoice_add_paid', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 181, '7:64dd19caa1fc0c22e5cbe9c65e8bbddd', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::069_alter_notification.sql::pnaroznik
ALTER TABLE notification
  ADD COLUMN read_confirmation_required BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN notification.read_confirmation_required IS 'Czy powiadomienie wymaga potwierdzenia przeczytania';

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('069_alter_notification.sql', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 182, '7:0ecf8faae8c4a319e4e75fd9160c67c5', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::070_invoice_remove_issue_id.sql::tsliwinski
ALTER TABLE INVOICE
  DROP CONSTRAINT fk_issue_invoice_issue;

ALTER TABLE INVOICE
  DROP COLUMN issue_id;

ALTER TABLE INVOICE_AUD
  DROP COLUMN issue_id;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('070_invoice_remove_issue_id.sql', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 183, '7:0ce309dd79817e1d03c132e3dba54e6b', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::012_insert_default_admin_user::pnaroznik
INSERT INTO alwin_user
(first_name, last_name, email, creation_date, update_date, phone_number)
VALUES ('Admin', 'Admin', 'admin@admin', now(), now(), NULL);

INSERT INTO alwin_operator
(operator_type_id, alwin_user_id, parent_alwin_operator_id, permission_id, active, login, password, salt)
VALUES (
  (SELECT id
   FROM operator_type
   WHERE type_name = 'ADMIN'),
  (SELECT id
   FROM alwin_user
   WHERE email = 'admin@admin'),
  NULL, NULL, TRUE,
  'admin',
  'c4e0590c6d6a0bda72be5068faad49129d9b2695a2f6258e224a4c6ad7f3ace9638a55506951c88747c08ac150d039517dcf76cd62f2758f42b885705887d567',
  'b87f4720b4d8bbf71e2d3cfaf393ddd0a084ea0b7e60ece81d204be72e39df70d32f80fc31085034847c74af0f88a1fcf749ca2c061993d1439b27da0ccb94d0');

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('012_insert_default_admin_user', 'pnaroznik', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 184, '7:0eb9a74e582662baf7c80d2b4f931255', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Changeset src/main/dbschema/1.0.0/db.changelog.xml::071_issue_add_eur_balances::tsliwinski
ALTER TABLE issue
  RENAME rpb TO rpb_pln;

ALTER TABLE issue
  RENAME balance_start TO balance_start_pln;

ALTER TABLE issue
  RENAME balance_additional TO balance_additional_pln;

ALTER TABLE issue
  RENAME payments TO payments_pln;

ALTER TABLE issue
  ADD COLUMN rpb_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;

ALTER TABLE issue
  ADD COLUMN balance_start_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;

ALTER TABLE issue
  ADD COLUMN balance_additional_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;

ALTER TABLE issue
  ADD COLUMN payments_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;

COMMENT ON COLUMN issue.rpb_pln IS 'Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia (PLN)';

COMMENT ON COLUMN issue.balance_start_pln IS 'Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane (PLN)';

COMMENT ON COLUMN issue.balance_additional_pln IS 'Saldo dokumentow, ktore staly sie wymagalne w trakcie obslugi zlecenia (PLN)';

COMMENT ON COLUMN issue.payments_pln IS 'Suma dokonanych wplat (PLN)';

COMMENT ON COLUMN issue.rpb_eur IS 'Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia (EUR)';

COMMENT ON COLUMN issue.balance_start_eur IS 'Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane (EUR)';

COMMENT ON COLUMN issue.balance_additional_eur IS 'Saldo dokumentow, ktore staly sie wymagalne w trakcie obslugi zlecenia (EUR)';

COMMENT ON COLUMN issue.payments_eur IS 'Suma dokonanych wplat (EUR)';

-- audit table

ALTER TABLE issue_aud
  RENAME rpb TO rpb_pln;

ALTER TABLE issue_aud
  RENAME balance_start TO balance_start_pln;

ALTER TABLE issue_aud
  RENAME balance_additional TO balance_additional_pln;

ALTER TABLE issue_aud
  RENAME payments TO payments_pln;

ALTER TABLE issue_aud
  ADD COLUMN rpb_eur NUMERIC(18, 2);

ALTER TABLE issue_aud
  ADD COLUMN balance_start_eur NUMERIC(18, 2);

ALTER TABLE issue_aud
  ADD COLUMN balance_additional_eur NUMERIC(18, 2);

ALTER TABLE issue_aud
  ADD COLUMN payments_eur NUMERIC(18, 2);

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('071_issue_add_eur_balances', 'tsliwinski', 'src/main/dbschema/1.0.0/db.changelog.xml', NOW(), 185, '7:dc769ff2704618049276ac069134e51a', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '0952722297');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

