INSERT INTO activity_detail_property (property_name, property_type, property_key) VALUES ('Komentarz', 'java.lang.String', 'REMARK');

INSERT INTO activity_detail (activity_detail_property_id, activity_id, property_value)
SELECT (SELECT id FROM activity_detail_property WHERE property_key = 'REMARK'), id, remark FROM activity WHERE remark IS NOT NULL;

ALTER TABLE ACTIVITY DROP COLUMN REMARK;

DELETE FROM activity_type_has_detail_property where activity_detail_property_id in
(SELECT id FROM activity_detail_property WHERE property_name = 'Typ wezwania (podstawowe)' OR property_name = 'Typ wezwania (ostateczne)');

DELETE FROM activity_detail where activity_detail_property_id in
(SELECT id FROM activity_detail_property WHERE property_key in ('BASIC_CALL_TYPE', 'FINAL_CALL_TYPE'));

DELETE FROM activity_detail_property WHERE property_name = 'Typ wezwania (podstawowe)';
DELETE FROM activity_detail_property WHERE property_name = 'Typ wezwania (ostateczne)';