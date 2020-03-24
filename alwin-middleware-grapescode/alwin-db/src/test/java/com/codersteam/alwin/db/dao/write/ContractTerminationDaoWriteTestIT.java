package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.db.dao.AddressDao;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.testdata.AddressTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dariusz Rackowski
 */
@SuppressWarnings({"SameParameterValue", "OptionalGetWithoutIsPresent"})
@UsingDataSet({"test-permission.json", "test-data.json"})
public class ContractTerminationDaoWriteTestIT extends WriteTestBase {

    @EJB
    private AddressDao addressDao;
    @EJB
    private ContractTerminationDao contractTerminationDao;

    @Test
    public void shouldSaveContractTerminationWithAddress() throws Exception {
        // given
        assertAddress(AddressTestData.ADDRESS_ID_4);
        final ContractTermination contractTermination = buildContractTerminationWithAddress(AddressTestData.ADDRESS_ID_4);

        // when
        contractTerminationDao.create(contractTermination);
        commitTrx();

        // then
        assertContractsTerminationAddress(contractTermination.getId(), AddressTestData.address4());

    }

    private void assertContractsTerminationAddress(final Long contractTerminationId, final Address address) {
        final Optional<ContractTermination> contractTermination1 = contractTerminationDao.get(contractTerminationId);
        assertThat(contractTermination1).isPresent();
        assertThat(contractTermination1.get().getCompanyAddress())
                .isEqualToComparingFieldByFieldRecursively(address);
    }

    private ContractTermination buildContractTerminationWithAddress(final Long addressId) {
        final ContractTermination ct = new ContractTermination();
        ct.setContractNumber("CONTRACT/01");
        ct.setTerminationDate(new Date());
        ct.setType(ContractTerminationType.CONDITIONAL);
        ct.setState(ContractTerminationState.NEW);
        ct.setGeneratedBy("Jan Kowalski");
        ct.setOperatorPhoneNumber("+22 345 34 34");
        ct.setOperatorEmail("jan@kowalski.pl");
        ct.setCompanyAddressId(addressId);
        ct.setSubjects(new ArrayList<>());
        ct.setFormalDebtCollectionInvoices(new ArrayList<>());
        return ct;
    }

    private void assertAddress(final Long addressId) {
        final Optional<Address> address = addressDao.get(addressId);
        assertThat(address).isPresent();
    }

}
