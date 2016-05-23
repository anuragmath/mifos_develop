package org.apache.fineract.portfolio.loanaccount.service;

import java.util.Collection;

import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryPdcData;

public interface PaymentInventoryReadPlatformService {
	

	Collection<PaymentInventoryPdcData> retrievePdcPaymentDetails(Long pdcId, boolean b);

	
	PaymentInventoryData retrievePaymentDetails(Long id, Long loanId);
	
	Collection<PaymentInventoryData> retrievePaymentInventory(Long loanId);


}
