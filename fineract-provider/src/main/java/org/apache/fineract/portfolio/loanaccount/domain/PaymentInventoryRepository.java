package org.apache.fineract.portfolio.loanaccount.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentInventoryRepository extends JpaRepository<PaymentInventory, Long>, JpaSpecificationExecutor<PaymentInventory>{
	
	//@Query("select paymentinventory from PaymentInventory paymentinventory, IN(paymentinventory.loan) loan where loan.id = :loanId")
	//List<PaymentInventory> retrievePaymentInventoryByLoanId(@Param("loanId") Long loanId);
}
