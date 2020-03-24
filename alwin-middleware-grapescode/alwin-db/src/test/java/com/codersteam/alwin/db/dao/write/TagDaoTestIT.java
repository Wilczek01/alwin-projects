package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.TagDao;
import com.codersteam.alwin.jpa.issue.Tag;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.TagIconTestData.testTagIcon3;
import static com.codersteam.alwin.testdata.TagTestData.ADDED_ID;
import static com.codersteam.alwin.testdata.TagTestData.ID_1;
import static com.codersteam.alwin.testdata.TagTestData.MODIFIED_DESCRIPTION;
import static com.codersteam.alwin.testdata.TagTestData.MODIFIED_NAME;
import static com.codersteam.alwin.testdata.TagTestData.NAME_1;
import static com.codersteam.alwin.testdata.TagTestData.testAddedTag1;
import static com.codersteam.alwin.testdata.TagTestData.testModifiedTag1;
import static com.codersteam.alwin.testdata.TagTestData.testTagToAdd;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
public class TagDaoTestIT extends WriteTestBase {

    @EJB
    private TagDao dao;

    @Test
    public void shouldCreateTag() {
        // given
        assertFalse(dao.get(ADDED_ID).isPresent());

        // when
        dao.create(testTagToAdd());

        // then
        assertThat(dao.get(ADDED_ID).get()).isEqualToComparingFieldByFieldRecursively(testAddedTag1());
    }

    @Test
    public void shouldUpdateTag() {
        // given
        final Optional<Tag> optionalExistingTag = dao.get(ID_1);
        assertTrue(optionalExistingTag.isPresent());
        final Tag existingTag = optionalExistingTag.get();
        assertEquals(existingTag.getName(), NAME_1);
        existingTag.setName(MODIFIED_NAME);
        existingTag.setDescription(MODIFIED_DESCRIPTION);
        existingTag.setTagIcon(testTagIcon3());

        // when
        dao.update(existingTag);

        // then
        final Optional<Tag> optionalUpdatedTag = dao.get(ID_1);
        assertTrue(optionalUpdatedTag.isPresent());
        final Tag updatedTag = optionalUpdatedTag.get();
        assertEquals(updatedTag.getName(), MODIFIED_NAME);
        assertThat(updatedTag).isEqualToComparingFieldByFieldRecursively(testModifiedTag1());
    }

    @Test
    public void shouldDeleteTag() {
        // given
        final Optional<Tag> existingTag = dao.get(ID_1);
        assertTrue(existingTag.isPresent());

        // when
        dao.delete(ID_1);

        // then
        final Optional<Tag> deletedTag = dao.get(ID_1);
        assertFalse(deletedTag.isPresent());
    }

}
