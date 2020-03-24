ALTER TABLE issue
  ADD COLUMN created_manually BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN issue.created_manually IS 'czy zlecenie zostało utworzone manualnie';