-- high priority , assign
--1 - date in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10011, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10011, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10011, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10011, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10011, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--2 - date in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10012, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10012, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10012, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10012, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10012, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--3 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10013, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10013, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10013, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10013, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10013, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--4 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10014, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10014, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10014, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10014, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10014, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--normal priority
--5 - day in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10015, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10015, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10015, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10015, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10015, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--6 - date in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10016, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10016, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10016, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10016, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10016, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--7 - today popsponed
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10017, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10017, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10017, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10017, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10017, 1, 1, 'przeniosiona z 10028-04-04 na dzisiaj', 0, 'POSTPONED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--8 - today popsponed
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10018, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10018, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10018, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10018, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10018, 1, 1, 'przeniosiona z 10028-04-04 na dzisiaj', 0, 'POSTPONED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--9 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10019, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10019, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10019, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10019, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10019, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--10 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10020, 5, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10020, 4, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10020, 3, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10020, 2, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10020, 1, 1, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--no assign
-- high priority
--11 - date in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10021, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10021, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10021, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10021, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10021, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--12 - date in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10022, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10022, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10022, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10022, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10022, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--13 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10023, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10023, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10023, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10023, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10023, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--14 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10024, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10024, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10024, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10024, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10024, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--normal priority
--15 - day in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10025, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10025, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10025, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10025, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10025, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--16 - date in the past
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10026, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10026, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10026, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10026, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10026, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp);

--17 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10027, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10027, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10027, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10027, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10027, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--18 - today
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10028, 5, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '10 day', current_timestamp, current_timestamp),
  (10028, 4, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '9 day', current_timestamp, current_timestamp),
  (10028, 3, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '6 day', current_timestamp, current_timestamp),
  (10028, 2, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '1 day', current_timestamp, current_timestamp),
  (10028, 1, NULL, NULL, 0, 'PLANNED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp);

--closed
--19
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10029, 5, NULL, NULL, 0, 'EXECUTED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp),
  (10029, 4, NULL, NULL, 0, 'CANCELED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp),
  (10029, 3, NULL, NULL, 0, 'EXECUTED', current_date + INTERVAL '-3 day', current_timestamp, current_timestamp),
  (10029, 2, NULL, NULL, 0, 'CANCELED', current_date + INTERVAL '-5 day', current_timestamp, current_timestamp),
  (10029, 1, NULL, NULL, 0, 'EXECUTED', current_date - INTERVAL '-10 day', current_timestamp, current_timestamp);

--20
INSERT INTO public.activity (issue_id, activity_type_id, operator_id, remark, charge, activity_state, planned_date, creation_date, activity_date)
VALUES (10030, 5, 1, NULL, 0, 'EXECUTED', current_date + INTERVAL '0 day', current_timestamp, current_timestamp),
  (10030, 4, 1, NULL, 0, 'CANCELED', current_date + INTERVAL '-1 day', current_timestamp, current_timestamp),
  (10030, 3, 1, NULL, 0, 'EXECUTED', current_date + INTERVAL '-3 day', current_timestamp, current_timestamp),
  (10030, 2, 1, NULL, 0, 'CANCELED', current_date + INTERVAL '-5 day', current_timestamp, current_timestamp),
  (10030, 1, 1, NULL, 0, 'EXECUTED', current_date - INTERVAL '-10 day', current_timestamp, current_timestamp);