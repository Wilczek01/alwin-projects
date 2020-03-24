--------------------------------------------------------------------------------------
-- activity_type
--------------------------------------------------------------------------------------

CREATE TABLE activity_type (
  id                   BIGSERIAL          NOT NULL,
  name                 VARCHAR(100)       NOT NULL,
  can_be_planned       BOOL               NOT NULL,
  charge_min           NUMERIC(18, 2)     NOT NULL,
  charge_max           NUMERIC(18, 2),
  may_be_automated     BOOL DEFAULT FALSE NOT NULL,
  may_have_declaration BOOL               NOT NULL,
  specific             BOOL               NOT NULL,
  CONSTRAINT pk_activity_type PRIMARY KEY (id)
);

COMMENT ON TABLE activity_type IS 'Tabela typów czynności';
COMMENT ON COLUMN activity_type.name IS 'Nazwa typu czynności';
COMMENT ON COLUMN activity_type.can_be_planned IS 'Czy czynność może zostać zaplanowana.';
COMMENT ON COLUMN activity_type.charge_min IS 'Minimalna wartość opłaty dodatkowej';
COMMENT ON COLUMN activity_type.charge_max IS 'Maksymalna wartość opłaty dodatkowej. Jeżeli opłata stała - nie w przedziale - null.';
COMMENT ON COLUMN activity_type.may_be_automated IS 'Czy czynność może być wykonana przez system.';
COMMENT ON COLUMN activity_type.may_have_declaration IS 'Czy do czynności można dodać deklarację.';
COMMENT ON COLUMN activity_type.specific IS 'Czy czynność szczególna.';

--------------------------------------------------------------------------------------
-- activity
--------------------------------------------------------------------------------------

CREATE TABLE activity (
  id               BIGSERIAL   NOT NULL,
  issue_id         BIGINT      NOT NULL,
  operator_id      BIGINT      NOT NULL,
  activity_type_id BIGINT      NOT NULL,
  activity_date    DATE,
  planned_date     DATE,
  creation_date    DATE        NOT NULL,
  activity_state   VARCHAR(50) NOT NULL DEFAULT 'PLANNED',
  remark           TEXT,
  charge           NUMERIC(18, 2),
  invoice_id       VARCHAR(30),
  CONSTRAINT pk_activity PRIMARY KEY (id),
  CONSTRAINT fk_activity_alwin_user FOREIGN KEY (operator_id) REFERENCES alwin_user (id),
  CONSTRAINT fk_activity_issue FOREIGN KEY (issue_id) REFERENCES issue (id),
  CONSTRAINT fk_activity_activity_type FOREIGN KEY (activity_type_id) REFERENCES activity_type (id)
);

CREATE INDEX idx_activity_issue
  ON activity (issue_id);

CREATE INDEX idx_activity_operator
  ON activity (operator_id);

CREATE INDEX idx_activity_activity_type
  ON activity (activity_type_id);

COMMENT ON TABLE activity IS 'Tabela czynności windykacyjnych';
COMMENT ON COLUMN activity.issue_id IS 'Link do tabeli zleceń';
COMMENT ON COLUMN activity.operator_id IS 'Link do operatora wykonującego czynność';
COMMENT ON COLUMN activity.activity_type_id IS 'Link do typu czynności';
COMMENT ON COLUMN activity.activity_date IS 'Data wykonania czynności';
COMMENT ON COLUMN activity.planned_date IS 'Zaplanowana data wykonania czynności';
COMMENT ON COLUMN activity.creation_date IS 'Data utworzenia czynności';
COMMENT ON COLUMN activity.activity_state IS 'Stan czynności';
COMMENT ON COLUMN activity.remark IS 'Uwaga/komentarz do czynności';
COMMENT ON COLUMN activity.charge IS 'Opłata naliczona za zdarzenie';
COMMENT ON COLUMN activity.invoice_id IS 'Nr faktury za opłate dodatkową - odniesienie do systemu księgowego.';

--------------------------------------------------------------------------------------
-- activity_detail_property
--------------------------------------------------------------------------------------

CREATE TABLE activity_detail_property (
  id            BIGSERIAL    NOT NULL,
  property_name VARCHAR(100) NOT NULL,
  property_type VARCHAR      NOT NULL,
  CONSTRAINT pk_activity_detail_type PRIMARY KEY (id)
);

COMMENT ON TABLE activity_detail_property IS 'Lista cech dodatkowych zdarzeń';
COMMENT ON COLUMN activity_detail_property.property_name IS 'Nazwa typu cechy dodatkowej';
COMMENT ON COLUMN activity_detail_property.property_type IS 'Typ danych cechy';

--------------------------------------------------------------------------------------
-- activity_detail
--------------------------------------------------------------------------------------

CREATE TABLE activity_detail (
  id                          BIGSERIAL     NOT NULL,
  activity_detail_property_id BIGINT        NOT NULL,
  activity_id                 BIGINT        NOT NULL,
  property_value              VARCHAR(1000) NOT NULL,
  CONSTRAINT pk_activity_detail PRIMARY KEY (id),
  CONSTRAINT fk_activity_detail_property FOREIGN KEY (activity_detail_property_id) REFERENCES activity_detail_property (id),
  CONSTRAINT fk_activity_detail_activity FOREIGN KEY (activity_id) REFERENCES activity (id)
);

CREATE INDEX idx_activity_detail_property
  ON activity_detail (activity_detail_property_id);

CREATE INDEX idx_activity_detail_activity
  ON activity_detail (activity_id);

COMMENT ON TABLE activity_detail IS 'Tabela szczegółów zdarzeń';
COMMENT ON COLUMN activity_detail.property_value IS 'Wartość cechy dodatkowej';
COMMENT ON COLUMN activity_detail.activity_detail_property_id IS 'Link do tabeli cech dodatkowych zdarzeń';
COMMENT ON COLUMN activity_detail.activity_id IS 'Link do tabeli czynności windykacyjnych';

--------------------------------------------------------------------------------------
-- activity_type_has_detail_property
--------------------------------------------------------------------------------------

CREATE TABLE activity_type_has_detail_property (
  activity_type_id            BIGINT NOT NULL,
  activity_detail_property_id BIGINT NOT NULL,
  CONSTRAINT idx_activity_detail_schema PRIMARY KEY (activity_type_id, activity_detail_property_id),
  CONSTRAINT fk_activity_detail_schema_activity_type FOREIGN KEY (activity_type_id) REFERENCES activity_type (id),
  CONSTRAINT fk_activity_detail_schema_activity_detail_property FOREIGN KEY (activity_detail_property_id) REFERENCES activity_detail_property (id)
);

CREATE INDEX idx_activity_detail_schema_activity_type
  ON activity_type_has_detail_property (activity_type_id);

CREATE INDEX idx_activity_detail_schema_activity_detail_property
  ON activity_type_has_detail_property (activity_detail_property_id);

COMMENT ON TABLE activity_type_has_detail_property IS 'Tabela asocjacyjna - schemat cech dodatkowych dla typu czynności';
COMMENT ON COLUMN activity_type_has_detail_property.activity_type_id IS 'Link do tabeli typów czynności';
COMMENT ON COLUMN activity_type_has_detail_property.activity_detail_property_id IS 'Link do tabeli cech dodatkowych zdarzeń';

--------------------------------------------------------------------------------------
-- declaration
--------------------------------------------------------------------------------------

CREATE TABLE declaration (
  id                      BIGSERIAL      NOT NULL,
  activity_id             BIGINT         NOT NULL,
  declaration_date        DATE           NOT NULL,
  declared_payment_date   DATE           NOT NULL,
  declared_payment_amount NUMERIC(18, 2) NOT NULL,
  cash_paid               NUMERIC(18, 2),
  CONSTRAINT pk_declaration PRIMARY KEY (id),
  CONSTRAINT fk_declaration_activity FOREIGN KEY (activity_id) REFERENCES activity (id)
);

CREATE INDEX idx_declaration_activity
  ON declaration (activity_id);

COMMENT ON TABLE declaration IS 'Tabela deklaracji splaty';
COMMENT ON COLUMN declaration.activity_id IS 'Link do tabeli czynności windykacyjnych';
COMMENT ON COLUMN declaration.declaration_date IS 'Data złożenia deklaracji';
COMMENT ON COLUMN declaration.declared_payment_date IS 'Zadeklarowana data zapłaty';
COMMENT ON COLUMN declaration.cash_paid IS 'Kwota faktycznie zapłacona';

--------------------------------------------------------------------------------------
-- default_issue_action
--------------------------------------------------------------------------------------

CREATE TABLE default_issue_activity (
  id               BIGSERIAL                           NOT NULL,
  issue_type_id    BIGINT                              NOT NULL,
  activity_type_id BIGINT                              NOT NULL,
  default_day      INTEGER                             NOT NULL,
  version          INTEGER                             NOT NULL,
  creating_date    TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_default_issue_activity PRIMARY KEY (id),
  CONSTRAINT fk_issue_type FOREIGN KEY (issue_type_id) REFERENCES issue_type (id),
  CONSTRAINT fk_activity_type FOREIGN KEY (activity_type_id) REFERENCES activity_type (id)
);

COMMENT ON TABLE default_issue_activity IS 'Domyślne czynności windykacyjne dla zlecenia';
COMMENT ON COLUMN default_issue_activity.id IS 'Identyfikator';
COMMENT ON COLUMN default_issue_activity.issue_type_id IS 'Identyfikator typu zlecenia';
COMMENT ON COLUMN default_issue_activity.activity_type_id IS 'Identyfikator typu czynności';
COMMENT ON COLUMN default_issue_activity.default_day IS 'Standardowy dzień obsługi';
COMMENT ON COLUMN default_issue_activity.version IS 'Wersja czynności';
COMMENT ON COLUMN default_issue_activity.creating_date IS 'Data utworzenia podanej wersji czynności';
