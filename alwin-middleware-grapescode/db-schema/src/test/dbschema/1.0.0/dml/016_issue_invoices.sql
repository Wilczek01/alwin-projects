INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000875/12/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000875/12/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-12-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000934/01/2017/RL/LO/PLN', NULL, 676.06, 831.55, '000934/01/2017/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2017-01-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '001076/02/2017/RL/LO/PLN', NULL, 676.06, 831.55, '001076/02/2017/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2017-02-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '001202/03/2017/RL/LO/PLN', NULL, 676.06, 831.55, '001202/03/2017/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2017-03-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '001721/04/2017/RL/LO/PLN', NULL, 676.06, 831.55, '001721/04/2017/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2017-04-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '002260/05/2017/RL/LO/PLN', NULL, 676.06, 831.55, '002260/05/2017/RL/LO/PLN', 'PLN', -831.55, '009077/16/1', 3, '2017-11-30 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000379/06/2016/TO', NULL, 300.00, 369.00, '000379/06/2016/TO', 'PLN', 0.00, '009077/16/1', 5, '2016-07-14 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000690/06/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000690/06/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-07-08 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000536/07/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000536/07/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-07-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000608/08/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000608/08/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-08-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000690/09/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000690/09/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-09-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000769/10/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000769/10/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-10-28 00:00:00.000');
INSERT INTO public.issue_invoice
(issue_id, invoice_id, last_payment_date, net_amount, gross_amount, "number", currency, current_balance, contract_number, type_id, due_date)
VALUES(10011, '000842/11/2016/RL/LO/PLN', NULL, 676.06, 831.55, '000842/11/2016/RL/LO/PLN', 'PLN', 0.00, '009077/16/1', 3, '2016-11-28 00:00:00.000');

INSERT INTO public.issue_invoice
  select
    nextval('issue_invoice_id_seq'), i.id, ii.invoice_id, ii.last_payment_date, ii.net_amount, ii.gross_amount, ii."number", ii.currency, ii.current_balance, ii.contract_number, ii.type_id, ii.due_date
  from public.issue_invoice ii, public.issue i where ii.issue_id = 10011 and i.id > 10011 and i.id < 10031