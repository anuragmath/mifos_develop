package org.apache.fineract.portfolio.loanaccount.domain;

public enum PdcPresentationStatus {
	INVALID(0, "pdcPresentationStatus.invalid"), //
	UNUSED(1, "pdcPresentationStatus.unused"), //
	PRESENTED(2, "pdcPresentationStatus.presented"), //
	PRESENTED_AND_CLEARED(3, "pdcPresentationStatus.presentedAndCleared"), //
	PRESENTED_AND_DECLINED(4, "pdcPresentationStatus.presentedAndDeclined");
	
	private final Integer value;
	private final String code;
	
	private PdcPresentationStatus(final Integer value, final String code) {
		this.value = value;
		this.code = code;
	}
	
	public Integer getValue() {
	     return this.value;
	}

	public String getCode() {
	     return this.code;
	}
	
	public static PdcPresentationStatus fromInt(final Integer presentationStatus){
		
		if(presentationStatus == null) { return PdcPresentationStatus.INVALID; }
		
		PdcPresentationStatus pdcPresentationStatus = null;
		switch (presentationStatus) {
		case 1:
			pdcPresentationStatus = PdcPresentationStatus.UNUSED;	
		break;
		case 2:
			pdcPresentationStatus = PdcPresentationStatus.PRESENTED;
		break;
		case 3:
			pdcPresentationStatus = PdcPresentationStatus.PRESENTED_AND_CLEARED;
		break;
		case 4:
			pdcPresentationStatus = PdcPresentationStatus.PRESENTED_AND_DECLINED;
		break;
		default:
			pdcPresentationStatus = PdcPresentationStatus.INVALID;
		break;
		}
	return pdcPresentationStatus;
	}
	
	public boolean isInvalid(){
		return this.value.equals(PdcPresentationStatus.INVALID.getValue());
	}
	
	public boolean isUnused(){
		return this.value.equals(PdcPresentationStatus.UNUSED.getValue());
	}
	
	public boolean isPresented(){
		return this.value.equals(PdcPresentationStatus.PRESENTED.getValue());
	}
	
	public boolean isPresentedAndCleared(){
		return this.value.equals(PdcPresentationStatus.PRESENTED_AND_CLEARED.getValue());
	}
	
	public boolean isDeclined(){
		return this.value.equals(PdcPresentationStatus.PRESENTED_AND_DECLINED.getValue());
	}
}