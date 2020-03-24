INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_2'),
  (SELECT id
   FROM activity_type
   WHERE key = 'OUTGOING_PHONE_CALL'),
  0, 1, current_timestamp);
