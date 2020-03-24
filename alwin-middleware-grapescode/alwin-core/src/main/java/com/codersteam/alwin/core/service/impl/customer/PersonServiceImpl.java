package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.customer.PersonDto;
import com.codersteam.alwin.core.api.service.issue.PersonService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.CompanyPersonDao;
import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

import static java.lang.String.format;

@Stateless
public class PersonServiceImpl implements PersonService {

    private CompanyPersonDao companyPersonDao;
    private CompanyDao companyDao;
    private PersonDao personDao;
    private AlwinMapper mapper;

    public PersonServiceImpl() {
    }

    @Inject
    public PersonServiceImpl(final CompanyPersonDao companyPersonDao, final CompanyDao companyDao, final PersonDao personDao, final AlwinMapper mapper) {
        this.companyPersonDao = companyPersonDao;
        this.companyDao = companyDao;
        this.personDao = personDao;
        this.mapper = mapper;
    }

    @Override
    public void setContactPerson(final Long personId, final Long companyId, final boolean contactPerson) {
        final CompanyPerson companyPerson = companyPersonDao.findByPersonIdAndCompanyId(personId, companyId)
                .orElseThrow(() -> new EntityNotFoundException(entityId(personId, companyId)));
        companyPerson.setContactPerson(contactPerson);
        companyPersonDao.update(companyPerson);
    }

    @Override
    public void createPerson(final Long companyId, final PersonDto personDto) {
        createCompanyPerson(getCompany(companyId), createPerson(personDto), personDto.isContactPerson());
    }

    @Override
    public List<PersonDto> getCompanyPersonsByCompanyId(long companyId) {
        return companyDao.get(companyId)
                .map(Company::getCompanyPersons)
                .map(p -> mapper.mapAsList(p, PersonDto.class))
                .orElseThrow(() -> new EntityNotFoundException(companyId));
    }

    private void createCompanyPerson(final Company company, final Person person, final boolean contactPerson) {
        final CompanyPerson companyPerson = new CompanyPerson();
        companyPerson.setPerson(person);
        companyPerson.setCompany(company);
        companyPerson.setContactPerson(contactPerson);
        companyPersonDao.create(companyPerson);
    }

    private Person createPerson(final PersonDto personDto) {
        final Person person = mapper.map(personDto, Person.class);
        personDao.create(person);
        return person;
    }

    private Company getCompany(final Long companyId) {
        return companyDao.get(companyId).orElseThrow(() -> new EntityNotFoundException(companyId));
    }

    private String entityId(final Long personId, final Long companyId) {
        return format("CompanyPersonId[personId= %d, companyId= %d]", personId, companyId);
    }
}