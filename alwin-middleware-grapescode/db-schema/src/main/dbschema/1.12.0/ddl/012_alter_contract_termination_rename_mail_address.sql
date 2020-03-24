ALTER TABLE contract_termination
  RENAME COLUMN company_mail_address_id TO company_correspondence_address_id;

ALTER TABLE contract_termination_aud
  RENAME COLUMN company_mail_address_id TO company_correspondence_address_id;
