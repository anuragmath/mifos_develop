package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;

public class PaymentInventoryPdcData {



		private final Integer period;
		
		private final org.joda.time.LocalDate date;
		
		private final BigDecimal amount;
		
		private final org.joda.time.LocalDate chequeDate;
		
		private final Long chequeno;
		
		private final String nameOfBank;
		
		private final String ifscCode;
		
		private final int presentationStatus;
		
		private final boolean makePresentation;
		
		public PaymentInventoryPdcData(final Integer period,final org.joda.time.LocalDate date, final BigDecimal amount, final org.joda.time.LocalDate chequeDate2
				,final Long chequeno, final String nameOfBank, final String ifscCode, final int presentationStatus, final boolean makePresentation){
			this.period = period;
			this.date = date;
			this.amount = amount;
			this.chequeDate = chequeDate2;
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
