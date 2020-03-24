CREATE TABLE activity_type_detail_property_dict_value (
  id                          BIGSERIAL     NOT NULL,
  activity_detail_property_id BIGINT        NOT NULL,
  value                       VARCHAR(1000) NOT NULL,
  CONSTRAINT pk_activity_type_detail_property_dict_value PRIMARY KEY (id),
  CONSTRAINT idx_activity_detail_value UNIQUE (activity_detail_property_id, value),
  CONSTRAINT fk_activity_detail_property FOREIGN KEY (activity_detail_property_id) REFERENCES activity_detail_property (id)
);

COMMENT ON TABLE activity_type_detail_property_dict_value
IS 'Wartości słownikowe cechy dodatkowej dla czynności';

COMMENT ON COLUMN activity_type_detail_property_dict_value.id
IS 'Identyfikator wartości słownikowej cechy dodatkowej';

COMMENT ON COLUMN activity_type_detail_property_dict_value.activity_detail_property_id
IS 'Link do tabeli typów cech dodatkowych';

COMMENT ON COLUMN activity_type_detail_property_dict_value.value
IS 'Wartość słownikowa dla cechy dodatkowej';

CREATE TABLE activity_type_detail_property_dict_value_aud (
  id                          BIGSERIAL NOT NULl,
  rev                         INTEGER   NOT NULL,
  revtype                     SMALLINT,
  activity_detail_property_id BIGINT,
  dict_value                  VARCHAR(1000),
  CONSTRAINT pk_activity_type_detail_property_dict_value_aud PRIMARY KEY (id, rev)
);

COMMENT ON TABLE activity_type_detail_property_dict_value_aud
IS 'Tabela audytowa wartości słownikowych cechy dodatkowej dla czynności';
