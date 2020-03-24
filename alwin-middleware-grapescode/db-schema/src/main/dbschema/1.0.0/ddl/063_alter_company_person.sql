DROP TABLE company_person_AUD;

ALTER TABLE company_person
  ADD COLUMN contactPerson BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE company_person
  ALTER COLUMN contactPerson DROP DEFAULT;

COMMENT ON COLUMN company_person.contactPerson IS 'Czy osoba jest osobą do kontaktu w firmie?';
