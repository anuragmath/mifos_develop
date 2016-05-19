package org.apache.fineract.portfolio.loanaccount.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Path("/loans/{loanId}/paymentInventory")
@Component
@Scope("singleton")
public class LoanPaymentInventoryApiResource {
	
	private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(java.util.Arrays.asList("isDirectDebitActive","id"));
	
	private final String resourceNameForPermission = "LOAN";
	
	private final PlatformSecurityContext context;
	
	private final DefaultToApiJsonSerializer<PaymentInventoryData> toApiJsonSerializer;
	private final ApiRequestParameterHelper apiRequestParameterHelper;
	private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	
	
	@Autowired
	public LoanPaymentInventoryApiResource(final PlatformSecurityContext context, final DefaultToApiJsonSerializer<PaymentInventoryData> toApiJsonSerializer,
	final ApiRequestParameterHelper apiRequestParameterHelper,final PortfolioCommandSourceWritePlatformService commandSourceWritePlatformService){
		this.context = context;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.apiRequestParameterHelper = apiRequestParameterHelper;
		this.commandsSourceWritePlatformService = commandSourceWritePlatformService;
	}
	
	
	 @POST
	  @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String addPaymentInventory(@PathParam("loanId") final Long loanId, @QueryParam("command") final String commandParam,
	            final String apiRequestBodyAsJson) {

	        CommandProcessingResult result = null;
	            final CommandWrapper commandRequest = new CommandWrapperBuilder().createPaymentInventory(loanId).withJson(apiRequestBodyAsJson)
	                    .build();
	            result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
	       

	        return this.toApiJsonSerializer.serialize(result);
	    }

	 
	
	

}
