package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.api.model.user.OperatorType
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueTypeDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER
import static com.codersteam.alwin.jpa.type.OperatorNameType.SECURITY_SPECIALIST
import static com.codersteam.alwin.testdata.IssueTypeTestData.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class IssueTypeServiceImplTest extends Specification {

    @Subject
    private IssueTypeServiceImpl service

    private IssueTypeDao issueTypeDao
    private AlwinMapper alwinMapper = new AlwinMapper()

    def "setup"() {
        issueTypeDao = Mock(IssueTypeDao)
        service = new IssueTypeServiceImpl(issueTypeDao, alwinMapper)
    }

    def "should return all issue types"() {
        given:
            issueTypeDao.findAllIssueTypes() >> [issueType1(), issueType3()]
        when:
            def issueTypes = service.findAllIssueTypes()
        then:
            assertThat(issueTypes).isEqualToComparingFieldByFieldRecursively([issueTypeWithOperatorTypesDto1(), issueTypeWithOperatorTypesDto3()])
    }

    def "should find issue types by operator type"() {
        given:
            issueTypeDao.findIssueTypesByOperatorNameType(PHONE_DEBT_COLLECTOR_MANAGER) >> [issueType1(), issueType2()]
        when:
            def issueTypes = service.findIssueTypesByOperatorType(OperatorType.PHONE_DEBT_COLLECTOR_MANAGER)
        then:
            assertThat(issueTypes).isEqualToComparingFieldByFieldRecursively([issueTypeDto1(), issueTypeDto2()])
    }

    def "should not find issue types by operator type"() {
        given:
            issueTypeDao.findIssueTypesByOperatorNameType(SECURITY_SPECIALIST) >> []
        when:
            def issueTypes = service.findIssueTypesByOperatorType(OperatorType.SECURITY_SPECIALIST)
        then:
            issueTypes == []
    }

    def "should find issue type by id"() {
        given:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueType1())
        when:
            def issueType = service.findIssueTypeById(ISSUE_TYPE_ID_1)
        then:
            assertThat(issueType).isEqualToComparingFieldByField(issueTypeWithOperatorTypesDto1())
    }

    def "should not find issue type by id"() {
        given:
            issueTypeDao.findIssueTypeById(NOT_EXISTING_ISSUE_TYPE_ID) >> Optional.empty()
        when:
            def issueType = service.findIssueTypeById(NOT_EXISTING_ISSUE_TYPE_ID)
        then:
            issueType == null
    }
}