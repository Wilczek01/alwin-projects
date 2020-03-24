INSERT INTO ISSUE_TYPE (name, label) VALUES ('PHONE_DEBT_COLLECTION_1', 'Windykacja telefoniczna sekcja 1');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('PHONE_DEBT_COLLECTION_2', 'Windykacja telefoniczna sekcja 2');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('DIRECT_DEBT_COLLECTION', 'Windykacja bezpośrednia');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM', 'Windykacja prawna - pozew o wydanie przedmiotu');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('SUBJECT_TRANSPORT', 'Transport przedmiotu');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('REALIZATION_OF_COLLATERAL', 'Realizacja zabezpieczenia');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('LAW_DEBT_COLLECTION_MOTION_TO_PAY', 'Windykacja prawna - pozew o zapłatę');
INSERT INTO ISSUE_TYPE (name, label) VALUES ('RESTRUCTURING', 'Restrukturyzacja');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'PHONE_DEBT_COLLECTION_1'), (SELECT it.ID
                                                 FROM ISSUE_TYPE it
                                                 WHERE it.NAME = 'PHONE_DEBT_COLLECTION_2'), 'none');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'PHONE_DEBT_COLLECTION_2'), (SELECT it.ID
                                                 FROM ISSUE_TYPE it
                                                 WHERE it.NAME = 'DIRECT_DEBT_COLLECTION'), 'none');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'DIRECT_DEBT_COLLECTION'), (SELECT it.ID
                                                FROM ISSUE_TYPE it
                                                WHERE it.NAME = 'SUBJECT_TRANSPORT'), 'securedItem');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'DIRECT_DEBT_COLLECTION'), (SELECT it.ID
                                                FROM ISSUE_TYPE it
                                                WHERE it.NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM'), 'nonSecuredItem');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM'), (SELECT it.ID
                                                                    FROM ISSUE_TYPE it
                                                                    WHERE it.NAME = 'SUBJECT_TRANSPORT'), 'securedItem');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'SUBJECT_TRANSPORT'), (SELECT it.ID
                                           FROM ISSUE_TYPE it
                                           WHERE it.NAME = 'REALIZATION_OF_COLLATERAL'), 'none');

INSERT INTO ISSUE_TYPE_TRANSITION (closed_issue_type_id, child_issue_type_id, condition) VALUES
  ((SELECT it.ID
    FROM ISSUE_TYPE it
    WHERE it.NAME = 'REALIZATION_OF_COLLATERAL'), (SELECT it.ID
                                                   FROM ISSUE_TYPE it
                                                   WHERE it.NAME = 'LAW_DEBT_COLLECTION_MOTION_TO_PAY'), 'none');
