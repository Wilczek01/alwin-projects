ALTER TABLE contact_detail
  ADD COLUMN remark TEXT;
ALTER TABLE contact_detail
  ADD COLUMN state VARCHAR(100) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE contact_detail
  ALTER COLUMN state DROP DEFAULT;
ALTER TABLE contact_detail
  DROP COLUMN is_active;

COMMENT ON COLUMN contact_detail.remark IS 'Komentarz do danych kontaktowych';
COMMENT ON COLUMN contact_detail.state IS 'Status danych kontaktowych';

ALTER TABLE address
  ADD COLUMN street_prefix VARCHAR(5);
ALTER TABLE address
  ALTER COLUMN house_no DROP NOT NULL;
ALTER TABLE address
  ALTER COLUMN flat_no DROP NOT NULL;
ALTER TABLE address
  ALTER COLUMN country DROP NOT NULL;

ALTER TABLE company DROP COLUMN postal_code;
ALTER TABLE company DROP COLUMN city;
ALTER TABLE company DROP COLUMN prefix;
ALTER TABLE company DROP COLUMN street;
ALTER TABLE company DROP COLUMN email;
ALTER TABLE company DROP COLUMN correspondence_postal_code;
ALTER TABLE company DROP COLUMN correspondence_city;
ALTER TABLE company DROP COLUMN correspondence_prefix;
ALTER TABLE company DROP COLUMN correspondence_street;
ALTER TABLE company DROP COLUMN contact_person;
ALTER TABLE company DROP COLUMN phone_number_1;
ALTER TABLE company DROP COLUMN phone_number_2;
ALTER TABLE company DROP COLUMN phone_number_3;
ALTER TABLE company DROP COLUMN mobile_phone_number;
ALTER TABLE company DROP COLUMN documents_email;
ALTER TABLE company DROP COLUMN office_email;
ALTER TABLE company DROP COLUMN website_address;