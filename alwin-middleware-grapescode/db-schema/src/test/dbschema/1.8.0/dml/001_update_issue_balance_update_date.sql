-- zmiana daty przeliczania salda na datę z przyszłości w celu uniknięcia każdorazowego przeliczania salda
update issue
set balance_update_date = '2044-10-10 01:00:00';