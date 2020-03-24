package com.codersteam.alwin.core.service.impl.operator

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.OperatorTypeDao
import spock.lang.Specification

import static com.codersteam.alwin.testdata.OperatorTypeTestData.*
import static java.util.Arrays.asList
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class OperatorTypeServiceImplTest extends Specification {

    def "should find all operator types"() {
        given:
            def operatorTypeDao = Mock(OperatorTypeDao)
            operatorTypeDao.findAll() >> asList(operatorTypeAdmin(), operatorTypePhoneDebtCollector(), operatorTypePhoneDebtCollectorManager())

        and:
            def operatorTypeService = new OperatorTypeServiceImpl(operatorTypeDao, new AlwinMapper())

        when:
            def result = operatorTypeService.findAllTypes()

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(asList(TEST_OPERATOR_TYPE_DTO, TEST_OPERATOR_TYPE_DTO_2, TEST_OPERATOR_TYPE_DTO_3))
    }
}
