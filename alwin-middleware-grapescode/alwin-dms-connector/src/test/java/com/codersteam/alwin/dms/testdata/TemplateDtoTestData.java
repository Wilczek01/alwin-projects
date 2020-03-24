package com.codersteam.alwin.dms.testdata;


import com.codersteam.alwin.dms.model.TemplateDto;

public class TemplateDtoTestData {
    public static final String DESCRIPTION_1 = "DESCRIPTION_1";
    public static final String HTML_URL_1 = "HTML_URL_1";
    public static final String ID_1 = "ID_1";
    public static final String NAME_1 = "NAME_1";
    public static final String DESCRIPTION_2 = "DESCRIPTION_2";
    public static final String HTML_URL_2 = "HTML_URL_2";
    public static final String ID_2 = "ID_2";
    public static final String NAME_2 = "NAME_2";
    public static final String DESCRIPTION_3 = "DESCRIPTION_3";
    public static final String HTML_URL_3 = "HTML_URL_3";
    public static final String ID_3 = "ID_3";
    public static final String NAME_3 = "NAME_3";
    public static final String DESCRIPTION_4 = "DESCRIPTION_4";
    public static final String HTML_URL_4 = "HTML_URL_4";
    public static final String ID_4 = "ID_4";
    public static final String NAME_4 = "NAME_4";

    public static TemplateDto templateDto1() {
        return templateDto(DESCRIPTION_1, HTML_URL_1, ID_1, NAME_1);
    }

    public static TemplateDto templateDto2() {
        return templateDto(DESCRIPTION_2, HTML_URL_2, ID_2, NAME_2);
    }

    public static TemplateDto templateDto3() {
        return templateDto(DESCRIPTION_3, HTML_URL_3, ID_3, NAME_3);
    }

    public static TemplateDto templateDto4() {
        return templateDto(DESCRIPTION_4, HTML_URL_4, ID_4, NAME_4);
    }

    private static TemplateDto templateDto(final String description, final String htmlUrl, final String id, final String name) {
        final TemplateDto templateDto = new TemplateDto();
        templateDto.setDescription(description);
        templateDto.setHtmlUrl(htmlUrl);
        templateDto.setId(id);
        templateDto.setName(name);
        return templateDto;
    }
}