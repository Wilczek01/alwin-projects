package com.codersteam.alwin.dms.testdata;

import com.codersteam.alwin.dms.model.AttachmentDto;
import com.codersteam.alwin.dms.model.GenerateDocumentDto;

import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.dms.testdata.AdditionalInformationDtoTestData.additionalInformations1;
import static com.codersteam.alwin.dms.testdata.AttachmentDtoTestData.*;
import static com.codersteam.alwin.dms.testdata.DescriptionsDtoTestData.descriptions1;
import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
public class GenerateDocumentDtoTestData {
    public static final Map<String, String> ADDITIONAL_INFORMATIONS_1 = additionalInformations1();
    public static final List<AttachmentDto> ATTACHMENTS_1 = asList(attachmentDto1(), attachmentDto2(), attachmentDto3(), attachmentDto4());
    public static final Map<String, String> DESCRIPTIONS_1 = descriptions1();
    public static final String TEMPLATE_ID_1 = "TEMPLATE_ID_1";
    public static final Map<String, String> ADDITIONAL_INFORMATIONS_2 = null;
    public static final List<AttachmentDto> ATTACHMENTS_2 = null;
    public static final Map<String, String> DESCRIPTIONS_2 = null;
    public static final String TEMPLATE_ID_2 = "TEMPLATE_ID_2";
    public static final Map<String, String> ADDITIONAL_INFORMATIONS_3 = null;
    public static final List<AttachmentDto> ATTACHMENTS_3 = null;
    public static final Map<String, String> DESCRIPTIONS_3 = null;
    public static final String TEMPLATE_ID_3 = "TEMPLATE_ID_3";
    public static final Map<String, String> ADDITIONAL_INFORMATIONS_4 = null;
    public static final List<AttachmentDto> ATTACHMENTS_4 = null;
    public static final Map<String, String> DESCRIPTIONS_4 = null;
    public static final String TEMPLATE_ID_4 = "TEMPLATE_ID_4";

    public static GenerateDocumentDto generateDocumentDto1() {
        return generateDocumentDto(ADDITIONAL_INFORMATIONS_1, ATTACHMENTS_1, DESCRIPTIONS_1,
                TEMPLATE_ID_1);
    }

    public static GenerateDocumentDto generateDocumentDto2() {
        return generateDocumentDto(ADDITIONAL_INFORMATIONS_2, ATTACHMENTS_2, DESCRIPTIONS_2,
                TEMPLATE_ID_2);
    }

    public static GenerateDocumentDto generateDocumentDto3() {
        return generateDocumentDto(ADDITIONAL_INFORMATIONS_3, ATTACHMENTS_3, DESCRIPTIONS_3,
                TEMPLATE_ID_3);
    }

    public static GenerateDocumentDto generateDocumentDto4() {
        return generateDocumentDto(ADDITIONAL_INFORMATIONS_4, ATTACHMENTS_4, DESCRIPTIONS_4,
                TEMPLATE_ID_4);
    }

    private static GenerateDocumentDto generateDocumentDto(final Map<String, String> additionalInformations, final List<AttachmentDto> attachments,
                                                           final Map<String, String> descriptions, final String templateId) {
        final GenerateDocumentDto generateDocumentDto = new GenerateDocumentDto();
        generateDocumentDto.setAdditionalInformations(additionalInformations);
        generateDocumentDto.setAttachments(attachments);
        generateDocumentDto.setDescriptions(descriptions);
        generateDocumentDto.setTemplateId(templateId);
        return generateDocumentDto;
    }
}