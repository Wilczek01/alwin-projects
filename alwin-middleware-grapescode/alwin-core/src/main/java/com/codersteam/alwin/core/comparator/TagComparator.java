package com.codersteam.alwin.core.comparator;

import com.codersteam.alwin.core.api.model.tag.TagDto;

import java.util.Comparator;

/**
 * Klasa do sortowania etykiet
 *
 * @author Dariusz Rackowski
 */
public class TagComparator implements Comparator<TagDto> {

    @Override
    public int compare(final TagDto tag1, final TagDto tag2) {
        return Long.compare(tag1.getId(), tag2.getId());
    }
}
