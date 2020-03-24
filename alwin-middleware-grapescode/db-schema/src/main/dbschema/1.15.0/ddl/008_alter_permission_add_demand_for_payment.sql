ALTER TABLE PERMISSION ADD COLUMN allowed_to_manage_demands_for_payment BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE PERMISSION_AUD ADD COLUMN allowed_to_manage_demands_for_payment BOOLEAN;
