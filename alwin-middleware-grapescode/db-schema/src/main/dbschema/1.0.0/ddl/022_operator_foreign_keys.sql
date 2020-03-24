DROP INDEX idx_issue_alwin_user;
DROP INDEX idx_issue_excluding_alwin_user;

ALTER TABLE ACTIVITY
  DROP CONSTRAINT fk_activity_alwin_user;
ALTER TABLE ACTIVITY
  ADD CONSTRAINT fk_activity_alwin_operator FOREIGN KEY (operator_id) REFERENCES ALWIN_OPERATOR (id);

ALTER TABLE ISSUE
  DROP CONSTRAINT fk_issue_alwin_user;
ALTER TABLE ISSUE
  DROP CONSTRAINT fk_issue_excluding_alwin_user;

UPDATE ISSUE i
SET operator_id = (SELECT o.id
                   FROM ALWIN_OPERATOR o
                     JOIN ALWIN_USER u ON o.alwin_user_id = u.id
                   WHERE u.id = i.operator_id
                   LIMIT 1);

ALTER TABLE ISSUE
  ADD CONSTRAINT fk_issue_alwin_operator FOREIGN KEY (operator_id) REFERENCES ALWIN_OPERATOR (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ISSUE
  ADD CONSTRAINT fk_issue_excluding_alwin_operator FOREIGN KEY (excluding_operator_id) REFERENCES ALWIN_OPERATOR (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE INDEX idx_issue_alwin_operator
  ON ISSUE (operator_id);

CREATE INDEX idx_issue_excluding_alwin_operator
  ON ISSUE (excluding_operator_id);

ALTER TABLE ALWIN_USER
  DROP CONSTRAINT fk_alwin_user_alwin_role;

ALTER TABLE ALWIN_USER
  DROP COLUMN alwin_role_id;

ALTER TABLE ALWIN_USER
  DROP COLUMN login;

ALTER TABLE ALWIN_USER
  DROP COLUMN "password";

ALTER TABLE ALWIN_USER
  DROP COLUMN salt;

DROP TABLE ALWIN_ROLE;
