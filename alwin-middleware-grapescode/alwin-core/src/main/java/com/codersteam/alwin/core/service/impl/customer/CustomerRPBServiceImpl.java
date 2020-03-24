package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.aida.core.api.model.CustomerInvolvementDto;
import com.codersteam.aida.core.api.service.InvolvementService;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.customer.CustomerRPBService;
import com.codersteam.alwin.core.api.service.customer.CustomerService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class CustomerRPBServiceImpl implements CustomerRPBService {

    private final InvolvementService involvementService;
    private final CustomerService customerService;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public CustomerRPBServiceImpl(final AidaService aidaService, final CustomerService customerService) {
        this.involvementService = aidaService.getInvolvementService();
        this.customerService = customerService;
    }

    @Override
    public BigDecimal calculateCompanyRPB(final Long extCompanyId) {
        final List<CustomerInvolvementDto> companyInvolvements = involvementService.getInvolvements(extCompanyId);
        final Set<String> blockedContractNumbers = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);
        return companyInvolvements.stream()
                .filter(customerInvolvement -> !blockedContractNumbers.contains(customerInvolvement.getContractNo()))
                .map(CustomerInvolvementDto::getRpb)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);
    }
}
