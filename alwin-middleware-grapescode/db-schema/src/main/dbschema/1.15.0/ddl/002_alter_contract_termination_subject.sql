alter table contract_termination_subject
  add column gps_installed bool not null default false;

alter table contract_termination_subject_aud
  add column gps_installed bool;

COMMENT ON COLUMN contract_termination_subject.gps_installed
IS 'Czy przedmiot umowy ma już zainstalowany GPS';