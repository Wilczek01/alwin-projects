ALTER TABLE company
  ADD COLUMN pk_code VARCHAR(50);

ALTER TABLE company
  ADD COLUMN pk_name VARCHAR(200);

COMMENT ON COLUMN company.pk_code IS 'Kod pkd firmy';
COMMENT ON COLUMN company.pk_name IS 'Nazwa pkd firmy';

ALTER TABLE company_aud
  ADD COLUMN pk_code VARCHAR(50);

ALTER TABLE company_aud
  ADD COLUMN pk_name VARCHAR(200);