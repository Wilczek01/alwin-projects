CREATE TABLE issue_type_activity_type (
  id              BIGSERIAL,
  issue_type_id   BIGINT,
  activity_type_id BIGINT,
  CONSTRAINT pk_issue_type_activity_type PRIMARY KEY (id),
  CONSTRAINT fk_issue_type_activity_type_issue_type_id FOREIGN KEY (issue_type_id) REFERENCES issue_type (id),
  CONSTRAINT fk_issue_type_activity_type_activity_type_id FOREIGN KEY (activity_type_id) REFERENCES activity_type (id)
);

COMMENT ON TABLE issue_type_activity_type IS 'Typy czynnosci przypisane do typow zlecen';
COMMENT ON COLUMN issue_type_activity_type.issue_type_id IS 'Identyfikator typu zlecenia';
COMMENT ON COLUMN issue_type_activity_type.activity_type_id IS 'Identyfikator typu czynnosci';

CREATE TABLE issue_type_activity_type_aud (
  id              BIGSERIAL NOT NULL,
  rev             INTEGER   NOT NULL,
  revtype         SMALLINT,
  issue_type_id   BIGINT,
  activity_type_id BIGINT,
  CONSTRAINT pk_issue_type_activity_type_aud PRIMARY KEY (id, rev)
);