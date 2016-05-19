package org.apache.fineract.portfolio.loanaccount.service;

import java.util.Collection;

import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryPdcData;

public interface PaymentInventoryReadPlatformService {
	
	PaymentInventoryPdcData retrievePdcPaymentDetails(Long pdcId, Long loanId);
	
	PaymentInventoryData retrievePaymentDetails(Long id, Long loanId);

}
