package org.apache.fineract.portfolio.loanaccount.handler;

import javax.transaction.Transactional;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.service.PaymentInventoryWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommandType(entity = "PAYMENTINVENTORY", action = "CREATE")
public class AddPaymentInventoryCommandHandler  implements NewCommandSourceHandler{
	
	private final PaymentInventoryWritePlatformService writePlatformService;
	
	@Autowired
	public AddPaymentInventoryCommandHandler(final PaymentInventoryWritePlatformService writePlatformService){
		this.writePlatformService = writePlatformService;
		
	}
	
	@Transactional
	@Override
	public CommandProcessingResult processCommand(final JsonCommand command){
		return this.writePlatformService.addPaymentInventory(command.getLoanId(), command);
	}

}
