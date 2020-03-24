DELETE FROM issue_invoice;

ALTER TABLE issue_invoice
  ADD COLUMN last_payment_date  TIMESTAMP,
  ADD COLUMN net_amount         NUMERIC(18, 2)  NOT NULL,
  ADD COLUMN gross_amount       NUMERIC(18, 2)  NOT NULL,
  ADD COLUMN number             VARCHAR(30)     NOT NULL,
  ADD COLUMN currency           VARCHAR(5)      NOT NULL,
  ADD COLUMN current_balance    NUMERIC(18, 2)  NOT NULL,
  ADD COLUMN contract_number    VARCHAR(150)    NOT NULL,
  ADD COLUMN type               INT;


COMMENT ON COLUMN issue_invoice.last_payment_date IS 'Data ostatniej wplaty';
COMMENT ON COLUMN issue_invoice.net_amount IS 'Wartosc netto';
COMMENT ON COLUMN issue_invoice.gross_amount IS 'Wartosc brutto';
COMMENT ON COLUMN issue_invoice.number IS 'Numer faktury';
COMMENT ON COLUMN issue_invoice.currency IS 'Waluta';
COMMENT ON COLUMN issue_invoice.current_balance IS 'Saldo biezace';
COMMENT ON COLUMN issue_invoice.contract_number IS 'Numer umowy';
COMMENT ON COLUMN issue_invoice.type IS 'Typ faktury';