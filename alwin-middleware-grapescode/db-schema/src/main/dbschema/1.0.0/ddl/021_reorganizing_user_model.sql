CREATE TABLE PERMISSION (
  id                                         BIGSERIAL NOT NULL,
  allowed_to_externally_plan_activities      BOOLEAN   NOT NULL,
  allowed_to_change_activity_charge          BOOLEAN   NOT NULL,
  allowed_to_change_activity_charge_percent  BOOLEAN   NOT NULL,
  allowed_to_mark_own_attachment_deleted     BOOLEAN   NOT NULL,
  allowed_to_mark_others_attachments_deleted BOOLEAN   NOT NULL,
  allowed_to_transfer_issues_internally      BOOLEAN   NOT NULL,
  allowed_to_transfer_issues_externally      BOOLEAN   NOT NULL,
  allowed_to_exclude_issue                   BOOLEAN   NOT NULL,
  allowed_to_set_customer_out_of_service     BOOLEAN   NOT NULL,
  CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE OPERATOR_TYPE (
  id         BIGSERIAL    NOT NULL,
  type_name  VARCHAR(100) NOT NULL,
  type_label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_operator_type PRIMARY KEY (id),
  CONSTRAINT idx_operator_type_name UNIQUE (type_name)
);

CREATE TABLE ALWIN_OPERATOR (
  id                       BIGSERIAL    NOT NULL,
  operator_type_id         BIGINT       NOT NULL,
  alwin_user_id            BIGINT       NOT NULL,
  parent_alwin_operator_id BIGINT,
  permission_id           BIGINT,
  active                   BOOLEAN      NOT NULL,
  login                    VARCHAR(40)  NOT NULL,
  "password"               VARCHAR(255) NOT NULL,
  salt                     VARCHAR(255) NOT NULL,
  CONSTRAINT pk_alwin_operator PRIMARY KEY (id),
  CONSTRAINT fk_alwin_operator_operator_type FOREIGN KEY (operator_type_id) REFERENCES OPERATOR_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alwin_operator_alwin_user FOREIGN KEY (alwin_user_id) REFERENCES alwin_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alwin_operator_alwin_operator FOREIGN KEY (parent_alwin_operator_id) REFERENCES ALWIN_OPERATOR (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alwin_operator_permission FOREIGN KEY (permission_id) REFERENCES PERMISSION (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE OPERATOR_TYPE_ISSUE_TYPE (
  id               BIGSERIAL NOT NULL,
  operator_type_id BIGINT    NOT NULL,
  issue_type_id    BIGINT    NOT NULL,
  CONSTRAINT pk_operator_type_issue_type PRIMARY KEY (id),
  CONSTRAINT fk_operator_type_issue_type_operator_type FOREIGN KEY (operator_type_id) REFERENCES OPERATOR_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_operator_type_issue_type_issue_type FOREIGN KEY (issue_type_id) REFERENCES ISSUE_TYPE (id) ON DELETE CASCADE ON UPDATE CASCADE
);