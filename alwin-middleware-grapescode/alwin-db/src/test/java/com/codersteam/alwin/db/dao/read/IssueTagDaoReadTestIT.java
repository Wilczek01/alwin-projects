package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.IssueTagDao;
import com.codersteam.alwin.jpa.issue.IssueTag;
import org.junit.Test;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

public class IssueTagDaoReadTestIT extends ReadTestBase {

    @EJB
    private IssueTagDao dao;

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<IssueTag> type = dao.getType();

        // then
        assertEquals(IssueTag.class, type);
    }

}
