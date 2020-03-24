CREATE TABLE SCHEDULER_EXECUTION (
  id         BIGSERIAL                           NOT NULL,
  start_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  end_date   TIMESTAMP,
  state      VARCHAR(500),
  type       VARCHAR(100)                        NOT NULL,
  manual     BOOLEAN                             NOT NULL DEFAULT FALSE,
  CONSTRAINT pk_scheduler_execution PRIMARY KEY (id)
);

COMMENT ON TABLE SCHEDULER_EXECUTION IS 'informacje o wykonywaniu zadań cyklicznych';
COMMENT ON COLUMN SCHEDULER_EXECUTION.id IS 'identyfikator wykonania';
COMMENT ON COLUMN SCHEDULER_EXECUTION.start_date IS 'data rozpoczecia';
COMMENT ON COLUMN SCHEDULER_EXECUTION.end_date IS 'data zakonczenia';
COMMENT ON COLUMN SCHEDULER_EXECUTION.state IS 'status - dodatkowa informacja w przypadku niepowodzenia';
COMMENT ON COLUMN SCHEDULER_EXECUTION.type IS 'typ zadania';
COMMENT ON COLUMN SCHEDULER_EXECUTION.manual IS 'czy zostalo wystartowane recznie';
