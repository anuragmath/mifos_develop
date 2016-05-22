package org.apache.fineract.portfolio.loanaccount.data;

import java.util.Collection;

public class PaymentInventoryData {
	
		
		
		@SuppressWarnings("unused")
		private Long id;
		
		@SuppressWarnings("unused")
		private Integer periods;
		
		@SuppressWarnings("unused")
		private boolean isDirectDebitActive;
		
		private final Long loanId;
		
		private Collection<PaymentInventoryPdcData> paymentInventoryPdc;
		
		
		public PaymentInventoryData template(final Integer Periods){
			return new PaymentInventoryData(null, Periods, false,null,null);
		}
		
		public PaymentInventoryData newInventoryData(final Integer periods, final boolean isDirectDebitActive, final Collection<PaymentInventoryPdcData> paymentInventoryPdcDatas){
			return new PaymentInventoryData(null, periods, isDirectDebitActive, null,paymentInventoryPdcDatas);
		}
		
		
		public PaymentInventoryData defaults(final Long id, final Integer periods, boolean isDirectDebitActive){
			return defaults(id, periods, isDirectDebitActive);
		}
		
		
		public PaymentInventoryData(final Long id,final Integer periods, final boolean isDirectDebitActive,
				final Long loanid, final Collection<PaymentInventoryPdcData> paymentInventoryPdcData){		

			this.id = id;
			this.periods = periods;
			this.isDirectDebitActive = isDirectDebitActive;
			this.loanId = loanid;
			this.paymentInventoryPdc = paymentInventoryPdcData;
			
		}
		
		public PaymentInventoryData(PaymentInventoryData paymentInventoryData,
				Collection<PaymentInventoryPdcData> pdcInventoryData) {
			this.id = paymentInventoryData.id;
			this.loanId = paymentInventoryData.loanId;
			this.periods = paymentInventoryData.periods;
			this.isDirectDebitActive = paymentInventoryData.isDirectDebitActive;
			this.paymentInventoryPdc = pdcInventoryData;
		}
		
		public Collection<PaymentInventoryPdcData> getPaymentInventoryPdcData(){
			return this.paymentInventoryPdc;
		}

}

	
