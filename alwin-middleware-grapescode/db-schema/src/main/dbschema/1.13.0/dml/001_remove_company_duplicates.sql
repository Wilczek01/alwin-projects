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