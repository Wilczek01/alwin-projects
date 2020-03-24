ALTER TABLE invoice DROP CONSTRAINT pk_issue_invoice;
ALTER TABLE invoice DROP CONSTRAINT fk_issue_invoice_issue;

ALTER INDEX idx_issue_invoice_issue RENAME TO idx_invoice_issue;

ALTER TABLE invoice add CONSTRAINT pk_invoice PRIMARY KEY (id);
ALTER TABLE invoice add CONSTRAINT fk_issue_invoice_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE ISSUE_INVOICE (
  id            BIGSERIAL   NOT NULL,
  issue_id      BIGINT      NOT NULL,
  invoice_id    BIGINT      NOT NULL,
  addition_date TIMESTAMP   NOT NULL,
  CONSTRAINT pk_issue_invoice PRIMARY KEY (id),
  CONSTRAINT fk_issue_invoice_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id),
  CONSTRAINT fk_issue_invoice_invoice FOREIGN KEY (invoice_id) REFERENCES INVOICE (id)
);

CREATE INDEX idx_issue_invoice_issue ON ISSUE_INVOICE (issue_id);

COMMENT ON TABLE ISSUE_INVOICE IS 'Powiązanie dokumentów obciążeniowych ze zleceniem w celu uniknięcia powielaniu danych w tabeli faktur';
COMMENT ON COLUMN ISSUE_INVOICE.issue_id IS 'Identyfikator zlecenia';
COMMENT ON COLUMN ISSUE_INVOICE.invoice_id IS 'Identyfikator faktury';
COMMENT ON COLUMN ISSUE_INVOICE.addition_date IS 'Data dodania powiązania';