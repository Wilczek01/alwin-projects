ALTER TABLE demand_for_payment
  ADD COLUMN created_manually BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN demand_for_payment.created_manually
IS 'Flaga określająca, czy wezwanie utworzono ręcznie';
