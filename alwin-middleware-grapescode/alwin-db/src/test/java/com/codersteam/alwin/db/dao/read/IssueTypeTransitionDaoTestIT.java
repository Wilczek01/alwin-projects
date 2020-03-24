package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.IssueTypeTransitionDao;
import com.codersteam.alwin.jpa.issue.IssueTypeTransition;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.stream.Collectors;

import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType8;
import static com.codersteam.alwin.testdata.IssueTypeTransitionTestData.ISSUE_TYPE_TRANSITION_1;
import static com.codersteam.alwin.testdata.IssueTypeTransitionTestData.allTypeIds;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class IssueTypeTransitionDaoTestIT extends ReadTestBase {

    @EJB
    private IssueTypeTransitionDao issueTypeTransitionDao;

    @Test
    public void shouldReturnAllIssueTypeTransitions() {
        //given
        final List<Long> expectedIds = allTypeIds();

        //when
        final List<IssueTypeTransition> all = issueTypeTransitionDao.all();
        final List<Long> actualIds = all.stream().map(IssueTypeTransition::getId).collect(Collectors.toList());

        //then
        assertTrue(expectedIds.containsAll(actualIds));
        assertTrue(actualIds.containsAll(expectedIds));
    }

    @Test
    public void shouldFindIssueTypeTransitionsForIssueType() {
        //when
        final List<IssueTypeTransition> types = issueTypeTransitionDao.findByClosedIssueType(issueType1());

        //then
        assertEquals(1, types.size());
        assertThat(types.get(0)).isEqualToComparingFieldByFieldRecursively(ISSUE_TYPE_TRANSITION_1);
    }

    @Test
    public void shouldNotFindIssueTypeTransitionsForIssueType() {
        //when
        final List<IssueTypeTransition> types = issueTypeTransitionDao.findByClosedIssueType(issueType8());

        //then
        assertEquals(0, types.size());
    }

    @Test
    public void getType() {
        assertEquals(IssueTypeTransition.class, issueTypeTransitionDao.getType());
    }

}