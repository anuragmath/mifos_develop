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
package org.apache.fineract.portfolio.client.service;

import java.util.Map;

import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.codes.exception.CodeValueNotFoundException;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.command.ClientAddressCommand;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientAddress;
import org.apache.fineract.portfolio.client.domain.ClientAddressRepository;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.client.exception.ClientAddressNotFoundException;
import org.apache.fineract.portfolio.client.exception.DuplicateClientAddressException;
import org.apache.fineract.portfolio.client.serialization.ClientAddressCommandFromApiJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientAddressWritePlatformServiceJpaRepositoryImpl implements ClientAddressWritePlatformService{
	
	private final static Logger logger = LoggerFactory.getLogger(ClientAddressWritePlatformServiceJpaRepositoryImpl.class);
	
	private final PlatformSecurityContext context;
    private final ClientRepositoryWrapper clientRepository;
    private final ClientAddressRepository clientAddressRepository;
    private final CodeValueRepositoryWrapper codeValueRepository;
    private final ClientAddressCommandFromApiJsonDeserializer clientAddressCommandFromApiJsonDeserializer;
    
    @Autowired
    public ClientAddressWritePlatformServiceJpaRepositoryImpl(final PlatformSecurityContext context,
            final ClientRepositoryWrapper clientRepository, final ClientAddressRepository clientAddressRepository,
            final CodeValueRepositoryWrapper codeValueRepository,
            final ClientAddressCommandFromApiJsonDeserializer clientAddressCommandFromApiJsonDeserializer) {
        this.context = context;
        this.clientRepository = clientRepository;
        this.clientAddressRepository = clientAddressRepository;
        this.codeValueRepository = codeValueRepository;
        this.clientAddressCommandFromApiJsonDeserializer = clientAddressCommandFromApiJsonDeserializer;
    }
    
    @Transactional
    @Override
    public CommandProcessingResult addClientAddress(final Long clientId, final JsonCommand command){
    	this.context.authenticatedUser();
    	final ClientAddressCommand clientAddressCommand = this.clientAddressCommandFromApiJsonDeserializer
                .commandFromApiJson(command.json());
        clientAddressCommand.validateForCreate();
        
        final String address_line = clientAddressCommand.getAddressLine();
        String addressTypeLabel = null;
        Long addressTypeId = null;
        String stateTypeLabel = null;
        Long stateTypeId = null;
        
        try {
        	
        	final Client client = this.clientRepository.findOneWithNotFoundDetection(clientId);
        	final CodeValue addressType = this.codeValueRepository.findOneWithNotFoundDetection(clientAddressCommand.getAddressTypeId());
        	addressTypeId = addressType.getId();
        	addressTypeLabel = addressType.label();
        	final CodeValue stateType = this.codeValueRepository.findOneWithNotFoundDetection(clientAddressCommand.getStateTypeId());
        	stateTypeId = stateType.getId();
        	stateTypeLabel = stateType.label();
        
        	final ClientAddress clientAddress = ClientAddress.fromJson(client, addressType, stateType, command);
        	
        	this.clientAddressRepository.save(clientAddress);
        	
        	return new CommandProcessingResultBuilder() //
        	 		.withCommandId(command.commandId())//
        	 		.withOfficeId(client.officeId())//
        	 		.withClientId(clientId)//
        	 		.withEntityId(clientAddress.getId())//
        	 		.build();
        } 
        catch (final DataIntegrityViolationException dve){
        	handleClientAddressDataIntegrityViolation(addressTypeLabel,addressTypeId,address_line, dve);
        	return CommandProcessingResult.empty();
        }
       
        	
    }
    
    @Transactional
    @Override
    public CommandProcessingResult updateClientAddress(final Long clientId, final Long addressId, final JsonCommand command ){
    	
    	this.context.authenticatedUser();
        final ClientAddressCommand clientAddressCommand = this.clientAddressCommandFromApiJsonDeserializer
                .commandFromApiJson(command.json());
        clientAddressCommand.validateForUpdate();
        
        String addressTypeLabel = null;
        Long addressTypeId = clientAddressCommand.getAddressTypeId();
        String stateTypeLabel = null;
        Long stateTypeId = clientAddressCommand.getStateTypeId();
        String address_line = null;
        try {
        	
        	CodeValue addressType = null;
        	CodeValue stateType = null;
        	final Client client = this.clientRepository.findOneWithNotFoundDetection(clientId);
            final ClientAddress clientAddressForUpdate = this.clientAddressRepository.findOne(addressId);
            if (clientAddressForUpdate == null) {
            	throw new ClientAddressNotFoundException(addressId); 
            	}
            
            final Map<String, Object> changes = clientAddressForUpdate.update(command);
            
            if (changes.containsKey("addressTypeId")) {
                addressType = this.codeValueRepository.findOneWithNotFoundDetection(addressTypeId);
                if (addressType == null) { throw new CodeValueNotFoundException(addressTypeId); }

                addressTypeId = addressType.getId();
                addressTypeLabel = addressType.label();
                clientAddressForUpdate.updateAddress(addressType);
            }
            
            
            if(changes.containsKey("stateTypeId")){
            	stateType = this.codeValueRepository.findOneWithNotFoundDetection(stateTypeId);
            	if (stateType == null){throw new CodeValueNotFoundException(stateTypeId);}
            	
            	stateTypeId = stateType.getId();
            	stateTypeLabel = stateType.label();
            	
            	clientAddressForUpdate.updateState(stateType);
            }
            
            if (!changes.isEmpty()) {
                this.clientAddressRepository.saveAndFlush(clientAddressForUpdate);
            }
            
            return new CommandProcessingResultBuilder() //
            .withCommandId(command.commandId()) //
            .withOfficeId(client.officeId()) //
            .withClientId(clientId) //
            .withEntityId(addressId) //
            .with(changes) //
            .build();
        
        } catch (final DataIntegrityViolationException dve) {
            handleClientAddressDataIntegrityViolation(addressTypeLabel, addressTypeId,address_line, dve);
            return new CommandProcessingResult(Long.valueOf(-1));
        }
    }
        
        @Transactional
        @Override
        public CommandProcessingResult deleteClientAddress(final Long clientId, final Long addressId, final Long commandId){
        	
        	final Client client = this.clientRepository.findOneWithNotFoundDetection(clientId);
        	
        	final ClientAddress clientAddress = this.clientAddressRepository.findOne(addressId);
            if (clientAddress == null) { throw new ClientAddressNotFoundException(addressId); }
            this.clientAddressRepository.delete(clientAddress);
            
            return new CommandProcessingResultBuilder() //
            .withCommandId(commandId) //
            .withOfficeId(client.officeId()) //
            .withClientId(clientId) //
            .withEntityId(addressId) //
            .build();

        }
        
        private void handleClientAddressDataIntegrityViolation(final String addressTypeLabel, final Long addressTypeId,final String address_line,
        	 final DataIntegrityViolationException dve){
        	
        	 if (dve.getMostSpecificCause().getMessage().contains("unique_address_type")) {
        		 
                 throw new DuplicateClientAddressException(addressTypeLabel);
             }
        	 else if (dve.getMostSpecificCause().getMessage().contains("unique_address_line")) { throw new DuplicateClientAddressException(
                    addressTypeId, addressTypeLabel,address_line); }
        	 logAsErrorUnexpectedDataIntegrityException(dve);	
        	 throw new PlatformDataIntegrityException("error.msg.clientaddress.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
        	 
        }
        
        private void logAsErrorUnexpectedDataIntegrityException(final DataIntegrityViolationException dve) {
            logger.error(dve.getMessage(), dve);

}
               
    }