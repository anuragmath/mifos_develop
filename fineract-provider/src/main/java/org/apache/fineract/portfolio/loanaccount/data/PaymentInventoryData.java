package org.apache.fineract.portfolio.loanaccount.data;

import java.awt.List;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.portfolio.client.data.ClientAddressData;
import org.apache.fineract.portfolio.client.data.ClientData;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleData;
import org.apache.fineract.portfolio.loanaccount.service.PaymentInventoryReadPlatformService;
import org.joda.time.LocalDate;

public class PaymentInventoryData {
		
		private final Long id;
		
		private final Integer periods;
		
		private final boolean isDirectDebitActive;
		
		private final Long loanId;
		
		private final Collection<PaymentInventoryPdcData> paymentInventoryPdc;
	
		private final LoanScheduleData repayment;
		
		private final PaymentInventoryPdcData options;
		

		
		public PaymentInventoryData defaults(final Long id, final Integer periods, boolean isDirectDebitActive){
			return defaults(id, periods, isDirectDebitActive);
		}
		
		public PaymentInventoryData(final Long id, final Integer periods,final boolean isDirectDebitActive, final Long loanid, 
				final Collection<PaymentInventoryPdcData> paymentInventoryPdcData,final LoanScheduleData repayment,
				final PaymentInventoryPdcData options){		
			this.id = id;
			this.periods = periods;
			this.isDirectDebitActive = isDirectDebitActive;
			this.loanId = loanid;
			this.paymentInventoryPdc = paymentInventoryPdcData;
			this.repayment = repayment;
			this.options = options;
		}
		
		public static PaymentInventoryData template(final Integer period, final LoanScheduleData repayment, final PaymentInventoryPdcData options){
			return new PaymentInventoryData(null, null, false, null, null,repayment, options);
		}
		
		public static PaymentInventoryData template(final LoanScheduleData repayment, final PaymentInventoryPdcData options){
			return template(1,repayment, options);
		}

		

		public PaymentInventoryData(PaymentInventoryData paymentInventoryData,
				Collection<PaymentInventoryPdcData> pdcInventoryData) {
			this.id = paymentInventoryData.id;
			this.loanId = paymentInventoryData.loanId;
			this.periods = paymentInventoryData.periods;
			this.isDirectDebitActive = paymentInventoryData.isDirectDebitActive;
			this.paymentInventoryPdc = pdcInventoryData;
			this.repayment = null;
			this.options = null;
		
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

	
