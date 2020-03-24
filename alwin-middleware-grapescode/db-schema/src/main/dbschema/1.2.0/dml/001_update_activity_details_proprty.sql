UPDATE activity_detail_property
SET property_key = 'PHONE_CALL_PERSON', property_type = 'java.lang.String'
WHERE property_key = 'DECISION_MAKER_CALL';

UPDATE activity_detail
SET property_value = 'Inny'
WHERE activity_detail_property_id IN
      (SELECT id
       FROM activity_detail_property
       WHERE property_key = 'PHONE_CALL_PERSON');