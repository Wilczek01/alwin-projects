package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.TagIconDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.TagIconTestData.*
import static java.util.Arrays.asList
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Dariusz Rackowski
 */
class TagIconServiceImplTest extends Specification {

    def dao = Mock(TagIconDao)

    @Subject
    private TagIconServiceImpl service

    def setup() {
        service = new TagIconServiceImpl(dao, new AlwinMapper())
    }

    def "should find all icon tags"() {
        given:
            dao.allOrderedById() >> [testTagIcon1(), testTagIcon2()]

        when:
            def tags = service.findAllTags()

        then:
            assertThat(tags).isEqualToComparingFieldByFieldRecursively(asList(testTagIconDto1(), testTagIconDto2()))

    }

    def "should not find tags"() {
        given:
            dao.allOrderedById() >> []

        when:
            def tags = service.findAllTags()

        then:
            tags.isEmpty()
    }


}
