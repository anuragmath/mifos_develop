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
		
		
		public PaymentInventoryData(final Long id,
				final Integer periods,
				
				final boolean isDirectDebitActive,
				
				final Long loanid, final Collection<PaymentInventoryPdcData> paymentInventoryPdcDatas){
				

			this.id = id;
			this.periods = periods;
			this.isDirectDebitActive = isDirectDebitActive;
			this.loanId = loanid;
			this.paymentInventoryPdc = paymentInventoryPdcDatas;
			
		}
		
		

	}

	
