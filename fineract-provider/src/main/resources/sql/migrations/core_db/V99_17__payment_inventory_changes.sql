ALTER TABLE `m_payment_inventory` MODIFY `loan_id` INT(11) NOT NULL,
ADD CONSTRAINT loan_id_unique unique (loan_id);