INSERT INTO COUNTRY (id, long_name) VALUES (10001, 'Polska'), (10002, 'Słowacja');

INSERT INTO COMPANY (id, ext_company_id, company_name, nip, regon, postal_code, city, prefix, street, email, correspondence_postal_code, correspondence_city,
                     correspondence_prefix, correspondence_street, krs, recipient_name, rating, rating_date, contact_person, phone_number_1, phone_number_2,
                     phone_number_3, mobile_phone_number, documents_email, office_email, website_address, external_db_agreement, external_db_agreement_date)
VALUES (10001, 2, 'Nazwa Firmy Sp. z o.o.', '123456789', '123456', '01-234', 'Warszawa', 'aleje', 'Jerozolimskie 8', 'contat@company.pl', '02-567',
  'Kraków', 'ul.', 'Kalwaryjska 3', '98765432234', 'Mikołaj Rej', 'Q01', CURRENT_TIMESTAMP, 'Jan Kochanowski', '220987654', '221234567', '225678901',
        '0048123456789', 'documents@email.pl', 'test@test.pl', 'http://www.google.pl', TRUE, CURRENT_TIMESTAMP);

INSERT INTO PERSON (id, person_id, pesel, first_name, last_name, representative, address, marital_status, id_doc_country, id_doc_number, id_doc_signed_by,
                    id_doc_sign_date, politician, job_function, birth_date, real_beneficiary, id_doc_type, country_id) VALUES
  (10002, 2, '81011101234', 'Zuzana', 'Fialová', FALSE, 'Banská Bystrica, Sládkovičova 54', 1, 'SK', 'SAA12456', 'Rodne číslo',
   CURRENT_TIMESTAMP, FALSE, 'Všeobecná poisťovňa', CURRENT_TIMESTAMP, FALSE, 1, 10002),
  (10003, 3, '88010101234', 'Jan', 'Kowalsky', 'true', 'ul. Testowa 4/1', 0, 'PL', 'QWE123456', 'Prezydent Miasta Radzionkowo', CURRENT_TIMESTAMP, FALSE,
   'Konserwator nawierzchni', CURRENT_TIMESTAMP, FALSE, 1, 10001);

INSERT INTO COMPANY_PERSON (company_id, person_id) VALUES (10001, 10002), (10001, 10003);
INSERT INTO CUSTOMER (id, company_id, person_id) VALUES (10001, 10001, NULL);

UPDATE ISSUE
SET customer_id = 10001
WHERE ID = 10028;