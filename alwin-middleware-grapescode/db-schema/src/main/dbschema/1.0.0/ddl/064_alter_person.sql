ALTER TABLE person
  ALTER COLUMN pesel DROP NOT NULL;

ALTER TABLE person
  ALTER COLUMN person_id DROP NOT NULL;

ALTER TABLE person_aud
  ADD COLUMN country VARCHAR(100);

UPDATE person_aud
SET country = (SELECT c.long_name
               FROM country_aud c
               WHERE c.id = country_id);

ALTER TABLE person_aud
  DROP COLUMN country_id;
DROP TABLE country_aud;