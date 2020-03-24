ALTER TABLE customer_out_of_service
  ALTER COLUMN start_date SET DEFAULT current_timestamp;
ALTER TABLE customer_out_of_service
  ALTER COLUMN end_date DROP NOT NULL;

ALTER TABLE contract_out_of_service
  ALTER COLUMN start_date SET DEFAULT current_timestamp;
ALTER TABLE customer_out_of_service
  ALTER COLUMN start_date SET NOT NULL;


