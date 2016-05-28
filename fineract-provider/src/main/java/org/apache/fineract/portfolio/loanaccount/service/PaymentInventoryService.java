package org.apache.fineract.portfolio.loanaccount.service;

import java.util.List;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;

public interface PaymentInventoryService {
	
	List<EnumOptionData> retriveEnumOptions();

}
