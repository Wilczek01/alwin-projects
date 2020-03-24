CREATE TABLE issue_type_duration (
  id            BIGSERIAL   NOT NULL,
  issue_type_id BIGINT      NOT NULL,
  segment       VARCHAR(10) NOT NULL,
  duration      NUMERIC(4)  NOT NULL,
  CONSTRAINT pk_issue_type_duration PRIMARY KEY (id),
  CONSTRAINT idx_issue_type_length_type_id_and_segment UNIQUE (issue_type_id, segment)
);

COMMENT ON COLUMN issue_type_duration.segment IS 'segment zlecenia';
COMMENT ON COLUMN issue_type_duration.duration IS 'długość trwania zlecenia w dniach';
COMMENT ON COLUMN issue_type_duration.issue_type_id IS 'identyfikaotr typu zlecenia';

ALTER TABLE company_aud
  ADD COLUMN segment VARCHAR(10);
ALTER TABLE company_aud
  ADD COLUMN involvement NUMERIC(18, 2)