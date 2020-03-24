INSERT INTO ALWIN_USER (alwin_role_id, first_name, last_name, login, email, salt, "password", status) VALUES
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'ADMIN'),
   'Adam', 'Mickiewicz', 'amickiewicz', 'amickiewicz@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'PHONE_DEBT_COLLECTOR'),
   'Juliusz', 'Słowacki', 'jslowacki', 'jslowacki@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'FIELD_DEBT_COLLECTOR'),
   'Jan', 'Kochanowski', 'jkochanowski', 'jkochanowski@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'RESTRUCTURING_SPECIALIST'),
   'Zygmunt', 'Krasiński', 'zkrasinski', 'zkrasinski@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'RENUNCIATION_COORDINATOR'),
   'Cyprian Kamil', 'Norwid', 'cknorwid', 'cknorwid@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'SECURITY_SPECIALIST'),
   'Leopold', 'Staff', 'lstaff', 'lstaff@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'PHONE_DEBT_COLLECTOR_MANAGER'),
   'Czesław', 'Miłosz', 'cmilosz', 'cmilosz@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'DIRECT_DEBT_COLLECTION_MANAGER'),
   'Jan', 'Brzechwa', 'jbrzechwa', 'jbrzechwa@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'ANALYST'),
   'Mikołaj', 'Rej', 'mrej', 'mrej@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE'),
  ((SELECT ID
    FROM ALWIN_ROLE
    WHERE NAME = 'DEPARTMENT_MANAGER'),
   'Ignacy', 'Krasicki', 'ikrasicki', 'ikrasicki@wieszcz.pl',
   '7ad53ec9-876f-4cf3-b7cd-526a153e5836',
   '30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d',
   'ACTIVE');

