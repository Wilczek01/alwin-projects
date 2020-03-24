package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.TagDao;
import com.google.common.collect.Sets;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Set;

import static com.codersteam.alwin.testdata.TagTestData.ID_1;
import static com.codersteam.alwin.testdata.TagTestData.ID_2;
import static com.codersteam.alwin.testdata.TagTestData.ID_3;
import static com.codersteam.alwin.testdata.TagTestData.NOT_EXISTING_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TagDaoTestIT extends ReadTestBase {

    @EJB
    private TagDao dao;

    @Test
    public void shouldFindExistingTagsIds() {
        // when
        final Set<Long> result = dao.findExistingTagsIds(Sets.newHashSet(ID_1, ID_2, NOT_EXISTING_ID));

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(ID_1));
        assertTrue(result.contains(ID_2));
    }

    @Test
    public void shouldCheckThatTagExists() {
        // when
        final boolean exists = dao.exists(ID_1);

        // then
        assertTrue(exists);
    }

    @Test
    public void shouldCheckThatTagIsDeletable() {
        // when
        final boolean deletable = dao.isDeletable(ID_1);

        // then
        assertTrue(deletable);
    }

    @Test
    public void shouldCheckThatTagIsNotDeletable() {
        // when
        final boolean deletable = dao.isDeletable(ID_3);

        // then
        assertFalse(deletable);
    }

    @Test
    public void shouldCheckThatNotExistingTagIsNotDeletable() {
        // when
        final boolean deletable = dao.isDeletable(NOT_EXISTING_ID);

        // then
        assertFalse(deletable);
    }

}
