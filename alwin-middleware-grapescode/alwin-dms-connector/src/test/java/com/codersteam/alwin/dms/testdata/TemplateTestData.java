package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentTemplateDTO;

public class TemplateTestData extends TemplateDtoTestData {

    public static DocumentTemplateDTO documentTemplate1() {
        return documentTemplate(DESCRIPTION_1, HTML_URL_1, ID_1, NAME_1);
    }

    public static DocumentTemplateDTO documentTemplate2() {
        return documentTemplate(DESCRIPTION_2, HTML_URL_2, ID_2, NAME_2);
    }

    public static DocumentTemplateDTO documentTemplate3() {
        return documentTemplate(DESCRIPTION_3, HTML_URL_3, ID_3, NAME_3);
    }

    public static DocumentTemplateDTO documentTemplate4() {
        return documentTemplate(DESCRIPTION_4, HTML_URL_4, ID_4, NAME_4);
    }

    public static DocumentTemplateDTO documentTemplate(final String description, final String htmlUrl, final String id, final String name) {
        final DocumentTemplateDTO templateDto = new DocumentTemplateDTO();
        templateDto.setDescription(description);
        templateDto.setHtmlUrl(htmlUrl);
        templateDto.setId(id);
        templateDto.setName(name);
        return templateDto;
    }
}