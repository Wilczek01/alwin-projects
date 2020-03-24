CREATE TABLE message_template (
  id    BIGSERIAL        NOT NULL,
  type  VARCHAR(10)   NOT NULL,
  name  VARCHAR(100)  NOT NULL,
  body  TEXT NOT NULL,
  topic VARCHAR(100),
  PRIMARY KEY (id),
  UNIQUE (type, name)
);

COMMENT ON COLUMN message_template.type IS 'typ wiadomości';
COMMENT ON COLUMN message_template.name IS 'nazwa szablonu';
COMMENT ON COLUMN message_template.body IS 'treść szablonu';
COMMENT ON COLUMN message_template.topic IS 'temat wiadomości (e-mail)';
