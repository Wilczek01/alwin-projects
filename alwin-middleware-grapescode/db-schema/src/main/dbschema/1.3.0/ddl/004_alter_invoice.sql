alter table invoice
  alter column current_balance drop not null;

alter table invoice
  alter column paid drop not null;