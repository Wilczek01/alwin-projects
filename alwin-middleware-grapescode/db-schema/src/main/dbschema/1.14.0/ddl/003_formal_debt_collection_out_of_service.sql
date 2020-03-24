CREATE TABLE formal_debt_collection_customer_out_of_service (
  id                   BIGSERIAL    NOT NULL,
  customer_id          BIGINT       NOT NULL,
  start_date           DATE         NOT NULL,
  end_date             DATE         NOT NULL,
  blocking_operator_id BIGINT       NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  reason_type          VARCHAR(50)  NOT NULL,
  remark               TEXT,
  demand_for_payment_type          VARCHAR(50)  NOT NULL,
  CONSTRAINT pk_formal_debt_collection_customer_out_of_service PRIMARY KEY (id)
);

CREATE INDEX idx_formal_debt_collection_customer_out_of_service_customer_id
  ON formal_debt_collection_customer_out_of_service (customer_id);
CREATE INDEX idx_formal_debt_collection_customer_out_of_service_blocking_operator_id
  ON formal_debt_collection_customer_out_of_service (blocking_operator_id);

COMMENT ON TABLE formal_debt_collection_customer_out_of_service
IS 'Dodano tabelę klientów wykluczonych z tworzenia wezwań do zapłaty.';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.customer_id
IS 'ID zablokowanego klienta';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.start_date
IS 'Data wykluczenia';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.end_date
IS 'Data zakończenia wykluczenia.';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.blocking_operator_id
IS 'Operator wykluczający z obsługi';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.reason
IS 'Przyczyna wykluczenia z obsługi';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.reason_type
IS 'Słownikowa wartość przyczyny wykluczenia z obsługi';
COMMENT ON COLUMN formal_debt_collection_customer_out_of_service.demand_for_payment_type
IS 'Typ wezwania dla których obowiązuje wykluczenie z obsługi';

CREATE TABLE formal_debt_collection_contract_out_of_service (
  id                   BIGSERIAL    NOT NULL,
  start_date           DATE,
  end_date             DATE,
  blocking_operator_id BIGINT       NOT NULL,
  contract_no          VARCHAR(30)  NOT NULL,
  reason               VARCHAR(100) NOT NULL,
  reason_type          VARCHAR(50)  NOT NULL,
  remark               TEXT,
  demand_for_payment_type          VARCHAR(50)  NOT NULL,
  CONSTRAINT pk_formal_debt_collection_contract_out_of_service PRIMARY KEY (id),
  CONSTRAINT fk_formal_debt_collection_contract_out_of_service_blocking_operator_id FOREIGN KEY (blocking_operator_id) REFERENCES alwin_operator (id)
);

CREATE INDEX idx_formal_debt_collection_contract_out_of_service_blocking_operator_id
  ON formal_debt_collection_contract_out_of_service (blocking_operator_id);

COMMENT ON TABLE formal_debt_collection_contract_out_of_service
IS 'Kontrakty klientow wykluczone z obslugi';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.start_date
IS 'Data rozpoczecia wykluczenia';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.end_date
IS 'Data zakończenia wykluczenia.';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.blocking_operator_id
IS 'Operator wykluczający kontrakt z obsługi';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.contract_no
IS 'Numer kontraktu';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.reason
IS 'Przyczyna wykluczenia z obsługi';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.remark
IS 'Komentarz';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.reason_type
IS 'Słownikowa wartość przyczyny wykluczenia z obsługi';
COMMENT ON COLUMN formal_debt_collection_contract_out_of_service.demand_for_payment_type
IS 'Typ wezwania dla których obowiązuje wykluczenie z obsługi';
