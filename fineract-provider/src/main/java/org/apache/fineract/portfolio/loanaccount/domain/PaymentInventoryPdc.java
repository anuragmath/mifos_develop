package org.apache.fineract.portfolio.loanaccount.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

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
		
	  @Column(name = "ifsc_code", nullable = false)
	  private final String ifscCode;
		
	  @Column(name = "present_type_of", nullable = false)
	  private final Integer presentationStatus;
		
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

	  
	  /*public PaymentInventoryPdc(final PaymentInventory paymentInventory, final Integer period, final Date date, 
			  final BigDecimal amount, final Date chequeDate, final Long chequeno, 
			  final String nameOfBank, final String ifscCode, final Integer presentationStatus, final boolean makePresentation){
		  this.period = period;
		  this.amount = amount;
		  this.chequeDate = chequeDate;
		  this.chequeno = chequeno;
		  this.date = date;
		  this.ifscCode = ifscCode;
		  this.nameOfBank = nameOfBank;
		  this.presentationStatus = presentationStatus;
		  this.makePresentation = makePresentation;
	  }*/


	  public PaymentInventoryPdc(final Integer period, final org.joda.time.LocalDate date, 
			  final BigDecimal amount, final org.joda.time.LocalDate chequeDate, final Long chequeno, 
			  final String nameOfBank, final String ifscCode, final Integer presentationStatus, final boolean makePresentation){
		  this.period = period;
		  this.amount = amount;
		  this.chequeDate = chequeDate.toDate();
		  this.chequeno = chequeno;
		  this.date = date.toDate();
		  this.ifscCode = ifscCode;
		  this.nameOfBank = nameOfBank;
		  this.presentationStatus = presentationStatus;
		  this.makePresentation = makePresentation;
	  }




	public static PaymentInventoryPdc createNew(final Integer period, final org.joda.time.LocalDate date, final BigDecimal amount,
			final org.joda.time.LocalDate chequeDate, final Long chequeno, final String nameOfBank, final String ifscCode,
			final Integer presentationStatus, final boolean makePresentation) {
		// TODO Auto-generated method stub
		return new PaymentInventoryPdc(period, date, amount, chequeDate, chequeno, nameOfBank, ifscCode, presentationStatus, makePresentation);
	}
}
