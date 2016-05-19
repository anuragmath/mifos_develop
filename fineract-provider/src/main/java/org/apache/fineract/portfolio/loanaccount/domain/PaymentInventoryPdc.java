package org.apache.fineract.portfolio.loanaccount.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "m_payment_inventory_pdc")
public class PaymentInventoryPdc extends AbstractPersistable<Long>{
	
	 @ManyToOne(optional = false)
	 @JoinColumn(name = "payment_inventory_id", referencedColumnName = "id", nullable = false)
	 private PaymentInventory paymentinventory;
	 
	 @Column(name = "period", nullable = true)
	 private Integer period;
	 
	  @Temporal(TemporalType.DATE)
	  @Column(name = "date")
	  private Date date;
	  
	  @Column(name = "amount", scale = 6, precision = 19, nullable = false)
	  private BigDecimal amount;
	  
	  @Temporal(TemporalType.DATE)
	  @Column(name = "cheque_date" )
	  private Date chequeDate;
	  
	  @Column(name = "cheque_no", nullable = false)
	  private final Long chequeno;
	
	  @Column(name = "name_of_bank", nullable = false)
	  private final String nameOfBank;
		
	  @Column(name = "ifs_code", nullable = false)
	  private final String ifscCode;
		
	  @Column(name = "present_type_of", nullable = false)
	  private final Collection<PdcPresentationEnumOption> presentationStatus;
		
	  @Column(name = "make_presentation", nullable = false)
	  private final boolean makePresentation;
	  
	  protected PaymentInventoryPdc(){
	    	this.date = null;
	    	this.presentationStatus = null;
	    	this.amount = null;
	    	this.chequeDate = null;
	    	this.chequeno = null;
	    	this.ifscCode = null;
	    	this.period = null;
	    	this.nameOfBank = null;
	    	this.makePresentation = false;
	  }

		private PaymentInventoryPdc(final PaymentInventory paymentInventory, final Integer period, final Date date, final BigDecimal amount,
				final Date chequeDate, final Long chequeno, final String nameOfBank, final String ifscCode, final Collection<PdcPresentationEnumOption> presentationStatus, final boolean makePresentation){
			super();
			this.paymentinventory = paymentInventory;
			this.amount = amount;
			this.period = period;
			this.date = date;
			this.chequeDate = chequeDate;
			this.chequeno = chequeno;
			this.nameOfBank = nameOfBank;
			this.ifscCode = ifscCode;
			this.presentationStatus = presentationStatus;
			this.makePresentation = makePresentation;
		}
}
