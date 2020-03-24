package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.service.issue.TagService
import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.util.IdsDto
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.TagTestData.*

/**
 * @author Michal Horowic
 */
class TagValidatorTest extends Specification {

    @Subject
    private TagValidator tagValidator

    private TagService tagService = Mock(TagService)

    def "setup"() {
        tagValidator = new TagValidator(tagService)
    }

    def "should check if color is invalid"(String color, expectedResult) {
        when:
            def invalid = tagValidator.isInvalidColor(color)

        then:
            invalid == expectedResult

        where:
            color      | expectedResult
            '#1f1f1F'  | false
            '#AFAFAF'  | false
            '#1AFFa1'  | false
            '#222fff'  | false
            '#F00'     | false
            '123456'   | true
            '#afafah'  | true
            '#123abce' | true
            'aFaE3f'   | true
            'F00'      | true
            '#afaf'    | true
            '#F0h'     | true
    }

    def "should check if tags exist"() {
        given:
            tagService.findExistingTagsIds(new LinkedHashSet<Long>([ID_1, ID_2, ADDED_ID])) >> new LinkedHashSet<Long>([ID_1])

        when:
            tagValidator.checkIfTagsExists(new IdsDto([ID_1, ID_2, ADDED_ID]))

        then:
            def e = thrown(AlwinValidationException)

        and:
            e.message == 'Etykieta o podanym identyfikatorze [[2, 100]] nie istnieje'
    }
}
