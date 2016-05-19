package org.apache.fineract.portfolio.loanaccount.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface PaymentInventoryWritePlatformService {

	CommandProcessingResult addPaymentInventory(Long loanId, JsonCommand command);

}
