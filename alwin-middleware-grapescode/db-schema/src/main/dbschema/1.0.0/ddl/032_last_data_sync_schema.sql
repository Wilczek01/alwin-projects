CREATE TABLE last_data_sync (
  id        BIGSERIAL          NOT NULL,
  type      VARCHAR(30) UNIQUE NOT NULL,
  to_date   DATE,
  from_date DATE,
  CONSTRAINT pk_last_data_sync PRIMARY KEY (id)
);

COMMENT ON TABLE last_data_sync IS 'informacje o ostatniej synchronizacji dancyh';
COMMENT ON COLUMN last_data_sync.id IS 'identyfikator wpisu';
COMMENT ON COLUMN last_data_sync.type IS 'rodzaj synchronizowanych danych';
COMMENT ON COLUMN last_data_sync.to_date IS 'przedzial czasu, do którego były synchronizowane dane ostatnio';
COMMENT ON COLUMN last_data_sync.from_date IS 'przedzial czasu, od którego były zsynchronizowane dane ostatnio';