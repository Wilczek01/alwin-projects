ALTER TABLE operator_type
  ADD COLUMN parent_operator_type_id BIGINT,
  ADD CONSTRAINT fk_operator_type_parent_operator_type_id FOREIGN KEY (parent_operator_type_id) REFERENCES operator_type (id);


COMMENT ON COLUMN operator_type.parent_operator_type_id IS 'Typ operatora, ktoremu podlega ten typ';