ALTER TABLE demand_for_payment
  ADD COLUMN aborted BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN demand_for_payment.aborted
IS 'Flaga określająca, czy wezwanie zastąpione w procesie wezwaniem utworzonym ręcznie (wyłączone z procesu)';
