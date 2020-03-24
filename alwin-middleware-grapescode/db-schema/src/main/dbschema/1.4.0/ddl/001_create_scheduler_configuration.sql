CREATE TABLE SCHEDULER_CONFIGURATION (
  id            BIGSERIAL                           NOT NULL,
  batch_process VARCHAR(500)                        NOT NULL,
  update_date   TIMESTAMP DEFAULT current_timestamp NOT NULL,
  hour          INT                                 NOT NULL,
  CONSTRAINT pk_scheduler_process PRIMARY KEY (id),
  CONSTRAINT idx_scheduler_configuration_process UNIQUE (batch_process)
);

COMMENT ON TABLE SCHEDULER_CONFIGURATION IS 'configuracja zadań cyklicznych';
COMMENT ON COLUMN SCHEDULER_CONFIGURATION.id IS 'identyfikator konfiguracji';
COMMENT ON COLUMN SCHEDULER_CONFIGURATION.batch_process IS 'identyfikator grupy zadań cyklicznych ';
COMMENT ON COLUMN SCHEDULER_CONFIGURATION.update_date IS 'data ostatniej zmiany konfiguracji';
COMMENT ON COLUMN SCHEDULER_CONFIGURATION.hour IS 'godzina startu procesu';
