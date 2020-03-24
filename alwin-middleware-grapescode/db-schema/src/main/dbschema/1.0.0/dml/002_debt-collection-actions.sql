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
