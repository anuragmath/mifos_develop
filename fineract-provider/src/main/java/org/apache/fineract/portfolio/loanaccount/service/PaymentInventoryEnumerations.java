package org.apache.fineract.portfolio.loanaccount.service;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.domain.PdcPresentationEnumOption;

public class PaymentInventoryEnumerations {
	
	public static EnumOptionData presentationTime(final int d){
		return presentationTime(PdcPresentationEnumOption.fromInt(d));
	}

	public static EnumOptionData presentationTime(final PdcPresentationEnumOption pdcPresentation){
		EnumOptionData option = null;
		
		switch(pdcPresentation){
			
		case INVENTORY:
			option = new EnumOptionData(PdcPresentationEnumOption.INVENTORY.getValue().longValue(), PdcPresentationEnumOption.INVENTORY.getCode(), "Inventory");
			break;
		case PRESENTED:
			option = new EnumOptionData(PdcPresentationEnumOption.PRESENTED.getValue().longValue(), PdcPresentationEnumOption.PRESENTED.getCode(), "Presented");

		break;
		case PRESENTED_AND_CLEARED:
			option = new EnumOptionData(PdcPresentationEnumOption.PRESENTED_AND_CLEARED.getValue().longValue(), PdcPresentationEnumOption.PRESENTED_AND_CLEARED.getCode(), "Presented and Cleared");
			
			break;
		case DECLINED:
			option = new EnumOptionData(PdcPresentationEnumOption.DECLINED.getValue().longValue(), PdcPresentationEnumOption.DECLINED.getCode(), "Declined");
			break;
			
		default:
		
		option = new EnumOptionData(PdcPresentationEnumOption.INVALID.getValue().longValue(), PdcPresentationEnumOption.INVALID.getCode(), "Invalid");
		break;
		
		}
		return option;
	}
}
