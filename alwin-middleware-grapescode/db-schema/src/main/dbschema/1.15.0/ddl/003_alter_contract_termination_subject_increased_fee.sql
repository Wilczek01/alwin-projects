alter table contract_termination_subject
  add column gps_increased_fee bool not null default false;

alter table contract_termination_subject
  add column gps_increased_installation_charge NUMERIC(18, 2);

alter table contract_termination_subject_aud
  add column gps_increased_fee bool;

alter table contract_termination_subject_aud
  add column gps_increased_installation_charge NUMERIC(18, 2);

COMMENT ON COLUMN contract_termination_subject.gps_increased_fee
IS 'Czy powiększona opłata za instalację GPS';

COMMENT ON COLUMN contract_termination_subject.gps_increased_installation_charge
IS 'Powiększony koszt instalacji GPS';