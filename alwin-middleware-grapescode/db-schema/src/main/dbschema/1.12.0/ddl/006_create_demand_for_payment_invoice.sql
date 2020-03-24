-- łączenie wezwań z fakturami

-- usunięcie starej tabeli
DROP TABLE demand_for_payment_invoices;

CREATE TABLE demand_for_payment_invoice (
  demand_for_payment_id             BIGINT NOT NULL,
  formal_debt_collection_invoice_id BIGINT NOT NULL,
  CONSTRAINT pk_demand_for_payment_invoice PRIMARY KEY (demand_for_payment_id, formal_debt_collection_invoice_id),
  CONSTRAINT fk_demand_for_payment_invoice_invoice FOREIGN KEY (formal_debt_collection_invoice_id) REFERENCES formal_debt_collection_invoice (id),
  CONSTRAINT fk_demand_for_payment_invoice_demand_for_payment FOREIGN KEY (demand_for_payment_id) REFERENCES demand_for_payment (id)
);

COMMENT ON TABLE demand_for_payment_invoice
IS 'Tabela łącząca wezwania do zapłaty z fakturami w procesie windykacji formalnej';
