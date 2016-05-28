package org.apache.fineract.portfolio.loanaccount.service;

import java.util.Collection;
import java.util.List;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryPdcData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;

public interface PaymentInventoryReadPlatformService {
	

	Collection<PaymentInventoryPdcData> retrievePdcPaymentDetails(Long pdcId, boolean b);

	
	PaymentInventoryData retrievePaymentDetails(Long id, Long loanId);
	
	Collection<PaymentInventoryData> retrievePaymentInventory(Long loanId);


	PaymentInventoryPdcData retrieveEnumOptions();


	
	
	


}
