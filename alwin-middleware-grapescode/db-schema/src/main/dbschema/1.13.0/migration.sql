-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/dbschema/1.13.0/db.changelog.xml
-- Ran at: 9/17/18 2:53 PM
-- Against: alwin_admin@jdbc:postgresql://localhost:5432/alwin
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = 'fda8:c63:2ed9:3400:4546:70ea:f5c7:821b%en0 (fda8:c63:2ed9:3400:4546:70ea:f5c7:821b%en0)', LOCKGRANTED = '2018-09-17 14:53:09.916' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/dbschema/1.13.0/db.changelog.xml::001_remove_company_duplicates::pnaroznik
-- sprawdzamy jakie fimry trzeba usunąć i które id jest największe do zostawienia
create table tmp_duplicated_companies as
select * from (
select ext_company_id, max(id) as max_id, count(*) as ile from company group by ext_company_id) as a
where a.ile > 1;

-- pobranie company id do zostawienia z tabeli customer
create table tmp_used_company_id as
select tmp.ext_company_id, cu.company_id from tmp_duplicated_companies tmp, company co, customer cu where
tmp.ext_company_id = co.ext_company_id and co.id = cu.company_id;

-- polaczenie powyzszych danych
UPDATE tmp_duplicated_companies d
SET max_id = u.company_id
FROM tmp_used_company_id u
WHERE u.ext_company_id = d.ext_company_id;

-- id-ki firm do usunięcie
create table tmp_companied_to_delete as
select c.id from company c, tmp_duplicated_companies t where c.ext_company_id = t.ext_company_id and c.id not in (select max_id from tmp_duplicated_companies);

--usuwanie danych
delete from company_person where company_id in (select id from tmp_companied_to_delete);

delete from company_address where company_id in (select id from tmp_companied_to_delete);

delete from company_contact_detail where company_id in (select id from tmp_companied_to_delete);

delete from company where id in (select id from tmp_companied_to_delete);

-- czyszczenie tymczasowej tabeli
drop table tmp_duplicated_companies;

drop table tmp_used_company_id;

drop table tmp_companied_to_delete;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('001_remove_company_duplicates', 'pnaroznik', 'src/main/dbschema/1.13.0/db.changelog.xml', NOW(), 201, '7:13f45e5b84cf344b1ed0f262d07fb250', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '7188790723');

-- Changeset src/main/dbschema/1.13.0/db.changelog.xml::002_update_issue_type_configuration::astepnowski
UPDATE issue_type_configuration SET dpd_start = 50 WHERE dpd_start is null;

UPDATE issue_type_configuration SET min_balance_start = -100.00 WHERE min_balance_start is null;

INSERT INTO databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('002_update_issue_type_configuration', 'astepnowski', 'src/main/dbschema/1.13.0/db.changelog.xml', NOW(), 202, '7:15e751104e15450fc7420e8a871c7f79', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.5.3', '7188790723');

-- Release Database Lock
UPDATE databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

