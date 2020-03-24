INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'DIRECT_DEBT_COLLECTION'),
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  2, 1, current_timestamp);

UPDATE activity_type SET can_be_planned = true WHERE key = 'FIELD_VISIT';

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'REMARK'),
   'PLANNED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'REMARK'),
   'EXECUTED'
);