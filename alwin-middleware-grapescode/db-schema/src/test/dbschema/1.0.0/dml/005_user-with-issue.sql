INSERT INTO ALWIN_USER (alwin_role_id, first_name, last_name, login, email, salt, "password", status) VALUES
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'PHONE_DEBT_COLLECTOR'),
   'Jan', 'Długosz', 'jdlugosz', 'jdlugosz@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE');

INSERT INTO ALWIN_USER (alwin_role_id, first_name, last_name, login, email, salt, "password", status) VALUES
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'PHONE_DEBT_COLLECTOR'),
   'Aleksander', 'Fredro', 'afredro', 'afredro@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE');


INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10031, (SELECT ID
                FROM ALWIN_USER
                WHERE LOGIN = 'afredro'), 10001, 10000, current_date - INTERVAL '1 day', '2030-08-10', 'testowy powód 21',
               'windykacja telefoniczna', 'NEW', 183, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action (issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10031, 5, 11, (SELECT ID FROM ALWIN_USER WHERE LOGIN = 'afredro'), NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', NULL, current_timestamp, current_timestamp),
  (10031, 4, 10, (SELECT ID FROM ALWIN_USER WHERE LOGIN = 'afredro'), NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', NULL, current_timestamp, current_timestamp),
  (10031, 3, 7, (SELECT ID FROM ALWIN_USER WHERE LOGIN = 'afredro'), NULL, 0, 'PLANNED', current_date + INTERVAL '-3 day', NULL, current_timestamp, current_timestamp),
  (10031, 2, 2, (SELECT ID FROM ALWIN_USER WHERE LOGIN = 'afredro'), NULL, 0, 'PLANNED', current_date + INTERVAL '-5 day', NULL, current_timestamp, current_timestamp),
  (10031, 1, 1, (SELECT ID FROM ALWIN_USER WHERE LOGIN = 'afredro'), NULL, 0, 'PLANNED', current_date + INTERVAL '-10 day', NULL, current_timestamp, current_timestamp);
