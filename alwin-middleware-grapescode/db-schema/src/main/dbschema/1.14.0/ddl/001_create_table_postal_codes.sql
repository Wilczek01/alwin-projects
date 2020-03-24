CREATE TABLE postal_code (
  id          BIGSERIAL  NOT NULL,
  mask        VARCHAR(6) NOT NULL,
  operator_id BIGINT     NOT NULL,
  CONSTRAINT pk_postal_code PRIMARY KEY (id),
  CONSTRAINT uq_postal_code_mask UNIQUE (mask),
  CONSTRAINT fk_postal_code_alwin_operator FOREIGN KEY (operator_id) REFERENCES alwin_operator (id)
);

COMMENT ON TABLE postal_code
IS 'Maski kodow pocztowych przypisane do operatorow';
COMMENT ON COLUMN postal_code.id
IS 'Identyfikator przypisania maski';
COMMENT ON COLUMN postal_code.mask
IS 'Maska kodu pocztowego dla operatora';
COMMENT ON COLUMN postal_code.operator_id
IS 'Identyfikator operatora przypisanego do maski kodu pocztowego';


CREATE INDEX idx_postal_code_mask
  ON postal_code (mask);

CREATE TABLE postal_code_aud (
  id          BIGSERIAL NOT NULL,
  mask        VARCHAR(6),
  operator_id BIGINT,
  REV         INTEGER   NOT NULL,
  REVTYPE     SMALLINT,
  CONSTRAINT pk_postal_code_aud PRIMARY KEY (id, REV)
);