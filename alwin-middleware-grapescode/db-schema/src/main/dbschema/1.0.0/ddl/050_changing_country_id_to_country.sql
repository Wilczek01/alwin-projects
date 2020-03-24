ALTER TABLE person
  ADD COLUMN country VARCHAR(100);
UPDATE person
SET country = (SELECT c.long_name
               FROM country c
               WHERE c.id = country_id);
ALTER TABLE person
  DROP COLUMN country_id;
DROP TABLE country;

