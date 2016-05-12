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
package org.apache.fineract.portfolio.client.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.data.ClientData;
import org.apache.fineract.portfolio.client.data.ClientAddressData;
import org.apache.fineract.portfolio.client.exception.DuplicateClientAddressException;
import org.apache.fineract.portfolio.client.service.ClientAddressReadPlatformService;
import org.apache.fineract.portfolio.client.service.ClientReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.Path;

@Path("/clients/{clientId}/addresses")
@Component
@Scope("singleton")
public class ClientAddressApiResource {
	
	private static final Set<String> CLIENT_ADDRESS_DATA_PARAMETERS = new HashSet<>(Arrays.asList("id", 
			"clientId","addressType", "address_line", "address_line_two", "landmark", "pincode","isboth","city", "stateType","allowedAddressTypes", "allowedStateTypes"));
	
	private final String resourceNameForPermissions = "CLIENTADDRESS";
	
	private final PlatformSecurityContext context;
    private final ClientReadPlatformService clientReadPlatformService;
    private final ClientAddressReadPlatformService clientAddressReadPlatformService;
    private final CodeValueReadPlatformService codeValueReadPlatformService;
    private final DefaultToApiJsonSerializer<ClientAddressData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    
    @Autowired
    
    public ClientAddressApiResource(final PlatformSecurityContext context, final ClientReadPlatformService readPlatformService,
    		final CodeValueReadPlatformService codeValueReadPlatformService,
    		final DefaultToApiJsonSerializer<ClientAddressData>toApiJsonSerializer,
    		final ApiRequestParameterHelper apiRequestParameterHelper,
    		final PortfolioCommandSourceWritePlatformService commandSourceWritePlatformService,
    		final ClientAddressReadPlatformService clientAddressReadPlatformService) {
    	
    	this.context = context;
    	this.clientReadPlatformService = readPlatformService;
    	this.codeValueReadPlatformService = codeValueReadPlatformService;
    	this.toApiJsonSerializer = toApiJsonSerializer;
    	this.apiRequestParameterHelper = apiRequestParameterHelper;
    	this.commandsSourceWritePlatformService = commandSourceWritePlatformService;
    	this.clientAddressReadPlatformService = clientAddressReadPlatformService;
    	
    	
    }
    
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveAllClientAddresses(@Context final UriInfo uriInfo, @PathParam("clientId") final Long clientId ){
    	
    	
    	this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
    	
    	final Collection<ClientAddressData> clientAddresses = this.clientAddressReadPlatformService
                .retrieveClientAddresses(clientId);
    	
    	final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, clientAddresses, CLIENT_ADDRESS_DATA_PARAMETERS);
    	
    	
    }
    
    @GET
    @Path("template")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String newClientAddressDetails(@Context final UriInfo uriInfo) {

        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);

        final Collection<CodeValueData> codeValues = this.codeValueReadPlatformService.retrieveCodeValuesByCode("Address Type");
        final Collection<CodeValueData> stateValues = this.codeValueReadPlatformService.retrieveCodeValuesByCode("State UT");
        
        final ClientAddressData clientAddressData = ClientAddressData.template(codeValues,stateValues);
        
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, clientAddressData, CLIENT_ADDRESS_DATA_PARAMETERS);
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String createClientAddress(@PathParam("clientId") final Long clientId, final String apiRequestBodyAsJson) {

        try {
            final CommandWrapper commandRequest = new CommandWrapperBuilder().createClientAddress(clientId)
                    .withJson(apiRequestBodyAsJson).build();

            final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

            return this.toApiJsonSerializer.serialize(result);
        } catch (final DuplicateClientAddressException e) {
            DuplicateClientAddressException rethrowas = e;
            if (e.getAddressTypeId() != null) {
                // need to fetch client info
                final ClientData clientInfo = this.clientReadPlatformService.retrieveClientByAddress(e.getAddressTypeId(),
                        e.getAddress_Line());
                
                rethrowas = new DuplicateClientAddressException(clientInfo.displayName(), clientInfo.officeName(),
                        e.getAddressType(), e.getAddress_Line());
            }
            
            throw rethrowas;
        }
    }
    
    @GET
    @Path("{addressId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveClientIdentifiers(@PathParam("clientId") final Long clientId,
            @PathParam("addressId") final Long clientAddressId, @Context final UriInfo uriInfo) {

        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);

        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());

        ClientAddressData clientAddressData = this.clientAddressReadPlatformService.retrieveClientAddress(clientId,
                clientAddressId);
        if (settings.isTemplate()) {
        	
        	final Collection<CodeValueData> codeValues = this.codeValueReadPlatformService.retrieveCodeValuesByCode("Address Type");
        	final Collection<CodeValueData> proofValues = this.codeValueReadPlatformService.retrieveCodeValuesByCode("State UT");
            clientAddressData = ClientAddressData.template(clientAddressData, codeValues, proofValues);
            
        
        }
        

        return this.toApiJsonSerializer.serialize(settings, clientAddressData, CLIENT_ADDRESS_DATA_PARAMETERS);
    }

    @PUT
    @Path("{addressId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String updateClientIdentifer(@PathParam("clientId") final Long clientId,
            @PathParam("addressId") final Long clientAddressId, final String apiRequestBodyAsJson) {

        try {
            final CommandWrapper commandRequest = new CommandWrapperBuilder().updateClientAddress(clientId, clientAddressId)
                    .withJson(apiRequestBodyAsJson).build();

            final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

            return this.toApiJsonSerializer.serialize(result);
        } catch (final DuplicateClientAddressException e) {
            DuplicateClientAddressException reThrowAs = e;
            if (e.getAddressTypeId() != null) {
                final ClientData clientInfo = this.clientReadPlatformService.retrieveClientByAddress(e.getAddressTypeId(),
                        e.getAddress_Line());
                reThrowAs = new DuplicateClientAddressException(clientInfo.displayName(), clientInfo.officeName(),
                        e.getAddressType(),e.getAddress_Line());
            }
            throw reThrowAs;
        }
    }

    @DELETE
    @Path("{addressId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String deleteClientIdentifier(@PathParam("clientId") final Long clientId,
            @PathParam("addressId") final Long clientAddressId) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder().deleteClientIdentifier(clientId, clientAddressId).build();

        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return this.toApiJsonSerializer.serialize(result);
    }
    
    		
    		
    		
    	
    
}