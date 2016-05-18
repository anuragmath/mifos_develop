package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.fineract.portfolio.loanaccount.domain.PdcPresentationEnumOption;
import org.joda.time.LocalDate;

import com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;

public class PaymentInventoryPdcData<EnumOptionData> {

		
		private final Long id;

		private final Integer period;
		
		private final LocalDate date;
		
		private final BigDecimal amount;
		
		private final LocalDate chequeDate;
		
		private final Long chequeno;
		
		private final String nameOfBank;
		
		private final String ifscCode;
		
		final Collection<PdcPresentationEnumOption> presentationStatus;
		
		private final boolean makePresentation;
		
		
		public static PaymentInventoryPdcData templateOntop(final PaymentInventoryPdcData paymentInventoryPdcData,
				final Collection<PdcPresentationEnumOption> presentationStatus){
			return new PaymentInventoryPdcData(paymentInventoryPdcData.id,paymentInventoryPdcData.period,paymentInventoryPdcData.date
					,paymentInventoryPdcData.amount,paymentInventoryPdcData.chequeDate,paymentInventoryPdcData.chequeno,
					paymentInventoryPdcData.nameOfBank,paymentInventoryPdcData.ifscCode, presentationStatus,
					paymentInventoryPdcData.makePresentation);
		}
		
		


		
		public PaymentInventoryPdcData LoanPdcDataForRepaymentTemplate(final Integer period, final LocalDate date, final Collection<EnumOptionData> presentationStatus){
			final Long id = null;
			final BigDecimal amount = null;
			final LocalDate chequeDate = null;
			final String nameOfBank = null;
			final String ifscCode = null;
			final boolean makePresentation = false;
			
			return new PaymentInventoryPdcData(id, period, date, amount, chequeDate, id, nameOfBank, ifscCode, presentationStatus, makePresentation);
					
			
		}
		
		
		
		private PaymentInventoryPdcData(Long id, final Integer period, final LocalDate date, final BigDecimal amount, 
				final LocalDate chequeDate, final Long chequeno, final String nameOfBank, final String ifscCode, final Collection<PdcPresentationEnumOption> presentationStatus,
				final boolean makePresentation){
			
			this.id = id;
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
}

