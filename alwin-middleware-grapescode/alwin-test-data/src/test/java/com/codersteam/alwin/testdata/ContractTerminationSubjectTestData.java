package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.termination.TerminationContractSubjectDto;
import com.codersteam.alwin.jpa.termination.ContractTerminationSubject;

import java.math.BigDecimal;

@SuppressWarnings("WeakerAccess")
public class ContractTerminationSubjectTestData {

    public static final Long ID_100 = 100L;
    public static final Long SUBJECT_ID_100 = 1090L;
    public static final Boolean GPS_INSTALLED_100 = true;
    public static final Boolean GPS_TO_INSTALL_100 = false;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_100 = new BigDecimal("0.00");
    public static final Boolean GPS_INCREASED_FEE_100 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_100 = new BigDecimal("0.00");

    public static final Long ID_101 = 101L;
    public static final Long SUBJECT_ID_101 = 10101L;
    public static final Boolean GPS_INSTALLED_101 = false;
    public static final Boolean GPS_TO_INSTALL_101 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_101 = new BigDecimal("500.00");
    public static final Boolean GPS_INCREASED_FEE_101 = true;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_101 = new BigDecimal("600.00");

    public static final Long ID_102 = 102L;
    public static final Long SUBJECT_ID_102 = 10102L;
    public static final Boolean GPS_INSTALLED_102 = true;
    public static final Boolean GPS_TO_INSTALL_102 = false;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_102 = null;
    public static final Boolean GPS_INCREASED_FEE_102 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_102 = null;

    public static final Long ID_103 = 103L;
    public static final Long SUBJECT_ID_103 = 10103L;
    public static final Boolean GPS_INSTALLED_103 = false;
    public static final Boolean GPS_TO_INSTALL_103 = false;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_103 = null;
    public static final Boolean GPS_INCREASED_FEE_103 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_103 = null;

    public static final Long ID_104 = 104L;
    public static final Long SUBJECT_ID_104 = 10201L;
    public static final Boolean GPS_INSTALLED_104 = false;
    public static final Boolean GPS_TO_INSTALL_104 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_104 = new BigDecimal("900.00");
    public static final Boolean GPS_INCREASED_FEE_104 = true;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_104 = new BigDecimal("1000.00");

    public static final Long ID_106 = 106L;
    public static final Long SUBJECT_ID_106 = 1090L;
    public static final Boolean GPS_INSTALLED_106 = false;
    public static final Boolean GPS_TO_INSTALL_106 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_106 = null;
    public static final Boolean GPS_INCREASED_FEE_106 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_106 = null;

    public static final Long ID_107 = 107L;
    public static final Long SUBJECT_ID_107 = 10901L;
    public static final Boolean GPS_INSTALLED_107 = false;
    public static final Boolean GPS_TO_INSTALL_107 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_107 = null;
    public static final Boolean GPS_INCREASED_FEE_107 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_107 = null;

    public static final Long ID_108 = 108L;
    public static final Long SUBJECT_ID_108 = 1090L;
    public static final Boolean GPS_INSTALLED_108 = false;
    public static final Boolean GPS_TO_INSTALL_108 = false;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_108 = null;
    public static final Boolean GPS_INCREASED_FEE_108 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_108 = null;

    public static final Long ID_126 = 126L;
    public static final Long SUBJECT_ID_126 = 1090L;
    public static final Boolean GPS_INSTALLED_126 = false;
    public static final Boolean GPS_TO_INSTALL_126 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_126 = new BigDecimal("880.70");
    public static final Boolean GPS_INCREASED_FEE_126 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_126 = new BigDecimal("990.80");

    public static final Long ID_128 = 128L;
    public static final Long SUBJECT_ID_128 = 1090L;
    public static final Boolean GPS_INSTALLED_128 = false;
    public static final Boolean GPS_TO_INSTALL_128 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_128 = new BigDecimal("540.00");
    public static final Boolean GPS_INCREASED_FEE_128 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_128 = new BigDecimal("640.00");

    public static final Long ID_129 = 129L;
    public static final Long SUBJECT_ID_129 = 1090L;
    public static final Boolean GPS_INSTALLED_129 = false;
    public static final Boolean GPS_TO_INSTALL_129 = false;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_129 = null;
    public static final Boolean GPS_INCREASED_FEE_129 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_129 = null;

    public static final Long ID_1106 = 1106L;
    public static final Long SUBJECT_ID_1106 = 1096L;
    public static final Boolean GPS_INSTALLED_1106 = false;
    public static final Boolean GPS_TO_INSTALL_1106 = true;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_1106 = new BigDecimal("900.00");
    public static final Boolean GPS_INCREASED_FEE_1106 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_1106 = null;

    public static final Long ID_1107 = 1107L;
    public static final Long SUBJECT_ID_1107 = 1097L;
    public static final Boolean GPS_INSTALLED_1107 = false;
    public static final Boolean GPS_TO_INSTALL_1107 = false;
    public static final BigDecimal GPS_INSTALLATION_CHARGE_1107 = new BigDecimal("0.00");
    public static final Boolean GPS_INCREASED_FEE_1107 = false;
    public static final BigDecimal GPS_INCREASED_INSTALLATION_CHARGE_1107 = null;

    public static final int COUNT_OF_ALL = 14;

    public static ContractTerminationSubject newContractTerminationSubject100() {
        return contractTerminationSubject(ID_100, SUBJECT_ID_100, GPS_TO_INSTALL_100, GPS_INSTALLATION_CHARGE_100, GPS_INSTALLED_100,
                GPS_INCREASED_FEE_100, GPS_INCREASED_INSTALLATION_CHARGE_100);
    }

    public static ContractTerminationSubject contractTerminationSubjectWithoutGpsInstallation() {
        final ContractTerminationSubject contractTerminationSubject = contractTerminationSubject101();
        contractTerminationSubject.setGpsToInstall(false);
        return contractTerminationSubject;
    }

    public static ContractTerminationSubject contractTerminationSubject101() {
        return contractTerminationSubject(ID_101, SUBJECT_ID_101, GPS_TO_INSTALL_101, GPS_INSTALLATION_CHARGE_101, GPS_INSTALLED_101,
                GPS_INCREASED_FEE_101, GPS_INCREASED_INSTALLATION_CHARGE_101);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto101() {
        return terminationContractSubjectDto(ID_101, SUBJECT_ID_101, GPS_TO_INSTALL_101, GPS_INSTALLATION_CHARGE_101, GPS_INSTALLED_101,
                GPS_INCREASED_FEE_101, GPS_INCREASED_INSTALLATION_CHARGE_101);
    }

    public static ContractTerminationSubject contractTerminationSubject102() {
        return contractTerminationSubject(ID_102, SUBJECT_ID_102, GPS_TO_INSTALL_102, GPS_INSTALLATION_CHARGE_102, GPS_INSTALLED_102,
                GPS_INCREASED_FEE_102, GPS_INCREASED_INSTALLATION_CHARGE_102);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto102() {
        return terminationContractSubjectDto(ID_102, SUBJECT_ID_102, GPS_TO_INSTALL_102, GPS_INSTALLATION_CHARGE_102, GPS_INSTALLED_102,
                GPS_INCREASED_FEE_102, GPS_INCREASED_INSTALLATION_CHARGE_102);
    }

    public static ContractTerminationSubject contractTerminationSubject103() {
        return contractTerminationSubject(ID_103, SUBJECT_ID_103, GPS_TO_INSTALL_103, GPS_INSTALLATION_CHARGE_103, GPS_INSTALLED_103,
                GPS_INCREASED_FEE_103, GPS_INCREASED_INSTALLATION_CHARGE_103);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto103() {
        return terminationContractSubjectDto(ID_103, SUBJECT_ID_103, GPS_TO_INSTALL_103, GPS_INSTALLATION_CHARGE_103, GPS_INSTALLED_103,
                GPS_INCREASED_FEE_103, GPS_INCREASED_INSTALLATION_CHARGE_103);
    }

    public static ContractTerminationSubject contractTerminationSubject104() {
        return contractTerminationSubject(ID_104, SUBJECT_ID_104, GPS_TO_INSTALL_104, GPS_INSTALLATION_CHARGE_104, GPS_INSTALLED_104,
                GPS_INCREASED_FEE_104, GPS_INCREASED_INSTALLATION_CHARGE_104);
    }

    public static ContractTerminationSubject contractTerminationSubject106() {
        return contractTerminationSubject(ID_106, SUBJECT_ID_106, GPS_TO_INSTALL_106, GPS_INSTALLATION_CHARGE_106, GPS_INSTALLED_106,
                GPS_INCREASED_FEE_106, GPS_INCREASED_INSTALLATION_CHARGE_106);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto106() {
        return terminationContractSubjectDto(ID_106, SUBJECT_ID_106, GPS_TO_INSTALL_106, GPS_INSTALLATION_CHARGE_106, GPS_INSTALLED_106,
                GPS_INCREASED_FEE_106, GPS_INCREASED_INSTALLATION_CHARGE_106);
    }

    public static ContractTerminationSubject contractTerminationSubject107() {
        return contractTerminationSubject(ID_107, SUBJECT_ID_107, GPS_TO_INSTALL_107, GPS_INSTALLATION_CHARGE_107, GPS_INSTALLED_107,
                GPS_INCREASED_FEE_107, GPS_INCREASED_INSTALLATION_CHARGE_107);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto107() {
        return terminationContractSubjectDto(ID_107, SUBJECT_ID_107, GPS_TO_INSTALL_107, GPS_INSTALLATION_CHARGE_107, GPS_INSTALLED_107,
                GPS_INCREASED_FEE_107, GPS_INCREASED_INSTALLATION_CHARGE_107);
    }

    public static ContractTerminationSubject contractTerminationSubject108() {
        return contractTerminationSubject(ID_108, SUBJECT_ID_108, GPS_TO_INSTALL_108, GPS_INSTALLATION_CHARGE_108, GPS_INSTALLED_108,
                GPS_INCREASED_FEE_108, GPS_INCREASED_INSTALLATION_CHARGE_108);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto108() {
        return terminationContractSubjectDto(ID_108, SUBJECT_ID_108, GPS_TO_INSTALL_108, GPS_INSTALLATION_CHARGE_108, GPS_INSTALLED_108,
                GPS_INCREASED_FEE_108, GPS_INCREASED_INSTALLATION_CHARGE_108);
    }

    public static ContractTerminationSubject contractTerminationSubject126() {
        return contractTerminationSubject(ID_126, SUBJECT_ID_126, GPS_TO_INSTALL_126, GPS_INSTALLATION_CHARGE_126, GPS_INSTALLED_126,
                GPS_INCREASED_FEE_126, GPS_INCREASED_INSTALLATION_CHARGE_126);
    }

    public static ContractTerminationSubject contractTerminationSubject128() {
        return contractTerminationSubject(ID_128, SUBJECT_ID_128, GPS_TO_INSTALL_128, GPS_INSTALLATION_CHARGE_128, GPS_INSTALLED_128,
                GPS_INCREASED_FEE_128, GPS_INCREASED_INSTALLATION_CHARGE_128);
    }

    public static ContractTerminationSubject contractTerminationSubject129() {
        return contractTerminationSubject(ID_129, SUBJECT_ID_129, GPS_TO_INSTALL_129, GPS_INSTALLATION_CHARGE_129, GPS_INSTALLED_129,
                GPS_INCREASED_FEE_129, GPS_INCREASED_INSTALLATION_CHARGE_129);
    }

    public static ContractTerminationSubject contractTerminationSubject1106() {
        return contractTerminationSubject(ID_1106, SUBJECT_ID_1106, GPS_TO_INSTALL_1106, GPS_INSTALLATION_CHARGE_1106, GPS_INSTALLED_1106,
                GPS_INCREASED_FEE_1106, GPS_INCREASED_INSTALLATION_CHARGE_1106);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto1106() {
        return terminationContractSubjectDto(ID_1106, SUBJECT_ID_1106, GPS_TO_INSTALL_1106, GPS_INSTALLATION_CHARGE_1106, GPS_INSTALLED_1106,
                GPS_INCREASED_FEE_1106, GPS_INCREASED_INSTALLATION_CHARGE_1106);
    }

    public static TerminationContractSubjectDto terminationContractSubjectDto1107() {
        return terminationContractSubjectDto(ID_1107, SUBJECT_ID_1107, GPS_TO_INSTALL_1107, GPS_INSTALLATION_CHARGE_1107, GPS_INSTALLED_1107,
                GPS_INCREASED_FEE_1107, GPS_INCREASED_INSTALLATION_CHARGE_1107);
    }

    public static ContractTerminationSubject contractTerminationSubject1107() {
        return contractTerminationSubject(ID_1107, SUBJECT_ID_1107, GPS_TO_INSTALL_1107, GPS_INSTALLATION_CHARGE_1107, GPS_INSTALLED_1107,
                GPS_INCREASED_FEE_1107, GPS_INCREASED_INSTALLATION_CHARGE_1107);
    }

    private static TerminationContractSubjectDto terminationContractSubjectDto(final Long id, final Long subjectId, final Boolean gpsToInstall,
                                                                               final BigDecimal gpsInstallationCharge, final Boolean gpsInstalled,
                                                                               Boolean gpsIncreasedFee, BigDecimal gpsIncreasedInstallationCharge) {
        final TerminationContractSubjectDto tcs = new TerminationContractSubjectDto();
        tcs.setId(id);
        tcs.setSubjectId(subjectId);
        tcs.setGpsToInstall(gpsToInstall);
        tcs.setGpsInstallationCharge(gpsInstallationCharge);
        tcs.setGpsInstalled(gpsInstalled);
        tcs.setGpsIncreasedFee(gpsIncreasedFee);
        tcs.setGpsIncreasedInstallationCharge(gpsIncreasedInstallationCharge);
        return tcs;
    }

    private static ContractTerminationSubject contractTerminationSubject(final Long id, final Long subjectId, final Boolean gpsToInstall,
                                                                         final BigDecimal gpsInstallationCharge, final Boolean gpsInstalled,
                                                                         Boolean gpsIncreasedFee, BigDecimal gpsIncreasedInstallationCharge) {
        final ContractTerminationSubject subject = new ContractTerminationSubject();
        subject.setId(id);
        subject.setSubjectId(subjectId);
        subject.setGpsToInstall(gpsToInstall);
        subject.setGpsInstallationCharge(gpsInstallationCharge);
        subject.setGpsInstalled(gpsInstalled);
        subject.setGpsIncreasedFee(gpsIncreasedFee);
        subject.setGpsIncreasedInstallationCharge(gpsIncreasedInstallationCharge);
        return subject;
    }
}
