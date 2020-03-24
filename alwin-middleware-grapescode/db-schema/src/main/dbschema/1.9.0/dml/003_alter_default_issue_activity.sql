INSERT INTO default_issue_activity(issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
    (select id from issue_type where name = 'PHONE_DEBT_COLLECTION_1'),
    (select id from activity_type where key = 'OUTGOING_PHONE_CALL'),
    1,
    (select max(version) + 1 from default_issue_activity where issue_type_id = (select id from issue_type where name = 'PHONE_DEBT_COLLECTION_1') and activity_type_id = (select id from activity_type where key = 'OUTGOING_PHONE_CALL')),
    now());