-- high priority , assign
--1 - date in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10011, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 1', 'windykacja telefoniczna', 'NEW', 290, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10011, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10011, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10011, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10011, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10011, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);


--2 - date in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10012, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 2', 'windykacja telefoniczna', 'NEW', 190, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10012, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10012, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10012, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10012, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10012, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--3 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10013, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 3', 'windykacja telefoniczna', 'NEW', 280, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10013, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10013, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10013, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10013, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10013, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--4 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10014, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 4', 'windykacja telefoniczna', 'NEW', 180, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10014, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10014, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10014, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10014, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10014, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--normal priority
--5 - day in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10015, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 5', 'windykacja telefoniczna', 'NEW', 291, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10015, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10015, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10015, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10015, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10015, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--6 - date in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10016, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 6', 'windykacja telefoniczna', 'NEW', 191, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10016, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10016, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10016, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10016, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10016, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--7 - today popsponed
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10017, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 7', 'windykacja telefoniczna', 'NEW', 284, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10017, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10017, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10017, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10017, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10017, 1, 1, 1, 'przeniosiona z 10028-04-04 na dzisiaj', 0, 'POSTPONED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--8 - today popsponed
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10018, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 8', 'windykacja telefoniczna', 'NEW', 184, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10018, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10018, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10018, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10018, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10018, 1, 1, 1, 'przeniosiona z 10028-04-04 na dzisiaj', 0, 'POSTPONED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--9 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10019, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 9', 'windykacja telefoniczna', 'NEW', 281, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10019, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10019, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10019, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10019, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10019, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--10 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10020, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 10', 'windykacja telefoniczna', 'NEW', 181, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10020, 5, 11, 1, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10020, 4, 10, 1, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10020, 3, 7, 1, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10020, 2, 2, 1, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10020, 1, 1, 1, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);


--no assign
-- high priority
--11 - date in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10021, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 11', 'windykacja telefoniczna', 'NEW', 292, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10021, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10021, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10021, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10021, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10021, 1, 1, null, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--12 - date in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10022, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 12', 'windykacja telefoniczna', 'NEW', 192, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10022, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10022, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10022, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10022, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10022, 1, 1, null, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--13 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10023, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 13', 'windykacja telefoniczna', 'NEW', 282, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10023, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10023, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10023, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10023, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10023, 1, 1, null, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--14 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10024, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 14', 'windykacja telefoniczna', 'NEW', 182, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10024, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10024, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10024, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10024, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10024, 1, 1, null, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--normal priority
--15 - day in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10025, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 15', 'windykacja telefoniczna', 'NEW', 293, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10025, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10025, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10025, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10025, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10025, 1, 1, null, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--16 - date in the past
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10026, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 16', 'windykacja telefoniczna', 'NEW', 193, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10026, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10026, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10026, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10026, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10026, 1, 1, null, null, 0, 'PLANNED', current_date + interval '-1 day', null, current_timestamp, current_timestamp);

--17 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10027, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 17', 'windykacja telefoniczna', 'NEW', 283, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10027, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10027, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10027, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10027, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10027, 1, 1, null, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);

--18 - today
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10028, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 18', 'windykacja telefoniczna', 'NEW', 183, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10028, 5, 11, null, null, 0, 'PLANNED', current_date + interval '10 day', null, current_timestamp, current_timestamp),
  (10028, 4, 10, null, null, 0, 'PLANNED', current_date + interval '9 day', null, current_timestamp, current_timestamp),
  (10028, 3, 7, null, null, 0, 'PLANNED', current_date + interval '6 day', null, current_timestamp, current_timestamp),
  (10028, 2, 2, null, null, 0, 'PLANNED', current_date + interval '1 day', null, current_timestamp, current_timestamp),
  (10028, 1, 1, null, null, 0, 'PLANNED', current_date + interval '0 day', null, current_timestamp, current_timestamp);


--closed
--19
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10029, null, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 19', 'windykacja telefoniczna', 'DONE', 183, 11.11, 22.22, 33.33, FALSE, 1);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10029, 5, 11, null, null, 0, 'EXECUTED', current_date + interval '0 day', null, current_timestamp, current_timestamp),
  (10029, 4, 10, null, null, 0, 'CANCELED', current_date + interval '-1 day', null, current_timestamp, current_timestamp),
  (10029, 3, 7, null, null, 0, 'EXECUTED', current_date + interval '-3 day', null, current_timestamp, current_timestamp),
  (10029, 2, 2, null, null, 0, 'CANCELED', current_date + interval '-5 day', null, current_timestamp, current_timestamp),
  (10029, 1, 1, null, null, 0, 'EXECUTED', current_date - interval '-10 day', null, current_timestamp, current_timestamp);

--20
INSERT INTO ISSUE (id, operator_id, customer_id, contract_id, start_date, expiration_date, termination_cause, issue_type, issue_state, rpb, balance_start, balance_additional, payments, excluded_from_stats, priority)
VALUES (10030, 1, 10000, 10000, current_date - interval '1 day', '10030-08-10', 'testowy powód 20', 'windykacja telefoniczna', 'DONE', 183, 11.11, 22.22, 33.33, FALSE, 0);

INSERT INTO public.issue_debt_collection_action(issue_id, debt_collection_action_id, day, assignee_id, comment, handling_fee, state, start_date, end_date, creating_date, updating_date)
VALUES (10030, 5, 11, 1, null, 0, 'EXECUTED', current_date + interval '0 day', null, current_timestamp, current_timestamp),
  (10030, 4, 10, 1, null, 0, 'CANCELED', current_date + interval '-1 day', null, current_timestamp, current_timestamp),
  (10030, 3, 7, 1, null, 0, 'EXECUTED', current_date + interval '-3 day', null, current_timestamp, current_timestamp),
  (10030, 2, 2, 1, null, 0, 'CANCELED', current_date + interval '-5 day', null, current_timestamp, current_timestamp),
  (10030, 1, 1, 1, null, 0, 'EXECUTED', current_date - interval '-10 day', null, current_timestamp, current_timestamp);