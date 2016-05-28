package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;
import java.util.List;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.joda.time.LocalDate;

public class PaymentInventoryPdcData {
		


		private final Integer period;
		
		private final LocalDate date;
		
		private final BigDecimal amount;
		
		private final LocalDate chequeDate;
		
		private final Long chequeno;
		
		private final String nameOfBank;
		
		private final String ifscCode;
		
		private final EnumOptionData presentationStatus;
		
		private final boolean makePresentation;
		
		private final List<EnumOptionData> presentationType;
		
		public static PaymentInventoryPdcData template(final List<EnumOptionData> presentationType){
			
			return new PaymentInventoryPdcData(null, null, null, null, null, null, null, null, false, presentationType);
			
		}
		
		public static PaymentInventoryPdcData instance(final Integer period,final LocalDate date, final BigDecimal amount, final LocalDate chequeDate,
				final Long chequeno, final String nameOfBank, final String ifscCode, final EnumOptionData presentationStatus, 
				final boolean makePresentation){
			
			final List<EnumOptionData> presentationType = null;
			
			return new PaymentInventoryPdcData(period, date, amount, chequeDate, chequeno, nameOfBank, ifscCode, presentationStatus, makePresentation, presentationType);
			
		}

		private  PaymentInventoryPdcData( final Integer period,final LocalDate date, final BigDecimal amount, final LocalDate chequeDate,
				final Long chequeno, final String nameOfBank, final String ifscCode, final EnumOptionData presentationStatus, final boolean makePresentation,final List<EnumOptionData> presentationType ){
			this.period = period;
			this.date = date;
			this.amount = amount;
			this.chequeDate = chequeDate;
			this.chequeno = chequeno;
			this.nameOfBank = nameOfBank;
			this.ifscCode = ifscCode;
			this.presentationStatus = presentationStatus;
			this.makePresentation = makePresentation;
			this.presentationType = presentationType;
		}
		
		
		public Integer getPeriod() {
			return period;
		}
		
		


		public LocalDate getDate() {
			return date;
		}


		public LocalDate getChequeDate() {
			return chequeDate;
		}


		public String getNameOfBank() {
			return nameOfBank;
		}


		public String getIfscCode() {
			return ifscCode;
		}


		public EnumOptionData getPresentationStatus() {
			return presentationStatus;
		}


		public boolean isMakePresentation() {
			return makePresentation;
		}


		public BigDecimal getAmount() {
			return amount;
		}

		public Long getChequeno() {
			return chequeno;
		}
}


