ALTER TABLE TAG ADD COLUMN predefined BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE TAG ADD COLUMN tag_type VARCHAR(100) NOT NULL DEFAULT 'CUSTOM';

COMMENT ON COLUMN tag.predefined IS 'Okresla czy etykieta jest zdefiniowana przez system';
COMMENT ON COLUMN tag.tag_type IS 'Typ etykiety';