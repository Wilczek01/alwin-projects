ALTER TABLE address
  ADD COLUMN imported_from_aida BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN address.imported_from_aida IS 'czy wpis został zaimportowany z systemu AIDA';

ALTER TABLE address
  ADD COLUMN imported_type VARCHAR(100);

COMMENT ON COLUMN address.imported_type IS 'typ zaimportowany z systemu AIDA';

ALTER TABLE contact_detail
  ADD COLUMN imported_from_aida BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN contact_detail.imported_from_aida IS 'czy wpis został zaimportowany z systemu AIDA';

ALTER TABLE contact_detail
  ADD COLUMN imported_type VARCHAR(100);

COMMENT ON COLUMN contact_detail.imported_type IS 'typ zaimportowany z systemu AIDA';