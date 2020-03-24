package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.common.issue.Segment
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.activity.DefaultIssueActivityService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_1
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.DEFAULT_ISSUE_ACTIVITY_WT1_DTOS
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class IssueConfigurationServiceImplTest extends Specification {

    private IssueTypeConfigurationDao issueTypeConfigurationDao = Mock(IssueTypeConfigurationDao)
    private DefaultIssueActivityService defaultIssueActivityService = Mock(DefaultIssueActivityService)
    private AlwinMapper mapper = new AlwinMapper()

    @Subject
    private IssueConfigurationServiceImpl service

    def "setup"() {
        service = new IssueConfigurationServiceImpl(issueTypeConfigurationDao, defaultIssueActivityService, mapper)
    }

    def "should have default constructor for EJB"() {
        when:
            def service = new IssueConfigurationServiceImpl()
        then:
            service
    }

    def "should find all issue type configuration"() {
        given:
            issueTypeConfigurationDao.findAllIssueTypeConfiguration() >> ALL_ISSUE_TYPE_CONFIGURATIONS
        when:
            defaultIssueActivityService.findDefaultIssueActivities(ISSUE_TYPE_ID_1) >> DEFAULT_ISSUE_ACTIVITY_WT1_DTOS
        and:
            def configurations = service.findAllIssueTypeConfigurations()
        then:
            configurations.size() == 16
            assertThat(configurations[0]).isEqualToComparingFieldByField(issueTypeConfigurationWithDefaultIssueActivityAndOperatorTypeDto())
    }

    def "should update existing configuration"() {
        when:
            service.updateIssueTypeConfiguration(ID_1, issueTypeConfigurationWithOperatorTypeDto())

        then:
            1 * issueTypeConfigurationDao.update(_ as IssueTypeConfiguration, _ as Long, _ as Set) >> { args ->
                def configuration = (IssueTypeConfiguration) args[0]
                assertThat(configuration).isEqualToComparingFieldByFieldRecursively(issueTypeConfigurationWithoutParentOperator())
                def issueTypeId = (Long) args[1]
                assert issueTypeId == ID_1
                def operatorTypesIds = (Set) args[2]
                assert operatorTypesIds == [1, 2, 3, 12] as Set
                configuration
            }
    }

    def "should find issue type configuration"() {
        given:
            issueTypeConfigurationDao.findIssueTypeConfigurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.A) >> Optional.of(issueTypeConfiguration1())
        when:
            def issueConfiguration = service.findIssueTypeConfiguration(PHONE_DEBT_COLLECTION_1, Segment.A)
        then:
            assertThat(issueConfiguration).isEqualToComparingFieldByField(issueTypeConfigurationWithOperatorTypeDto())
    }

    def "should throw exception when finding issue type configuration for unknown type"() {
        given:
            issueTypeConfigurationDao.findIssueTypeConfigurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.A) >> Optional.empty()
        when:
            service.findIssueTypeConfiguration(PHONE_DEBT_COLLECTION_1, Segment.A)
        then:
            def e = thrown(EntityNotFoundException)
            e.entityId == "type=PHONE_DEBT_COLLECTION_1, segment=A"
    }

    def "should find all create automatically issue type configurations"() {
        given:
            issueTypeConfigurationDao.findAllCreateAutomaticallyIssueTypeConfigurations() >> CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATIONS
        when:
            def configurations = service.findAllCreateAutomaticallyIssueTypeConfigurations()
        then:
            assertThat(configurations)
                    .usingComparatorForFields(SKIP_COMPARATOR, "operatorTypes")
                    .isEqualToComparingFieldByFieldRecursively(CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATION_DTOS)
    }
}