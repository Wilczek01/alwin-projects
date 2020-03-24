package com.codersteam.alwin.dms

import com.codersteam.alwin.dms.model.DocumentDto
import com.codersteam.alwin.dms.model.TemplateDto
import pl.aliorleasing.dms.GenerateDocumentRequest
import pl.aliorleasing.dms.LoginRequest
import pl.aliorleasing.dms.SendDocumentRequest
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.dms.testdata.DocumentDtoTestData.documentDto1
import static com.codersteam.alwin.dms.testdata.DocumentTestData.document1
import static com.codersteam.alwin.dms.testdata.GenerateDocumentDtoTestData.generateDocumentDto1
import static com.codersteam.alwin.dms.testdata.GenerateDocumentTestData.generateDocument1
import static com.codersteam.alwin.dms.testdata.LoginRequestDtoTestData.loginRequestDto1
import static com.codersteam.alwin.dms.testdata.LoignRequestTestData.loginRequest1
import static com.codersteam.alwin.dms.testdata.SendDocumentDtoTestData.sendDocumentDto1
import static com.codersteam.alwin.dms.testdata.SendDocumentTestData.sendDocument1
import static com.codersteam.alwin.dms.testdata.TemplateDtoTestData.templateDto1
import static com.codersteam.alwin.dms.testdata.TemplateTestData.documentTemplate1
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class DmsMapperTest extends Specification {

    @Subject
    DmsMapper mapper = new DmsMapper()

    def "should map from dms document"() {
        when:
            def document = mapper.map(document1(), DocumentDto.class)
        then:
            assertThat(document).isEqualToComparingFieldByFieldRecursively(documentDto1())
    }

    def "should map to dms generate document"() {
        when:
            def generateDocument = mapper.map(generateDocumentDto1(), GenerateDocumentRequest.class)
        then:
            assertThat(generateDocument).isEqualToComparingFieldByFieldRecursively(generateDocument1())
    }

    def "should map to dms send document"() {
        when:
            def sendDocument = mapper.map(sendDocumentDto1(), SendDocumentRequest.class)
        then:
            assertThat(sendDocument).isEqualToComparingFieldByFieldRecursively(sendDocument1())
    }

    def "should map from dms template"() {
        when:
            def template = mapper.map(documentTemplate1(), TemplateDto.class)
        then:
            assertThat(template).isEqualToComparingFieldByFieldRecursively(templateDto1())
    }

    def "should map to dms login request"() {
        when:
            def loginRequest = mapper.map(loginRequestDto1(), LoginRequest.class)
        then:
            assertThat(loginRequest).isEqualToComparingFieldByFieldRecursively(loginRequest1())
    }
}