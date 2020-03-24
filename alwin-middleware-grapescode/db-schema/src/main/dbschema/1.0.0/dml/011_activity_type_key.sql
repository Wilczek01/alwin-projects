-- activity_type
UPDATE activity_type
SET key = 'OUTGOING_PHONE_CALL'
WHERE name = 'Telefon wychodzący';
UPDATE activity_type
SET key = 'FIRST_DEMAND_PAYMENT'
WHERE name = 'Wezwanie do zapłaty (podsatwowe)';
UPDATE activity_type
SET key = 'LAST_DEMAND_PAYMENT'
WHERE name = 'Wezwanie do zapłaty (ostateczne)';
UPDATE activity_type
SET key = 'OUTGOING_SMS'
WHERE name = 'Wiadomość SMS wychodząca';
UPDATE activity_type
SET key = 'INCOMING_PHONE_CALL'
WHERE name = 'Telefon przychodzący';
UPDATE activity_type
SET key = 'OUTGOING_EMAIL'
WHERE name = 'Email wychodzący';
UPDATE activity_type
SET key = 'FAILED_PHONE_CALL_ATTEMPT'
WHERE name = 'Nieudana próba kontaktu tel.';
UPDATE activity_type
SET key = 'INCOMING_EMAIL'
WHERE name = 'Email przychodzący';
UPDATE activity_type
SET key = 'INCOMING_SMS'
WHERE name = 'Wiadomość SMS przychodząca';
UPDATE activity_type
SET key = 'COMMENT'
WHERE name = 'Komentarz';
UPDATE activity_type
SET key = 'DATA_UPDATE'
WHERE name = 'Uaktualnienie danych klienta';

-- activity_detail_property
UPDATE activity_detail_property
SET key = 'PHONE_NUMBER'
WHERE property_name = 'Nr tel.';
UPDATE activity_detail_property
SET key = 'MESSAGE_LEFT'
WHERE property_name = 'Czy pozostawiono wiadomość';
UPDATE activity_detail_property
SET key = 'PHONE_CALL_LENGTH'
WHERE property_name = 'Długość rozmowy';
UPDATE activity_detail_property
SET key = 'DECISION_MAKER_CALL'
WHERE property_name = 'Czy rozmowa z osobą decyzyjną';
UPDATE activity_detail_property
SET key = 'DATE_OF_PAYMENT'
WHERE property_name = 'Termin zapłaty';
UPDATE activity_detail_property
SET key = 'BASIC_CALL_TYPE'
WHERE property_name = 'Typ wezwania (podstawowe)';
UPDATE activity_detail_property
SET key = 'FINAL_CALL_TYPE'
WHERE property_name = 'Typ wezwania (ostateczne)';
UPDATE activity_detail_property
SET key = 'MESSAGE_CONTENT'
WHERE property_name = 'Treść wiadomości';
UPDATE activity_detail_property
SET key = 'EMAIL_ADDRESS'
WHERE property_name = 'Adres email';