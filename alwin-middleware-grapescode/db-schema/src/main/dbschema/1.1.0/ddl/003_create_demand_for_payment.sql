COMMENT ON TABLE demand_for_payment_type IS 'Konfiguracji typów wezwań do zapłaty';

CREATE TABLE demand_for_payment (
  id                         BIGSERIAL   NOT NULL,
  issue_date                 TIMESTAMP   NOT NULL,
  due_date                   TIMESTAMP   NOT NULL,
  initial_invoice_no         VARCHAR(30) NOT NULL,
  ext_company_id             BIGINT      NOT NULL,
  demand_for_payment_type_id BIGINT      NOT NULL,
  charge_invoice_no          VARCHAR(30),
  state                      VARCHAR(20) NOT NULL,
  attachment_ref             VARCHAR(1000),
  CONSTRAINT pk_demand_for_payment PRIMARY KEY (id),
  CONSTRAINT fk_demand_for_payment_type FOREIGN KEY (demand_for_payment_type_id) REFERENCES demand_for_payment_type (id)
);

COMMENT ON TABLE demand_for_payment IS 'Wezwania do zapłaty';
COMMENT ON COLUMN demand_for_payment.id IS 'Identyfikator wezwania do zapłaty';
COMMENT ON COLUMN demand_for_payment.issue_date IS 'Data wystawienia';
COMMENT ON COLUMN demand_for_payment.due_date IS 'Termin zapłaty';
COMMENT ON COLUMN demand_for_payment.initial_invoice_no IS 'Numer faktury inicjującej wystawienie';
COMMENT ON COLUMN demand_for_payment.ext_company_id IS 'Identyfikator klienta w systemie zewtnętrznym';
COMMENT ON COLUMN demand_for_payment.demand_for_payment_type_id IS 'Link do typu wezwania';
COMMENT ON COLUMN demand_for_payment.charge_invoice_no IS 'Nr FV za opłatę';
COMMENT ON COLUMN demand_for_payment.state IS 'Status';
COMMENT ON COLUMN demand_for_payment.attachment_ref IS 'Referencja do pliku z wezwaniem do zapłaty';

-- łączenie wezwań z fakturami

CREATE TABLE demand_for_payment_invoices (
  demand_for_payment_id BIGINT NOT NULL,
  invoice_id            BIGINT NOT NULL,
  CONSTRAINT idx_demand_for_payment_invoices PRIMARY KEY (demand_for_payment_id, invoice_id),
  CONSTRAINT fk_demand_for_payment_invoices_demand_for_payment FOREIGN KEY (demand_for_payment_id) REFERENCES demand_for_payment (id),
  CONSTRAINT fk_demand_for_payment_invoices_invoice FOREIGN KEY (invoice_id) REFERENCES invoice (id)
);

CREATE INDEX idx_demand_for_payment_invoices_demand_for_payment
  ON demand_for_payment_invoices (demand_for_payment_id);
CREATE INDEX idx_demand_for_payment_invoices_invoice
  ON demand_for_payment_invoices (invoice_id);

COMMENT ON TABLE demand_for_payment_invoices IS 'Tabela łącząca wezwania do zapłaty z fakturami';
