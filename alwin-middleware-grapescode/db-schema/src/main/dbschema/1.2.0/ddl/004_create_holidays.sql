CREATE TABLE holiday (
  id            BIGSERIAL    NOT NULL,
  description   VARCHAR(150) NOT NULL,
  holiday_day   SMALLINT     NOT NULL,
  holiday_month SMALLINT     NOT NULL,
  holiday_year  SMALLINT,
  CONSTRAINT pk_holiday PRIMARY KEY (id)
);

COMMENT ON TABLE holiday IS 'Dni wolne od pracy';
COMMENT ON COLUMN holiday.id IS 'Identyfikator dnia wolnego';
COMMENT ON COLUMN holiday.description IS 'Nazwa dnia wolnego';
COMMENT ON COLUMN holiday.holiday_day IS 'Dzien miesiaca dnia wolnego';
COMMENT ON COLUMN holiday.holiday_month IS 'Miesiac dnia wolnego';
COMMENT ON COLUMN holiday.holiday_year IS 'Rok dnia wolnego - brak oznacza powtarzajacy sie dzien wolny co roku';