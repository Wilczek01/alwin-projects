CREATE TABLE CONTRACT (
  id          BIGSERIAL   NOT NULL,
  customer_id BIGINT      NOT NULL,
  ext_contract_id VARCHAR(30) NOT NULL,
  CONSTRAINT pk_contract PRIMARY KEY (id),
  CONSTRAINT fk_contract_customer FOREIGN KEY (customer_id) REFERENCES CUSTOMER (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_contract
  ON CONTRACT (customer_id);

COMMENT ON TABLE CONTRACT IS 'umowy klientow';
COMMENT ON COLUMN CONTRACT.customer_id IS 'Indentyfikator klienta';
COMMENT ON COLUMN CONTRACT.ext_contract_id IS 'Numer umowy w zewnętrzynm systemie';

CREATE TABLE ISSUE (
  id                    BIGSERIAL      NOT NULL,
  operator_id           BIGINT,
  customer_id           BIGINT,
  contract_id           BIGINT,
  start_date            TIMESTAMP      NOT NULL,
  expiration_date       TIMESTAMP      NOT NULL,
  termination_cause     VARCHAR(1000),
  issue_type            VARCHAR(100)   NOT NULL,
  issue_state           VARCHAR(100)   NOT NULL DEFAULT 'NEW',
  rpb                   NUMERIC(18, 2) NOT NULL,
  balance_start         NUMERIC(18, 2) NOT NULL,
  balance_additional    NUMERIC(18, 2),
  payments              NUMERIC(18, 2),
  priority              NUMERIC(1),
  excluded_from_stats   BOOL           NOT NULL,
  exclusion_cause       VARCHAR(500),
  excluding_operator_id BIGINT,
  parent_issue_id       BIGINT,
  CONSTRAINT pk_issue PRIMARY KEY (id),
  CONSTRAINT fk_issue_contract FOREIGN KEY (contract_id) REFERENCES CONTRACT (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_alwin_user FOREIGN KEY (operator_id) REFERENCES ALWIN_USER (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_issue FOREIGN KEY (parent_issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_excluding_alwin_user FOREIGN KEY (excluding_operator_id) REFERENCES ALWIN_USER (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_issue_alwin_user
  ON ISSUE (operator_id);

CREATE INDEX idx_issue_customer
  ON ISSUE (customer_id);

CREATE INDEX idx_issue_contract
  ON ISSUE (contract_id);

CREATE INDEX idx_issue_excluding_alwin_user
  ON issue (excluding_operator_id);

CREATE INDEX idx_issue_parent_issue
  ON issue (parent_issue_id);

COMMENT ON TABLE ISSUE IS 'zlecenia windykacyjne';
COMMENT ON COLUMN ISSUE.operator_id IS 'Identyfikator uzytkownika zajmujacego sie zleceniem';
COMMENT ON COLUMN ISSUE.customer_id IS 'Identyfikator klienta. UWAGA: zlecenie moze być laczone z klientem lub z umowa (XOR)';
COMMENT ON COLUMN ISSUE.contract_id IS 'Identyfikator umowy. UWAGA: zlecenie moze być laczone z klientem lub z umowa (XOR)';
COMMENT ON COLUMN ISSUE.start_date IS 'Data rozpoczecia zlecenia';
COMMENT ON COLUMN ISSUE.expiration_date IS 'Data wygasniecia zlecenia.';
COMMENT ON COLUMN ISSUE.termination_cause IS 'Przyczyna przerwania realizacji zlecenia (jesli przerwane recznie)';
COMMENT ON COLUMN ISSUE.issue_type IS 'Typ zlecenia';
COMMENT ON COLUMN ISSUE.issue_state IS 'Stan zlecenia: NEW(Nowe) - domyslna wartosc, ustawiana w momencie utworzenia przez system, IN_PROGRESS(Realizowane), DONE(Zakonczone), CANCELED(Anulowane)';
COMMENT ON COLUMN ISSUE.rpb IS 'Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia';
COMMENT ON COLUMN ISSUE.balance_start IS 'Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane';
COMMENT ON COLUMN ISSUE.balance_additional IS 'Saldo dokumentow, ktore staly sie wymagalne w trakcie obslugi zlecenia';
COMMENT ON COLUMN ISSUE.payments IS 'Suma dokonanych wplat';
COMMENT ON COLUMN issue.priority IS 'Priorytet obsługi zlecenia';
COMMENT ON COLUMN issue.excluded_from_stats IS 'Czy zlecenie wykluczone z liczenia statystyk';
COMMENT ON COLUMN issue.exclusion_cause IS 'Przyczyna wykluczenia ze statystyk';
COMMENT ON COLUMN issue.excluding_operator_id IS 'Identyfikator uzytkownika wykluczającego zlecenie z ujęcia w statystykach';
COMMENT ON COLUMN issue.parent_issue_id IS 'Identyfikator zlecenia poprzedzajacego';


CREATE TABLE ISSUE_INVOICE (
  id         BIGSERIAL   NOT NULL,
  issue_id   BIGINT      NOT NULL,
  invoice_id VARCHAR(30) NOT NULL,
  CONSTRAINT pk_issue_invoice PRIMARY KEY (id),
  CONSTRAINT fk_issue_invoice_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_issue_invoice_issue
  ON ISSUE_INVOICE (issue_id);

COMMENT ON TABLE ISSUE_INVOICE IS 'Zestawienie dokumentow obciazeniowych, ktore obejmuje zlecenie - relacja do systemu ksiegowego';
COMMENT ON COLUMN ISSUE_INVOICE.invoice_id IS 'Nr faktury - relacja do systemu ksiegowego';


CREATE TABLE ISSUE_DEBT_COLLECTION_ACTION (
  id                        BIGSERIAL      NOT NULL,
  issue_id                  BIGINT         NOT NULL,
  debt_collection_action_id BIGINT         NOT NULL,
  day                       INTEGER,
  assignee_id               BIGINT,
  comment                   VARCHAR(1000),
  handling_fee              NUMERIC(18, 2) NOT NULL,
  state                     VARCHAR(100)   NOT NULL DEFAULT 'PLANNED',
  start_date                TIMESTAMP      NOT NULL,
  end_date                  TIMESTAMP,
  creating_date             TIMESTAMP      NOT NULL,
  updating_date             TIMESTAMP      NOT NULL,
  CONSTRAINT pk_issue_debt_collection_action PRIMARY KEY (id),
  CONSTRAINT fk_issue_debt_collection_action_issue FOREIGN KEY (issue_id) REFERENCES ISSUE (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_debt_collection_action_debt_collection_action FOREIGN KEY (debt_collection_action_id) REFERENCES DEBT_COLLECTION_ACTION (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_issue_debt_collection_action_alwin_user FOREIGN KEY (assignee_id) REFERENCES ALWIN_USER (id)
);

CREATE INDEX idx_issue_debt_collection_action_issue
  ON ISSUE_DEBT_COLLECTION_ACTION (issue_id);

COMMENT ON TABLE ISSUE_DEBT_COLLECTION_ACTION IS 'Zaplanowane czynnosci windykacyjne dla danego zlecenia';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.id IS 'Identyfikator czynnosci windykacyjnej w danym zleceniu';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.issue_id IS 'Identyfikator zlecenia dla ktorego podejmujemy czynnosc windykacyjna';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.debt_collection_action_id IS 'Identyfikator czynnosci windykacyjnej podejmowanej dla zlecenia';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.day IS 'Dzien w ktorym nalezy podjac czynnosc windykacyjna, jesli wartosc pusta to uzywamy domyslny dzien dla tej czynnosci';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.comment IS 'Komentarz do realizowanej czynnosci windykacyjnej';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.handling_fee IS 'Oplata manipulacyjna';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.state IS 'Stan wykonywanej czynnosci dla zlecenia: PLANNED(Zaplanowana) - domyslna wartosc, EXECUTED(Wykonana), POSTPONED(Przelozona), CANCELED(Anulowana)';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.start_date IS 'Data rozpoczecia czynnosci windykacyjnej dla zlecenia';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.end_date IS 'Data zakonczenia czynnosci windykacyjnej dla zlecenia';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.creating_date IS 'Data utworzenia wpisu';
COMMENT ON COLUMN ISSUE_DEBT_COLLECTION_ACTION.updating_date IS 'Data aktualizacji wpisu';
