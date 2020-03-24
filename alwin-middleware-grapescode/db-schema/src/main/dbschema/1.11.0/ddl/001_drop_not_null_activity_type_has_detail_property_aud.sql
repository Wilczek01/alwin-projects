ALTER TABLE activity_type_has_detail_property_AUD
  DROP CONSTRAINT activity_type_has_detail_property_aud_pkey;

ALTER TABLE activity_type_has_detail_property_AUD
  ADD PRIMARY KEY (id, REV);

ALTER TABLE activity_type_has_detail_property_AUD
  ALTER COLUMN activity_detail_property_id DROP NOT NULL;

ALTER TABLE activity_type_has_detail_property_AUD
  ALTER COLUMN activity_type_id DROP NOT NULL;