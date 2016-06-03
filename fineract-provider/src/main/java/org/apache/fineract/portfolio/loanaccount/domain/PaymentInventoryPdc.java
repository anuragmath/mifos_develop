/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.portfolio.loanaccount.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.drizzle.jdbc.internal.common.GeneratedIdValueObject;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name = "m_payment_inventory_pdc")
public class PaymentInventoryPdc extends AbstractPersistable<Long>{
	 
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
	  private Integer presentationStatus;
		
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


	  public PaymentInventoryPdc(final Integer period, final LocalDate date, final BigDecimal amount, 
			  final LocalDate chequeDate, final Long chequeno, final String nameOfBank, final String ifscCode, 
			  final PdcPresentationEnumOption status, final boolean makePresentation){
		  this.period = period;
		  this.amount = amount;
		  this.chequeDate = chequeDate.toDate();
		  this.chequeno = chequeno;
		  this.date = date.toDate();
		  this.ifscCode = ifscCode;
		  this.nameOfBank = nameOfBank;
		  this.presentationStatus = status.getValue();
		  this.makePresentation = makePresentation;
	
	  }




	public static PaymentInventoryPdc createNew(final Integer period, final LocalDate date, final BigDecimal amount,
			final LocalDate chequeDate, final Long chequeno, final String nameOfBank, final String ifscCode,
			final Integer presentationStatus, final boolean makePresentation) {
		
		final PdcPresentationEnumOption status = PdcPresentationEnumOption.fromInt(presentationStatus);
		return new PaymentInventoryPdc(period, date, amount, chequeDate, chequeno, nameOfBank, ifscCode, status, makePresentation);
	}
	
	public void setPresentationStatus(final Integer d){
		this.presentationStatus = d;
		
	}
	
	public Integer getPresentation(){
		return this.presentationStatus;
	}
}
