ALTER TABLE issue ADD COLUMN total_balance_start_pln NUMERIC(18, 2);
ALTER TABLE issue ADD COLUMN total_current_balance_pln NUMERIC(18, 2);
ALTER TABLE issue ADD COLUMN total_payments_pln NUMERIC(18, 2);

ALTER TABLE issue_aud ADD COLUMN total_balance_start_pln NUMERIC(18, 2);
ALTER TABLE issue_aud ADD COLUMN total_current_balance_pln NUMERIC(18, 2);
ALTER TABLE issue_aud ADD COLUMN total_payments_pln NUMERIC(18, 2);

COMMENT ON COLUMN issue.total_balance_start_pln IS 'Całkowite saldo zlecenia w chwili rozpoczęcia (PLN)';
COMMENT ON COLUMN issue.total_current_balance_pln IS 'Całkowite saldo dokumentów, które są przedmiotem zlecenia (PLN)';
COMMENT ON COLUMN issue.total_payments_pln IS 'Całkowita suma dokonanych wpłat (PLN)';

