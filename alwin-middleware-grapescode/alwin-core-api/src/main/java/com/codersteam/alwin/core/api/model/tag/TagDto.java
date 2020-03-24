package com.codersteam.alwin.core.api.model.tag;

/**
 * Etykieta dla zlecenia
 *
 * @author Michal Horowic
 */
public class TagDto extends TagInputDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDto)) return false;
        if (!super.equals(o)) return false;

        final TagDto tagDto = (TagDto) o;

        return id != null ? id.equals(tagDto.id) : tagDto.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                "} " + super.toString();
    }
}
