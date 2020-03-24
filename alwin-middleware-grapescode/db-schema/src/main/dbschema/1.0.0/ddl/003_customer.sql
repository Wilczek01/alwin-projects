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

