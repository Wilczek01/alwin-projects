ALTER TABLE ISSUE_TYPE_DURATION RENAME TO ISSUE_TYPE_CONFIGURATION;

ALTER TABLE ISSUE_TYPE_CONFIGURATION
  ADD COLUMN create_automatically BOOLEAN NOT NULL DEFAULT FALSE,
  ADD COLUMN dpd_start NUMERIC(4),
  ADD COLUMN min_balance_start NUMERIC(18, 2);


COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.create_automatically IS 'Czy dany typ zlecenia jest tworzony automatycznie';
COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.dpd_start IS 'DPD, w którym zlecenie jest automatycznie tworzone';
COMMENT ON COLUMN ISSUE_TYPE_CONFIGURATION.min_balance_start IS 'Minimalny próg zadłużenia';
