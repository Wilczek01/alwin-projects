package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.core.api.model.tag.TagDto;

/**
 * Portfele zleceń liczone dla zleceń przypisanych do etykiety.
 * Portfele są liczone z otwartych zleceń windykacyjnych.
 *
 * @author Piotr Naroznik
 */
public class TagWalletDto extends WalletDto {

    /**
     * Etykieta dla której liczony jest portfel
     */
    private TagDto tag;

    public TagDto getTag() {
        return tag;
    }

    public void setTag(final TagDto tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final TagWalletDto that = (TagWalletDto) o;

        return getTag() != null ? getTag().equals(that.getTag()) : that.getTag() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTag() != null ? getTag().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagWalletDto{" +
                "tag=" + tag +
                "} " + super.toString();
    }
}
