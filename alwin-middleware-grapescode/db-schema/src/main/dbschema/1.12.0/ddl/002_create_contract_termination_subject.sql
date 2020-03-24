CREATE TABLE contract_termination_subject (
  id                      BIGSERIAL NOT NULL,
  contract_termination_id BIGINT    NOT NULL,
  subject_id              BIGINT    NOT NULL,
  gps_to_install          BOOLEAN   NOT NULL,
  gps_installation_charge NUMERIC(18, 2),
  CONSTRAINT pk_contract_termination_subject PRIMARY KEY (id),
  CONSTRAINT fk_contract_termination FOREIGN KEY (contract_termination_id) REFERENCES contract_termination (id)
);

COMMENT ON TABLE contract_termination_subject
IS 'Przedmioty leasingu dla wypowiedzeń umów';
COMMENT ON COLUMN contract_termination_subject.id
IS 'Identyfikator przedmiotu leasingu w wypowiedzeniu umowy';
COMMENT ON COLUMN contract_termination_subject.subject_id
IS 'Przedmiot leasingu';
COMMENT ON COLUMN contract_termination_subject.gps_to_install
IS 'Czy ma być zainstalowany GPS?';
COMMENT ON COLUMN contract_termination_subject.gps_installation_charge
IS 'Koszt instalacji GPS';

CREATE TABLE contract_termination_subject_aud (
  id                      BIGSERIAL,
  rev                     INTEGER,
  revtype                 SMALLINT,
  subject_id              BIGINT,
  gps_to_install          BOOLEAN,
  gps_installation_charge BIGINT,
  CONSTRAINT pk_contract_termination_subject_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE contract_termination_subject_aud
IS 'Tabela audytowa przedmiotów leasingu dla wypowiedzeń umów';
