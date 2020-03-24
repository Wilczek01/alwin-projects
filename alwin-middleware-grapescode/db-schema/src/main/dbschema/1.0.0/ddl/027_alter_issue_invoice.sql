ALTER TABLE issue_invoice
  ADD COLUMN due_date TIMESTAMP DEFAULT current_timestamp NOT NULL;

COMMENT ON COLUMN issue_invoice.due_date IS 'Termin wymagalności';