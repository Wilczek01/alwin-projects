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