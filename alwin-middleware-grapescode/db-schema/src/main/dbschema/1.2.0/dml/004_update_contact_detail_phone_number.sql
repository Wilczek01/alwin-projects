UPDATE contact_detail
SET contact_type = 'PHONE_NUMBER'
WHERE contact_type in ('MOBILE_PHONE', 'PHONE_NUMBER_1', 'PHONE_NUMBER_2', 'PHONE_NUMBER_3');

UPDATE contact_detail_aud
SET contact_type = 'PHONE_NUMBER'
WHERE contact_type in ('MOBILE_PHONE', 'PHONE_NUMBER_1', 'PHONE_NUMBER_2', 'PHONE_NUMBER_3');
