INSERT INTO activity_detail_property (property_name, property_type, property_key) VALUES ('Czy zastano klienta', 'java.lang.Boolean', 'CUSTOMER_WAS_PRESENT');
INSERT INTO activity_detail_property (property_name, property_type, property_key) VALUES ('Zdjęcie', 'java.lang.String', 'ATTACHMENT');

UPDATE activity_type SET may_have_declaration = true WHERE key = 'FIELD_VISIT';

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'CUSTOMER_WAS_PRESENT'),
   'PLANNED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'CUSTOMER_WAS_PRESENT'),
   'EXECUTED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'ATTACHMENT'),
   'PLANNED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'ATTACHMENT'),
   'EXECUTED'
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE key = 'FIELD_VISIT'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_key = 'PHONE_CALL_PERSON'),
   'EXECUTED'
);

