package org.apache.fineract.portfolio.loanaccount.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.loanaccount.data.DisbursementData;
import org.apache.fineract.portfolio.loanaccount.data.LoanAccountData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryPdcData;
import org.apache.fineract.portfolio.loanaccount.data.RepaymentScheduleRelatedLoanData;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleData;
import org.apache.fineract.portfolio.loanaccount.service.LoanReadPlatformService;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.loanschedule.service.LoanScheduleHistoryReadPlatformService;
import org.apache.fineract.portfolio.loanaccount.service.LoanReadPlatformService;
import org.apache.fineract.portfolio.loanaccount.service.LoanReadPlatformServiceImpl;
import org.apache.fineract.portfolio.loanaccount.service.PaymentInventoryReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Path("/loans/{loanId}/paymentInventory")
@Component
@Scope("singleton")
public class LoanPaymentInventoryApiResource {
	
	private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(java.util.Arrays.asList("isDirectDebitActive","id", "paymentInventoryPdc"));
	
	private final String resourceNameForPermissions = "LOAN";
	
	private final PlatformSecurityContext context;
	private final PaymentInventoryReadPlatformService paymentInventoryReadPlatformService;
	private final DefaultToApiJsonSerializer<PaymentInventoryData> toApiJsonSerializer;
	private final DefaultToApiJsonSerializer<LoanScheduleData> toLoanSchedule;
	private final ApiRequestParameterHelper apiRequestParameterHelper;
	private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	private final LoanReadPlatformService loanReadPlatformService;
	private final DefaultToApiJsonSerializer<PaymentInventoryPdcData> pdc;
	
	@Autowired
	public LoanPaymentInventoryApiResource(final PlatformSecurityContext context, final PaymentInventoryReadPlatformService paymentInventoryReadPlatformService,
			final DefaultToApiJsonSerializer<PaymentInventoryData> toApiJsonSerializer,final DefaultToApiJsonSerializer<LoanScheduleData> toLoanSchedule,
			final ApiRequestParameterHelper apiRequestParameterHelper,
			final PortfolioCommandSourceWritePlatformService commandSourceWritePlatformService,final LoanReadPlatformService loanReadPlatformService,
			final DefaultToApiJsonSerializer<PaymentInventoryPdcData> pdc){


		this.context = context;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.apiRequestParameterHelper = apiRequestParameterHelper;
		this.commandsSourceWritePlatformService = commandSourceWritePlatformService;
		this.paymentInventoryReadPlatformService = paymentInventoryReadPlatformService;
		this.loanReadPlatformService = loanReadPlatformService;
		this.toLoanSchedule = toLoanSchedule;
		this.pdc = pdc;
	}	
	
	
	@GET
    @Path("{inventoryId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	public String retrievePaymentInventory(@PathParam("loanId") final Long loanId, @PathParam("inventoryId") final Long inventoryId,
            @Context final UriInfo uriInfo){
			
		this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
		
		final PaymentInventoryData paymentInventory = this.paymentInventoryReadPlatformService.retrieveBasedOnInventoryId(inventoryId);
		
		final Collection<PaymentInventoryPdcData> pdcInventoryData = this.paymentInventoryReadPlatformService
				.retrievePdcPaymentDetails(inventoryId, true);
		
		final PaymentInventoryData paymentInventoryData = new PaymentInventoryData(paymentInventory, pdcInventoryData);
		
		final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
		return this.toApiJsonSerializer.serialize(settings, paymentInventoryData, this.RESPONSE_DATA_PARAMETERS);
		
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
	 
	 
	 
	 @GET
	 @Path("template")
	 @Consumes({ MediaType.APPLICATION_JSON })
	 @Produces({ MediaType.APPLICATION_JSON })
	 public String template(@QueryParam("loanId") final Long loanId , @Context final UriInfo uriInfo){
		 this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
		 
		 LoanAccountData loanBasicDetails = this.loanReadPlatformService.retrieveOne(loanId);
		 final RepaymentScheduleRelatedLoanData repaymentScheduleRelatedData = loanBasicDetails.repaymentScheduleRelatedData();
		 Collection<DisbursementData> disbursementData = null;
		 
		 //Return the Repayment Data in Formated manner with minimum value and th
		 final LoanScheduleData repayment = this.loanReadPlatformService.retrieveRepaymentSchedule(loanId, repaymentScheduleRelatedData, disbursementData, false, loanBasicDetails.getTotalPaidFeeCharges());
		 //Return the enumoptionData
		 
		 final PaymentInventoryPdcData options = this.paymentInventoryReadPlatformService.retrieveEnumOptions();
		 
		 final PaymentInventoryData result = PaymentInventoryData.template(repayment, options);
		 final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
			return this.toApiJsonSerializer.serialize(settings, result, this.RESPONSE_DATA_PARAMETERS);
	 }
	 
	 @DELETE
	    @Path("{inventoryId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String deletePaymentInventory(@PathParam("loanId") final Long loanId, @PathParam("inventoryId") final Long inventoryId) {

		 
	        final CommandWrapper commandRequest = new CommandWrapperBuilder().deletePaymentInventory(loanId, inventoryId).build();

	        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

	        return this.toApiJsonSerializer.serialize(result);
	    }
}
