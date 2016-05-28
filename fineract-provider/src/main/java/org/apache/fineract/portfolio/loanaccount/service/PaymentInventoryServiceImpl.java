package org.apache.fineract.portfolio.loanaccount.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.domain.PdcPresentationEnumOption;
import org.springframework.stereotype.Service;

@Service
public class PaymentInventoryServiceImpl implements PaymentInventoryService {
	
	 @Override
	    public List<EnumOptionData> retriveEnumOptions() {
	        final List<EnumOptionData> enumOptions = new ArrayList<>();
	        for (final PdcPresentationEnumOption enumOption : PdcPresentationEnumOption.values()) {
	            if (PdcPresentationEnumOption.INVALID.equals(enumOption)) {
	                continue;
	            }
	            enumOptions.add(PaymentInventoryEnumerations.presentationTime(enumOption));
	        }
	        return enumOptions;
	    }

}
