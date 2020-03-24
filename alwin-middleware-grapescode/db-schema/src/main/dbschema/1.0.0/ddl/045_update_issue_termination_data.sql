ALTER TABLE issue
  ADD COLUMN termination_date TIMESTAMP;

ALTER TABLE issue
  ADD COLUMN termination_operator_id BIGINT;

ALTER TABLE issue
  ADD CONSTRAINT fk_issue_termination_operator_id FOREIGN KEY (termination_operator_id) REFERENCES ALWIN_OPERATOR (id);