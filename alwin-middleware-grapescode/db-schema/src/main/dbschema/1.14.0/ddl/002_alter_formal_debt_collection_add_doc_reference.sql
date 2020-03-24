-- identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
ALTER TABLE demand_for_payment
  ADD COLUMN preceding_demand_for_payment_id BIGINT,
  ADD CONSTRAINT fk_demand_for_payment_preceding_demand_for_payment_id FOREIGN KEY (preceding_demand_for_payment_id) REFERENCES demand_for_payment (id);

COMMENT ON COLUMN demand_for_payment.preceding_demand_for_payment_id
IS 'Link do poprzedzającego wezwania do zapłaty';

-- identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
ALTER TABLE contract_termination
  ADD COLUMN preceding_demand_for_payment_id BIGINT,
  ADD CONSTRAINT fk_contract_termination_preceding_demand_for_payment_id FOREIGN KEY (preceding_demand_for_payment_id) REFERENCES demand_for_payment (id);

COMMENT ON COLUMN contract_termination.preceding_demand_for_payment_id
IS 'Link do poprzedzającego wezwania do zapłaty';

ALTER TABLE contract_termination_aud
  ADD COLUMN preceding_demand_for_payment_id BIGINT;

-- identyfikator poprzedzającego wypowiedzenia umowy (jeśli istnieje)
ALTER TABLE contract_termination
  ADD COLUMN preceding_contract_termination_id BIGINT,
  ADD CONSTRAINT fk_contract_termination_preceding_contract_termination_id FOREIGN KEY (preceding_contract_termination_id) REFERENCES contract_termination (id);

COMMENT ON COLUMN contract_termination.preceding_contract_termination_id
IS 'Link do poprzedzającego wypowiedzenia umowy';

ALTER TABLE contract_termination_aud
  ADD COLUMN preceding_contract_termination_id BIGINT;
