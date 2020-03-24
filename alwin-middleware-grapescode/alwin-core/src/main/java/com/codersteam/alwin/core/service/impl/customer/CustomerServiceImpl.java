package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.customer.*;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.*;
import com.codersteam.alwin.jpa.customer.*;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionContractOutOfService;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionCustomerOutOfService;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.codersteam.alwin.core.service.impl.issue.preparator.AddressPreparator.prepareAddresses;
import static com.codersteam.alwin.core.service.impl.issue.preparator.ContactDetailPreparator.prepareContactDetails;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Stateless
@LocalBean
@Local(CustomerService.class)
public class CustomerServiceImpl implements CustomerService {

    private CustomerOutOfServiceDao customerOutOfServiceDao;
    private ContractOutOfServiceDao contractOutOfServiceDao;
    private FormalDebtCollectionCustomerOutOfServiceDao formalDebtCollectionCustomerOutOfServiceDao;
    private FormalDebtCollectionContractOutOfServiceDao formalDebtCollectionContractOutOfServiceDao;
    private DateProvider dateProvider;
    private AlwinMapper mapper;
    private CustomerDao customerDao;
    private AidaService aidaService;
    private PersonDao personDao;
    private CompanyDao companyDao;

    public CustomerServiceImpl() {
    }

    @Inject
    public CustomerServiceImpl(CustomerOutOfServiceDao customerOutOfServiceDao,
                               ContractOutOfServiceDao contractOutOfServiceDao,
                               FormalDebtCollectionCustomerOutOfServiceDao formalDebtCollectionCustomerOutOfServiceDao,
                               FormalDebtCollectionContractOutOfServiceDao formalDebtCollectionContractOutOfServiceDao,
                               DateProvider dateProvider,
                               AlwinMapper mapper,
                               CustomerDao customerDao,
                               AidaService aidaService,
                               PersonDao personDao,
                               CompanyDao companyDao) {
        this.customerOutOfServiceDao = customerOutOfServiceDao;
        this.contractOutOfServiceDao = contractOutOfServiceDao;
        this.formalDebtCollectionCustomerOutOfServiceDao = formalDebtCollectionCustomerOutOfServiceDao;
        this.formalDebtCollectionContractOutOfServiceDao = formalDebtCollectionContractOutOfServiceDao;
        this.dateProvider = dateProvider;
        this.mapper = mapper;
        this.customerDao = customerDao;
        this.aidaService = aidaService;
        this.personDao = personDao;
        this.companyDao = companyDao;
    }


    @Override
    public Optional<CustomerDto> findCustomerByExtId(final Long extCompanyId) {
        final Optional<Customer> result = customerDao.findCustomerByExternalCompanyId(extCompanyId);
        if (!result.isPresent()) {
            return Optional.empty();
        }

        final Customer customer = result.get();

        final CustomerDto customerDto = mapCustomerFromEntity(customer);
        return Optional.of(customerDto);
    }

    @Override
    public boolean isCustomerOutOfService(final Long customerId, final Date operationDate) {
        return customerOutOfServiceDao.isCustomerOutOfService(customerId, operationDate);
    }

    @Override
    public boolean isFormalDebtCollectionCustomerOutOfService(final Long extCompanyId, final Date operationDate, final DemandForPaymentTypeKey demandForPaymentTypeKey) {
        final Optional<Customer> customer = customerDao.findCustomerByExternalCompanyId(extCompanyId);
        if (!customer.isPresent()) {
            return false;
        }
        final Long customerId = customer.get().getId();
        return formalDebtCollectionCustomerOutOfServiceDao.isCustomerOutOfFormalDebtCollection(customerId, operationDate, demandForPaymentTypeKey);
    }

    @Override
    public List<ContractOutOfServiceDto> findActiveContractsOutOfService(final Long extCompanyId) {
        final Date currentDate = dateProvider.getCurrentDate();
        final List<ContractOutOfService> contractsOutOfService = contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, currentDate);
        return mapper.mapAsList(contractsOutOfService, ContractOutOfServiceDto.class);
    }

    @Override
    public List<FormalDebtCollectionContractOutOfServiceDto> findActiveFormalDebtCollectionContractsOutOfService(final Long extCompanyId) {
        final Date currentDate = dateProvider.getCurrentDate();
        final List<FormalDebtCollectionContractOutOfService> contractsOutOfService =
                formalDebtCollectionContractOutOfServiceDao.findActiveFormalDebtCollectionContractsOutOfService(extCompanyId, currentDate);
        return mapper.mapAsList(contractsOutOfService, FormalDebtCollectionContractOutOfServiceDto.class);
    }

    @Override
    public Set<String> findActiveContractOutOfServiceNumbers(final Long extCompanyId) {
        final Date currentDate = dateProvider.getCurrentDate();
        final List<ContractOutOfService> contractsOutOfService = contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, currentDate);
        return contractsOutOfService.stream().map(ContractOutOfService::getContractNo).collect(toSet());
    }

    @Override
    public List<ContractOutOfServiceDto> findAllContractsOutOfService(final Long extCompanyId) {
        final List<ContractOutOfService> contractsOutOfService = contractOutOfServiceDao.findAllContractsOutOfService(extCompanyId);
        return mapper.mapAsList(contractsOutOfService, ContractOutOfServiceDto.class);
    }

    @Override
    public List<FormalDebtCollectionContractOutOfServiceDto> findAllFormalDebtCollectionContractsOutOfService(Long extCompanyId) {
        final List<FormalDebtCollectionContractOutOfService> contractsOutOfService = formalDebtCollectionContractOutOfServiceDao.findAllFormalDebtCollectionContractsOutOfService(extCompanyId);
        return mapper.mapAsList(contractsOutOfService, FormalDebtCollectionContractOutOfServiceDto.class);
    }

    @Override
    public List<CustomerOutOfServiceDto> findCustomersOutOfService(final Long extCompanyId, final Boolean active) {
        final List<CustomerOutOfService> contractsOutOfService;
        if (isTrue(active)) {
            contractsOutOfService = customerOutOfServiceDao.findCustomersOutOfService(extCompanyId, dateProvider.getCurrentDate());
        } else {
            contractsOutOfService = customerOutOfServiceDao.findAllCustomersOutOfService(extCompanyId);
        }
        return mapper.mapAsList(contractsOutOfService, CustomerOutOfServiceDto.class);
    }

    @Override
    public List<FormalDebtCollectionCustomerOutOfServiceDto> findFormalDebtCollectionCustomersOutOfService(final Long extCompanyId, final Boolean active) {
        final List<FormalDebtCollectionCustomerOutOfService> contractsOutOfService;
        if (isTrue(active)) {
            contractsOutOfService = formalDebtCollectionCustomerOutOfServiceDao.findFormalDebtCollectionCustomersOutOfService(extCompanyId, dateProvider.getCurrentDate());
        } else {
            contractsOutOfService = formalDebtCollectionCustomerOutOfServiceDao.findAllFormalDebtCollectionCustomersOutOfService(extCompanyId);
        }
        return mapper.mapAsList(contractsOutOfService, FormalDebtCollectionCustomerOutOfServiceDto.class);
    }

    @Override
    public void blockCustomer(final CustomerOutOfServiceInputDto customer, final long extCompanyId, final Long blockingOperatorId) {
        final CustomerOutOfService customerOutOfService = mapper.map(customer, CustomerOutOfService.class);
        customerOutOfService.setCustomer(getCustomer(extCompanyId));
        customerOutOfServiceDao.create(customerOutOfService, blockingOperatorId);
    }

    @Override
    public void blockFormalDebtCollectionCustomer(final FormalDebtCollectionCustomerOutOfServiceInputDto customer, final long extCompanyId,
                                                  final Long blockingOperatorId) {
        final FormalDebtCollectionCustomerOutOfService customerOutOfService = mapper.map(customer, FormalDebtCollectionCustomerOutOfService.class);
        customerOutOfService.setCustomer(getCustomer(extCompanyId));
        formalDebtCollectionCustomerOutOfServiceDao.create(customerOutOfService, blockingOperatorId);
    }

    @Override
    public void blockCustomerContract(final ContractOutOfServiceInputDto contract, final long extCompanyId, final String contractNo, final Long blockingOperatorId) {
        if (!doesCustomerExist(extCompanyId)) {
            buildAidaCustomer(extCompanyId);
        }
        final ContractOutOfService contractOutOfService = mapper.map(contract, ContractOutOfService.class);
        contractOutOfService.setExtCompanyId(extCompanyId);
        contractOutOfService.setContractNo(contractNo);
        contractOutOfServiceDao.create(contractOutOfService, blockingOperatorId);
    }

    @Override
    public void blockFormalDebtCollectionContract(final FormalDebtCollectionContractOutOfServiceInputDto contract, final long extCompanyId, final String contractNo, final Long blockingOperatorId) {
        if (!doesCustomerExist(extCompanyId)) {
            buildAidaCustomer(extCompanyId);
        }
        final FormalDebtCollectionContractOutOfService contractOutOfService = mapper.map(contract, FormalDebtCollectionContractOutOfService.class);
        contractOutOfService.setExtCompanyId(extCompanyId);
        contractOutOfService.setContractNo(contractNo);
        formalDebtCollectionContractOutOfServiceDao.create(contractOutOfService, blockingOperatorId);
    }

    @Override
    public void updateCustomerOutOfService(final CustomerOutOfServiceInputDto customer, final long customerOutOfServiceId, final Long blockingOperatorId) {
        final Optional<CustomerOutOfService> optionalCustomerOutOfService = customerOutOfServiceDao.get(customerOutOfServiceId);
        final CustomerOutOfService customerOutOfService = optionalCustomerOutOfService.orElseThrow(IllegalArgumentException::new);
        mapper.map(customer, customerOutOfService);
        customerOutOfServiceDao.update(customerOutOfService, blockingOperatorId);
    }

    @Override
    public void updateFormalDebtCollectionCustomerOutOfService(final FormalDebtCollectionCustomerOutOfServiceInputDto customer, final long customerOutOfServiceId,
                                                               final Long blockingOperatorId) {
        final Optional<FormalDebtCollectionCustomerOutOfService> optionalCustomerOutOfService =
                formalDebtCollectionCustomerOutOfServiceDao.get(customerOutOfServiceId);
        final FormalDebtCollectionCustomerOutOfService customerOutOfService = optionalCustomerOutOfService.orElseThrow(IllegalArgumentException::new);
        mapper.map(customer, customerOutOfService);
        formalDebtCollectionCustomerOutOfServiceDao.update(customerOutOfService, blockingOperatorId);
    }

    @Override
    public void updateCustomerContractOutOfService(final ContractOutOfServiceInputDto contract, final Long customerOutOfServiceId, final Long blockingOperatorId) {
        final Optional<ContractOutOfService> optionalContractOutOfService = contractOutOfServiceDao.get(customerOutOfServiceId);
        final ContractOutOfService contractOutOfService = optionalContractOutOfService.orElseThrow(IllegalArgumentException::new);
        mapper.map(contract, contractOutOfService);
        contractOutOfServiceDao.update(contractOutOfService, blockingOperatorId);
    }

    @Override
    public void updateFormalDebtCollectionCustomerContractOutOfService(FormalDebtCollectionContractOutOfServiceInputDto contract, Long customerOutOfServiceId, Long blockingOperatorId) {
        final FormalDebtCollectionContractOutOfService contractOutOfService =
                formalDebtCollectionContractOutOfServiceDao.get(customerOutOfServiceId).orElseThrow(IllegalArgumentException::new);
        mapper.map(contract, contractOutOfService);
        formalDebtCollectionContractOutOfServiceDao.update(contractOutOfService, blockingOperatorId);
    }

    @Override
    public boolean checkIfCustomerOutOfServiceExists(final long customerOutOfServiceId) {
        return customerOutOfServiceDao.checkIfCustomerOutOfServiceExists(customerOutOfServiceId) == 1;
    }

    @Override
    public boolean checkIfFormalDebtCollectionCustomerOutOfServiceExists(final long customerOutOfServiceId) {
        return formalDebtCollectionCustomerOutOfServiceDao.checkIfFormalDebtCollectionCustomerOutOfServiceExists(customerOutOfServiceId);
    }

    @Override
    public boolean checkIfContractOutOfServiceExists(final long contractOutOfServiceId, final long extCompanyId) {
        return contractOutOfServiceDao.checkIfContractOutOfServiceExists(contractOutOfServiceId, extCompanyId) == 1;
    }

    @Override
    public boolean checkIfFormalDebtCollectionContractOutOfServiceExists(final long contractOutOfServiceId, final long extCompanyId) {
        return formalDebtCollectionContractOutOfServiceDao.checkIfFormalDebtCollectionContractOutOfServiceExists(contractOutOfServiceId, extCompanyId);
    }

    @Override
    public void deleteCustomerOutOfService(final long customerOutOfServiceId) {
        customerOutOfServiceDao.delete(customerOutOfServiceId);
    }

    @Override
    public void deleteFormalDebtCollectionCustomerOutOfService(final long customerOutOfServiceId) {
        formalDebtCollectionCustomerOutOfServiceDao.delete(customerOutOfServiceId);
    }

    @Override
    public void deleteContractOutOfService(final long contractOutOfServiceId) {
        contractOutOfServiceDao.delete(contractOutOfServiceId);
    }

    @Override
    public void deleteFormalDebtCollectionContractOutOfService(final long contractOutOfServiceId) {
        formalDebtCollectionContractOutOfServiceDao.delete(contractOutOfServiceId);
    }

    @Override
    public void updateCustomerAccountManager(final long customerId, final Long accountManagerId) {
        customerDao.updateCustomerAccountManager(customerId, accountManagerId);
    }

    @Override
    public boolean checkIfCustomerExists(final long customerId) {
        return customerDao.get(customerId).isPresent();
    }

    private CustomerDto mapCustomerFromEntity(final Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    private boolean doesCustomerExist(final Long extCompanyId) {
        return customerDao.findCustomerByExternalCompanyId(extCompanyId).isPresent();
    }

    private Customer getCustomer(final Long extCompanyId) {
        final Optional<Customer> optionalCustomer = customerDao.findCustomerByExternalCompanyId(extCompanyId);
        return optionalCustomer.orElseGet(() -> buildAidaCustomer(extCompanyId));
    }

    public Customer getCustomerWithAdditionalData(final Long extCompanyId) {
        final Customer customer = getCustomer(extCompanyId);
        fillCompanyAdditionalData(extCompanyId, customer.getCompany());
        return customer;
    }

    private void fillCompanyAdditionalData(final Long companyId, final Company company) {
        company.setInvolvement(calculateInvolvement(companyId));
        company.setSegment(determineSegment(companyId));
    }

    private BigDecimal calculateInvolvement(final Long extCompanyId) {
        return aidaService.getInvolvementService().calculateCompanyInvolvement(extCompanyId);
    }

    private Segment determineSegment(final Long extCompanyId) {
        return mapper.map(aidaService.getSegmentService().findCompanySegment(extCompanyId), Segment.class);
    }

    private Customer buildAidaCustomer(final Long extCompanyId) {
        final Company company = companyDao.findCompanyByExtId(extCompanyId).orElseGet(() -> buildCompany(extCompanyId));
        final Customer customer = new Customer();
        customer.setCompany(company);
        customerDao.create(customer);
        return customer;
    }

    private Company buildCompany(final Long extCompanyId) {
        final AidaCompanyDto aidaCompany = aidaService.getCompanyService().findCompanyByCompanyId(extCompanyId);
        final Set<Person> persons = mapper.mapAsSet(aidaService.getPersonService().findPersonByCompanyId(extCompanyId), Person.class);
        final Company company = getCompanyForCustomer(aidaCompany);
        company.setCompanyPersons(prepareCompanyPersons(persons, company));
        companyDao.create(company);
        return company;
    }

    private List<CompanyPerson> prepareCompanyPersons(final Set<Person> persons, final Company company) {
        return persons.stream().map((Person person) -> {
            CompanyPerson companyPerson = new CompanyPerson();
            companyPerson.setPerson(createOrUpdatePerson(company.getExtCompanyId(), person));
            companyPerson.setCompany(company);
            return companyPerson;
        }).collect(toList());
    }

    private Person createOrUpdatePerson(final Long extCompanyId, final Person person) {
        final Optional<Person> optionalPerson = personDao.findByExtPersonIdAndExtCompanyId(person.getPersonId(), extCompanyId);
        return optionalPerson.map(entity -> updatePerson(person, entity)).orElseGet(() -> createPerson(person));
    }

    private Person updatePerson(final Person person, final Person entity) {
        mapper.map(person, entity);
        personDao.update(entity);
        return entity;
    }

    private Person createPerson(final Person person) {
        personDao.create(person);
        return person;
    }

    private Company getCompanyForCustomer(final AidaCompanyDto aidaCompany) {
        if (aidaCompany == null) {
            return new Company();
        }

        final Company company = new Company();
        mapper.map(aidaCompany, company);
        company.setContactDetails(prepareContactDetails(aidaCompany));
        company.setAddresses(prepareAddresses(aidaCompany));
        return company;
    }
}
