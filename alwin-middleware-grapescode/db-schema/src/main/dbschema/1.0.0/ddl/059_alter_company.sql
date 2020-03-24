ALTER TABLE company
  ALTER COLUMN segment DROP NOT NULL;
UPDATE company
SET segment = NULL;

ALTER TABLE company
  ALTER COLUMN involvement DROP NOT NULL;
UPDATE company
SET involvement = NULL;
