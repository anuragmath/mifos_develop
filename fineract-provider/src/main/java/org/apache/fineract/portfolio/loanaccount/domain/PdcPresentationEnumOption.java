package org.apache.fineract.portfolio.loanaccount.domain;

public enum PdcPresentationEnumOption {
	INVALID(0, "pdcPresentationEnumOption.invalid"), //
	INVENTORY(1, "pdcPresentationEnumOption.inventory"), //
	PRESENTED(2, "pdcPresentationEnumOption.presented"), //
	PRESENTED_AND_CLEARED(3, "pdcPresentationEnumOption.presentedAndCleared"), //
	DECLINED(4, "pdcPresentationEnumOption.declined");
	
	private final Integer value;
	private final String code;
	
	private PdcPresentationEnumOption(final Integer value, final String code) {
		this.value = value;
		this.code = code;
	}
	
	public Integer getValue() {
	     return this.value;
	}

	public String getCode() {
	     return this.code;
	}
	
	public static PdcPresentationEnumOption fromInt(final Integer presentationStatus){
		
		if(presentationStatus == null) { return PdcPresentationEnumOption.INVALID; }
		
		PdcPresentationEnumOption pdcPresentationEnumOption = null;
		switch (presentationStatus) {
		case 1:
			pdcPresentationEnumOption = PdcPresentationEnumOption.INVENTORY;	
		break;
		case 2:
			pdcPresentationEnumOption = PdcPresentationEnumOption.PRESENTED;
		break;
		case 3:
			pdcPresentationEnumOption = PdcPresentationEnumOption.PRESENTED_AND_CLEARED;
		break;
		case 4:
			pdcPresentationEnumOption = PdcPresentationEnumOption.DECLINED;
		break;
		default:
			pdcPresentationEnumOption = PdcPresentationEnumOption.INVALID;
		break;
		}
	return pdcPresentationEnumOption;
	}
	
	public boolean isInvalid(){
		return this.value.equals(PdcPresentationEnumOption.INVALID.getValue());
	}
	
	public boolean isPresented(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED.getValue());
	}
	
	public boolean isPresentedAndCleared(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED_AND_CLEARED.getValue());
	}
	
	public boolean isDeclined(){
		return this.value.equals(PdcPresentationEnumOption.DECLINED.getValue());
	}	
}