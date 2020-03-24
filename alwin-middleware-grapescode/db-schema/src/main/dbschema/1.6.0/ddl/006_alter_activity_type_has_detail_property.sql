ALTER TABLE activity_type_has_detail_property ADD COLUMN id BIGSERIAL;
ALTER TABLE activity_type_has_detail_property ADD COLUMN state VARCHAR(100) NOT NULL DEFAULT 'EXECUTED';
ALTER TABLE activity_type_has_detail_property ADD COLUMN required BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE activity_type_has_detail_property DROP CONSTRAINT idx_activity_detail_schema;
ALTER TABLE activity_type_has_detail_property ADD CONSTRAINT pk_activity_type_detail_property PRIMARY KEY (id);
ALTER TABLE activity_type_has_detail_property ADD CONSTRAINT idx_activity_type_detail_property UNIQUE (activity_type_id, activity_detail_property_id, state);

COMMENT ON COLUMN activity_type_has_detail_property.state IS 'Stan w odniesieniu do cech dodatkowych dla typu czynności';
COMMENT ON COLUMN activity_type_has_detail_property.required IS 'Czy wymagane w odniesieniu do cech dodatkowych dla typu czynności';

ALTER TABLE activity_type_has_detail_property_aud ADD COLUMN id BIGSERIAL;
ALTER TABLE activity_type_has_detail_property_aud ADD COLUMN state VARCHAR(100);
ALTER TABLE activity_type_has_detail_property_aud ADD COLUMN required BOOLEAN;
