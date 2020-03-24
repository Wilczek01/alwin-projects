package com.codersteam.alwin.efaktura;

import com.codersteam.alwin.common.prop.AlwinProperties;
import com.codersteam.alwin.common.prop.AlwinPropertyKey;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationException;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationRequestDto;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationResponseDto;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.aliorleasing.ali.contract_termination_api.ws.*;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import java.lang.invoke.MethodHandles;

/**
 * @author Dariusz Rackowski
 */
@Stateless
public class ContractTerminationClient {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AlwinProperties alwinProperties;
    private ConfigurableMapper mapper;

    @WebServiceRef(ContractTerminationApiBeanService.class)
    private ContractTerminationApiBeanService contractTerminationService;

    private ContractTerminationServiceType service;

    public ContractTerminationClient() {
    }

    @Inject
    public ContractTerminationClient(final AlwinProperties alwinProperties, final ContractTerminationClientMapper mapper) {
        this.alwinProperties = alwinProperties;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        service = contractTerminationService.getContractTerminationApiServicePort();
        final BindingProvider bp = (BindingProvider) service;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, alwinProperties.getProperty(AlwinPropertyKey.EFAKTURA_TERMINATION_ENDPOINT));
    }

    public ContractTerminationResponseDto generateContractTermination(final ContractTerminationRequestDto request) throws ContractTerminationException {
        final GenerateContractTerminationRequest generateContractTerminationRequest = new GenerateContractTerminationRequest();
        generateContractTerminationRequest.setContractTermination(mapper.map(request, ContractTermination.class));
        generateContractTerminationRequest.setSecurityInfo(ContractTerminationSecurityInfoUtils.prepareToken());
        try {
            final ContractTermination response = service.generateContractTermination(generateContractTerminationRequest);
            return mapper.map(response, ContractTerminationResponseDto.class);
        } catch (SecurityException_Exception | Exception_Exception e) {
            LOG.error("Error while generating contract termination", e);
            throw new ContractTerminationException(e.getMessage());
        }
    }

}
