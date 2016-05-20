package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class PaymentInventoryPdcData {

		private final Integer period;
		
		private final LocalDate date;
		
		private final BigDecimal amount;
		
		private final LocalDate chequeDate;
		
		private final Long chequeno;
		
		private final String nameOfBank;
		
		private final String ifscCode;
		
		private final int presentationStatus;
		
		private final boolean makePresentation;
		
		
		
		public PaymentInventoryPdcData(final Integer period,final LocalDate date, final BigDecimal amount, final LocalDate chequeDate,
				final Long chequeno, final String nameOfBank, final String ifscCode, final int presentationStatus, final boolean makePresentation){
			this.period = period;
			this.date = date;
			this.amount = amount;
			this.chequeDate = chequeDate;
			this.chequeno = chequeno;
			this.nameOfBank = nameOfBank;
			this.ifscCode = ifscCode;
			this.presentationStatus = presentationStatus;
			this.makePresentation = makePresentation;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public Long getChequeno() {
			return chequeno;
		}
}


