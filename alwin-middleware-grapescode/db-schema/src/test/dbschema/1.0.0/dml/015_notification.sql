INSERT INTO NOTIFICATION (issue_id, creation_date, message, sender_id, recipient_id) VALUES (1, current_timestamp, 'Powiadomienie nieodczytane nr 1', 9, 1);

INSERT INTO NOTIFICATION (issue_id, creation_date, message, sender_id, recipient_id) VALUES (21, current_timestamp, 'Powiadomienie nieodczytane nr 2', 9, 2);

INSERT INTO NOTIFICATION (issue_id, creation_date, read_date, message, sender_id, recipient_id)
VALUES (1, '2017-11-19', '2017-11-30', 'Powiadomienie odczytane nr 1', 9, 1);

INSERT INTO NOTIFICATION (issue_id, creation_date, read_date, message, sender_id, recipient_id)
VALUES (21, '2017-11-21', '2017-11-30', 'Powiadomienie odczytane nr 2', 9, 2);

