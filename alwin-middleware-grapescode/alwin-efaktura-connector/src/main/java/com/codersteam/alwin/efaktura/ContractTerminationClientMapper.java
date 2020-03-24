package com.codersteam.alwin.efaktura;

import com.codersteam.alwin.efaktura.model.termination.ContractTerminationRequestDto;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationResponseDto;
import com.codersteam.alwin.efaktura.model.termination.data.ContractTerminationContractDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import pl.aliorleasing.ali.contract_termination_api.ws.Contract;
import pl.aliorleasing.ali.contract_termination_api.ws.ContractTermination;

import javax.ejb.Stateless;

/**
 * @author Dariusz Rackowski
 */
@Stateless
public class ContractTerminationClientMapper extends ConfigurableMapper {

    @Override
    protected void configure(final MapperFactory factory) {
        factory.classMap(ContractTerminationRequestDto.class, ContractTermination.class)
                .field("contracts", "contracts.contract")
                .field("customer.city", "customerCity")
                .field("customer.email", "customerEmail")
                .field("customer.mailCity", "customerMailCity")
                .field("customer.mailPostalCode", "customerMailPostalCode")
                .field("customer.mailStreet", "customerMailStreet")
                .field("customer.name", "customerName")
                .field("customer.nip", "customerNip")
                .field("customer.no", "customerNo")
                .field("customer.phoneNo1", "customerPhoneNo1")
                .field("customer.phoneNo2", "customerPhoneNo2")
                .field("customer.postalCode", "customerPostalCode")
                .field("customer.street", "customerStreet")
                .byDefault()
                .register();

        factory.classMap(ContractTerminationContractDto.class, Contract.class)
                .field("invoices", "outstandingInvoices.invoice")
                .field("subjects", "contractSubjects.contractSubject")
                .byDefault()
                .register();

        factory.classMap(ContractTerminationResponseDto.class, ContractTermination.class)
                .field("documentHashes", "documentHashes.hash")
                .byDefault()
                .register();
    }

}
