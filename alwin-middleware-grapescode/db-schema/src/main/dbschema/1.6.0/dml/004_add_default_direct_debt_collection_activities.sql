INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Wizyta terenowa', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'FIELD_VISIT');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Wypowiedzenie warunkowe umowy', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'CONTRACT_TERMINATION');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Wezwanie do zwrotu przedmiotu', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'RETURN_SUBJECT_DEMAND');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Podejrzenie fraudu', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'FRAUD_SUSPECTED');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Odbiór przedmiotu leasingu', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'SUBJECT_COLLECTED');

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned, customer_contact, key)
VALUES ('Potwierdzenie wpłaty klienta', FALSE, 0, NULL, FALSE, FALSE, FALSE, FALSE, 'PAYMENT_COLLECTED_CONFIRMATION');

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'FIELD_VISIT'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'CONTRACT_TERMINATION'));


INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'RETURN_SUBJECT_DEMAND'));


INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'FRAUD_SUSPECTED'));


INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'SUBJECT_COLLECTED'));


INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id
         FROM issue_type
         WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id
                                                    FROM activity_type
                                                    WHERE
                                                      "key" = 'PAYMENT_COLLECTED_CONFIRMATION'));

