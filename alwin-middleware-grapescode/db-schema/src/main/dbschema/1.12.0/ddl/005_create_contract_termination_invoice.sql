CREATE TABLE contract_termination_invoice (
  contract_termination_id           BIGINT NOT NULL,
  formal_debt_collection_invoice_id BIGINT NOT NULL,
  CONSTRAINT pk_contract_termination_invoice PRIMARY KEY (contract_termination_id, formal_debt_collection_invoice_id),
  CONSTRAINT fk_formal_debt_collection_invoice_invoice FOREIGN KEY (formal_debt_collection_invoice_id) REFERENCES formal_debt_collection_invoice (id),
  CONSTRAINT fk_contract_termination_invoice FOREIGN KEY (contract_termination_id) REFERENCES contract_termination (id)
);

COMMENT ON TABLE contract_termination_invoice
IS 'Tabela łącząca wypowiedzenia umów z fakturami w procesie windykacji formalnej';

CREATE TABLE contract_termination_invoice_aud (
  rev                               INTEGER,
  revtype                           SMALLINT,
  contract_termination_id           BIGINT,
  formal_debt_collection_invoice_id BIGINT,
  CONSTRAINT pk_contract_termination_invoice_aud PRIMARY KEY (contract_termination_id, formal_debt_collection_invoice_id, rev)
);

COMMENT ON TABLE contract_termination_invoice_aud
IS 'Tabela audytowa dla połączeń wypowiedzeń umów z fakturami w procesie windykacji formalnej';
