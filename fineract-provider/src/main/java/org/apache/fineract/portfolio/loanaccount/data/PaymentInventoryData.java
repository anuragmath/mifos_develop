package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.service.PaymentInventoryReadPlatformService;
import org.joda.time.LocalDate;

public class PaymentInventoryData {
		
		private final Long id;
		
		private final Integer periods;
		
		private final boolean isDirectDebitActive;
		
		private final Long loanId;
		
		private final Collection<PaymentInventoryPdcData> paymentInventoryPdc;
		
		//associations

		private final Collection<LoanRepaymentScheduleInstallment> repayment;
		
		
		public PaymentInventoryData template(final Integer Periods){
			return new PaymentInventoryData(null, Periods, false, null, null, null);
		}
		
		public static PaymentInventoryData onTemplate(final PaymentInventoryData payment,final Collection<LoanRepaymentScheduleInstallment> repayment){
			return new PaymentInventoryData(payment.id, payment.periods, payment.isDirectDebitActive, payment.paymentInventoryPdc,payment.loanId, repayment);
		}
		public static PaymentInventoryData onTemplate(final Collection<LoanRepaymentScheduleInstallment> repayment){
			return onTemplate(repayment);
		}

		public PaymentInventoryData newInventoryData(final Integer periods, final boolean isDirectDebitActive, final Collection<PaymentInventoryPdcData> paymentInventoryPdcDatas){
			return new PaymentInventoryData(null, periods, isDirectDebitActive, null,paymentInventoryPdcDatas, null);
		}
		
		
		public PaymentInventoryData defaults(final Long id, final Integer periods, boolean isDirectDebitActive){
			return defaults(id, periods, isDirectDebitActive);
		}
		
		public PaymentInventoryData(final Long id, final Integer periods,final boolean isDirectDebitActive, final Long loanid, 
				final Collection<PaymentInventoryPdcData> paymentInventoryPdcData){		
			this.id = id;
			this.periods = periods;
			this.isDirectDebitActive = isDirectDebitActive;
			this.loanId = loanid;
			this.paymentInventoryPdc = paymentInventoryPdcData;
			this.repayment = null;
			
		}
		

		public PaymentInventoryData(PaymentInventoryData paymentInventoryData,
				Collection<PaymentInventoryPdcData> pdcInventoryData) {
			this.id = paymentInventoryData.id;
			this.loanId = paymentInventoryData.loanId;
			this.periods = paymentInventoryData.periods;
			this.isDirectDebitActive = paymentInventoryData.isDirectDebitActive;
			this.paymentInventoryPdc = pdcInventoryData;
			this.repayment = null;
		}
		






		public PaymentInventoryData(Object object, Object object2, Object object3, Object object4, Object object5,
				final Collection<LoanRepaymentScheduleInstallment> repayment) {
			this.id = null;
			this.periods = null;
			this.isDirectDebitActive = false;
			this.paymentInventoryPdc = null;
			this.loanId = null;
			this.repayment = repayment;
			
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
		
		public Collection<LoanRepaymentScheduleInstallment> geLoanRepaymentScheduleInstallments(){
			return this.repayment;
		}

}

	
