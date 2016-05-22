package org.apache.fineract.portfolio.loanaccount.data;

import java.util.Collection;

public class PaymentInventoryData {
		
		private final Long id;
		
		private final Integer periods;
		
		private final boolean isDirectDebitActive;
		
		private final Long loanId;
		
		private final Collection<PaymentInventoryPdcData> paymentInventoryPdc;
		
		
		public PaymentInventoryData template(final Integer Periods){
			return new PaymentInventoryData(null, Periods, false,null,null);
		}
		
		public PaymentInventoryData newInventoryData(final Integer periods, final boolean isDirectDebitActive, final Collection<PaymentInventoryPdcData> paymentInventoryPdcDatas){
			return new PaymentInventoryData(null, periods, isDirectDebitActive, null,paymentInventoryPdcDatas);
		}
		
		
		public PaymentInventoryData defaults(final Long id, final Integer periods, boolean isDirectDebitActive){
			return defaults(id, periods, isDirectDebitActive);
		}
		
		public PaymentInventoryData(final Long id, final Integer periods,final boolean isDirectDebitActive,
				final Long loanid, final Collection<PaymentInventoryPdcData> paymentInventoryPdcData){		
			this.id = id;
			this.periods = periods;
			this.isDirectDebitActive = isDirectDebitActive;
			this.loanId = loanid;
			this.paymentInventoryPdc = paymentInventoryPdcData;
			
		}
		
		public PaymentInventoryData(PaymentInventoryData paymentInventory,
				Collection<PaymentInventoryPdcData> pdcInventoryData) {
			this.id = paymentInventory.id;
			this.loanId = paymentInventory.loanId;
			this.periods = paymentInventory.periods;
			this.isDirectDebitActive = paymentInventory.isDirectDebitActive;
			this.paymentInventoryPdc = pdcInventoryData;
		}

		public Long getId(){
			return this.id;
		}
		public boolean getIsDirectDebitActive(){
			return this.isDirectDebitActive;
		}
		
		public Collection<PaymentInventoryPdcData> getPaymentInventoryPdcData(){
			return this.paymentInventoryPdc;
		}
}

	
