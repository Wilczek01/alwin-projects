ALTER TABLE issue
  ADD COLUMN dpd_start_date TIMESTAMP;

ALTER TABLE issue_aud
  ADD COLUMN dpd_start_date TIMESTAMP;

COMMENT ON COLUMN issue.dpd_start_date
IS 'Data, od której jest wyliczane DPD rozpoczęcia dla zlecenia';
