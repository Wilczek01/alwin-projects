ALTER TABLE company
  RENAME COLUMN pk_code TO pkd_code;

ALTER TABLE company
  RENAME COLUMN pk_name TO pkd_name;

ALTER TABLE company_aud
  RENAME COLUMN pk_code TO pkd_code;

ALTER TABLE company_aud
  RENAME COLUMN pk_name TO pkd_name;