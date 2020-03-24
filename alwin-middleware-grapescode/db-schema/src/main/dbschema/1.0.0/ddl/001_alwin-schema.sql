CREATE TABLE ALWIN_ROLE (
  id    BIGSERIAL    NOT NULL,
  name  VARCHAR(100) NOT NULL,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_alwin_role PRIMARY KEY (id),
  CONSTRAINT idx_alwin_role_name UNIQUE (name)
);

COMMENT ON TABLE ALWIN_ROLE IS 'role uzytkownikow';
COMMENT ON COLUMN ALWIN_ROLE.id IS 'identyfikator roli';
COMMENT ON COLUMN ALWIN_ROLE.name IS 'nazwa roli';
COMMENT ON COLUMN ALWIN_ROLE.label IS 'opis roli';

CREATE TABLE ALWIN_USER (
  id            BIGSERIAL                           NOT NULL,
  alwin_role_id BIGINT                              NOT NULL,
  first_name    VARCHAR(100)                        NOT NULL,
  last_name     VARCHAR(100)                        NOT NULL,
  login         VARCHAR(40)                         NOT NULL,
  email         VARCHAR(512)                        NOT NULL,
  "password"    VARCHAR(255)                        NOT NULL,
  salt          VARCHAR(255)                        NOT NULL,
  status        VARCHAR(40)                         NOT NULL,
  creation_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  update_date   TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_alwin_user PRIMARY KEY (id),
  CONSTRAINT idx_alwin_user_login UNIQUE (login),
  CONSTRAINT fk_alwin_user_alwin_role FOREIGN KEY (alwin_role_id) REFERENCES ALWIN_ROLE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_alwin_user_role
  ON ALWIN_USER (alwin_role_id);

COMMENT ON TABLE ALWIN_USER IS 'uzytkownicy systemu';
COMMENT ON COLUMN ALWIN_USER.id IS 'identyfikator uzytkownika';
COMMENT ON COLUMN ALWIN_USER.alwin_role_id IS 'identyfikator roli uzytkownika';
COMMENT ON COLUMN ALWIN_USER.first_name IS 'imie uzytkownika';
COMMENT ON COLUMN ALWIN_USER.last_name IS 'nazwisko uzytkownika';
COMMENT ON COLUMN ALWIN_USER.login IS 'login uzytkownika';
COMMENT ON COLUMN ALWIN_USER.email IS 'e-mail uzytkownika';
COMMENT ON COLUMN ALWIN_USER."password" IS 'haslo uzytkownika';
COMMENT ON COLUMN ALWIN_USER.salt IS 'sol do hasla uzytkownika';
COMMENT ON COLUMN ALWIN_USER.status IS 'status konta uzytkownika';
COMMENT ON COLUMN ALWIN_USER.creation_date IS 'data utworzenia konta uzytkownika';
COMMENT ON COLUMN ALWIN_USER.update_date IS 'data aktualizacji konta uzytkownika';
