CREATE TABLE contract_out_of_service (
  id                   BIGSERIAL    NOT NULL,
  start_date           DATE,
  end_date             DATE,
  blocking_operator_id BIGINT       NOT NULL,
  contract_no          VARCHAR(30)  NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  remark               TEXT,
  CONSTRAINT pk_contract_out_of_service PRIMARY KEY (id),
  CONSTRAINT fk_contract_out_of_service_blocking_operator_id FOREIGN KEY (blocking_operator_id) REFERENCES alwin_operator (id)
);

CREATE INDEX idx_contract_out_of_service_blocking_operator_id
  ON contract_out_of_service (blocking_operator_id);

COMMENT ON TABLE contract_out_of_service IS 'Kontrakty klientow wykluczone z obslugi';
COMMENT ON COLUMN contract_out_of_service.start_date IS 'Data rozpoczecia wykluczenia';
COMMENT ON COLUMN contract_out_of_service.end_date IS 'Data zakończenia wykluczenia.';
COMMENT ON COLUMN contract_out_of_service.blocking_operator_id IS 'Operator wykluczający kontrakt z obsługi';
COMMENT ON COLUMN contract_out_of_service.contract_no IS 'Numer kontraktu';
COMMENT ON COLUMN contract_out_of_service.reason IS 'Przyczyna wykluczenia z obsługi';
COMMENT ON COLUMN contract_out_of_service.remark IS 'Komentarz';
