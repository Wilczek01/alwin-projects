--aktualizacja danych klienta
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='DATA_UPDATE'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);


--komentarz
update activity_type set can_be_planned=true where "key" = 'COMMENT';

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='COMMENT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='COMMENT'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', true);

--sms przychodzący

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='INCOMING_SMS'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);


--email przychodzący
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='EMAIL_ADDRESS' );

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='INCOMING_EMAIL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);


--nieudana próba kontaktu
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_CALL_PERSON' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_LEFT' );

INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='FAILED_PHONE_CALL_ATTEMPT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--sms wychodzący
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_EMAIL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='EMAIL_ADDRESS' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='MESSAGE_CONTENT'), 'PLANNED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_EMAIL'), (select id from activity_detail_property where property_key='EMAIL_ADDRESS'), 'PLANNED', false);


-- tel przychodzący

-- rozmówca wymagany
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_CALL_PERSON' );

-- Czy pozostawiono wiadomość
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='INCOMING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_LEFT' );

-- Nr tel
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='INCOMING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='INCOMING_PHONE_CALL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);


-- sms wychodzący
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_SMS')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_CONTENT' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

--rozmówca
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='PHONE_CALL_PERSON'), 'PLANNED', false);

--nr tel
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='PHONE_NUMBER'), 'PLANNED', false);

--Treść wiadomości
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_SMS'), (select id from activity_detail_property where property_key='MESSAGE_CONTENT'), 'PLANNED', false);


-- ostateczne wezwanie do zapłaty

--data płatności
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='LAST_DEMAND_PAYMENT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='DATE_OF_PAYMENT' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='LAST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='LAST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);


-- pierwsze wezwanie do zapłaty

--data płatności
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='FIRST_DEMAND_PAYMENT')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='DATE_OF_PAYMENT' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='FIRST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='FIRST_DEMAND_PAYMENT'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);

--phone call wykonana

-- rozmówca wymagany
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_CALL_PERSON' );

-- Czy pozostawiono wiadomość
delete from activity_type_has_detail_property where
  activity_type_id = (select id from activity_type where key='OUTGOING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='MESSAGE_LEFT' );

-- Nr tel
update activity_type_has_detail_property set required=true where
  activity_type_id = (select id from activity_type where key='OUTGOING_PHONE_CALL')
  and activity_detail_property_id = (select id from activity_detail_property where property_key='PHONE_NUMBER' );

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='REMARK'), 'EXECUTED', true);


--phone call planowana

--rozmówca
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='PHONE_CALL_PERSON'), 'PLANNED', false);

--nr tel
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='PHONE_NUMBER'), 'PLANNED', false);

--komentarz
INSERT INTO public.activity_type_has_detail_property
(activity_type_id, activity_detail_property_id, state, required)
VALUES((select id from activity_type where key='OUTGOING_PHONE_CALL'), (select id from activity_detail_property where property_key='REMARK'), 'PLANNED', false);