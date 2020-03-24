--------------------------------------------------------------------------------------------
-- update daty dpd start zlecenia dla zleceń utworzonych automatycznie
--  * dla nowych zleceń DPD start date będzie wynosił 5, gdyż z założenia będzie to najstarszy dłużny dokument spełniający warunek progu kwoty (#25363)
--  * dla istniejących danych wyszukujemy najstarszy dokument spełniający warunek kwoty i jego datę płatności przyjmujemy za DPD start date dla zlecenia
--------------------------------------------------------------------------------------------
update issue isu
set dpd_start_date = (
  select min(i.due_date)
  from issue_invoice ii, invoice i
  where
    ii.invoice_id = i.id
    and
    ii.issue_id = isu.id
    and
    ii.excluded = false
    and
    ii.issue_subject = true
    and
    ((i.currency = 'PLN' and i.current_balance < -100) or (i.currency = 'EUR' and i.current_balance * i.exchange_rate < -100))
)
where isu.created_manually = false and isu.dpd_start_date is null;

--------------------------------------------------------------------------------------------
-- update daty dpd start zlecenia dla zleceń utworzonych ręcznie
--  * dla zleceń utworzonych ręcznie ustawiamy datę płatności najstarszego zaległego dokumentu [saldo <0] lub null jeśli nie ma zaległych dokumentów
--------------------------------------------------------------------------------------------
update issue isu
set dpd_start_date = (
  select min(i.due_date)
  from issue_invoice ii, invoice i
  where
    ii.invoice_id = i.id
    and
    ii.issue_id = isu.id
    and
    ii.excluded = false
    and
    ii.issue_subject = true
    and
    i.current_balance < 0
)
where isu.created_manually = true and isu.dpd_start_date is null;
