ALTER SEQUENCE contact_detail_id_seq START 20000 RESTART 20100 MINVALUE 20000;

INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10001, 'Jan Kochanowski', 'CONTACT_PERSON', 'ACTIVE', 'trudno się dodzownić');
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10002, 'Adam Mickiewicz', 'CONTACT_PERSON', 'PREFERRED', 'w godzinach 10-18');
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10003, 'Juliusz Słowacki', 'CONTACT_PERSON', 'INACTIVE', 'nie odbiera');
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10004, '0048123456790', 'MOBILE_PHONE', 'ACTIVE', 'Jan - trudno się dodzownić');
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10005, '0048123456791', 'MOBILE_PHONE', 'PREFERRED', 'Adam -  w godzinach 10-18');
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10006, '0048123456789', 'MOBILE_PHONE', 'INACTIVE', 'Juliusz - nie odbiera');
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10007, '220987654', 'PHONE_NUMBER', 'ACTIVE', NULL);
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10008, '221234567', 'PHONE_NUMBER', 'ACTIVE', NULL);
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10009, '225678901', 'PHONE_NUMBER', 'ACTIVE', NULL);
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10010, 'contat@company.pl', 'E_MAIL', 'ACTIVE', NULL);
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10011, 'documents@email.pl', 'DOCUMENT_E_MAIL', 'ACTIVE', NULL);
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10012, 'test@test.pl', 'OFFICE_E_EMAIL', 'ACTIVE', NULL);
INSERT INTO contact_detail (id, contact, contact_type, state, remark)
VALUES (10013, 'http://www.google.pl', 'INTERNET_ADDRESS', 'ACTIVE', NULL);

INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10001);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10002);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10003);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10004);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10005);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10006);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10007);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10008);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10009);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10010);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10011);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10012);
INSERT INTO company_contact_detail (company_id, contact_detail_id) VALUES (10001, 10013);

ALTER SEQUENCE address_id_seq START 20000 RESTART 20100 MINVALUE 20000;

INSERT INTO address (id, street_prefix, street, house_no, flat_no, post_code, city, country, address_type, remark)
VALUES (10001, 'ul.', 'Kalwaryjska', '3', '10', '02-567', 'Kraków', 'Polska', 'CORRESPONDENCE', 'Test remark');

INSERT INTO address (id, street_prefix, street, house_no, flat_no, post_code, city, country, address_type, remark)
VALUES (10002, 'aleje', 'Jerozolimskie', '8', '19', '01-234', 'Warszawa', 'Polska', 'RESIDENCE', NULL);

INSERT INTO company_address (company_id, address_id) VALUES (10001, 10001);
INSERT INTO company_address (company_id, address_id) VALUES (10001, 10002);