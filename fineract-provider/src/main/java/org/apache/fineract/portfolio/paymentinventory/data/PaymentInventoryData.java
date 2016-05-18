package org.apache.fineract.portfolio.paymentinventory.data;

import java.util.Collection;

public class PaymentInventoryData {
	
	@SuppressWarnings("unused")
	private Long id;
	
	@SuppressWarnings("unused")
	private Integer periods;
	
	@SuppressWarnings("unused")
	private boolean isDirectDebitActive;
	
	private Collection<PaymentInventoryPdcData> paymentInventoryPdc;
	
	
	public PaymentInventoryData template(final Long id, final Integer Periods){
		final boolean isDirectDebitActive = false;
		final Collection<PaymentInventoryPdcData> paymentInventoryPdc = null;
		
		return new PaymentInventoryData(id, Periods, isDirectDebitActive, paymentInventoryPdc);
		
	}
	
	
	public PaymentInventoryData defaults(final Long id, final Integer periods, boolean isDirectDebitActive, final Collection<PaymentInventoryData> paymentInventoryPdc){
		return defaults(id, periods, isDirectDebitActive, paymentInventoryPdc);
	}
	
	
	private PaymentInventoryData(final Long id,
			final Integer periods,
			
			final boolean isDirectDebitActive,
			
			final Collection<PaymentInventoryPdcData> paymentInventoryPdc){
		
		this.id = id;
		this.periods = periods;
		this.isDirectDebitActive = isDirectDebitActive;
		this.paymentInventoryPdc = paymentInventoryPdc;
		
	}
	
	

}
