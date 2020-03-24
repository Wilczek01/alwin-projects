CREATE TABLE issue_termination_request (
  id                         BIGSERIAL     NOT NULL,
  issue_id                   BIGINT        NOT NULL,
  request_cause              VARCHAR(1000) NOT NULL,
  request_operator_id        BIGINT        NOT NULL,
  excluded_from_stats        BOOLEAN       NOT NULL,
  exclusion_from_stats_cause VARCHAR(500),
  state                      VARCHAR(20)   NOT NULL,
  manager_operator_id        BIGINT,
  comment                    VARCHAR(500),
  created                    TIMESTAMP     NOT NULL,
  updated                    TIMESTAMP     NOT NULL,
  CONSTRAINT pk_issue_termination_request PRIMARY KEY (id),
  CONSTRAINT fk_issue_termination_request_issue FOREIGN KEY (issue_id) REFERENCES issue (id),
  CONSTRAINT fk_issue_termination_request_request_operator FOREIGN KEY (request_operator_id) REFERENCES alwin_operator (id),
  CONSTRAINT fk_issue_termination_request_manager_operator FOREIGN KEY (manager_operator_id) REFERENCES alwin_operator (id)
);

COMMENT ON TABLE issue_termination_request IS 'Tabela do przechowywania żądań przedterminowego zakończenia zlecenia';
COMMENT ON COLUMN issue_termination_request.id IS 'Identyfikaotr żądania';
COMMENT ON COLUMN issue_termination_request.issue_id IS 'Identyfikator zlecenia do zamknięcia';
COMMENT ON COLUMN issue_termination_request.request_cause IS 'Przyczyna przedterminowego zamknięcia';
COMMENT ON COLUMN issue_termination_request.request_operator_id IS 'Identyfikator operatora tworzącego żądanie';
COMMENT ON COLUMN issue_termination_request.excluded_from_stats IS 'Czy zlecenie powinno być pominięte w statystykach';
COMMENT ON COLUMN issue_termination_request.state IS 'Status żądania';
COMMENT ON COLUMN issue_termination_request.manager_operator_id IS 'Identyfikator operatora, który obsłużył żądanie';
COMMENT ON COLUMN issue_termination_request.comment IS 'Komentarz dotyczący obsługi żądania';
COMMENT ON COLUMN issue_termination_request.created IS 'Data utworzenia żądania';
COMMENT ON COLUMN issue_termination_request.updated IS 'Data aktulaizacji żądania';