package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.customer.AddressDto;
import com.codersteam.alwin.core.api.service.customer.AddressService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.AddressDao;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class AddressServiceImpl implements AddressService {

    private final PersonDao personDao;
    private final AddressDao addressDao;
    private final CompanyDao companyDao;
    private final AlwinMapper alwinMapper;

    @Inject
    public AddressServiceImpl(final PersonDao personDao, final AlwinMapper alwinMapper, final AddressDao addressDao, final CompanyDao companyDao) {
        this.personDao = personDao;
        this.alwinMapper = alwinMapper;
        this.addressDao = addressDao;
        this.companyDao = companyDao;
    }

    @Override
    public List<AddressDto> findAllAddressesForCompany(final long companyId) {
        return companyDao.get(companyId).map(company -> alwinMapper.mapAsList(company.getAddresses(), AddressDto.class)).orElse(emptyList());
    }

    @Override
    @Transactional
    public void createNewAddressForCompany(final long companyId, final AddressDto address) {
        final Company company = companyDao.get(companyId).orElseThrow(() -> new EntityNotFoundException(companyId));
        company.getAddresses().add(alwinMapper.map(address, Address.class));
        companyDao.update(company);
    }

    @Override
    public List<AddressDto> findAllAddressesForPerson(final long personId) {
        return personDao.get(personId).map(person -> alwinMapper.mapAsList(person.getAddresses(), AddressDto.class)).orElse(emptyList());
    }

    @Override
    public void createNewAddressForPerson(final long personId, final AddressDto address) {
        final Person person = personDao.get(personId).orElseThrow(() -> new EntityNotFoundException(personId));
        person.getAddresses().add(alwinMapper.map(address, Address.class));
        personDao.update(person);
    }

    @Override
    @Transactional
    public void updateAddress(final AddressDto addressDto) {
        final Address address = addressDao.get(addressDto.getId()).orElseThrow(() -> new EntityNotFoundException(addressDto.getId()));
        alwinMapper.map(addressDto, address);
        addressDao.update(address);
    }

    @Override
    public AddressDto findAddress(long addressId) {
        final Address address = addressDao.get(addressId).orElseThrow(() -> new EntityNotFoundException(addressId));
        return alwinMapper.map(address, AddressDto.class);
    }
}
