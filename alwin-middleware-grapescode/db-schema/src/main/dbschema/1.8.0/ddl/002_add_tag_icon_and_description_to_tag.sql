ALTER TABLE TAG ADD COLUMN description VARCHAR(500);
ALTER TABLE TAG ADD COLUMN tag_icon_id BIGINT;
UPDATE TAG SET tag_icon_id = (SELECT min(id) FROM tag_icon) WHERE tag_icon_id IS NULL;
ALTER TABLE TAG ALTER COLUMN tag_icon_id SET NOT NULL;

ALTER TABLE TAG ADD CONSTRAINT fk_tag_tag_icon FOREIGN KEY (tag_icon_id) REFERENCES TAG_ICON (id);

ALTER TABLE TAG_AUD ADD COLUMN description VARCHAR(500);
ALTER TABLE TAG_AUD ADD COLUMN tag_icon_id BIGINT;

COMMENT ON COLUMN tag.description IS 'Opis etykiety';
COMMENT ON COLUMN tag.tag_icon_id IS 'Identyfikator symbolu etykiety';
