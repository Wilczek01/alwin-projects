alter table issue_aud add creation_date timestamp;
comment on column issue.creation_date is 'Data i godzina utworzenia zlecenia';