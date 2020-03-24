ALTER TABLE contact_detail
  ADD COLUMN send_automatic_sms BOOLEAN;

COMMENT ON COLUMN contact_detail.send_automatic_sms IS 'Czy system może wysyłać automatyczne smsy na ten numer?';

ALTER TABLE contact_detail_aud
  ADD COLUMN send_automatic_sms BOOLEAN;