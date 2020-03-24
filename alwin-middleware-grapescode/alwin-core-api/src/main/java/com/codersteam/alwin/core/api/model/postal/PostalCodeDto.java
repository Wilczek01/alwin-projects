package com.codersteam.alwin.core.api.model.postal;

/**
 * @author Michal Horowic
 */
public class PostalCodeDto extends PostalCodeInputDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
