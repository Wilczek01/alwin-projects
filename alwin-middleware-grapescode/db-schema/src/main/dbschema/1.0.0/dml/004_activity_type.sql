--------------------------------------------------------------------------------------
-- activity_type
--------------------------------------------------------------------------------------

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Telefon wychodzący', FALSE, 0, NULL, TRUE, TRUE, TRUE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wezwanie do zapłaty (podsatwowe)', TRUE, 50, NULL, FALSE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wezwanie do zapłaty (ostateczne)', TRUE, 50, NULL, FALSE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wiadomość SMS wychodząca', TRUE, 0, NULL, FALSE, TRUE, TRUE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Telefon przychodzący', FALSE, 0, NULL, TRUE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Email wychodzący', TRUE, 0, NULL, FALSE, TRUE, TRUE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Nieudana próba kontaktu tel.', FALSE, 0, NULL, FALSE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Email przychodzący', FALSE, 0, NULL, TRUE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Wiadomość SMS przychodząca', FALSE, 0, NULL, TRUE, TRUE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Komentarz', FALSE, 0, NULL, FALSE, FALSE, FALSE);

INSERT INTO activity_type (name, may_be_automated, charge_min, charge_max, may_have_declaration, specific, can_be_planned)
VALUES ('Uaktualnienie danych klienta', FALSE, 0, NULL, FALSE, FALSE, FALSE);

--------------------------------------------------------------------------------------
-- activity_detail_property
--------------------------------------------------------------------------------------

INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Nr tel.', 'java.lang.String');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Czy pozostawiono wiadomość', 'java.lang.Boolean');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Długość rozmowy', 'java.lang.Integer');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Czy rozmowa z osobą decyzyjną', 'java.lang.Boolean');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Termin zapłaty', 'java.util.Date');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Typ wezwania (podstawowe)', 'java.lang.String');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Typ wezwania (ostateczne)', 'java.lang.String');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Treść wiadomości', 'java.lang.String');
INSERT INTO activity_detail_property (property_name, property_type) VALUES ('Adres email', 'java.lang.String');

--------------------------------------------------------------------------------------
-- activity_type_has_detail_property
--------------------------------------------------------------------------------------

-- Telefon wychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy pozostawiono wiadomość')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Długość rozmowy')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy rozmowa z osobą decyzyjną')
);

-- Wezwanie do zapłaty (podsatwowe)
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (podsatwowe)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Termin zapłaty')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (podsatwowe)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Typ wezwania (podstawowe)')
);

-- Wezwanie do zapłaty (ostateczne)
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (ostateczne)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Termin zapłaty')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wezwanie do zapłaty (ostateczne)'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Typ wezwania (ostateczne)')
);

-- Wiadomość SMS wychodząca
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS wychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS wychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

-- Telefon przychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Długość rozmowy')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Telefon przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy rozmowa z osobą decyzyjną')
);

-- Email wychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Adres email')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email wychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

-- Nieudana próba kontaktu tel
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Nieudana próba kontaktu tel.'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Nieudana próba kontaktu tel.'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy pozostawiono wiadomość')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Nieudana próba kontaktu tel.'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Czy rozmowa z osobą decyzyjną')
);

-- Email przychodzący
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Adres email')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Email przychodzący'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);

-- Wiadomość SMS przychodząca
INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS przychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Nr tel.')
);

INSERT INTO activity_type_has_detail_property (activity_type_id, activity_detail_property_id)
VALUES (
  (SELECT id
   FROM activity_type
   WHERE name = 'Wiadomość SMS przychodząca'),
  (SELECT id
   FROM activity_detail_property
   WHERE property_name = 'Treść wiadomości')
);
