package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.termination.ContractTerminationAttachment;

@SuppressWarnings({"WeakerAccess"})
public class ContractTerminationAttachmentTestData {

    public static final Long ID_101 = 101L;
    public static final String REFERENCE_101 = "ba66bbb1bd8a16304b48e11de620937e";

    public static final Long ID_103 = 103L;
    public static final String REFERENCE_103 = "f820e6fc0758ad274a172d9fc4d6783d";

    public static ContractTerminationAttachment contractTerminationAttachment101() {
        return contractTerminationAttachment(ID_101, REFERENCE_101);
    }

    public static ContractTerminationAttachment contractTerminationAttachment103() {
        return contractTerminationAttachment(ID_103, REFERENCE_103);
    }

    public static ContractTerminationAttachment contractTerminationAttachment(final Long id, final String reference) {
        final ContractTerminationAttachment cta = new ContractTerminationAttachment();
        cta.setId(id);
        cta.setReference(reference);
        return cta;
    }

}
