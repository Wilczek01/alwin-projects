CREATE TABLE formal_debt_collection_invoice (
  id              BIGSERIAL      NOT NULL,
  invoice_number  VARCHAR(255)   NOT NULL,
  contract_number VARCHAR(255)   NOT NULL,
  issue_date      TIMESTAMP      NOT NULL,
  due_date        TIMESTAMP      NOT NULL,
  currency        VARCHAR(5)     NOT NULL,
  net_amount      NUMERIC(18, 2) NOT NULL,
  gross_amount    NUMERIC(18, 2) NOT NULL,
  current_balance NUMERIC(18, 2) NOT NULL,
  CONSTRAINT pk_formal_debt_collection_invoice PRIMARY KEY (id),
  CONSTRAINT formal_debt_collection_invoice_invoice_for_contract UNIQUE (invoice_number, contract_number)
);

COMMENT ON TABLE formal_debt_collection_invoice
IS 'Faktury w procesie windykacji formalnej';
COMMENT ON COLUMN formal_debt_collection_invoice.id
IS 'Identyfikator faktury w procesie windykacji formalnej';
COMMENT ON COLUMN formal_debt_collection_invoice.invoice_number
IS 'Nr faktury';
COMMENT ON COLUMN formal_debt_collection_invoice.contract_number
IS 'Nr umowy';
COMMENT ON COLUMN formal_debt_collection_invoice.issue_date
IS 'Data wystawienia';
COMMENT ON COLUMN formal_debt_collection_invoice.due_date
IS 'Termin płatności';
COMMENT ON COLUMN formal_debt_collection_invoice.currency
IS 'Waluta';
COMMENT ON COLUMN formal_debt_collection_invoice.net_amount
IS 'Kwota netto dokumentu';
COMMENT ON COLUMN formal_debt_collection_invoice.gross_amount
IS 'Kwota brutto dokumentu';
COMMENT ON COLUMN formal_debt_collection_invoice.current_balance
IS 'Saldo dokumentu';

CREATE TABLE formal_debt_collection_invoice_aud (
  id              BIGSERIAL      NOT NULl,
  rev             INTEGER        NOT NULL,
  revtype         SMALLINT,
  invoice_number  VARCHAR(255)   NOT NULL,
  contract_number VARCHAR(255)   NOT NULL,
  issue_date      TIMESTAMP      NOT NULL,
  due_date        TIMESTAMP      NOT NULL,
  currency        VARCHAR(5)     NOT NULL,
  net_amount      NUMERIC(18, 2) NOT NULL,
  gross_amount    NUMERIC(18, 2) NOT NULL,
  current_balance NUMERIC(18, 2) NOT NULL,
  CONSTRAINT pk_formal_debt_collection_invoice_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE formal_debt_collection_invoice_aud
IS 'Tabela audytowa dla faktur w procesie windykacji formalnej';
