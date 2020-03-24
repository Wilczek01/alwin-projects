package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.model.customer.ContactStateDto;
import com.codersteam.alwin.core.api.model.customer.PhoneNumberDto;
import com.codersteam.alwin.core.api.service.customer.PhoneNumberService;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.type.ContactType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam Stepnowski
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class PhoneNumberServiceImpl implements PhoneNumberService {

    public static final String COMPANY_LABEL = "Firma";
    private final CompanyDao companyDao;

    @Inject
    public PhoneNumberServiceImpl(final CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public List<PhoneNumberDto> findSuggestedPhoneNumbers(final long companyId) {
        final List<PhoneNumberDto> phoneNumbers = new ArrayList<>();
        final Optional<Company> company = companyDao.get(companyId);
        if (company.isPresent()) {
            fillCompanyPhoneNumbers(phoneNumbers, company.get());
            fillCompanyPersonPhoneNumbers(phoneNumbers, company.get());

            phoneNumbers.sort(Comparator.comparingInt(o -> ContactStateDto.suggestedOrder(o.getContactState())));
        }
        return phoneNumbers;
    }

    private void fillCompanyPersonPhoneNumbers(final List<PhoneNumberDto> phoneNumbers, final Company company) {
        final List<CompanyPerson> companyPersons = company.getCompanyPersons();
        for (final CompanyPerson companyPerson : companyPersons) {
            final List<ContactDetail> personContactDetails = companyPerson.getPerson().getContactDetails().stream()
                    .filter(contactDetail -> contactDetail.getContactType() == ContactType.PHONE_NUMBER)
                    .sorted(Comparator.comparing(ContactDetail::getContact))
                    .collect(Collectors.toList());

            for (final ContactDetail contactDetail : personContactDetails) {
                phoneNumbers.add(new PhoneNumberDto(String.format("%s %s", companyPerson.getPerson().getFirstName(), companyPerson.getPerson().getLastName()),
                        contactDetail.getContact(), contactDetail.getState().getLabel()));
            }
        }
    }

    private void fillCompanyPhoneNumbers(final List<PhoneNumberDto> phoneNumbers, final Company company) {
        final Set<ContactDetail> contactDetails = company.getContactDetails();
        final List<ContactDetail> companyContactDetails = contactDetails.stream()
                .filter(contactDetail -> contactDetail.getContactType() == ContactType.PHONE_NUMBER)
                .sorted(Comparator.comparing(ContactDetail::getContact))
                .collect(Collectors.toList());

        for (final ContactDetail contactDetail : companyContactDetails) {
            phoneNumbers.add(new PhoneNumberDto(COMPANY_LABEL, contactDetail.getContact(), contactDetail.getState().getLabel()));
        }
    }
}
