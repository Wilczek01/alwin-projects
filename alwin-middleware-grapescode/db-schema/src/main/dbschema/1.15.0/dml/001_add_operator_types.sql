INSERT INTO operator_type (type_name, type_label, parent_operator_type_id)
VALUES ('PHONE_DEBT_COLLECTOR_1', 'Windykator telefoniczny sekcja 1', (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR'));

INSERT INTO operator_type (type_name, type_label, parent_operator_type_id)
VALUES ('PHONE_DEBT_COLLECTOR_2', 'Windykator telefoniczny sekcja 2', (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR'));

INSERT INTO operator_type_issue_type (issue_type_id, operator_type_id)
VALUES ((select id from issue_type where "name" = 'PHONE_DEBT_COLLECTION_1'), (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR_1'));

INSERT INTO operator_type_issue_type (issue_type_id, operator_type_id)
VALUES ((select id from issue_type where "name" = 'PHONE_DEBT_COLLECTION_2'), (select id from operator_type where type_name = 'PHONE_DEBT_COLLECTOR_2'));