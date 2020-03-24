ALTER TABLE activity_type
  ALTER COLUMN key SET NOT NULL;

CREATE UNIQUE INDEX uactivity_type_key_idx
  ON activity_type (key);

ALTER TABLE activity_detail_property
  ALTER COLUMN key SET NOT NULL;

CREATE UNIQUE INDEX activity_detail_property_idx
  ON activity_detail_property (key);