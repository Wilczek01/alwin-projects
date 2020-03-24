INSERT INTO public.issue_termination_request (issue_id, request_cause, request_operator_id, excluded_from_stats, exclusion_from_stats_cause, state, manager_operator_id, comment, created, updated)
VALUES
  (10011, 'przyczyna przerwania 1', (SELECT ID
                                     FROM alwin_operator
                                     WHERE login = 'jslowacki'), TRUE, 'przyczyna wyłączenia ze statystyk 1', 'NEW', NULL, NULL, '2016-01-01 01:01:01.000',
   '2016-01-01 01:01:01.000');

INSERT INTO public.issue_termination_request (issue_id, request_cause, request_operator_id, excluded_from_stats, exclusion_from_stats_cause, state, manager_operator_id, comment, created, updated)
VALUES
  (10019, 'przyczyna przerwania 2', (SELECT ID
                                     FROM alwin_operator
                                     WHERE login = 'jslowacki'), TRUE, 'przyczyna wyłączenia ze statystyk 2', 'ACCEPTED', (SELECT ID
                                                                                                                           FROM alwin_operator
                                                                                                                           WHERE login = 'cmilosz'),
   'zgadzdam się, ma to sens', '2016-02-02 02:02:02.000', '2016-02-12 12:12:12.000');

INSERT INTO public.issue_termination_request (issue_id, request_cause, request_operator_id, excluded_from_stats, exclusion_from_stats_cause, state, manager_operator_id, comment, created, updated)
VALUES
  (10012, 'przyczyna przerwania 3', (SELECT ID
                                     FROM alwin_operator
                                     WHERE login = 'jslowacki'), FALSE, NULL, 'REJECTED', (SELECT ID
                                                                                           FROM alwin_operator
                                                                                           WHERE login = 'cmilosz'),
   'nie zgadzdam się, proszę podać lepszy powód ', '2016-03-03 03:03:03.000', '2016-03-13 13:13:13.000');
