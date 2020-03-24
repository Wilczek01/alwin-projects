package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.customer.PhoneNumberDto;
import com.codersteam.alwin.jpa.type.ContactState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
public class SuggestedPhoneNumbersTestData {

    public static PhoneNumberDto suggestedPhoneNumber(final String label, final String phoneNumber, final String contactState) {
        return new PhoneNumberDto(label, phoneNumber, contactState);
    }

    public static List<PhoneNumberDto> suggestedPhoneNumbers1() {
        final List<PhoneNumberDto> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(suggestedPhoneNumber("Jan Kowalsky", "0048123456791", ContactState.PREFERRED.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "0048534978576", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "221297834", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "223247865", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "228901267", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Jan Kowalsky", "0048123456790", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Jan Kowalsky", "220987654", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Jan Kowalsky", "221234567", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Jan Kowalsky", "225678901", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Zuzana Fialová", "0048534978576", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Zuzana Fialová", "221297834", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Zuzana Fialová", "223247865", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Zuzana Fialová", "228901267", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Jan Kowalsky", "0048123456789", ContactState.INACTIVE.getLabel()));

        return phoneNumbers;
    }

    public static List<PhoneNumberDto> suggestedPhoneNumbers2() {
        final List<PhoneNumberDto> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(suggestedPhoneNumber("Firma", "0048123456791", ContactState.PREFERRED.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "0048123456790", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "220987654", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "221234567", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "225678901", ContactState.ACTIVE.getLabel()));
        phoneNumbers.add(suggestedPhoneNumber("Firma", "0048123456789", ContactState.INACTIVE.getLabel()));

        return phoneNumbers;
    }
}
