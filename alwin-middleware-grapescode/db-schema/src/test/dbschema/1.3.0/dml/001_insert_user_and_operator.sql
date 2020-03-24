ALTER SEQUENCE alwin_user_id_seq
start 30000 restart 30000
MINVALUE 30000;

INSERT INTO alwin_user
(first_name, last_name, email, creation_date, update_date, phone_number)
VALUES ('Maria', 'Konopnicka', 'mkonopnicka@grapescode.pl', now(), now(), NULL);

INSERT INTO alwin_operator
(operator_type_id, alwin_user_id, parent_alwin_operator_id, permission_id, active, login, password, salt)
VALUES (
  (SELECT id
   FROM operator_type
   WHERE type_name = 'CUSTOMER_ASSISTANT'),
  (SELECT id
   FROM alwin_user
   WHERE email = 'mkonopnicka@grapescode.pl'),
  NULL, NULL, FALSE,
  'mkonopnicka',
  'c4e0590c6d6a0bda72be5068faad49129d9b2695a2f6258e224a4c6ad7f3ace9638a55506951c88747c08ac150d039517dcf76cd62f2758f42b885705887d567',
  'b87f4720b4d8bbf71e2d3cfaf393ddd0a084ea0b7e60ece81d204be72e39df70d32f80fc31085034847c74af0f88a1fcf749ca2c061993d1439b27da0ccb94d0');