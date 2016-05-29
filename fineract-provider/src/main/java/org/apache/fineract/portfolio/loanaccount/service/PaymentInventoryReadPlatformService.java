package org.apache.fineract.portfolio.loanaccount.service;

import java.util.Collection;
import java.util.List;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryPdcData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventoryPdc;

public interface PaymentInventoryReadPlatformService {
	

	Collection<PaymentInventoryPdcData> retrievePdcPaymentDetails(Long pdcId, boolean b);
	
	PaymentInventoryData retrieveBasedOnLoanId(Long loanId);
	
	PaymentInventoryData retrieveBasedOnInventoryId(Long inventoryId);
	
	Collection<PaymentInventoryPdcData> retrievePdcInventory(Long inventoryId);
	
	PaymentInventoryPdcData retrieveByInstallment(Integer installmentNumber, final Long inventoryId);
	
	
	PaymentInventoryPdcData retrieveEnumOptions();

}
