ALTER SEQUENCE alwin_user_id_seq
START 20000 RESTART 20100
MINVALUE 20000;

ALTER SEQUENCE permission_id_seq
START 20000 RESTART 20100
MINVALUE 20000;

ALTER SEQUENCE alwin_operator_id_seq
START 20000 RESTART 20100
MINVALUE 20000;

ALTER TABLE issue_invoice
  ALTER COLUMN currency DROP NOT NULL;

ALTER TABLE address
  ALTER COLUMN street DROP NOT NULL;

ALTER TABLE address
  ALTER COLUMN post_code DROP NOT NULL;