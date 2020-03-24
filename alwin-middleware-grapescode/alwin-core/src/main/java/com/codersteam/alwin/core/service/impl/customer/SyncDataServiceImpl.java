package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.model.AidaPersonDto;
import com.codersteam.alwin.core.api.service.customer.SyncDataService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.customer.*;
import com.codersteam.alwin.jpa.type.AddressType;
import com.codersteam.alwin.jpa.type.ContactImportedType;
import com.codersteam.alwin.jpa.type.ContactState;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.codersteam.alwin.jpa.type.AddressType.CORRESPONDENCE;
import static com.codersteam.alwin.jpa.type.AddressType.RESIDENCE;
import static com.codersteam.alwin.jpa.type.ContactType.forImportedType;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Piotr Naroznik
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class SyncDataServiceImpl implements SyncDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CompanyDao companyDao;
    private final ConfigurableMapper mapper;
    private final PersonDao personDao;

    @Inject
    public SyncDataServiceImpl(final CompanyDao companyDao, final AlwinMapper mapper, final PersonDao personDao) {
        this.companyDao = companyDao;
        this.mapper = mapper;
        this.personDao = personDao;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void updateCompanyIfExist(final AidaCompanyDto aidaCompany, final List<AidaPersonDto> aidaPersons, final Long extCompanyId) {
        companyDao.findCompanyByExtId(extCompanyId)
                .ifPresent(company -> updateCompany(aidaCompany, aidaPersons, company));
    }


    private void updateCompany(final AidaCompanyDto aidaCompany, final List<AidaPersonDto> aidaPersons, final Company company) {
        if (aidaCompany != null) {
            updateCompany(company, aidaCompany);
            updateAddresses(company.getAddresses(), aidaCompany);
            updateContactDetails(company.getContactDetails(), aidaCompany);
        }
        if (isNotEmpty(aidaPersons)) {
            updatePersons(company.getCompanyPersons(), aidaPersons, company);
        }
        companyDao.update(company);
    }

    private void updateCompany(final Company company, final AidaCompanyDto aidaCompany) {
        mapper.map(aidaCompany, company);
    }

    private void updateAddresses(final Set<Address> addresses, final AidaCompanyDto aidaCompany) {
        addresses.stream().filter(Address::isImportedFromAida).forEach(address -> {
            switch (address.getImportedType()) {
                case RESIDENCE:
                    updateAddress(aidaCompany.getCity(), aidaCompany.getPostalCode(), aidaCompany.getStreet(), aidaCompany.getPrefix(), RESIDENCE, address);
                    break;

                case CORRESPONDENCE:
                    updateAddress(aidaCompany.getCorrespondenceCity(), aidaCompany.getCorrespondencePostalCode(), aidaCompany.getCorrespondenceStreet(),
                            aidaCompany.getCorrespondencePrefix(), CORRESPONDENCE, address);
                    break;

                default:
                    LOG.error("An address with id {} exists with not supported type {} for update", address.getId(), address.getAddressType().name());
            }
        });
    }

    private void updateAddress(final String city, final String postalCode, final String street, final String prefix, final AddressType addressType,
                               final Address address) {
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setStreetName(street);
        address.setStreetPrefix(prefix);
        address.setAddressType(addressType);
    }

    private void updateContactDetails(final Set<ContactDetail> contactDetails, final AidaCompanyDto aidaCompany) {
        final Map<ContactImportedType, ContactDetail> contactDetailsMap = contactDetails.stream().filter(ContactDetail::isImportedFromAida).collect
                (toMap(ContactDetail::getImportedType, identity()));

        stream(ContactImportedType.values())
                .forEach(contactImportedType ->
                        updateContactDetail(contactDetails, contactImportedType.getPropertyValue(aidaCompany), contactImportedType, contactDetailsMap));
    }

    private void updateContactDetail(final Set<ContactDetail> contactDetails, final String contact, final ContactImportedType type,
                                     final Map<ContactImportedType, ContactDetail> contactDetailsMap) {
        if (isNotBlank(contact)) {
            if (contactDetailsMap.containsKey(type)) {
                final ContactDetail contactDetail = contactDetailsMap.get(type);
                contactDetail.setContact(contact);
            } else {
                contactDetails.add(new ContactDetail(contact, forImportedType(type), ContactState.ACTIVE, null, true, type));
            }
        }
    }

    protected void updatePersons(final List<CompanyPerson> companyPersons, final List<AidaPersonDto> aidaPersons, final Company company) {
        final Map<Long, Person> companyPersonMap = companyPersons.stream()
                .collect(toMap(companyPerson -> companyPerson.getPerson().getPersonId(), CompanyPerson::getPerson, (companyPerson1, companyPerson2) -> companyPerson1));
        aidaPersons.forEach(aidaPerson -> {
            if (companyPersonMap.containsKey(aidaPerson.getPersonId())) {
                updatePerson(aidaPerson, companyPersonMap.get(aidaPerson.getPersonId()));
            } else {
                companyPersons.add(createCompanyPerson(company, aidaPerson));
            }
        });
    }

    private CompanyPerson createCompanyPerson(final Company company, final AidaPersonDto aidaPerson) {
        final CompanyPerson companyPerson = new CompanyPerson();
        companyPerson.setPerson(createOrUpdatePerson(company.getExtCompanyId(), aidaPerson));
        companyPerson.setCompany(company);
        return companyPerson;
    }

    private Person createOrUpdatePerson(final Long extCompanyId, final AidaPersonDto aidaPerson) {
        final Optional<Person> optionalPerson = personDao.findByExtPersonIdAndExtCompanyId(aidaPerson.getPersonId(), extCompanyId);
        return optionalPerson.map(person -> updatePerson(aidaPerson, person)).orElseGet(() -> createPerson(aidaPerson));
    }

    private Person createPerson(final AidaPersonDto aidaPerson) {
        final Person person = mapper.map(aidaPerson, Person.class);
        personDao.create(person);
        return person;
    }

    private Person updatePerson(final AidaPersonDto aidaPerson, final Person person) {
        mapper.map(aidaPerson, person);
        personDao.update(person);
        return person;
    }
}
