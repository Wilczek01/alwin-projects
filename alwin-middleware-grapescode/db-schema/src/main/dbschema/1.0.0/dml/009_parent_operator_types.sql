UPDATE operator_type
SET parent_operator_type_id = (SELECT o.id
                               FROM operator_type o
                               WHERE o.type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
WHERE type_name = 'PHONE_DEBT_COLLECTOR';


UPDATE alwin_operator
SET parent_alwin_operator_id = (SELECT o.id
                                FROM alwin_operator o
                                  JOIN operator_type t ON o.operator_type_id = t.id
                                WHERE t.type_name = 'PHONE_DEBT_COLLECTOR_MANAGER')
WHERE
  id IN (SELECT o.id
         FROM alwin_operator o
           JOIN operator_type t ON o.operator_type_id = t.id
         WHERE t.type_name = 'PHONE_DEBT_COLLECTOR')