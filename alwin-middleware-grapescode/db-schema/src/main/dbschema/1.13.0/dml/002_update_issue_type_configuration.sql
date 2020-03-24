UPDATE issue_type_configuration SET dpd_start = 50 WHERE dpd_start is null;

UPDATE issue_type_configuration SET min_balance_start = -100.00 WHERE min_balance_start is null;