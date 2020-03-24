CREATE TABLE TAG_ICON (
  id   BIGSERIAL   NOT NULL,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT pk_tag_icon PRIMARY KEY (id)
);

COMMENT ON COLUMN tag_icon.id IS 'Identyfikator symbolu etykiety';
COMMENT ON COLUMN tag_icon.name IS 'Nazwa symbolu etykiety (zgodna z ikonami material design)';

CREATE TABLE TAG_ICON_AUD (
  id      BIGSERIAL,
  rev     INTEGER NOT NULL,
  revtype SMALLINT,
  name    VARCHAR(50),
  CONSTRAINT pk_tag_icon_aud PRIMARY KEY (id, rev)
)
