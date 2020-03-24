-- zmiana nazwy tabeli demand_for_payment_type na demand_for_payment_type_configuration

alter table demand_for_payment
  drop constraint fk_demand_for_payment_type;

alter table demand_for_payment
  rename demand_for_payment_type_id to demand_for_payment_type_configuration_id;

alter table demand_for_payment_type
  rename to demand_for_payment_type_configuration;

alter table demand_for_payment
  add CONSTRAINT fk_demand_for_payment_type_configuration FOREIGN KEY (demand_for_payment_type_configuration_id) REFERENCES demand_for_payment_type_configuration (id);
