INSERT INTO activity_detail_property (property_name, property_type, property_key)
VALUES ('Przyczyna nieudanego kontaktu tel.', 'java.lang.String', 'FAILED_PHONE_CALL_REASON');

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id, state, required)
VALUES ((SELECT id FROM activity_type WHERE key = 'FAILED_PHONE_CALL_ATTEMPT'),
        (SELECT id FROM activity_detail_property WHERE property_key = 'FAILED_PHONE_CALL_REASON'),
        'EXECUTED',
        true);
