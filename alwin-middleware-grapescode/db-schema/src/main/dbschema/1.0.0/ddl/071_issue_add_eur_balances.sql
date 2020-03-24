ALTER TABLE issue
  RENAME rpb TO rpb_pln;
ALTER TABLE issue
  RENAME balance_start TO balance_start_pln;
ALTER TABLE issue
  RENAME balance_additional TO balance_additional_pln;
ALTER TABLE issue
  RENAME payments TO payments_pln;

ALTER TABLE issue
  ADD COLUMN rpb_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;
ALTER TABLE issue
  ADD COLUMN balance_start_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;
ALTER TABLE issue
  ADD COLUMN balance_additional_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;
ALTER TABLE issue
  ADD COLUMN payments_eur NUMERIC(18, 2) NOT NULL DEFAULT 0;

COMMENT ON COLUMN issue.rpb_pln IS 'Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia (PLN)';
COMMENT ON COLUMN issue.balance_start_pln IS 'Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane (PLN)';
COMMENT ON COLUMN issue.balance_additional_pln IS 'Saldo dokumentow, ktore staly sie wymagalne w trakcie obslugi zlecenia (PLN)';
COMMENT ON COLUMN issue.payments_pln IS 'Suma dokonanych wplat (PLN)';

COMMENT ON COLUMN issue.rpb_eur IS 'Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia (EUR)';
COMMENT ON COLUMN issue.balance_start_eur IS 'Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane (EUR)';
COMMENT ON COLUMN issue.balance_additional_eur IS 'Saldo dokumentow, ktore staly sie wymagalne w trakcie obslugi zlecenia (EUR)';
COMMENT ON COLUMN issue.payments_eur IS 'Suma dokonanych wplat (EUR)';

-- audit table

ALTER TABLE issue_aud
  RENAME rpb TO rpb_pln;
ALTER TABLE issue_aud
  RENAME balance_start TO balance_start_pln;
ALTER TABLE issue_aud
  RENAME balance_additional TO balance_additional_pln;
ALTER TABLE issue_aud
  RENAME payments TO payments_pln;

ALTER TABLE issue_aud
  ADD COLUMN rpb_eur NUMERIC(18, 2);
ALTER TABLE issue_aud
  ADD COLUMN balance_start_eur NUMERIC(18, 2);
ALTER TABLE issue_aud
  ADD COLUMN balance_additional_eur NUMERIC(18, 2);
ALTER TABLE issue_aud
  ADD COLUMN payments_eur NUMERIC(18, 2);
