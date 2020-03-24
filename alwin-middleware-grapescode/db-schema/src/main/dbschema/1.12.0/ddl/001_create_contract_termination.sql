CREATE TABLE contract_termination (
  id                                            BIGSERIAL    NOT NULL,
  contract_number                               VARCHAR(255) NOT NULL,
  termination_date                              TIMESTAMP    NOT NULL,
  type                                          VARCHAR(50)  NOT NULL,
  state                                         VARCHAR(100) NOT NULL,
  generated_by                                  VARCHAR(200) NOT NULL,
  operator_phone_number                         VARCHAR(30)  NOT NULL,
  operator_email                                VARCHAR(512) NOT NULL,
  company_id                                    BIGINT,
  company_name                                  VARCHAR(200),
  company_address_id                            BIGINT,
  company_mail_address_id                       BIGINT,
  resumption_cost                               NUMERIC(18, 2),
  due_date_in_demand_for_payment                TIMESTAMP,
  amount_in_demand_for_payment                  NUMERIC(18, 2),
  total_payments_since_demand_for_payment       NUMERIC(18, 2),
  invoice_number_initiating_demand_for_payment  VARCHAR(255),
  invoice_amount_initiating_demand_for_payment  NUMERIC(18, 2),
  invoice_balance_initiating_demand_for_payment NUMERIC(18, 2),
  remark                                        VARCHAR(255),
  CONSTRAINT pk_contract_termination PRIMARY KEY (id)
);

COMMENT ON TABLE contract_termination
IS 'Wypowiedzenia umów';
COMMENT ON COLUMN contract_termination.id
IS 'Identyfikator wypowiedzenia umowy';
COMMENT ON COLUMN contract_termination.contract_number
IS 'Nr umowy';
COMMENT ON COLUMN contract_termination.termination_date
IS 'Data wypowiedzenia';
COMMENT ON COLUMN contract_termination.type
IS 'Typ wypowiedzenia';
COMMENT ON COLUMN contract_termination.generated_by
IS 'Imię i nazwisko operatora generującego dokument';
COMMENT ON COLUMN contract_termination.operator_phone_number
IS 'Nr telefonu operatora obecnie obsługującego klienta';
COMMENT ON COLUMN contract_termination.operator_email
IS 'Email operatora obecnie obsługującego klienta';
COMMENT ON COLUMN contract_termination.company_id
IS 'Nr klienta (z LEO)';
COMMENT ON COLUMN contract_termination.company_name
IS 'Nazwa klienta';
COMMENT ON COLUMN contract_termination.company_address_id
IS 'Adres siedziby';
COMMENT ON COLUMN contract_termination.company_mail_address_id
IS 'Adres korespondencyjny';
COMMENT ON COLUMN contract_termination.resumption_cost
IS 'Wysokość opłaty za wznowienie';
COMMENT ON COLUMN contract_termination.due_date_in_demand_for_payment
IS 'Termin wskazany do zapłaty na wezwaniu';
COMMENT ON COLUMN contract_termination.amount_in_demand_for_payment
IS 'Kwota wskazana do spłaty na wezwaniu';
COMMENT ON COLUMN contract_termination.total_payments_since_demand_for_payment
IS 'Suma wpłat dokonanych od wezwania';
COMMENT ON COLUMN contract_termination.invoice_number_initiating_demand_for_payment
IS 'Nr FV inicjującej wezwanie do zapłaty';
COMMENT ON COLUMN contract_termination.invoice_amount_initiating_demand_for_payment
IS 'Kwota FV inicjującej wystawienie wezwania';
COMMENT ON COLUMN contract_termination.invoice_balance_initiating_demand_for_payment
IS 'Saldo FV inicjującej wystawienie wezwania';

CREATE TABLE contract_termination_aud (
  id                                            BIGSERIAL NOT NULl,
  rev                                           INTEGER   NOT NULL,
  revtype                                       SMALLINT,
  contract_number                               VARCHAR(255),
  termination_date                              TIMESTAMP,
  type                                          VARCHAR(50),
  generated_by                                  VARCHAR(200),
  operator_phone_number                         VARCHAR(30),
  operator_email                                VARCHAR(512),
  company_id                                    BIGINT,
  company_name                                  VARCHAR(200),
  company_address_id                            BIGINT,
  company_mail_address_id                       BIGINT,
  resumption_cost                               NUMERIC(18, 2),
  due_date_in_demand_for_payment                TIMESTAMP,
  amount_in_demand_for_payment                  NUMERIC(18, 2),
  total_payments_since_demand_for_payment       NUMERIC(18, 2),
  invoice_number_initiating_demand_for_payment  VARCHAR(255),
  invoice_amount_initiating_demand_for_payment  NUMERIC(18, 2),
  invoice_balance_initiating_demand_for_payment NUMERIC(18, 2),
  CONSTRAINT pk_contract_termination_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE contract_termination_aud
IS 'Tabela audytowa wypowiedzeń umów';
