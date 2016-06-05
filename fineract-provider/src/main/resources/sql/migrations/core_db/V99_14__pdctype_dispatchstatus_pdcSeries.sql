ALTER TABLE  `m_payment_inventory`
ADD  `pdc_Type` INT( 11 ) NULL DEFAULT NULL ,
ADD  `is_SeriesCheques` TINYINT( 1 ) NULL DEFAULT NULL ,
ADD  `is_ChequesDispatched` TINYINT( 1 ) NULL DEFAULT NULL ;