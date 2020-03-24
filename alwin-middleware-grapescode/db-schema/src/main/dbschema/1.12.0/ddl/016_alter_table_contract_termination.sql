ALTER TABLE contract_termination
  DROP COLUMN amount_in_demand_for_payment;

ALTER TABLE contract_termination_aud
  DROP COLUMN amount_in_demand_for_payment;

ALTER TABLE contract_termination
  ADD COLUMN amount_in_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination
  ADD COLUMN amount_in_demand_for_payment_eur NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN amount_in_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN amount_in_demand_for_payment_eur NUMERIC(18, 2);

COMMENT ON COLUMN contract_termination.amount_in_demand_for_payment_pln
IS 'Kwota wskazana do spłaty na wezwaniu PLN';

COMMENT ON COLUMN contract_termination.amount_in_demand_for_payment_eur
IS 'Kwota wskazana do spłaty na wezwaniu EUR';


ALTER TABLE contract_termination
  DROP COLUMN total_payments_since_demand_for_payment;

ALTER TABLE contract_termination_aud
  DROP COLUMN total_payments_since_demand_for_payment;

ALTER TABLE contract_termination
  ADD COLUMN total_payments_since_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination
  ADD COLUMN total_payments_since_demand_for_payment_eur NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN total_payments_since_demand_for_payment_pln NUMERIC(18, 2);

ALTER TABLE contract_termination_aud
  ADD COLUMN total_payments_since_demand_for_payment_eur NUMERIC(18, 2);

COMMENT ON COLUMN contract_termination.total_payments_since_demand_for_payment_pln
IS 'Suma wpłat dokonanych od wezwania PLN';

COMMENT ON COLUMN contract_termination.total_payments_since_demand_for_payment_eur
IS 'Suma wpłat dokonanych od wezwania EUR';