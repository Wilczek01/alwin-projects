CREATE TABLE contract_termination_attachment (
  id        BIGSERIAL    NOT NULL,
  reference VARCHAR(255) NOT NULL,
  CONSTRAINT pk_contract_termination_attachment PRIMARY KEY (id)
);

COMMENT ON TABLE contract_termination_attachment
IS 'Dokumenty wypowiedzenia umowy';
COMMENT ON COLUMN contract_termination_attachment.id
IS 'Identyfikator dokumentu wypowiedzenia umowy';
COMMENT ON COLUMN contract_termination_attachment.reference
IS 'Referencja do dokumentu';

CREATE TABLE contract_termination_attachment_aud (
  id        BIGSERIAL NOT NULl,
  rev       INTEGER   NOT NULL,
  revtype   SMALLINT,
  reference VARCHAR(255),
  CONSTRAINT pk_contract_termination_attachment_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE contract_termination_attachment_aud
IS 'Tabela audytowa dla dokumentów wypowiedzeń umów';


CREATE TABLE contract_termination_has_attachment (
  contract_termination_id            BIGINT NOT NULL,
  contract_termination_attachment_id BIGINT NOT NULL,
  CONSTRAINT pk_contract_termination_has_attachment PRIMARY KEY (contract_termination_id, contract_termination_attachment_id),
  CONSTRAINT fk_contract_termination_invoice FOREIGN KEY (contract_termination_id) REFERENCES contract_termination (id),
  CONSTRAINT fk_contract_termination_attachment FOREIGN KEY (contract_termination_attachment_id) REFERENCES contract_termination_attachment (id)
);

COMMENT ON TABLE contract_termination_has_attachment
IS 'Tabela łącząca wypowiedzenia umów z dokumentami wypowiedzeń';

CREATE TABLE contract_termination_has_attachment_aud (
  rev                                INTEGER,
  revtype                            SMALLINT,
  contract_termination_id            BIGINT NOT NULL,
  contract_termination_attachment_id BIGINT NOT NULL,
  CONSTRAINT pk_contract_termination_has_attachment_aud PRIMARY KEY (contract_termination_id, contract_termination_attachment_id, rev)
);

COMMENT ON TABLE contract_termination_has_attachment_aud
IS 'Tabela audytowa dla wypowiedzeń umów z dokumentami wypowiedzeń';
