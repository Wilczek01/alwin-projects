create view activity_remark_view AS
  select
    ad.activity_id,
    ad.property_value as remark
  from activity_detail ad inner join activity_detail_property adp on ad.activity_detail_property_id = adp.id
  where adp.property_key = 'REMARK';

COMMENT ON VIEW activity_remark_view
IS 'Widok przechowujący komentarze czynności windykacyjnych';