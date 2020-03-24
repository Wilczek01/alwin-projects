update operator_type
set parent_operator_type_id = (select id
                               from operator_type
                               where type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
where type_name = 'PHONE_DEBT_COLLECTOR_1';

update operator_type
set parent_operator_type_id = (select id
                               from operator_type
                               where type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
where type_name = 'PHONE_DEBT_COLLECTOR_2';