package com.codersteam.alwin.testdata;


import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.customer.CompanyDto;
import com.codersteam.alwin.core.api.model.customer.PersonDto;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.testdata.aida.AidaPersonTestData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.AddressTestData.address1;
import static com.codersteam.alwin.testdata.AddressTestData.address2;
import static com.codersteam.alwin.testdata.AddressTestData.address3;
import static com.codersteam.alwin.testdata.AddressTestData.addressToCreate;
import static com.codersteam.alwin.testdata.CompanyPersonTestData.company2Persons;
import static com.codersteam.alwin.testdata.CompanyPersonTestData.companyPerson1;
import static com.codersteam.alwin.testdata.CompanyPersonTestData.fillCompany;
import static com.codersteam.alwin.testdata.ContactDetailTestData.*;
import static com.codersteam.alwin.testdata.PersonTestData.testPersonDtos;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public class CompanyTestData {

    public static final Long NON_EXISTING_COMPANY_ID = -1L;

    public static final Long COMPANY_ID_1 = 1L;
    public static final Long EXT_COMPANY_ID_1 = 9999L;
    public static final String COMPANY_NAME_1 = "Nazwa Firmmy Sp. z o.o.";
    private static final String SHORT_NAME_1 = "Nazwa Firmmy";
    private static final String NIP_1 = "123456789";
    private static final String REGON_1 = "123456";
    private static final String KRS_1 = "98765432234";
    private static final String RECIPIENT_NAME_1 = "Mikołaj Rej";
    private static final String RATING_1 = "Q01";
    private static final Date RATING_DATE_1 = parse("2017-09-01 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_1 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_1 = parse("2017-07-10 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_1 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_1 = emptySet();
    public static final Segment SEGMENT_1 = Segment.A;
    public static final BigDecimal INVOLVEMENT_1 = new BigDecimal("12345.67");
    public static final String COMPANY_PKD_CODE_1 = "20.12.Z";
    public static final String COMPANY_PKD_NAME_1 = "Produkcja barwników i pigmentów";

    public static final Long COMPANY_ID_2 = 2L;
    public static final Long EXT_COMPANY_ID_2 = 9998L;
    public static final String COMPANY_NAME_2 = "Najlepsza Firma Sp. z o.o.";
    private static final String SHORT_NAME_2 = "Najlepsza Firma";
    private static final String NIP_2 = "8866123123";
    private static final String REGON_2 = "989867612";
    private static final String KRS_2 = "876543232324";
    private static final String RECIPIENT_NAME_2 = "Krzysztof Kolumb";
    private static final String RATING_2 = "W02";
    private static final Date RATING_DATE_2 = parse("2016-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_2 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_2 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_2 = company2Persons();
    private static final Set<PersonDto> PERSONS_DTO_2 = testPersonDtos();
    private static final Set<Address> ADDRESSES_2 = new HashSet<>(singletonList(address3()));
    private static final Set<ContactDetail> CONTACT_DETAILS_2 = new HashSet<>(asList(contactDetail21(), contactDetail22(), contactDetail23(),
            contactDetail24(), contactDetail25(), contactDetail26(), contactDetail27(), contactDetail28(), contactDetail29()));
    private static final Segment SEGMENT_2 = Segment.B;
    public static final BigDecimal INVOLVEMENT_2 = new BigDecimal("23456.78");
    public static final String COMPANY_PKD_CODE_2 = "01.11.Z";
    public static final String COMPANY_PKD_NAME_2 = "Uprawa zbóż, roślin strączkowych i roślin oleistych na nasiona, z wyłączeniem ryżu";

    public static final Long COMPANY_ID_3 = 3L;
    public static final Long EXT_COMPANY_ID_3 = 9997L;
    public static final String COMPANY_NAME_3 = "Druga Najlepsza Firma Sp. z o.o.";
    private static final String SHORT_NAME_3 = "Druga Najlepsza Firma";
    private static final String NIP_3 = "7488099035";
    private static final String REGON_3 = "270261889";
    private static final String KRS_3 = "098789876342";
    private static final String RECIPIENT_NAME_3 = "John Smith";
    private static final String RATING_3 = "W02";
    private static final Date RATING_DATE_3 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_3 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_3 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_3 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_3 = emptySet();
    private static final Segment SEGMENT_3 = Segment.A;
    public static final BigDecimal INVOLVEMENT_3 = new BigDecimal("34567.89");
    public static final String COMPANY_PKD_CODE_3 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_3 = "Uprawa ryżu";

    public static final Long COMPANY_ID_4 = 4L;
    public static final Long EXT_COMPANY_ID_4 = 9996L;
    public static final String COMPANY_NAME_4 = "Trzecia Najlepsza Firma Sp. z o.o.";
    private static final String SHORT_NAME_4 = "Trzecia Najlepsza Firma";
    private static final String NIP_4 = "3567636850";
    private static final String REGON_4 = "050309810";
    private static final String KRS_4 = "17328636";
    private static final String RECIPIENT_NAME_4 = "Janko Walski";
    private static final String RATING_4 = "W02";
    private static final Date RATING_DATE_4 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_4 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_4 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_4 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_4 = emptySet();
    private static final Segment SEGMENT_4 = Segment.A;
    public static final BigDecimal INVOLVEMENT_4 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_4 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_4 = "Tarcie parmezanu";

    public static final Long COMPANY_ID_5 = 5L;
    public static final Long EXT_COMPANY_ID_5 = 9995L;
    public static final String COMPANY_NAME_5 = "Firma 9995";
    private static final String SHORT_NAME_5 = "Firma9995";
    private static final String NIP_5 = "3819667897";
    private static final String REGON_5 = "935884179";
    private static final String KRS_5 = "KRS 9995";
    private static final String RECIPIENT_NAME_5 = "Odbiorca firma 9995";
    private static final String RATING_5 = "W02";
    private static final Date RATING_DATE_5 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_5 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_5 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_5 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_5 = emptySet();
    private static final Segment SEGMENT_5 = Segment.A;
    public static final BigDecimal INVOLVEMENT_5 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_5 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_5 = "Uprawa ryżu";

    public static final Long COMPANY_ID_6 = 6L;
    public static final Long EXT_COMPANY_ID_6 = 9994L;
    public static final String COMPANY_NAME_6 = "Firma 9994";
    private static final String SHORT_NAME_6 = "Firma9994";
    private static final String NIP_6 = "6780323778";
    private static final String REGON_6 = "756152902";
    private static final String KRS_6 = "KRS 9994";
    private static final String RECIPIENT_NAME_6 = "Odbiorca firma 9994";
    private static final String RATING_6 = "W02";
    private static final Date RATING_DATE_6 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_6 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_6 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_6 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_6 = emptySet();
    private static final Segment SEGMENT_6 = Segment.A;
    public static final BigDecimal INVOLVEMENT_6 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_6 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_6 = "Uprawa ryżu";

    public static final Long COMPANY_ID_7 = 7L;
    public static final Long EXT_COMPANY_ID_7 = 9993L;
    public static final String COMPANY_NAME_7 = "Firma 9993";
    private static final String SHORT_NAME_7 = "Firma9993";
    private static final String NIP_7 = "2619474934";
    private static final String REGON_7 = "150939459";
    private static final String KRS_7 = "KRS 9993";
    private static final String RECIPIENT_NAME_7 = "Odbiorca firma 9993";
    private static final String RATING_7 = "W02";
    private static final Date RATING_DATE_7 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_7 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_7 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_7 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_7 = emptySet();
    private static final Segment SEGMENT_7 = Segment.A;
    public static final BigDecimal INVOLVEMENT_7 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_7 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_7 = "Uprawa ryżu";

    public static final Long COMPANY_ID_8 = 8L;
    public static final Long EXT_COMPANY_ID_8 = 9992L;
    public static final String COMPANY_NAME_8 = "Firma 9992";
    private static final String SHORT_NAME_8 = "Firma9992";
    private static final String NIP_8 = "9393539165";
    private static final String REGON_8 = "735583142";
    private static final String KRS_8 = "KRS 9992";
    private static final String RECIPIENT_NAME_8 = "Odbiorca firma 9992";
    private static final String RATING_8 = "W02";
    private static final Date RATING_DATE_8 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_8 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_8 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_8 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_8 = emptySet();
    private static final Segment SEGMENT_8 = Segment.A;
    public static final BigDecimal INVOLVEMENT_8 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_8 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_8 = "Uprawa ryżu";

    public static final Long COMPANY_ID_9 = 9L;
    public static final Long EXT_COMPANY_ID_9 = 9991L;
    public static final String COMPANY_NAME_9 = "Firma 9991";
    private static final String SHORT_NAME_9 = "Firma9991";
    public static final String NIP_9 = "6837458881";
    public static final String REGON_9 = "615557755";
    private static final String KRS_9 = "KRS 9991";
    private static final String RECIPIENT_NAME_9 = "Odbiorca firma 9991";
    private static final String RATING_9 = "W02";
    private static final Date RATING_DATE_9 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_9 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_9 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_9 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_9 = emptySet();
    private static final Segment SEGMENT_9 = Segment.A;
    public static final BigDecimal INVOLVEMENT_9 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_9 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_9 = "Uprawa ryżu";

    public static final Long COMPANY_ID_10 = 10L;
    public static final Long EXT_COMPANY_ID_10 = 9990L;
    public static final String COMPANY_NAME_10 = "Firma 9990";
    private static final String SHORT_NAME_10 = "Firma9990";
    private static final String NIP_10 = "7341282285";
    private static final String REGON_10 = "893713518";
    private static final String KRS_10 = "KRS 9990";
    private static final String RECIPIENT_NAME_10 = "Odbiorca firma 9990";
    private static final String RATING_10 = "W02";
    private static final Date RATING_DATE_10 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_10 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_10 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_10 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_10 = emptySet();
    private static final Segment SEGMENT_10 = Segment.A;
    public static final BigDecimal INVOLVEMENT_10 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_10 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_10 = "Uprawa ryżu";

    public static final Long COMPANY_ID_11 = 11L;
    public static final Long EXT_COMPANY_ID_11 = 9989L;
    public static final String COMPANY_NAME_11 = "Firma 9989";
    private static final String SHORT_NAME_11 = "Firma9989";
    private static final String NIP_11 = "1654307226";
    private static final String REGON_11 = "035394345";
    private static final String KRS_11 = "KRS 9989";
    private static final String RECIPIENT_NAME_11 = "Odbiorca firma 9989";
    private static final String RATING_11 = "W02";
    private static final Date RATING_DATE_11 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_11 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_11 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_11 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_11 = emptySet();
    private static final Segment SEGMENT_11 = Segment.A;
    public static final BigDecimal INVOLVEMENT_11 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_11 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_11 = "Uprawa ryżu";

    public static final Long COMPANY_ID_12 = 12L;
    public static final Long EXT_COMPANY_ID_12 = 9988L;
    public static final String COMPANY_NAME_12 = "Firma 9988";
    private static final String SHORT_NAME_12 = "Firma9988";
    private static final String NIP_12 = "5332616395";
    private static final String REGON_12 = "791671434";
    private static final String KRS_12 = "KRS 9988";
    private static final String RECIPIENT_NAME_12 = "Odbiorca firma 9988";
    private static final String RATING_12 = "W02";
    private static final Date RATING_DATE_12 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_12 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_12 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_12 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_12 = emptySet();
    private static final Segment SEGMENT_12 = Segment.A;
    public static final BigDecimal INVOLVEMENT_12 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_12 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_12 = "Uprawa ryżu";

    public static final Long COMPANY_ID_13 = 13L;
    public static final Long EXT_COMPANY_ID_13 = 9987L;
    public static final String COMPANY_NAME_13 = "Firma 9987";
    private static final String SHORT_NAME_13 = "Firma9987";
    private static final String NIP_13 = "3647943212";
    private static final String REGON_13 = "914725610";
    private static final String KRS_13 = "KRS 9987";
    private static final String RECIPIENT_NAME_13 = "Odbiorca firma 9987";
    private static final String RATING_13 = "W02";
    private static final Date RATING_DATE_13 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_13 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_13 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_13 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_13 = emptySet();
    private static final Segment SEGMENT_13 = Segment.A;
    public static final BigDecimal INVOLVEMENT_13 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_13 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_13 = "Uprawa ryżu";

    public static final Long COMPANY_ID_14 = 14L;
    public static final Long EXT_COMPANY_ID_14 = 9986L;
    public static final String COMPANY_NAME_14 = "Firma 9986";
    private static final String SHORT_NAME_14 = "Firma9986";
    private static final String NIP_14 = "9882335491";
    private static final String REGON_14 = "491081759";
    private static final String KRS_14 = "KRS 9986";
    private static final String RECIPIENT_NAME_14 = "Odbiorca firma 9986";
    private static final String RATING_14 = "W02";
    private static final Date RATING_DATE_14 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_14 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_14 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_14 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_14 = emptySet();
    private static final Segment SEGMENT_14 = Segment.A;
    public static final BigDecimal INVOLVEMENT_14 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_14 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_14 = "Uprawa ryżu";

    public static final Long COMPANY_ID_15 = 15L;
    public static final Long EXT_COMPANY_ID_15 = 9985L;
    public static final String COMPANY_NAME_15 = "Firma 9985";
    private static final String SHORT_NAME_15 = "Firma9985";
    private static final String NIP_15 = "9981051644";
    private static final String REGON_15 = "150182720";
    private static final String KRS_15 = "KRS 9985";
    private static final String RECIPIENT_NAME_15 = "Odbiorca firma 9985";
    private static final String RATING_15 = "W02";
    private static final Date RATING_DATE_15 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_15 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_15 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_15 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_15 = emptySet();
    private static final Segment SEGMENT_15 = Segment.A;
    public static final BigDecimal INVOLVEMENT_15 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_15 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_15 = "Uprawa ryżu";

    public static final Long COMPANY_ID_16 = 16L;
    public static final Long EXT_COMPANY_ID_16 = 9984L;
    public static final String COMPANY_NAME_16 = "Firma 9984";
    private static final String SHORT_NAME_16 = "Firma9984";
    private static final String NIP_16 = "4669628820";
    private static final String REGON_16 = "071429334";
    private static final String KRS_16 = "KRS 9984";
    private static final String RECIPIENT_NAME_16 = "Odbiorca firma 9984";
    private static final String RATING_16 = "W02";
    private static final Date RATING_DATE_16 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_16 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_16 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_16 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_16 = emptySet();
    private static final Segment SEGMENT_16 = Segment.A;
    public static final BigDecimal INVOLVEMENT_16 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_16 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_16 = "Uprawa ryżu";

    public static final Long COMPANY_ID_17 = 17L;
    public static final Long EXT_COMPANY_ID_17 = 9983L;
    public static final String COMPANY_NAME_17 = "Firma 9983";
    private static final String SHORT_NAME_17 = "Firma9983";
    private static final String NIP_17 = "5129997804";
    private static final String REGON_17 = "517228042";
    private static final String KRS_17 = "KRS 9983";
    private static final String RECIPIENT_NAME_17 = "Odbiorca firma 9983";
    private static final String RATING_17 = "W02";
    private static final Date RATING_DATE_17 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_17 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_17 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_17 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_17 = emptySet();
    private static final Segment SEGMENT_17 = Segment.A;
    public static final BigDecimal INVOLVEMENT_17 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_17 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_17 = "Uprawa ryżu";

    public static final Long COMPANY_ID_18 = 18L;
    public static final Long EXT_COMPANY_ID_18 = 9982L;
    public static final String COMPANY_NAME_18 = "Firma 9982";
    private static final String SHORT_NAME_18 = "Firma9982";
    private static final String NIP_18 = "9740010632";
    private static final String REGON_18 = "738848530";
    private static final String KRS_18 = "KRS 9982";
    private static final String RECIPIENT_NAME_18 = "Odbiorca firma 9982";
    private static final String RATING_18 = "W02";
    private static final Date RATING_DATE_18 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_18 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_18 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_18 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_18 = emptySet();
    private static final Segment SEGMENT_18 = Segment.A;
    public static final BigDecimal INVOLVEMENT_18 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_18 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_18 = "Uprawa ryżu";

    public static final Long COMPANY_ID_19 = 19L;
    public static final Long EXT_COMPANY_ID_19 = 9981L;
    public static final String COMPANY_NAME_19 = "Firma 9981";
    private static final String SHORT_NAME_19 = "Firma9981";
    private static final String NIP_19 = "6426764493";
    private static final String REGON_19 = "251933591";
    private static final String KRS_19 = "KRS 9981";
    private static final String RECIPIENT_NAME_19 = "Odbiorca firma 9981";
    private static final String RATING_19 = "W02";
    private static final Date RATING_DATE_19 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_19 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_19 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_19 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_19 = emptySet();
    private static final Segment SEGMENT_19 = Segment.A;
    public static final BigDecimal INVOLVEMENT_19 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_19 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_19 = "Uprawa ryżu";

    public static final Long COMPANY_ID_20 = 20L;
    public static final Long EXT_COMPANY_ID_20 = 9980L;
    public static final String COMPANY_NAME_20 = "Firma 9980";
    private static final String SHORT_NAME_20 = "Firma9980";
    private static final String NIP_20 = "7131051243";
    private static final String REGON_20 = "738802060";
    private static final String KRS_20 = "KRS 9980";
    private static final String RECIPIENT_NAME_20 = "Odbiorca firma 9980";
    private static final String RATING_20 = "W02";
    private static final Date RATING_DATE_20 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_20 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_20 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_20 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_20 = emptySet();
    private static final Segment SEGMENT_20 = Segment.A;
    public static final BigDecimal INVOLVEMENT_20 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_20 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_20 = "Uprawa ryżu";

    public static final Long COMPANY_ID_21 = 21L;
    public static final Long EXT_COMPANY_ID_21 = 9979L;
    public static final String COMPANY_NAME_21 = "Firma 9979";
    private static final String SHORT_NAME_21 = "Firma9979";
    private static final String NIP_21 = "8538966033";
    private static final String REGON_21 = "352494251";
    private static final String KRS_21 = "KRS 9979";
    private static final String RECIPIENT_NAME_21 = "Odbiorca firma 9979";
    private static final String RATING_21 = "W02";
    private static final Date RATING_DATE_21 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_21 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_21 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_21 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_21 = emptySet();
    private static final Segment SEGMENT_21 = Segment.A;
    public static final BigDecimal INVOLVEMENT_21 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_21 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_21 = "Uprawa ryżu";

    public static final Long COMPANY_ID_22 = 22L;
    public static final Long EXT_COMPANY_ID_22 = 9978L;
    public static final String COMPANY_NAME_22 = "Firma 9978";
    private static final String SHORT_NAME_22 = "Firma9978";
    private static final String NIP_22 = "3541983180";
    private static final String REGON_22 = "457545719";
    private static final String KRS_22 = "KRS 9978";
    private static final String RECIPIENT_NAME_22 = "Odbiorca firma 9978";
    private static final String RATING_22 = "W02";
    private static final Date RATING_DATE_22 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_22 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_22 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_22 = emptyList();
    private static final Set<PersonDto> PERSONS_DTO_22 = emptySet();
    private static final Segment SEGMENT_22 = Segment.A;
    public static final BigDecimal INVOLVEMENT_22 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_22 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_22 = "Uprawa ryżu";


    public static final String COMPANY_NAME_24 = "Firma 9976";
    private static final String SHORT_NAME_24 = "Firma9976";
    private static final String NIP_24 = "3541983180";
    private static final String REGON_24 = "457545719";
    private static final String KRS_24 = "KRS 9976";
    private static final String RECIPIENT_NAME_24 = "Odbiorca firma 9976";
    private static final String RATING_24 = "W02";
    private static final Date RATING_DATE_24 = parse("2017-10-25 00:00:00.000000");
    private static final Boolean EXTERNAL_DB_AGREEMENT_24 = true;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_24 = parse("2016-03-13 00:00:00.000000");
    private static final List<CompanyPerson> COMPANY_PERSONS_24 = emptyList();
    private static final Segment SEGMENT_24 = Segment.B;
    public static final BigDecimal INVOLVEMENT_24 = new BigDecimal("0.00");
    public static final String COMPANY_PKD_CODE_24 = "01.12.Z";
    public static final String COMPANY_PKD_NAME_24 = "Uprawa ryżu";

    public static final Long COMPANY_ID_23 = 23L;
    public static final Long EXT_COMPANY_ID_23 = 9977L;
    public static final Long COMPANY_ID_24 = 24L;
    public static final Long EXT_COMPANY_ID_24 = 9976L;
    public static final Long EXT_COMPANY_ID_25 = 9975L;
    public static final Long COMPANY_ID_26 = 26L;
    public static final Long EXT_COMPANY_ID_26 = 9974L;
    public static final Long EXT_COMPANY_ID_27 = 9973L;

    public static List<Long> issueFilterExtCompanyIds() {
        return asList(
                // 9998, bez zleceń
                EXT_COMPANY_ID_2,
                // 9997, 20 (PHONE_DEBT_COLLECTION_1, cancelled), 25 (PHONE_DEBT_COLLECTION_1, cancelled), przez customer i contract
                EXT_COMPANY_ID_3,
                // 9992, 5 (PHONE_DEBT_COLLECTION_1, new) przez customer i contract
                EXT_COMPANY_ID_8,
                // 9988, 9 (PHONE_DEBT_COLLECTION_1, new) przez customer
                EXT_COMPANY_ID_12,
                // 9982, 15 (PHONE_DEBT_COLLECTION_1, new) przez contract
                EXT_COMPANY_ID_18,
                // 9994, 3 (PHONE_DEBT_COLLECTION_2, in_progress) przez customer
                EXT_COMPANY_ID_6
        );
    }

    public static Company company1() {
        return company(COMPANY_ID_1, EXT_COMPANY_ID_1, COMPANY_NAME_1, SHORT_NAME_1, NIP_1, REGON_1, KRS_1, RECIPIENT_NAME_1, RATING_1,
                RATING_DATE_1, EXTERNAL_DB_AGREEMENT_1, EXTERNAL_DB_AGREEMENT_DATE_1, COMPANY_PERSONS_1, company1Addresses(),
                company1ContactDetails(), SEGMENT_1, INVOLVEMENT_1, COMPANY_PKD_CODE_1, COMPANY_PKD_NAME_1);
    }

    private static HashSet<ContactDetail> company1ContactDetails() {
        return new HashSet<>(asList(contactDetail1(), contactDetail2(), contactDetail3(), contactDetail4(), contactDetail5(), contactDetail6(),
                contactDetail7(), contactDetail8(), contactDetail9(), contactDetail10(), contactDetail11(), contactDetail12(), contactDetail13()));
    }

    private static HashSet<Address> company1Addresses() {
        return new HashSet<>(asList(address1(), address2()));
    }

    public static CompanyDto companyDto1() {
        return companyDto(COMPANY_ID_1, EXT_COMPANY_ID_1, COMPANY_NAME_1, SHORT_NAME_1, NIP_1, REGON_1, KRS_1, RECIPIENT_NAME_1, RATING_1,
                RATING_DATE_1, EXTERNAL_DB_AGREEMENT_1, EXTERNAL_DB_AGREEMENT_DATE_1, PERSONS_DTO_1,
                COMPANY_PKD_CODE_1, COMPANY_PKD_NAME_1);
    }

    public static Company company2() {
        return company(COMPANY_ID_2, EXT_COMPANY_ID_2, COMPANY_NAME_2, SHORT_NAME_2, NIP_2, REGON_2, KRS_2, RECIPIENT_NAME_2, RATING_2,
                RATING_DATE_2, EXTERNAL_DB_AGREEMENT_2, EXTERNAL_DB_AGREEMENT_DATE_2, COMPANY_PERSONS_2, ADDRESSES_2, CONTACT_DETAILS_2, SEGMENT_2,
                INVOLVEMENT_2, COMPANY_PKD_CODE_2, COMPANY_PKD_NAME_2);
    }

    public static CompanyDto ompanyDto2() {
        return companyDto(COMPANY_ID_2, EXT_COMPANY_ID_2, COMPANY_NAME_2, SHORT_NAME_2, NIP_2, REGON_2, KRS_2, RECIPIENT_NAME_2, RATING_2,
                RATING_DATE_2, EXTERNAL_DB_AGREEMENT_2, EXTERNAL_DB_AGREEMENT_DATE_2, PERSONS_DTO_2,
                COMPANY_PKD_CODE_2, COMPANY_PKD_NAME_2);
    }

    public static Company company3() {
        return company(COMPANY_ID_3, EXT_COMPANY_ID_3, COMPANY_NAME_3, SHORT_NAME_3, NIP_3, REGON_3, KRS_3, RECIPIENT_NAME_3, RATING_3,
                RATING_DATE_3, EXTERNAL_DB_AGREEMENT_3, EXTERNAL_DB_AGREEMENT_DATE_3, COMPANY_PERSONS_3, new HashSet<>(),
                new HashSet<>(), SEGMENT_3, INVOLVEMENT_3, COMPANY_PKD_CODE_3, COMPANY_PKD_NAME_3);
    }

    public static Company company4() {
        return company(COMPANY_ID_4, EXT_COMPANY_ID_4, COMPANY_NAME_4, SHORT_NAME_4, NIP_4, REGON_4, KRS_4, RECIPIENT_NAME_4, RATING_4,
                RATING_DATE_4, EXTERNAL_DB_AGREEMENT_4, EXTERNAL_DB_AGREEMENT_DATE_4, COMPANY_PERSONS_4, new HashSet<>(),
                new HashSet<>(), SEGMENT_4, INVOLVEMENT_4, COMPANY_PKD_CODE_4, COMPANY_PKD_NAME_4);
    }

    public static Company company5() {
        return company(COMPANY_ID_5, EXT_COMPANY_ID_5, COMPANY_NAME_5, SHORT_NAME_5, NIP_5, REGON_5, KRS_5, RECIPIENT_NAME_5, RATING_5,
                RATING_DATE_5, EXTERNAL_DB_AGREEMENT_5, EXTERNAL_DB_AGREEMENT_DATE_5, COMPANY_PERSONS_5, new HashSet<>(),
                new HashSet<>(), SEGMENT_5, INVOLVEMENT_5, COMPANY_PKD_CODE_5, COMPANY_PKD_NAME_5);
    }

    public static Company company6() {
        return company(COMPANY_ID_6, EXT_COMPANY_ID_6, COMPANY_NAME_6, SHORT_NAME_6, NIP_6, REGON_6, KRS_6, RECIPIENT_NAME_6, RATING_6,
                RATING_DATE_6, EXTERNAL_DB_AGREEMENT_6, EXTERNAL_DB_AGREEMENT_DATE_6, COMPANY_PERSONS_6, new HashSet<>(),
                new HashSet<>(), SEGMENT_6, INVOLVEMENT_6, COMPANY_PKD_CODE_6, COMPANY_PKD_NAME_6);
    }

    public static Company company7() {
        return company(COMPANY_ID_7, EXT_COMPANY_ID_7, COMPANY_NAME_7, SHORT_NAME_7, NIP_7, REGON_7, KRS_7, RECIPIENT_NAME_7, RATING_7,
                RATING_DATE_7, EXTERNAL_DB_AGREEMENT_7, EXTERNAL_DB_AGREEMENT_DATE_7, COMPANY_PERSONS_7, new HashSet<>(),
                new HashSet<>(), SEGMENT_7, INVOLVEMENT_7, COMPANY_PKD_CODE_7, COMPANY_PKD_NAME_7);
    }

    public static Company company8() {
        return company(COMPANY_ID_8, EXT_COMPANY_ID_8, COMPANY_NAME_8, SHORT_NAME_8, NIP_8, REGON_8, KRS_8, RECIPIENT_NAME_8, RATING_8,
                RATING_DATE_8, EXTERNAL_DB_AGREEMENT_8, EXTERNAL_DB_AGREEMENT_DATE_8, COMPANY_PERSONS_8, new HashSet<>(),
                new HashSet<>(), SEGMENT_8, INVOLVEMENT_8, COMPANY_PKD_CODE_8, COMPANY_PKD_NAME_8);
    }

    public static Company company9() {
        return company(COMPANY_ID_9, EXT_COMPANY_ID_9, COMPANY_NAME_9, SHORT_NAME_9, NIP_9, REGON_9, KRS_9, RECIPIENT_NAME_9, RATING_9,
                RATING_DATE_9, EXTERNAL_DB_AGREEMENT_9, EXTERNAL_DB_AGREEMENT_DATE_9, COMPANY_PERSONS_9, new HashSet<>(),
                new HashSet<>(), SEGMENT_9, INVOLVEMENT_9, COMPANY_PKD_CODE_9, COMPANY_PKD_NAME_9);
    }

    public static Company company10() {
        return company(COMPANY_ID_10, EXT_COMPANY_ID_10, COMPANY_NAME_10, SHORT_NAME_10, NIP_10, REGON_10, KRS_10, RECIPIENT_NAME_10, RATING_10,
                RATING_DATE_10, EXTERNAL_DB_AGREEMENT_10, EXTERNAL_DB_AGREEMENT_DATE_10, COMPANY_PERSONS_10, new HashSet<>(),
                new HashSet<>(), SEGMENT_10, INVOLVEMENT_10, COMPANY_PKD_CODE_10, COMPANY_PKD_NAME_10);
    }

    public static Company company11() {
        return company(COMPANY_ID_11, EXT_COMPANY_ID_11, COMPANY_NAME_11, SHORT_NAME_11, NIP_11, REGON_11, KRS_11, RECIPIENT_NAME_11, RATING_11,
                RATING_DATE_11, EXTERNAL_DB_AGREEMENT_11, EXTERNAL_DB_AGREEMENT_DATE_11, COMPANY_PERSONS_11, new HashSet<>(),
                new HashSet<>(), SEGMENT_11, INVOLVEMENT_11, COMPANY_PKD_CODE_11, COMPANY_PKD_NAME_11);
    }

    public static Company company12() {
        return company(COMPANY_ID_12, EXT_COMPANY_ID_12, COMPANY_NAME_12, SHORT_NAME_12, NIP_12, REGON_12, KRS_12, RECIPIENT_NAME_12, RATING_12,
                RATING_DATE_12, EXTERNAL_DB_AGREEMENT_12, EXTERNAL_DB_AGREEMENT_DATE_12, COMPANY_PERSONS_12, new HashSet<>(),
                new HashSet<>(), SEGMENT_12, INVOLVEMENT_12, COMPANY_PKD_CODE_12, COMPANY_PKD_NAME_12);
    }

    public static Company company13() {
        return company(COMPANY_ID_13, EXT_COMPANY_ID_13, COMPANY_NAME_13, SHORT_NAME_13, NIP_13, REGON_13, KRS_13, RECIPIENT_NAME_13, RATING_13,
                RATING_DATE_13, EXTERNAL_DB_AGREEMENT_13, EXTERNAL_DB_AGREEMENT_DATE_13, COMPANY_PERSONS_13, new HashSet<>(),
                new HashSet<>(), SEGMENT_13, INVOLVEMENT_13, COMPANY_PKD_CODE_13, COMPANY_PKD_NAME_13);
    }

    public static Company company14() {
        return company(COMPANY_ID_14, EXT_COMPANY_ID_14, COMPANY_NAME_14, SHORT_NAME_14, NIP_14, REGON_14, KRS_14, RECIPIENT_NAME_14, RATING_14,
                RATING_DATE_14, EXTERNAL_DB_AGREEMENT_14, EXTERNAL_DB_AGREEMENT_DATE_14, COMPANY_PERSONS_14, new HashSet<>(),
                new HashSet<>(), SEGMENT_14, INVOLVEMENT_14, COMPANY_PKD_CODE_14, COMPANY_PKD_NAME_14);
    }

    public static Company company15() {
        return company(COMPANY_ID_15, EXT_COMPANY_ID_15, COMPANY_NAME_15, SHORT_NAME_15, NIP_15, REGON_15, KRS_15, RECIPIENT_NAME_15, RATING_15,
                RATING_DATE_15, EXTERNAL_DB_AGREEMENT_15, EXTERNAL_DB_AGREEMENT_DATE_15, COMPANY_PERSONS_15, new HashSet<>(),
                new HashSet<>(), SEGMENT_15, INVOLVEMENT_15, COMPANY_PKD_CODE_15, COMPANY_PKD_NAME_15);
    }

    public static Company company16() {
        return company(COMPANY_ID_16, EXT_COMPANY_ID_16, COMPANY_NAME_16, SHORT_NAME_16, NIP_16, REGON_16, KRS_16, RECIPIENT_NAME_16, RATING_16,
                RATING_DATE_16, EXTERNAL_DB_AGREEMENT_16, EXTERNAL_DB_AGREEMENT_DATE_16, COMPANY_PERSONS_16, new HashSet<>(),
                new HashSet<>(), SEGMENT_16, INVOLVEMENT_16, COMPANY_PKD_CODE_16, COMPANY_PKD_NAME_16);
    }

    public static Company company17() {
        return company(COMPANY_ID_17, EXT_COMPANY_ID_17, COMPANY_NAME_17, SHORT_NAME_17, NIP_17, REGON_17, KRS_17, RECIPIENT_NAME_17, RATING_17,
                RATING_DATE_17, EXTERNAL_DB_AGREEMENT_17, EXTERNAL_DB_AGREEMENT_DATE_17, COMPANY_PERSONS_17, new HashSet<>(),
                new HashSet<>(), SEGMENT_17, INVOLVEMENT_17, COMPANY_PKD_CODE_17, COMPANY_PKD_NAME_17);
    }

    public static Company company18() {
        return company(COMPANY_ID_18, EXT_COMPANY_ID_18, COMPANY_NAME_18, SHORT_NAME_18, NIP_18, REGON_18, KRS_18, RECIPIENT_NAME_18, RATING_18,
                RATING_DATE_18, EXTERNAL_DB_AGREEMENT_18, EXTERNAL_DB_AGREEMENT_DATE_18, COMPANY_PERSONS_18, new HashSet<>(),
                new HashSet<>(), SEGMENT_18, INVOLVEMENT_18, COMPANY_PKD_CODE_18, COMPANY_PKD_NAME_18);
    }

    public static Company company19() {
        return company(COMPANY_ID_19, EXT_COMPANY_ID_19, COMPANY_NAME_19, SHORT_NAME_19, NIP_19, REGON_19, KRS_19, RECIPIENT_NAME_19, RATING_19,
                RATING_DATE_19, EXTERNAL_DB_AGREEMENT_19, EXTERNAL_DB_AGREEMENT_DATE_19, COMPANY_PERSONS_19, new HashSet<>(),
                new HashSet<>(), SEGMENT_19, INVOLVEMENT_19, COMPANY_PKD_CODE_19, COMPANY_PKD_NAME_19);
    }

    public static Company company20() {
        return company(COMPANY_ID_20, EXT_COMPANY_ID_20, COMPANY_NAME_20, SHORT_NAME_20, NIP_20, REGON_20, KRS_20, RECIPIENT_NAME_20, RATING_20,
                RATING_DATE_20, EXTERNAL_DB_AGREEMENT_20, EXTERNAL_DB_AGREEMENT_DATE_20, COMPANY_PERSONS_20, new HashSet<>(),
                new HashSet<>(), SEGMENT_20, INVOLVEMENT_20, COMPANY_PKD_CODE_20, COMPANY_PKD_NAME_20);
    }

    public static Company company24() {
        return company(COMPANY_ID_24, EXT_COMPANY_ID_24, COMPANY_NAME_24, SHORT_NAME_24, NIP_24, REGON_24, KRS_24, RECIPIENT_NAME_24, RATING_24,
                RATING_DATE_24, EXTERNAL_DB_AGREEMENT_24, EXTERNAL_DB_AGREEMENT_DATE_24, COMPANY_PERSONS_24, new HashSet<>(),
                new HashSet<>(), SEGMENT_24, INVOLVEMENT_24, COMPANY_PKD_CODE_24, COMPANY_PKD_NAME_24);
    }

    public static Company company21() {
        return company(COMPANY_ID_21, EXT_COMPANY_ID_21, COMPANY_NAME_21, SHORT_NAME_21, NIP_21, REGON_21, KRS_21, RECIPIENT_NAME_21, RATING_21,
                RATING_DATE_21, EXTERNAL_DB_AGREEMENT_21, EXTERNAL_DB_AGREEMENT_DATE_21, COMPANY_PERSONS_21, new HashSet<>(),
                new HashSet<>(), SEGMENT_21, INVOLVEMENT_21, COMPANY_PKD_CODE_21, COMPANY_PKD_NAME_21);
    }

    public static Company company22() {
        return company(COMPANY_ID_22, EXT_COMPANY_ID_22, COMPANY_NAME_22, SHORT_NAME_22, NIP_22, REGON_22, KRS_22, RECIPIENT_NAME_22, RATING_22,
                RATING_DATE_22, EXTERNAL_DB_AGREEMENT_22, EXTERNAL_DB_AGREEMENT_DATE_22, COMPANY_PERSONS_22, new HashSet<>(),
                new HashSet<>(), SEGMENT_22, INVOLVEMENT_22, COMPANY_PKD_CODE_22, COMPANY_PKD_NAME_22);
    }

    public static CompanyDto companyDto3() {
        return companyDto(COMPANY_ID_3, EXT_COMPANY_ID_3, COMPANY_NAME_3, SHORT_NAME_3, NIP_3, REGON_3, KRS_3, RECIPIENT_NAME_3, RATING_3,
                RATING_DATE_3, EXTERNAL_DB_AGREEMENT_3, EXTERNAL_DB_AGREEMENT_DATE_3, PERSONS_DTO_3,
                COMPANY_PKD_CODE_3, COMPANY_PKD_NAME_3);
    }

    public static CompanyDto companyDto4() {
        return companyDto(COMPANY_ID_4, EXT_COMPANY_ID_4, COMPANY_NAME_4, SHORT_NAME_4, NIP_4, REGON_4, KRS_4, RECIPIENT_NAME_4, RATING_4,
                RATING_DATE_4, EXTERNAL_DB_AGREEMENT_4, EXTERNAL_DB_AGREEMENT_DATE_4, PERSONS_DTO_4,
                COMPANY_PKD_CODE_4, COMPANY_PKD_NAME_4);
    }

    public static CompanyDto companyDto5() {
        return companyDto(COMPANY_ID_5, EXT_COMPANY_ID_5, COMPANY_NAME_5, SHORT_NAME_5, NIP_5, REGON_5, KRS_5, RECIPIENT_NAME_5, RATING_5,
                RATING_DATE_5, EXTERNAL_DB_AGREEMENT_5, EXTERNAL_DB_AGREEMENT_DATE_5, PERSONS_DTO_5,
                COMPANY_PKD_CODE_5, COMPANY_PKD_NAME_5);
    }

    public static CompanyDto companyDto6() {
        return companyDto(COMPANY_ID_6, EXT_COMPANY_ID_6, COMPANY_NAME_6, SHORT_NAME_6, NIP_6, REGON_6, KRS_6, RECIPIENT_NAME_6, RATING_6,
                RATING_DATE_6, EXTERNAL_DB_AGREEMENT_6, EXTERNAL_DB_AGREEMENT_DATE_6, PERSONS_DTO_6,
                COMPANY_PKD_CODE_6, COMPANY_PKD_NAME_6);
    }

    public static CompanyDto companyDto7() {
        return companyDto(COMPANY_ID_7, EXT_COMPANY_ID_7, COMPANY_NAME_7, SHORT_NAME_7, NIP_7, REGON_7, KRS_7, RECIPIENT_NAME_7, RATING_7,
                RATING_DATE_7, EXTERNAL_DB_AGREEMENT_7, EXTERNAL_DB_AGREEMENT_DATE_7, PERSONS_DTO_7,
                COMPANY_PKD_CODE_7, COMPANY_PKD_NAME_7);
    }

    public static CompanyDto companyDto8() {
        return companyDto(COMPANY_ID_8, EXT_COMPANY_ID_8, COMPANY_NAME_8, SHORT_NAME_8, NIP_8, REGON_8, KRS_8, RECIPIENT_NAME_8, RATING_8,
                RATING_DATE_8, EXTERNAL_DB_AGREEMENT_8, EXTERNAL_DB_AGREEMENT_DATE_8, PERSONS_DTO_8,
                COMPANY_PKD_CODE_8, COMPANY_PKD_NAME_8);
    }

    public static CompanyDto companyDto9() {
        return companyDto(COMPANY_ID_9, EXT_COMPANY_ID_9, COMPANY_NAME_9, SHORT_NAME_9, NIP_9, REGON_9, KRS_9, RECIPIENT_NAME_9, RATING_9,
                RATING_DATE_9, EXTERNAL_DB_AGREEMENT_9, EXTERNAL_DB_AGREEMENT_DATE_9, PERSONS_DTO_9,
                COMPANY_PKD_CODE_9, COMPANY_PKD_NAME_9);
    }

    public static CompanyDto companyDto10() {
        return companyDto(COMPANY_ID_10, EXT_COMPANY_ID_10, COMPANY_NAME_10, SHORT_NAME_10, NIP_10, REGON_10, KRS_10, RECIPIENT_NAME_10, RATING_10,
                RATING_DATE_10, EXTERNAL_DB_AGREEMENT_10, EXTERNAL_DB_AGREEMENT_DATE_10, PERSONS_DTO_10,
                COMPANY_PKD_CODE_10, COMPANY_PKD_NAME_10);
    }

    public static CompanyDto companyDto11() {
        return companyDto(COMPANY_ID_11, EXT_COMPANY_ID_11, COMPANY_NAME_11, SHORT_NAME_11, NIP_11, REGON_11, KRS_11, RECIPIENT_NAME_11, RATING_11,
                RATING_DATE_11, EXTERNAL_DB_AGREEMENT_11, EXTERNAL_DB_AGREEMENT_DATE_11, PERSONS_DTO_11,
                COMPANY_PKD_CODE_11, COMPANY_PKD_NAME_11);
    }

    public static CompanyDto companyDto12() {
        return companyDto(COMPANY_ID_12, EXT_COMPANY_ID_12, COMPANY_NAME_12, SHORT_NAME_12, NIP_12, REGON_12, KRS_12, RECIPIENT_NAME_12, RATING_12,
                RATING_DATE_12, EXTERNAL_DB_AGREEMENT_12, EXTERNAL_DB_AGREEMENT_DATE_12, PERSONS_DTO_12,
                COMPANY_PKD_CODE_12, COMPANY_PKD_NAME_12);
    }

    public static CompanyDto companyDto13() {
        return companyDto(COMPANY_ID_13, EXT_COMPANY_ID_13, COMPANY_NAME_13, SHORT_NAME_13, NIP_13, REGON_13, KRS_13, RECIPIENT_NAME_13, RATING_13,
                RATING_DATE_13, EXTERNAL_DB_AGREEMENT_13, EXTERNAL_DB_AGREEMENT_DATE_13, PERSONS_DTO_13,
                COMPANY_PKD_CODE_13, COMPANY_PKD_NAME_13);
    }

    public static CompanyDto companyDto14() {
        return companyDto(COMPANY_ID_14, EXT_COMPANY_ID_14, COMPANY_NAME_14, SHORT_NAME_14, NIP_14, REGON_14, KRS_14, RECIPIENT_NAME_14, RATING_14,
                RATING_DATE_14, EXTERNAL_DB_AGREEMENT_14, EXTERNAL_DB_AGREEMENT_DATE_14, PERSONS_DTO_14,
                COMPANY_PKD_CODE_14, COMPANY_PKD_NAME_14);
    }

    public static CompanyDto companyDto15() {
        return companyDto(COMPANY_ID_15, EXT_COMPANY_ID_15, COMPANY_NAME_15, SHORT_NAME_15, NIP_15, REGON_15, KRS_15, RECIPIENT_NAME_15, RATING_15,
                RATING_DATE_15, EXTERNAL_DB_AGREEMENT_15, EXTERNAL_DB_AGREEMENT_DATE_15, PERSONS_DTO_15,
                COMPANY_PKD_CODE_15, COMPANY_PKD_NAME_15);
    }

    public static CompanyDto companyDto16() {
        return companyDto(COMPANY_ID_16, EXT_COMPANY_ID_16, COMPANY_NAME_16, SHORT_NAME_16, NIP_16, REGON_16, KRS_16, RECIPIENT_NAME_16, RATING_16,
                RATING_DATE_16, EXTERNAL_DB_AGREEMENT_16, EXTERNAL_DB_AGREEMENT_DATE_16, PERSONS_DTO_16,
                COMPANY_PKD_CODE_16, COMPANY_PKD_NAME_16);
    }

    public static CompanyDto companyDto17() {
        return companyDto(COMPANY_ID_17, EXT_COMPANY_ID_17, COMPANY_NAME_17, SHORT_NAME_17, NIP_17, REGON_17, KRS_17, RECIPIENT_NAME_17, RATING_17,
                RATING_DATE_17, EXTERNAL_DB_AGREEMENT_17, EXTERNAL_DB_AGREEMENT_DATE_17, PERSONS_DTO_17,
                COMPANY_PKD_CODE_17, COMPANY_PKD_NAME_17);
    }

    public static CompanyDto companyDto18() {
        return companyDto(COMPANY_ID_18, EXT_COMPANY_ID_18, COMPANY_NAME_18, SHORT_NAME_18, NIP_18, REGON_18, KRS_18, RECIPIENT_NAME_18, RATING_18,
                RATING_DATE_18, EXTERNAL_DB_AGREEMENT_18, EXTERNAL_DB_AGREEMENT_DATE_18, PERSONS_DTO_18,
                COMPANY_PKD_CODE_18, COMPANY_PKD_NAME_18);
    }

    public static CompanyDto companyDto19() {
        return companyDto(COMPANY_ID_19, EXT_COMPANY_ID_19, COMPANY_NAME_19, SHORT_NAME_19, NIP_19, REGON_19, KRS_19, RECIPIENT_NAME_19, RATING_19,
                RATING_DATE_19, EXTERNAL_DB_AGREEMENT_19, EXTERNAL_DB_AGREEMENT_DATE_19, PERSONS_DTO_19,
                COMPANY_PKD_CODE_19, COMPANY_PKD_NAME_19);
    }

    public static CompanyDto companyDto20() {
        return companyDto(COMPANY_ID_20, EXT_COMPANY_ID_20, COMPANY_NAME_20, SHORT_NAME_20, NIP_20, REGON_20, KRS_20, RECIPIENT_NAME_20, RATING_20,
                RATING_DATE_20, EXTERNAL_DB_AGREEMENT_20, EXTERNAL_DB_AGREEMENT_DATE_20, PERSONS_DTO_20,
                COMPANY_PKD_CODE_20, COMPANY_PKD_NAME_20);
    }

    public static CompanyDto companyDto21() {
        return companyDto(COMPANY_ID_21, EXT_COMPANY_ID_21, COMPANY_NAME_21, SHORT_NAME_21, NIP_21, REGON_21, KRS_21, RECIPIENT_NAME_21, RATING_21,
                RATING_DATE_21, EXTERNAL_DB_AGREEMENT_21, EXTERNAL_DB_AGREEMENT_DATE_21, PERSONS_DTO_21,
                COMPANY_PKD_CODE_21, COMPANY_PKD_NAME_21);
    }

    public static CompanyDto companyDto22() {
        return companyDto(COMPANY_ID_22, EXT_COMPANY_ID_22, COMPANY_NAME_22, SHORT_NAME_22, NIP_22, REGON_22, KRS_22, RECIPIENT_NAME_22, RATING_22,
                RATING_DATE_22, EXTERNAL_DB_AGREEMENT_22, EXTERNAL_DB_AGREEMENT_DATE_22, PERSONS_DTO_22,
                COMPANY_PKD_CODE_22, COMPANY_PKD_NAME_22);
    }

    public static Company companyToUpdate() {
        return company(COMPANY_ID_2, EXT_COMPANY_ID_1, COMPANY_NAME_1, SHORT_NAME_1, NIP_1, REGON_1, KRS_1, RECIPIENT_NAME_1, RATING_1,
                RATING_DATE_1, EXTERNAL_DB_AGREEMENT_1, EXTERNAL_DB_AGREEMENT_DATE_1, COMPANY_PERSONS_1, singleton(addressToCreate()),
                singleton(contactDetailToCreate()), SEGMENT_1, INVOLVEMENT_1, COMPANY_PKD_CODE_1, COMPANY_PKD_NAME_1);
    }

    public static Company companyFromAida1() {
        return company(COMPANY_ID_1, AidaPersonTestData.COMPANY_ID, COMPANY_NAME_1, SHORT_NAME_1, NIP_1, REGON_1, KRS_1, RECIPIENT_NAME_1, RATING_1,
                RATING_DATE_1, EXTERNAL_DB_AGREEMENT_1, EXTERNAL_DB_AGREEMENT_DATE_1, new ArrayList<>(singletonList(companyPerson1())),
                company1Addresses(), new HashSet<>(singleton(contactDetail1())), SEGMENT_1, INVOLVEMENT_1, COMPANY_PKD_CODE_1, COMPANY_PKD_NAME_1);
    }

    public static Company company(final Long id, final Long extCompanyId, final String companyName, final String shortName, final String nip, final String regon,
                                  final String krs, final String recipientName, final String rating, final Date ratingDate, final Boolean externalDbAgreement,
                                  final Date externalDbAgreementDate, final List<CompanyPerson> companyPersons, final Set<Address> address,
                                  final Set<ContactDetail> contactDetails, final Segment segment, final BigDecimal involvement, final String pkdCode, final String pkdName) {
        final Company company = new Company();
        company.setId(id);
        company.setExtCompanyId(extCompanyId);
        company.setCompanyPersons(fillCompany(company, companyPersons));
        company.setCompanyName(companyName);
        company.setShortName(shortName);
        company.setNip(nip);
        company.setRegon(regon);
        company.setKrs(krs);
        company.setRecipientName(recipientName);
        company.setRating(rating);
        company.setRatingDate(ratingDate);
        company.setExternalDBAgreement(externalDbAgreement);
        company.setExternalDBAgreementDate(externalDbAgreementDate);
        company.setAddresses(address);
        company.setContactDetails(contactDetails);
        company.setSegment(segment);
        company.setInvolvement(involvement);
        company.setPkdCode(pkdCode);
        company.setPkdName(pkdName);
        return company;
    }

    private static CompanyDto companyDto(final Long id, final Long extCompanyId, final String companyName, final String shortName, final String nip,
                                         final String regon, final String krs, final String recipientName, final String rating, final Date ratingDate,
                                         final Boolean externalDbAgreement, final Date externalDbAgreementDate, final Set<PersonDto> persons,
                                         final String pkdCode, final String pkdName) {
        final CompanyDto company = new CompanyDto();
        company.setId(id);
        company.setExtCompanyId(extCompanyId);
        company.setPersons(persons);
        company.setCompanyName(companyName);
        company.setShortName(shortName);
        company.setNip(nip);
        company.setRegon(regon);
        company.setKrs(krs);
        company.setRecipientName(recipientName);
        company.setRating(rating);
        company.setRatingDate(ratingDate);
        company.setExternalDBAgreement(externalDbAgreement);
        company.setExternalDBAgreementDate(externalDbAgreementDate);
        company.setPkdCode(pkdCode);
        company.setPkdName(pkdName);
        return company;
    }

}
