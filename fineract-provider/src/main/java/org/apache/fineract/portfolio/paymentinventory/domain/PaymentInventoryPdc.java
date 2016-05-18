package org.apache.fineract.portfolio.paymentinventory.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.useradministration.domain.AppUser;

@Entity
@Table( name = "m_payment_inventory_pdc")
public class PaymentInventoryPdc {
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
	
	@Column(name = "period", nullable = false)
    private final Integer period;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private final Date date;
	
	@Column(name = "amount", scale = 6, precision = 19, nullable = false)
	private final BigDecimal amount;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "cheque_date", nullable = false)
    private final Date chequeDate;
	
	@Column(name = "cheque_no", nullable = false)
	private final Long chequeno;
	
	@Column(name = "name_of_bank", length = 100, nullable = true, unique = true)
	private String nameOfBank;
	
	@Column(name = "ifsc_code", length = 100, nullable = true, unique = true)
	private String ifscCode;
	

    @Column(name = "presentation_type_enum", nullable = false)
    private final Integer presentationStatus;
    
    @Column(name = "make_presentation", nullable = false)
    private boolean makePresentation;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private final Date createdDate;

    @ManyToOne
    @JoinColumn(name = "appuser_id", nullable = true)
    private final AppUser appUser;

    
    
    
    protected PaymentInventoryPdc(){
    	this.loan = null;
    	this.date = null;
    	this.presentationStatus = null;
    	
    }

	
	
	

}
