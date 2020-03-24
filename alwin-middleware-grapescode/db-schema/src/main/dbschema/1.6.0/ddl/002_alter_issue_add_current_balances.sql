ALTER TABLE issue
  rename balance_additional_pln to current_balance_pln;

comment on column issue.current_balance_pln
is 'Aktualne saldo dokumentów, które są przedmiotem zlecenia PLN';

ALTER TABLE issue
  rename balance_additional_eur to current_balance_eur;

comment on column issue.current_balance_eur
is 'Aktualne saldo dokumentów, które są przedmiotem zlecenia EUR';

ALTER TABLE issue_aud
  rename balance_additional_pln to current_balance_pln;

ALTER TABLE issue_aud
  rename balance_additional_eur to current_balance_eur;