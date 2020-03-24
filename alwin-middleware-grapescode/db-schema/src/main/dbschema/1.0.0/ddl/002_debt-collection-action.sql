CREATE TABLE DEBT_COLLECTION_ACTION (
  id            BIGSERIAL                           NOT NULL,
  name          VARCHAR(100)                        NOT NULL,
  label         VARCHAR(100)                        NOT NULL,
  default_day   INTEGER                             NOT NULL,
  version       INTEGER                             NOT NULL,
  creating_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_debt_collection_action PRIMARY KEY (id)
);

COMMENT ON TABLE DEBT_COLLECTION_ACTION IS 'czynnosci windykacyjne';
COMMENT ON COLUMN DEBT_COLLECTION_ACTION.id IS 'identyfikator czynnosci';
COMMENT ON COLUMN DEBT_COLLECTION_ACTION.name IS 'nazwa czynnosci';
COMMENT ON COLUMN DEBT_COLLECTION_ACTION.label IS 'opis czynnosci';
COMMENT ON COLUMN DEBT_COLLECTION_ACTION.default_day IS 'standardowy dzien obslugi';
COMMENT ON COLUMN DEBT_COLLECTION_ACTION.version IS 'wersja czynnosci';
COMMENT ON COLUMN DEBT_COLLECTION_ACTION.creating_date IS 'data utworzenia podanej wersji czynnosci';
