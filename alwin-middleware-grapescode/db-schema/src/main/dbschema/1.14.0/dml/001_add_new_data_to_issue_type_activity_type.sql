INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id FROM issue_type WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id FROM activity_type WHERE "key" = 'OUTGOING_PHONE_CALL'));

INSERT INTO issue_type_activity_type (issue_type_id, activity_type_id)
VALUES ((SELECT id FROM issue_type WHERE "name" = 'DIRECT_DEBT_COLLECTION'), (SELECT id FROM activity_type WHERE "key" = 'FAILED_PHONE_CALL_ATTEMPT'));