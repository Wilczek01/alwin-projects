insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Nie odbiera');

insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Tel. Wyłączony');

insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Nie ma takiego numeru');

insert into activity_type_detail_property_dict_value (activity_detail_property_id, value)
VALUES ((SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'), 'Inna');
