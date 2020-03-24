ALTER TABLE notification
  ADD COLUMN read_confirmation_required BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN notification.read_confirmation_required IS 'Czy powiadomienie wymaga potwierdzenia przeczytania';