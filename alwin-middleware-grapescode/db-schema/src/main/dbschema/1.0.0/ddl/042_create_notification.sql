CREATE TABLE NOTIFICATION (
  id            BIGSERIAL NOT NULL,
  issue_id      BIGINT    NOT NULL,
  creation_date TIMESTAMP NOT NULL,
  read_date     TIMESTAMP,
  message       TEXT      NOT NULL,
  sender_id     BIGINT    NOT NULL,
  recipient_id  BIGINT    NOT NULL,
  CONSTRAINT pk_notification PRIMARY KEY (id)
);

COMMENT ON TABLE NOTIFICATION IS 'Powiadomienia';
COMMENT ON COLUMN NOTIFICATION.id IS 'Identyfikator powiadomienia';
COMMENT ON COLUMN NOTIFICATION.issue_id IS 'Identyfikator zlecenia';
COMMENT ON COLUMN NOTIFICATION.creation_date IS 'Data utworzenia';
COMMENT ON COLUMN NOTIFICATION.read_date IS 'Data odczytania';
COMMENT ON COLUMN NOTIFICATION.message IS 'Treść wiadomości';
COMMENT ON COLUMN NOTIFICATION.sender_id IS 'Identyfikator autora powiadomienia';
COMMENT ON COLUMN NOTIFICATION.recipient_id IS 'Identyfikator odbiorcy powiadomienia';