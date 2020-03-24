-- wezwanie 1, segment A
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('FIRST', 7, 3, 50.00, 'A', -100.00);

-- wezwanie 2, segment A
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('SECOND', 15, 3, 99.00, 'A', -100.00);

-- wezwanie 1, segment B
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('FIRST', 7, 3, 50.00, 'B', -100.00);

-- wezwanie 2, segment B
insert into demand_for_payment_type_configuration (key, dpd_start, number_of_days_to_pay, charge, segment, min_due_payment_value)
values ('SECOND', 15, 3, 99.00, 'B', -100.00);
