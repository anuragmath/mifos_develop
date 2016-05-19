package org.apache.fineract.portfolio.loanaccount.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentInventoryRepository extends JpaRepository<PaymentInventory, Long>, JpaSpecificationExecutor<PaymentInventory>{
	///
}
