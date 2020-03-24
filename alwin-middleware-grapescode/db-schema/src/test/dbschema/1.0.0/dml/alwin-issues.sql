--
INSERT INTO COMPANY (id, ext_company_id, company_name, nip, regon)
VALUES (10000, 1, 'NIEPUBLICZNY ZAKŁAD OPIEKI ZDROWOTNEJ PRZYCHODNIA LEKARSKA TRZECH WIESZCZÓW', '5732591641', '240031641');
INSERT INTO PERSON (id, person_id, pesel, first_name, last_name) VALUES (10000, 1, '12345678901', 'Danuta Krystyna', 'Wink');
INSERT INTO CUSTOMER (id, company_id, person_id) VALUES (10000, 10000, 10000);

--
INSERT INTO CONTRACT (id, customer_id, ext_contract_id) VALUES (10000, 10000, 1243);

--
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats)
VALUES (10000, (SELECT ID
                FROM ALWIN_USER
                WHERE LOGIN = 'amickiewicz'),
               10000, 10000,
               '2017-07-10', '2017-08-10', 'testowy powód 1', 'windykacja telefoniczna', 'NEW', 123432.12, 65432.44, 234642.67, 987654.23, FALSE);

INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats)
VALUES (10002, (SELECT ID
                FROM ALWIN_USER
                WHERE LOGIN = 'amickiewicz'),
               10000, 10000,
               '2018-07-10', '2018-08-10', 'testowy powód 2', 'windykacja telefoniczna', 'NEW', 123432.12, 65432.44, 234642.67, 987654.23, FALSE);

INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats)
VALUES (10003, (SELECT ID
                FROM ALWIN_USER
                WHERE LOGIN = 'amickiewicz'),
               10000, 10000,
               '2019-07-10', '2019-08-10', 'testowy powód 3', 'windykacja telefoniczna', 'NEW', 123432.12, 65432.44, 234642.67, 987654.23, FALSE);

INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats)
VALUES (10004, (SELECT ID
                FROM ALWIN_USER
                WHERE LOGIN = 'amickiewicz'),
               10000, 10000,
               '2020-07-10', '2020-08-10', 'testowy powód 4', 'windykacja telefoniczna', 'NEW', 123432.12, 65432.44, 234642.67, 987654.23, FALSE);

INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats)
VALUES (10005, (SELECT ID
                FROM ALWIN_USER
                WHERE LOGIN = 'amickiewicz'),
               10000, 10000,
               '2020-07-10', '2020-08-10', 'testowy powód 5', 'windykacja telefoniczna', 'NEW', 123432.12, 65432.44, 234642.67, 987654.23, FALSE);

--
INSERT INTO ISSUE_INVOICE (id, issue_id, invoice_id) VALUES (10000, 10000, 7777);
