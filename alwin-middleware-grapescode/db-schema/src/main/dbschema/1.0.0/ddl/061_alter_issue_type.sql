INSERT INTO issue_type_duration (issue_type_id, segment, duration)
  SELECT id, 'B', 15 from issue_type;

INSERT INTO issue_type_duration (issue_type_id, segment, duration)
  SELECT id, 'A', 10 from issue_type;

ALTER TABLE issue_type
  DROP COLUMN case_length;
ALTER TABLE issue_type_aud
  DROP COLUMN case_length;