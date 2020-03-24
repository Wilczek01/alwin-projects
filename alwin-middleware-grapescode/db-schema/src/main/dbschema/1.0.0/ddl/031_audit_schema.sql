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