package org.apache.fineract.portfolio.loanaccount.domain;

public enum PdcPresentationEnumOption {
	INVALID(0, "pdcPresentationStatus.invalid"), //
	UNUSED(1, "pdcPresentationStatus.unused"), //
	PRESENTED(2, "pdcPresentationStatus.presented"), //
	PRESENTED_AND_CLEARED(3, "pdcPresentationStatus.presentedAndCleared"), //
	PRESENTED_AND_DECLINED(4, "pdcPresentationStatus.presentedAndDeclined");
	
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
		
		PdcPresentationEnumOption pdcPresentationStatus = null;
		switch (presentationStatus) {
		case 1:
			pdcPresentationStatus = PdcPresentationEnumOption.UNUSED;	
		break;
		case 2:
			pdcPresentationStatus = PdcPresentationEnumOption.PRESENTED;
		break;
		case 3:
			pdcPresentationStatus = PdcPresentationEnumOption.PRESENTED_AND_CLEARED;
		break;
		case 4:
			pdcPresentationStatus = PdcPresentationEnumOption.PRESENTED_AND_DECLINED;
		break;
		default:
			pdcPresentationStatus = PdcPresentationEnumOption.INVALID;
		break;
		}
	return pdcPresentationStatus;
	}
	
	public boolean isInvalid(){
		return this.value.equals(PdcPresentationEnumOption.INVALID.getValue());
	}
	
	public boolean isUnused(){
		return this.value.equals(PdcPresentationEnumOption.UNUSED.getValue());
	}
	
	public boolean isPresented(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED.getValue());
	}
	
	public boolean isPresentedAndCleared(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED_AND_CLEARED.getValue());
	}
	
	public boolean isDeclined(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED_AND_DECLINED.getValue());
	}
}