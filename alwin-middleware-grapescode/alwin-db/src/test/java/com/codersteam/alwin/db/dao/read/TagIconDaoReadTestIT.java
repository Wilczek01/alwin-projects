package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.TagIconDao;
import com.codersteam.alwin.jpa.issue.TagIcon;
import com.codersteam.alwin.testdata.TagIconTestData;
import org.junit.Assert;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author Dariusz Rackowski
 */
public class TagIconDaoReadTestIT extends ReadTestBase {

    @EJB
    private TagIconDao dao;

    public TagIconDaoReadTestIT() {
    }

    @Test
    public void shouldFindTagIconById() {
        // when
        final Optional<TagIcon> tagIconOptional = dao.get(TagIconTestData.ID_1);

        // then
        Assert.assertTrue(tagIconOptional.isPresent());
        final TagIcon tagIcon = tagIconOptional.get();
        assertThat(tagIcon.getId()).isEqualTo(TagIconTestData.ID_1);
        assertThat(tagIcon.getName()).isEqualTo(TagIconTestData.NAME_1);
    }

    @Test
    public void shouldReturnAllSortedByIdsTagIcons() {
        // when
        final List<TagIcon> tagIcons = dao.allOrderedById();

        // then
        assertThat(tagIcons).hasSize(TagIconTestData.ALL_TAG_ICONS.size());
        assertThat(tagIcons).isSortedAccordingTo(Comparator.comparing(TagIcon::getId));
    }

}
