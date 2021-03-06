ALTER TABLE DECLARATION RENAME COLUMN DECLARED_PAYMENT_AMOUNT TO DECLARED_PAYMENT_AMOUNT_PLN;
ALTER TABLE DECLARATION ADD COLUMN DECLARED_PAYMENT_AMOUNT_EUR NUMERIC(18, 2);

COMMENT ON COLUMN DECLARATION.DECLARED_PAYMENT_AMOUNT_PLN IS 'Deklaracja spłaty (PLN)';
COMMENT ON COLUMN DECLARATION.DECLARED_PAYMENT_AMOUNT_EUR IS 'Deklaracja spłaty (EUR)';

ALTER TABLE DECLARATION_AUD RENAME COLUMN DECLARED_PAYMENT_AMOUNT TO DECLARED_PAYMENT_AMOUNT_PLN;
ALTER TABLE DECLARATION_AUD ADD COLUMN DECLARED_PAYMENT_AMOUNT_EUR NUMERIC(18, 2);