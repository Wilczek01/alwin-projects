ALTER SEQUENCE scheduler_execution_id_seq
start 20000 restart 20101
MINVALUE 20000;

INSERT INTO SCHEDULER_EXECUTION
(start_date, end_date, state, type, manual)
VALUES
  ('2018-05-06 01:00:00', '2018-05-06 06:00:00', null, 'GENERATE_ISSUES', false),
  ('2018-05-06 12:00:00', '2018-05-06 13:00:00', null, 'UPDATE_COMPANY_DATA', true),
  ('2018-05-07 06:00:00', '2018-05-07 06:05:00', 'unknown error occurred', 'FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL', false),
  ('2018-05-09 05:00:00', null, null, 'UPDATE_ISSUES_BALANCE', false);
