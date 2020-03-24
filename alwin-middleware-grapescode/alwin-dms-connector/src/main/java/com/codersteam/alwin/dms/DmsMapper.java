package com.codersteam.alwin.dms;

import com.codersteam.alwin.dms.model.*;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import pl.aliorleasing.dms.*;

import javax.ejb.Stateless;

@Stateless
public class DmsMapper extends ConfigurableMapper {

    @Override
    protected void configure(final MapperFactory factory) {
        factory.classMap(DocumentDto.class, DocumentDTO.class)
                .field("metadataList", "documentMetadataList.documentMetadata")
                .byDefault()
                .register();

        factory.classMap(GenerateDocumentDto.class, GenerateDocumentRequest.class)
                .field("templateId", "documentTemplateId")
                .field("attachments", "documentAttachments.documentAttachment")
                .field("descriptions{key}", "documentDescriptions.documentDescription{key}")
                .field("descriptions{value}", "documentDescriptions.documentDescription{value}")
                .field("additionalInformations{key}", "documentAdditionalInformations.documentAdditionalInformation{key}")
                .field("additionalInformations{value}", "documentAdditionalInformations.documentAdditionalInformation{value}")
                .byDefault()
                .register();

        factory.classMap(SendDocumentDto.class, SendDocumentRequest.class)
                .field("metadataList", "documentMetadataList.documentMetadata")
                .byDefault()
                .register();

        factory.classMap(TemplateDto.class, DocumentTemplateDTO.class)
                .byDefault()
                .register();

        factory.classMap(LoginRequestDto.class, LoginRequest.class)
                .byDefault()
                .register();
    }

}