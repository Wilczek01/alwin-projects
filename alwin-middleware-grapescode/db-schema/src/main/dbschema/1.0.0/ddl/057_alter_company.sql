ALTER TABLE company
  ADD COLUMN segment VARCHAR(10) NOT NULL DEFAULT 'B';
ALTER TABLE company
  ALTER COLUMN segment DROP DEFAULT;

ALTER TABLE company
  ADD COLUMN involvement NUMERIC(18, 2) NOT NULL DEFAULT 0.00;
ALTER TABLE company
  ALTER COLUMN involvement DROP DEFAULT;

COMMENT ON COLUMN company.segment IS 'segment firmy';
COMMENT ON COLUMN company.involvement IS 'zaangażowanie firmy';