ALTER TABLE alwin_user
  ADD COLUMN phone_number VARCHAR(30);

COMMENT ON COLUMN alwin_user.phone_number IS 'Numer telefonu uzytkownika';