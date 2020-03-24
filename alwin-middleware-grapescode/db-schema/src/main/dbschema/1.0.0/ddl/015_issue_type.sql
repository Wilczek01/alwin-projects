CREATE TABLE ISSUE_TYPE (
  id    BIGSERIAL    NOT NULL,
  name  VARCHAR(100) NOT NULL,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_issue_type PRIMARY KEY (id),
  CONSTRAINT idx_issue_type_name UNIQUE (name)
);

COMMENT ON TABLE ISSUE_TYPE IS 'typ zlecenia';
COMMENT ON COLUMN ISSUE_TYPE.id IS 'identyfikator roli';
COMMENT ON COLUMN ISSUE_TYPE.name IS 'nazwa roli';
COMMENT ON COLUMN ISSUE_TYPE.label IS 'opis roli';

CREATE TABLE ISSUE_TYPE_TRANSITION (
  id                   BIGSERIAL    NOT NULL,
  closed_issue_type_id BIGINT       NOT NULL,
  child_issue_type_id  BIGINT       NOT NULL,
  condition            VARCHAR(100) NOT NULL,
  CONSTRAINT pk_issue_type_transition PRIMARY KEY (id),
  CONSTRAINT fk_closed_issue_type_id FOREIGN KEY (closed_issue_type_id) REFERENCES ISSUE_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_child_issue_type_id FOREIGN KEY (child_issue_type_id) REFERENCES ISSUE_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE ISSUE_TYPE_TRANSITION IS 'przepływ zlecen';
COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.id IS 'identyfikator przepływu zlecenia';
COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.closed_issue_type_id IS 'konczone zlecenie';
COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.child_issue_type_id IS 'nastepne zlecenie';
COMMENT ON COLUMN ISSUE_TYPE_TRANSITION.condition IS 'dodatkowe warunki do przepływu zlecenia';