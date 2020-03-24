INSERT INTO OPERATOR_TYPE_ISSUE_TYPE (operator_type_id, issue_type_id) VALUES

  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_1')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_2')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'DIRECT_DEBT_COLLECTION')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'SUBJECT_TRANSPORT')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'REALIZATION_OF_COLLATERAL')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_PAY')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'ADMIN'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'RESTRUCTURING')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_1')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_2')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR_MANAGER'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_1')),
  ((SELECT ID
    FROM OPERATOR_TYPE
    WHERE TYPE_NAME = 'PHONE_DEBT_COLLECTOR_MANAGER'),
   (SELECT ID
    FROM ISSUE_TYPE
    WHERE NAME = 'PHONE_DEBT_COLLECTION_2'));


