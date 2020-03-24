CREATE USER alwin_admin_int WITH PASSWORD 'yxFnV!+Yy7H@d5aR';

CREATE DATABASE alwin_int WITH OWNER = alwin_admin_int ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'pl_PL.UTF-8' LC_CTYPE = 'pl_PL.UTF-8' CONNECTION LIMIT = -1 TEMPLATE template0;

-- windows:
-- CREATE DATABASE alwin_int WITH OWNER = alwin_admin_int ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'Polish_Poland.1250' LC_CTYPE = 'Polish_Poland.1250' CONNECTION LIMIT = -1 TEMPLATE template0;

GRANT CONNECT, TEMPORARY ON DATABASE alwin_int TO PUBLIC;
GRANT ALL ON DATABASE alwin_int TO alwin_admin_int;