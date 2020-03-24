package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.tag.TagIconDto;
import com.codersteam.alwin.jpa.issue.TagIcon;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class TagIconTestData {

    public static final long ID_1 = 1L;
    public static final long ID_2 = 2L;
    public static final long ID_3 = 3L;

    public static final String NAME_1 = "bookmark";
    public static final String NAME_2 = "contact_support";
    public static final String NAME_3 = "date_range";

    public static final List<TagIcon> ALL_TAG_ICONS = Arrays.asList(testTagIcon1(), testTagIcon2(), testTagIcon3());

    public static TagIcon testTagIcon1() {
        return tagIcon(ID_1, NAME_1);
    }

    public static TagIcon testTagIcon2() {
        return tagIcon(ID_2, NAME_2);
    }

    public static TagIcon testTagIcon3() {
        return tagIcon(ID_3, NAME_3);
    }

    public static TagIconDto testTagIconDto1() {
        return tagIconDto(ID_1, NAME_1);
    }

    public static TagIconDto testTagIconDto2() {
        return tagIconDto(ID_2, NAME_2);
    }

    public static TagIconDto testTagIconDto3() {
        return tagIconDto(ID_3, NAME_3);
    }

    private static TagIcon tagIcon(final Long id, final String name) {
        final TagIcon tagIcon = new TagIcon();
        tagIcon.setId(id);
        tagIcon.setName(name);
        return tagIcon;
    }

    private static TagIconDto tagIconDto(final Long id, final String name) {
        final TagIconDto tagIcon = new TagIconDto();
        tagIcon.setId(id);
        tagIcon.setName(name);
        return tagIcon;
    }

}
