CREATE TABLE demand_for_payment_type (
  id                      BIGSERIAL      NOT NULL,
  key                     VARCHAR(10)    NOT NULL,
  dpd_start               NUMERIC(4)     NOT NULL,
  number_of_days_to_pay   NUMERIC(4)     NOT NULL,
  charge                  NUMERIC(18, 2) NOT NULL,
  segment                 VARCHAR(10)    NOT NULL,
  min_due_payment_value   NUMERIC(18, 2) NOT NULL,
  min_due_payment_percent NUMERIC(18, 2),
  CONSTRAINT pk_demand_for_payment_type PRIMARY KEY (id)
);

COMMENT ON COLUMN demand_for_payment_type.id IS 'Identyfikator typu wezwania do zapłaty';
COMMENT ON COLUMN demand_for_payment_type.key IS 'Typ wezwania (pierwsze, drugie)';
COMMENT ON COLUMN demand_for_payment_type.dpd_start IS 'DPD, po przekroczeniu którego dla FV generujemy wezwanie';
COMMENT ON COLUMN demand_for_payment_type.number_of_days_to_pay IS 'Liczba dni na zapłatę';
COMMENT ON COLUMN demand_for_payment_type.charge IS 'Koszt opłaty za wezwanie';
COMMENT ON COLUMN demand_for_payment_type.segment IS 'Segment LB (A, B)';
COMMENT ON COLUMN demand_for_payment_type.min_due_payment_value IS 'Minimalna zaległość';
COMMENT ON COLUMN demand_for_payment_type.min_due_payment_percent IS 'Minimalny % zaległości';
