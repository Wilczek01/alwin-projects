CREATE TABLE Activity_ActivityDetail_AUD (
  REV         INTEGER NOT NULL,
  activity_id BIGINT  NOT NULL,
  id          BIGINT  NOT NULL,
  REVTYPE     SMALLINT,
  PRIMARY KEY (REV, activity_id, id)
);

CREATE TABLE Activity_Declaration_AUD (
  REV         INTEGER NOT NULL,
  activity_id BIGINT  NOT NULL,
  id          BIGINT  NOT NULL,
  REVTYPE     SMALLINT,
  PRIMARY KEY (REV, activity_id, id)
);

CREATE TABLE activity_AUD (
  id               BIGINT  NOT NULL,
  REV              INTEGER NOT NULL,
  REVTYPE          SMALLINT,
  activity_date    TIMESTAMP,
  charge           DECIMAL(19, 2),
  creation_date    TIMESTAMP,
  invoice_id       VARCHAR(255),
  planned_date     TIMESTAMP,
  remark           VARCHAR(255),
  activity_state   VARCHAR(50),
  activity_type_id BIGINT,
  issue_id         BIGINT,
  operator_id      BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_detail_AUD (
  id                          BIGINT  NOT NULL,
  REV                         INTEGER NOT NULL,
  REVTYPE                     SMALLINT,
  property_value              VARCHAR(1000),
  activity_detail_property_id BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_detail_property_AUD (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  property_key  VARCHAR(100),
  property_name VARCHAR(100),
  property_type VARCHAR(255),
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_type_AUD (
  id                   BIGINT  NOT NULL,
  REV                  INTEGER NOT NULL,
  REVTYPE              SMALLINT,
  can_be_planned       BOOLEAN,
  charge_max           DECIMAL(19, 2),
  charge_min           DECIMAL(19, 2),
  customer_contact     BOOLEAN,
  key                  VARCHAR(100),
  may_be_automated     BOOLEAN,
  may_have_declaration BOOLEAN,
  name                 VARCHAR(100),
  specific             BOOLEAN,
  PRIMARY KEY (id, REV)
);

CREATE TABLE activity_type_has_detail_property_AUD (
  REV                         INTEGER NOT NULL,
  activity_type_id            BIGINT  NOT NULL,
  activity_detail_property_id BIGINT  NOT NULL,
  REVTYPE                     SMALLINT,
  PRIMARY KEY (REV, activity_type_id, activity_detail_property_id)
);

CREATE TABLE alwin_operator_AUD (
  id                       BIGINT  NOT NULL,
  REV                      INTEGER NOT NULL,
  REVTYPE                  SMALLINT,
  active                   BOOLEAN,
  login                    VARCHAR(40),
  password                 VARCHAR(255),
  salt                     VARCHAR(255),
  parent_alwin_operator_id BIGINT,
  permission_id            BIGINT,
  operator_type_id         BIGINT,
  alwin_user_id            BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE alwin_user_AUD (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  creation_date TIMESTAMP,
  email         VARCHAR(512),
  first_name    VARCHAR(100),
  last_name     VARCHAR(100),
  phone_number  VARCHAR(30),
  PRIMARY KEY (id, REV)
);

CREATE TABLE contract_AUD (
  id              BIGINT  NOT NULL,
  REV             INTEGER NOT NULL,
  REVTYPE         SMALLINT,
  ext_contract_id VARCHAR(30),
  customer_id     BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE customer_AUD (
  id           BIGINT  NOT NULL,
  REV          INTEGER NOT NULL,
  REVTYPE      SMALLINT,
  debt_segment VARCHAR(255),
  company_id   BIGINT,
  person_id    BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE declaration_AUD (
  id                      BIGINT  NOT NULL,
  REV                     INTEGER NOT NULL,
  REVTYPE                 SMALLINT,
  cash_paid               DECIMAL(19, 2),
  current_balance         DECIMAL(19, 2),
  declaration_date        TIMESTAMP,
  declared_payment_amount DECIMAL(19, 2),
  declared_payment_date   TIMESTAMP,
  monitored               BOOLEAN,
  PRIMARY KEY (id, REV)
);

CREATE TABLE issue_AUD (
  id                      BIGINT  NOT NULL,
  REV                     INTEGER NOT NULL,
  REVTYPE                 SMALLINT,
  balance_additional      DECIMAL(19, 2),
  balance_start           DECIMAL(19, 2),
  balance_update_date     TIMESTAMP,
  created_manually        BOOLEAN,
  excluded_from_stats     BOOLEAN,
  exclusion_cause         VARCHAR(500),
  expiration_date         TIMESTAMP,
  issue_state             VARCHAR(100),
  payments                DECIMAL(19, 2),
  priority                INTEGER,
  rpb                     DECIMAL(19, 2),
  start_date              TIMESTAMP,
  termination_cause       VARCHAR(1000),
  termination_date        TIMESTAMP,
  operator_id             BIGINT,
  contract_id             BIGINT,
  customer_id             BIGINT,
  excluding_operator_id   BIGINT,
  issue_type_id           BIGINT,
  parent_issue_id         BIGINT,
  termination_operator_id BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE issue_invoice_AUD (
  id                BIGINT  NOT NULL,
  REV               INTEGER NOT NULL,
  REVTYPE           SMALLINT,
  contract_number   VARCHAR(255),
  currency          VARCHAR(255),
  current_balance   DECIMAL(19, 2),
  due_date          TIMESTAMP,
  gross_amount      DECIMAL(19, 2),
  invoice_id        VARCHAR(30),
  last_payment_date TIMESTAMP,
  net_amount        DECIMAL(19, 2),
  number            VARCHAR(255),
  type_id           BIGINT,
  issue_id          BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE issue_type_AUD (
  id          BIGINT  NOT NULL,
  REV         INTEGER NOT NULL,
  REVTYPE     SMALLINT,
  case_length INTEGER,
  label       VARCHAR(100),
  name        VARCHAR(100),
  PRIMARY KEY (id, REV)
);

CREATE TABLE operator_type_AUD (
  id                      BIGINT  NOT NULL,
  REV                     INTEGER NOT NULL,
  REVTYPE                 SMALLINT,
  type_label              VARCHAR(100),
  type_name               VARCHAR(100),
  parent_operator_type_id BIGINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE operator_type_issue_type_AUD (
  REV              INTEGER NOT NULL,
  issue_type_id    BIGINT  NOT NULL,
  operator_type_id BIGINT  NOT NULL,
  REVTYPE          SMALLINT,
  PRIMARY KEY (REV, issue_type_id, operator_type_id)
);

CREATE TABLE permission_AUD (
  id                                         BIGINT  NOT NULL,
  REV                                        INTEGER NOT NULL,
  REVTYPE                                    SMALLINT,
  allowed_to_change_activity_charge          BOOLEAN,
  allowed_to_change_activity_charge_percent  BOOLEAN,
  allowed_to_exclude_issue                   BOOLEAN,
  allowed_to_externally_plan_activities      BOOLEAN,
  allowed_to_mark_others_attachments_deleted BOOLEAN,
  allowed_to_mark_own_attachment_deleted     BOOLEAN,
  allowed_to_set_customer_out_of_service     BOOLEAN,
  allowed_to_transfer_issues_externally      BOOLEAN,
  allowed_to_transfer_issues_internally      BOOLEAN,
  PRIMARY KEY (id, REV)
);

ALTER TABLE Activity_ActivityDetail_AUD
  ADD CONSTRAINT FKp25f0qwc1ss5tjx86bdkp274s FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE Activity_Declaration_AUD
  ADD CONSTRAINT FK98fo92y4m3lg1fno6ncpcvgdy FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE activity_AUD
  ADD CONSTRAINT FKaj3jrmqx0w728g0ueq4orx35c FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE activity_detail_AUD
  ADD CONSTRAINT FKrp74up6w4fj2yaiugck26ybcj FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE activity_detail_property_AUD
  ADD CONSTRAINT FKgfdldd57t34nbv0afk259oncb FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE activity_type_AUD
  ADD CONSTRAINT FK5kahvwyqtjg1lsf6nd77w0q6s FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE activity_type_has_detail_property_AUD
  ADD CONSTRAINT FK2hdmkbnit7lf7amkxtig2bbbo FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE alwin_operator_AUD
  ADD CONSTRAINT FKsj2com32ttjd08bfv9h28p87f FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE alwin_user_AUD
  ADD CONSTRAINT FKih515lnkismggrtfoq7hdnvne FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE contract_AUD
  ADD CONSTRAINT FKf9tfq0prxe8vbsau3bpbmlcya FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE customer_AUD
  ADD CONSTRAINT FKmeoyvv2wyms32m3q8xrby1lox FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE declaration_AUD
  ADD CONSTRAINT FKnyupmn62uvjwcpb85jrjxl8ox FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE issue_AUD
  ADD CONSTRAINT FK99r0ko1ns7art19gyloh1tsrc FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE issue_invoice_AUD
  ADD CONSTRAINT FK2vp072py05tlvikbnoa17pi6t FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE issue_type_AUD
  ADD CONSTRAINT FK1skel0aisimn0sje8mf3jetf6 FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE operator_type_AUD
  ADD CONSTRAINT FKran0g8nleqr6b22slrcnq3859 FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE operator_type_issue_type_AUD
  ADD CONSTRAINT FKlc8glcfria7kd3dj0n5rdcm3g FOREIGN KEY (REV) REFERENCES REVINFO;
ALTER TABLE permission_AUD
  ADD CONSTRAINT FKhgrao1tyqi2ai2pyrq7n9r09q FOREIGN KEY (REV) REFERENCES REVINFO;
