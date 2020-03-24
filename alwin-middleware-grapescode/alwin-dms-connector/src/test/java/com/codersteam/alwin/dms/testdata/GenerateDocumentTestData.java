package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentAdditionalInformation;
import pl.aliorleasing.dms.DocumentAttachment;
import pl.aliorleasing.dms.DocumentDescription;
import pl.aliorleasing.dms.GenerateDocumentRequest;
import pl.aliorleasing.dms.GenerateDocumentRequest.DocumentAdditionalInformations;
import pl.aliorleasing.dms.GenerateDocumentRequest.DocumentAttachments;
import pl.aliorleasing.dms.GenerateDocumentRequest.DocumentDescriptions;

import static com.codersteam.alwin.dms.testdata.AdditionalInformationTestData.*;
import static com.codersteam.alwin.dms.testdata.AttachmentTestData.*;
import static com.codersteam.alwin.dms.testdata.DescriptionTestData.*;
import static java.util.Arrays.asList;

public class GenerateDocumentTestData extends GenerateDocumentDtoTestData {

    private static final DocumentAdditionalInformations ADDITIONAL_INFORMATIONS_1 = documentAdditionalInformations(additionalInformation1(),
            additionalInformation2(), additionalInformation3(), additionalInformation4());
    private static final DocumentAttachments ATTACHMENTS_1 = attachments(attachment1(), attachment2(), attachment3(), attachment4());
    private static final DocumentDescriptions DESCRIPTIONS_1 = descriptions(description1(), description2(), description3(), description4());

    public static GenerateDocumentRequest generateDocument1() {
        return generateDocument(ADDITIONAL_INFORMATIONS_1, ATTACHMENTS_1, DESCRIPTIONS_1,
                TEMPLATE_ID_1);
    }

    private static GenerateDocumentRequest generateDocument(final DocumentAdditionalInformations additionalInformations, final DocumentAttachments attachments,
                                                            final DocumentDescriptions descriptions, final String templateId) {
        final GenerateDocumentRequest generateDocumentDto = new GenerateDocumentRequest();
        generateDocumentDto.setDocumentAdditionalInformations(additionalInformations);
        generateDocumentDto.setDocumentAttachments(attachments);
        generateDocumentDto.setDocumentDescriptions(descriptions);
        generateDocumentDto.setDocumentTemplateId(templateId);
        return generateDocumentDto;
    }

    private static DocumentDescriptions descriptions(final DocumentDescription... descriptions) {
        final DocumentDescriptions documentDescriptions = new DocumentDescriptions();
        documentDescriptions.getDocumentDescription().addAll(asList(descriptions));
        return documentDescriptions;
    }

    private static DocumentAttachments attachments(final DocumentAttachment... attachments) {
        final DocumentAttachments documentAttachments = new DocumentAttachments();
        documentAttachments.getDocumentAttachment().addAll(asList(attachments));
        return documentAttachments;
    }

    private static DocumentAdditionalInformations documentAdditionalInformations(final DocumentAdditionalInformation... additionalInformations) {
        final DocumentAdditionalInformations informations = new DocumentAdditionalInformations();
        informations.getDocumentAdditionalInformation().addAll(asList(additionalInformations));
        return informations;
    }

}