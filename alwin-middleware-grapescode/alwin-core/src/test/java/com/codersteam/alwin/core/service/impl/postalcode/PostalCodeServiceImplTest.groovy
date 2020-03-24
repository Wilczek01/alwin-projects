package com.codersteam.alwin.core.service.impl.postalcode

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.PostalCodeDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.PostalCodeTestData.*
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Michal Horowic
 */
class PostalCodeServiceImplTest extends Specification {

    @Subject
    PostalCodeServiceImpl service

    PostalCodeDao dao
    AlwinMapper mapper

    def setup() {
        this.dao = Mock(PostalCodeDao)
        this.mapper = new AlwinMapper()
        this.service = new PostalCodeServiceImpl(dao, mapper)
    }

    def "should return postal codes page"() {
        given:
            def mask = MASK_1

        and:
            dao.findPostalCodesByMask(mask) >> testPostalCodesList()
            dao.findPostalCodesByMaskCount(mask) >> 3

        when:
            def result = service.findPostalCodesByMask(mask)

        then:
            assertThat(result)
                    .usingComparatorForFields(SKIP_COMPARATOR, "operator.parentOperator")
                    .isEqualToComparingFieldByFieldRecursively(testPostalCodesPage())
    }

    def "should check if postal code exists"(exists, expectedResult) {
        given:
            dao.exists(ID_1) >> exists

        when:
            def result = service.checkIfPostalCodeExists(ID_1)

        then:
            result == expectedResult

        where:
            exists | expectedResult
            true   | true
            false  | false
    }

    def "should check if postal code mask exists"(exists, expectedResult) {
        given:
            dao.checkIfPostalCodeMaskExists(MASK_1) >> exists

        when:
            def result = service.checkIfPostalCodeMaskExists(MASK_1)

        then:
            result == expectedResult

        where:
            exists | expectedResult
            true   | true
            false  | false
    }
}
