INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  1, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (podsatwowe)')
  , 2, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  7, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (ostateczne)'),
  10, 1, current_timestamp);

INSERT INTO default_issue_activity (issue_type_id, activity_type_id, default_day, version, creating_date)
VALUES (
  (SELECT id
   FROM issue_type
   WHERE name = 'PHONE_DEBT_COLLECTION_1'),
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'), 11, 1, current_timestamp);
