ALTER TABLE ISSUE
  ADD COLUMN BALANCE_UPDATE_DATE TIMESTAMP;

COMMENT ON COLUMN ISSUE.BALANCE_UPDATE_DATE IS 'Data ostaniej akutalizacji sald';
