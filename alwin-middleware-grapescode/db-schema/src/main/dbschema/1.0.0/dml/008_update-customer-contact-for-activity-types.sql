UPDATE activity_type
SET customer_contact = TRUE
WHERE
  name IN ('Telefon wychodzący', 'Wiadomość SMS wychodząca', 'Telefon przychodzący', 'Email wychodzący', 'Email przychodzący', 'Wiadomość SMS przychodząca');