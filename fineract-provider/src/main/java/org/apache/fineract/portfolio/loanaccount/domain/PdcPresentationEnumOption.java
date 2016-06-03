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

public enum PdcPresentationEnumOption {
	INVALID(0, "pdcPresentationEnumOption.invalid"), //
	INVENTORY(1, "pdcPresentationEnumOption.inventory"), //
	PRESENTED(2, "pdcPresentationEnumOption.presented"), //
	PRESENTED_AND_CLEARED(3, "pdcPresentationEnumOption.presentedAndCleared"), //
	DECLINED(4, "pdcPresentationEnumOption.declined");
	
	private final Integer value;
	private final String code;
	
	private PdcPresentationEnumOption(final Integer value, final String code) {
		this.value = value;
		this.code = code;
	}
	
	public Integer getValue() {
	     return this.value;
	}

	public String getCode() {
	     return this.code;
	}
	
	public static PdcPresentationEnumOption fromInt(final Integer presentationStatus){
		
		if(presentationStatus == null) { return PdcPresentationEnumOption.INVALID; }
		
		PdcPresentationEnumOption pdcPresentationEnumOption = null;
		switch (presentationStatus) {
		case 1:
			pdcPresentationEnumOption = PdcPresentationEnumOption.INVENTORY;	
		break;
		case 2:
			pdcPresentationEnumOption = PdcPresentationEnumOption.PRESENTED;
		break;
		case 3:
			pdcPresentationEnumOption = PdcPresentationEnumOption.PRESENTED_AND_CLEARED;
		break;
		case 4:
			pdcPresentationEnumOption = PdcPresentationEnumOption.DECLINED;
		break;
		default:
			pdcPresentationEnumOption = PdcPresentationEnumOption.INVALID;
		break;
		}
	return pdcPresentationEnumOption;
	}
	
	public boolean isInvalid(){
		return this.value.equals(PdcPresentationEnumOption.INVALID.getValue());
	}
	
	public boolean isPresented(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED.getValue());
	}
	
	public boolean isPresentedAndCleared(){
		return this.value.equals(PdcPresentationEnumOption.PRESENTED_AND_CLEARED.getValue());
	}
	
	public boolean isDeclined(){
		return this.value.equals(PdcPresentationEnumOption.DECLINED.getValue());
	}
}