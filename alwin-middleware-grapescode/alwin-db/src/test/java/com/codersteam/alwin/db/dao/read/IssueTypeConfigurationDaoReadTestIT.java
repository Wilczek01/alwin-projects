package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao;
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_1;
import static com.codersteam.alwin.common.issue.Segment.A;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.ALL_ISSUE_TYPE_CONFIGURATIONS;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATIONS;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.DURATION_1;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.issueTypeConfiguration1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class IssueTypeConfigurationDaoReadTestIT extends ReadTestBase {

    @EJB
    private IssueTypeConfigurationDao issueTypeConfigurationDao;

    @Test
    public void shouldFindDurationByTypeNameAndSegment() {
        //when
        final Integer duration = issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, A);

        //then
        assertEquals(DURATION_1, duration.intValue());
    }

    @Test
    public void shouldFindAllIssueTypeConfiguration() {
        //when
        final List<IssueTypeConfiguration> allIssueTypeConfiguration = issueTypeConfigurationDao.findAllIssueTypeConfiguration();

        //then
        assertEquals(ALL_ISSUE_TYPE_CONFIGURATIONS.size(), allIssueTypeConfiguration.size());
        assertThat(allIssueTypeConfiguration).isEqualToComparingFieldByFieldRecursively(ALL_ISSUE_TYPE_CONFIGURATIONS);
    }

    @Test
    public void shouldFindIssueTypeConfigurationByTypeNameAndSegment() {
        // when
        final Optional<IssueTypeConfiguration> issueConfiguration =
                issueTypeConfigurationDao.findIssueTypeConfigurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, A);

        // then
        assertTrue(issueConfiguration.isPresent());
        assertThat(issueConfiguration.get()).isEqualToComparingFieldByFieldRecursively(issueTypeConfiguration1());
    }

    @Test
    public void shouldFindAllCreateAutomaticallyIssueTypeConfigurations() {
        // when
        final List<IssueTypeConfiguration> createAutomaticallyIssueConfigurations =
                issueTypeConfigurationDao.findAllCreateAutomaticallyIssueTypeConfigurations();

        // then
        assertThat(createAutomaticallyIssueConfigurations).isEqualToComparingFieldByFieldRecursively(CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATIONS);
    }

    @Test
    public void getType() {
        assertEquals(IssueTypeConfiguration.class, issueTypeConfigurationDao.getType());
    }
}