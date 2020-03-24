CREATE TABLE tag (
  id    BIGSERIAL   NOT NULL,
  name  VARCHAR(50) NOT NULL,
  color VARCHAR(7)  NOT NULL,
  CONSTRAINT pk_tag PRIMARY KEY (id)
);

COMMENT ON TABLE tag IS 'Etykiety dla zlecen';
COMMENT ON COLUMN tag.id IS 'Identyfikator etykiety';
COMMENT ON COLUMN tag.name IS 'Nazwa etykiety';
COMMENT ON COLUMN tag.color IS 'Kolor etykiety';

CREATE TABLE tag_aud (
  id      BIGSERIAL   NOT NULL,
  rev     INTEGER     NOT NULL,
  revtype SMALLINT,
  name    VARCHAR(50) NOT NULL,
  color   VARCHAR(7)  NOT NULL,
  CONSTRAINT pk_tag_aud PRIMARY KEY (id, rev)
);

CREATE TABLE issue_tag (
  id       BIGSERIAL,
  issue_id BIGINT,
  tag_id   BIGINT,
  CONSTRAINT pk_issue_tag PRIMARY KEY (id),
  CONSTRAINT fk_issue_tag_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id),
  CONSTRAINT fk_issue_tag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id)
);

COMMENT ON TABLE issue_tag IS 'Etykiety nadane zleceniom';
COMMENT ON COLUMN issue_tag.issue_id IS 'Identyfikator zlecenia';
COMMENT ON COLUMN issue_tag.tag_id IS 'Identyfikator etykiety';