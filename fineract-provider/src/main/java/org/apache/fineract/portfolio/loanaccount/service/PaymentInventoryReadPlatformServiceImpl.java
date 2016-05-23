package org.apache.fineract.portfolio.loanaccount.service;

import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.common.service.DropdownReadPlatformService;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryData;
import org.apache.fineract.portfolio.loanaccount.data.PaymentInventoryPdcData;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentInventoryReadPlatformServiceImpl implements PaymentInventoryReadPlatformService{
	
	private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    private final DropdownReadPlatformService dropdownReadPlatformService;
    
    @Autowired
    public PaymentInventoryReadPlatformServiceImpl(final PlatformSecurityContext context,
            final RoutingDataSource dataSource, final DropdownReadPlatformService dropdownReadPlatformService) {
    	this.context = context;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dropdownReadPlatformService = dropdownReadPlatformService;
	}
    
    private static final class PaymentInventoryMapper implements RowMapper<PaymentInventoryData> {
    	
    	public String schema(){
    		return "pi.id as id, pi.loan_id as loanId, pi.is_directDebitActive as directDebit, " 
    				+ "pi.periods as periods from m_payment_inventory pi "
    				+ "join m_loan l on l.id = pi.loan_id ";
    	}
    	
    	@Override
    	public PaymentInventoryData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
    		
    		final Long id = rs.getLong("id");
    		final Long loanId = rs.getLong("loanId");
    		final boolean isdirectDebitActive = rs.getBoolean("directDebit");
    		final int periods = rs.getInt("periods");
    		
    		return new PaymentInventoryData(id, periods, isdirectDebitActive, loanId, null);
    	}
    }
    
    @Override
	public PaymentInventoryData retrievePaymentDetails(final Long id, final Long loanId) {
		this.context.authenticatedUser();

        final PaymentInventoryMapper rm = new PaymentInventoryMapper();

        final String sql = "select " + rm.schema() + " where pi.id=? and pi.loan_id=?";

        return this.jdbcTemplate.queryForObject(sql, rm, new Object[] { id, loanId });

	}	
    
    @Override
    public Collection<PaymentInventoryData> retrievePaymentInventory(final Long loanId){
    	this.context.authenticatedUser();
    	
    	final PaymentInventoryMapper rm = new PaymentInventoryMapper();
    	
    	final String sql = "select " + rm.schema() + " where pi.loan_id=? "
    			+ "order by pi.id";
    	
    	return this.jdbcTemplate.query(sql, rm, new Object[] { loanId });
    }

    private static final class PaymentInventoryPdcMapper implements RowMapper<PaymentInventoryPdcData> {
    	
    	public String schema(){
    		return "pdc.period as pdcPeriod, "
    				+ "pdc.date as Date, " + "pdc.amount as Amount, " + "pdc.cheque_date as chequeDate, " + "pdc.cheque_no as chequeNo, "
    				+ "pdc.name_of_bank as bankName, " + "pdc.ifsc_code as ifscCode, " + "pdc.present_type_of as presentationStatus, "
    				+ "pdc.make_presentation as makePresentation " + "from m_payment_inventory_pdc pdc "
    				+ "join m_payment_inventory pi on pi.id = pdc.payment_inventory_id ";
    	}
    	
    	
		@Override
		public PaymentInventoryPdcData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {	
			
			final int pdcPeriod = rs.getInt("pdcPeriod");
			final BigDecimal amount = rs.getBigDecimal("Amount");
			LocalDate date = JdbcSupport.getLocalDate(rs, "Date");
			LocalDate chequeDate = JdbcSupport.getLocalDate(rs, "chequeDate");
			final Long chequeNo = rs.getLong("chequeNo");
			final String bankName = rs.getString("bankName");
			final String ifscCode = rs.getString("ifscCode");
			final int presentationStatus = rs.getInt("presentationStatus");
			final boolean makePresentation = rs.getBoolean("makePresentation");
			
			return new PaymentInventoryPdcData(pdcPeriod, date, amount, chequeDate, chequeNo, bankName, ifscCode, presentationStatus, makePresentation);
		}	
    }
    

    @Override
    public Collection<PaymentInventoryPdcData> retrievePdcPaymentDetails(Long inventoryId, boolean onlyPdcPendingDetails) {
    	final PaymentInventoryPdcMapper rm = new PaymentInventoryPdcMapper();
    	String sql = "select " + rm.schema() + "where pdc.payment_inventory_id=? ";
    	/*if (onlyPdcPendingDetails) {
            sql = sql + "and pdc.waived =0 and lic.is_paid_derived=0";
        }*/
    	return this.jdbcTemplate.query(sql, rm, new Object[] { inventoryId });
    }

}