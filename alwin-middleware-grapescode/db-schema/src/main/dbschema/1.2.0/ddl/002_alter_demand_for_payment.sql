ALTER TABLE demand_for_payment
  ALTER COLUMN issue_date DROP NOT NULL;

ALTER TABLE demand_for_payment
  ALTER COLUMN due_date DROP NOT NULL;
