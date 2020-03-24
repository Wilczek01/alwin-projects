ALTER TABLE customer
  ADD COLUMN account_manager_id BIGINT;

ALTER TABLE customer
  ADD CONSTRAINT fk_customer_alwin_operator FOREIGN KEY (account_manager_id) REFERENCES alwin_operator (id);

COMMENT ON COLUMN customer.account_manager_id
IS 'Opiekun klienta';

ALTER TABLE customer_aud
  ADD COLUMN account_manager_id BIGINT;