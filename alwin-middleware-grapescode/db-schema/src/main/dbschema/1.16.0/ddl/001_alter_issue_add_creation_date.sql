alter table issue add creation_date timestamp;
comment on column issue.creation_date is 'Data i godzina utworzenia zlecenia';
update issue set creation_date = start_date where creation_date isnull;
alter table issue alter column creation_date set not null;