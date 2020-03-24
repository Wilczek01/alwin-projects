ALTER TABLE holiday
  ADD COLUMN alwin_user_id BIGINT;

ALTER TABLE holiday
  ADD CONSTRAINT fk_holiday_alwin_user_id FOREIGN KEY (alwin_user_id) REFERENCES alwin_user (id);

CREATE TABLE holiday_aud (
  id            BIGINT  NOT NULL,
  REV           INTEGER NOT NULL,
  REVTYPE       SMALLINT,
  description   VARCHAR(150),
  holiday_day   SMALLINT,
  holiday_month SMALLINT,
  holiday_year  SMALLINT,
  alwin_user_id BIGINT,
  PRIMARY KEY (id, REV)
);
