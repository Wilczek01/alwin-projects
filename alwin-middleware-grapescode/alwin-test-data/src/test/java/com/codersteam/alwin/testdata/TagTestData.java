package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.customer.TagTypeDto;
import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.core.api.model.tag.TagIconDto;
import com.codersteam.alwin.core.api.model.tag.TagInputDto;
import com.codersteam.alwin.jpa.issue.Tag;
import com.codersteam.alwin.jpa.issue.TagIcon;
import com.codersteam.alwin.jpa.type.TagType;

import static com.codersteam.alwin.testdata.TagIconTestData.testTagIcon1;
import static com.codersteam.alwin.testdata.TagIconTestData.testTagIcon2;
import static com.codersteam.alwin.testdata.TagIconTestData.testTagIcon3;
import static com.codersteam.alwin.testdata.TagIconTestData.testTagIconDto1;
import static com.codersteam.alwin.testdata.TagIconTestData.testTagIconDto2;
import static com.codersteam.alwin.testdata.TagIconTestData.testTagIconDto3;

@SuppressWarnings("WeakerAccess")
public class TagTestData {

    public static final long ID_1 = 1L;
    public static final long ID_2 = 2L;
    public static final long ID_3 = 3L;
    public static final long ADDED_ID = 100L;
    public static final long NOT_EXISTING_ID = -1L;

    public static final String NAME_1 = "Koszyk 1";
    public static final String NAME_2 = "Koszyk 2";
    public static final String NAME_3 = "Zaległe";
    public static final String ADDED_NAME = "Koszyk 3";
    public static final String MODIFIED_NAME = "Koszyk 4";

    public static final String COLOR_1 = "#333333";
    public static final String COLOR_2 = "#999999";
    public static final String COLOR_3 = "#E53935";
    public static final String ADDED_COLOR = "#123456";

    public static final boolean PREDEFINED_1 = false;
    public static final boolean PREDEFINED_2 = false;
    public static final boolean PREDEFINED_3 = true;
    public static final boolean ADDED_PREDEFINED = false;

    public static final TagType TYPE_1 = TagType.CUSTOM;
    public static final TagType TYPE_2 = TagType.CUSTOM;
    public static final TagType TYPE_3 = TagType.OVERDUE;
    public static final TagType ADDED_TYPE = TagType.CUSTOM;

    public static final TagTypeDto TYPE_DTO_1 = TagTypeDto.CUSTOM;
    public static final TagTypeDto TYPE_DTO_2 = TagTypeDto.CUSTOM;
    public static final TagTypeDto TYPE_DTO_3 = TagTypeDto.OVERDUE;
    public static final TagTypeDto ADDED_TYPE_DTO = TagTypeDto.CUSTOM;

    public static final String DESCRIPTION_1 = "Zlecenie koszyka 1";
    public static final String DESCRIPTION_2 = "Zlecenie koszyka 2";
    public static final String DESCRIPTION_3 = "Zaległe zlecenie";
    public static final String ADDED_DESCRIPTION = "Zlecenie koszyka 3";
    public static final String MODIFIED_DESCRIPTION = "Zlecenie koszyka 4";


    public static Tag testTag1() {
        return tag(ID_1, NAME_1, COLOR_1, PREDEFINED_1, TYPE_1, DESCRIPTION_1, testTagIcon1());
    }

    public static Tag testTag2() {
        return tag(ID_2, NAME_2, COLOR_2, PREDEFINED_2, TYPE_2, DESCRIPTION_2, testTagIcon1());
    }

    public static Tag testTag3() {
        return tag(ID_3, NAME_3, COLOR_3, PREDEFINED_3, TYPE_3, DESCRIPTION_3, testTagIcon2());
    }

    public static Tag testAddedTag1() {
        return tag(ADDED_ID, ADDED_NAME, ADDED_COLOR, ADDED_PREDEFINED, ADDED_TYPE, ADDED_DESCRIPTION, testTagIcon1());
    }

    public static Tag testTagToAdd() {
        return tag(null, ADDED_NAME, ADDED_COLOR, ADDED_PREDEFINED, ADDED_TYPE, ADDED_DESCRIPTION, testTagIcon1());
    }

    public static Tag testModifiedTag1() {
        return tag(ID_1, MODIFIED_NAME, COLOR_1, PREDEFINED_1, TYPE_1, MODIFIED_DESCRIPTION, testTagIcon3());
    }

    public static TagDto testTagDto1() {
        return tagDto(ID_1, NAME_1, COLOR_1, PREDEFINED_1, TYPE_DTO_1, DESCRIPTION_1, testTagIconDto1());
    }

    public static TagDto testTagDto2() {
        return tagDto(ID_2, NAME_2, COLOR_2, PREDEFINED_2, TYPE_DTO_2, DESCRIPTION_2, testTagIconDto1());
    }

    public static TagDto testTagDto3() {
        return tagDto(ID_3, NAME_3, COLOR_3, PREDEFINED_3, TYPE_DTO_3, DESCRIPTION_3, testTagIconDto2());
    }

    public static TagInputDto testTagDtoToAdd() {
        return tagInputDto(ADDED_NAME, ADDED_COLOR, ADDED_PREDEFINED, ADDED_TYPE_DTO, ADDED_DESCRIPTION, testTagIconDto1());
    }

    public static TagInputDto testTagDtoToModifiy1() {
        return tagInputDto(MODIFIED_NAME, COLOR_1, PREDEFINED_1, TYPE_DTO_1, MODIFIED_DESCRIPTION, testTagIconDto3());
    }

    private static Tag tag(final Long id, final String name, final String color, final boolean predefined, final TagType type, final String description,
                           final TagIcon tagIcon) {
        final Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        tag.setColor(color);
        tag.setPredefined(predefined);
        tag.setType(type);
        tag.setDescription(description);
        tag.setTagIcon(tagIcon);
        return tag;
    }

    private static TagInputDto tagInputDto(final String name, final String color, final boolean predefined, final TagTypeDto type, final String description,
                                           final TagIconDto tagIcon) {
        return tagDto(null, name, color, predefined, type, description, tagIcon);
    }

    private static TagDto tagDto(final Long id, final String name, final String color, final boolean predefined, final TagTypeDto type, final String description,
                                 final TagIconDto tagIcon) {
        final TagDto tag = new TagDto();
        tag.setId(id);
        tag.setName(name);
        tag.setColor(color);
        tag.setPredefined(predefined);
        tag.setType(type);
        tag.setDescription(description);
        tag.setTagIcon(tagIcon);
        return tag;
    }

}
