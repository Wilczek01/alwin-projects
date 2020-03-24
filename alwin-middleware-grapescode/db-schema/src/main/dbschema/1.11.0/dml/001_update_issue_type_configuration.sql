-- Windykacja telefoniczna sekcja 1, segment B
UPDATE issue_type_configuration
SET dpd_start         = 1,
    min_balance_start = -100.00,
    duration          = 9
WHERE segment = 'B'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1');

-- Windykacja telefoniczna sekcja 1, segment A
UPDATE issue_type_configuration
SET dpd_start         = 1,
    min_balance_start = -100.00,
    duration          = 14
WHERE segment = 'A'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1');

-- Windykacja telefoniczna sekcja 2, segment B
UPDATE issue_type_configuration
SET dpd_start         = 11,
    min_balance_start = -100.00,
    duration          = 12
WHERE segment = 'B'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_2');

-- Windykacja telefoniczna sekcja 2, segment A
UPDATE issue_type_configuration
SET dpd_start         = 16,
    min_balance_start = -100.00,
    duration          = 17
WHERE segment = 'A'
  AND issue_type_id = (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_2');

-- wyłączenie automatycznego tworzenia zleceń innych niż WT1 i WT2
UPDATE issue_type_configuration
SET create_automatically = false
WHERE issue_type_id not in (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1'
                                                         OR name = 'PHONE_DEBT_COLLECTION_2');

-- włączenie automatycznego tworzenia zleceń WT1 i WT2
UPDATE issue_type_configuration
SET create_automatically = true
WHERE issue_type_id in (SELECT id FROM issue_type WHERE name = 'PHONE_DEBT_COLLECTION_1'
                                                     OR name = 'PHONE_DEBT_COLLECTION_2');
