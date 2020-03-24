package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.customer.ContactDetailDto;
import com.codersteam.alwin.core.api.service.customer.ContactDetailService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.ContactDetailDao;
import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.ContactDetail;
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
public class ContactDetailServiceImpl implements ContactDetailService {

    private final PersonDao personDao;
    private final CompanyDao companyDao;
    private final AlwinMapper alwinMapper;
    private final ContactDetailDao contactDetailDao;

    @Inject
    public ContactDetailServiceImpl(final PersonDao personDao, final AlwinMapper alwinMapper, final ContactDetailDao contactDetailDao, final CompanyDao
            companyDao) {
        this.personDao = personDao;
        this.alwinMapper = alwinMapper;
        this.companyDao = companyDao;
        this.contactDetailDao = contactDetailDao;
    }

    @Override
    public List<ContactDetailDto> findAllContactDetailsForCompany(final long companyId) {
        return companyDao.get(companyId).map(company -> alwinMapper.mapAsList(company.getContactDetails(), ContactDetailDto.class)).orElse(emptyList());
    }

    @Override
    public void createNewContactDetailForCompany(final long companyId, final ContactDetailDto contactDetail) {
        final Company company = companyDao.get(companyId).orElseThrow(() -> new EntityNotFoundException(companyId));
        company.getContactDetails().add(alwinMapper.map(contactDetail, ContactDetail.class));
        companyDao.update(company);
    }

    @Override
    public List<ContactDetailDto> findAllContactDetailsForPerson(final long personId) {
        return personDao.get(personId).map(person -> alwinMapper.mapAsList(person.getContactDetails(), ContactDetailDto.class)).orElse(emptyList());
    }

    @Override
    public void createNewContactDetailForPerson(final long personId, final ContactDetailDto contactDetail) {
        final Person person = personDao.get(personId).orElseThrow(() -> new EntityNotFoundException(personId));
        person.getContactDetails().add(alwinMapper.map(contactDetail, ContactDetail.class));
        personDao.update(person);
    }

    @Override
    @Transactional
    public void updateContactDetail(final ContactDetailDto contactDto) {
        final ContactDetail contact = contactDetailDao.get(contactDto.getId()).orElseThrow(() -> new EntityNotFoundException(contactDto.getId()));
        alwinMapper.map(contactDto, contact);
        contactDetailDao.update(contact);
    }
}
