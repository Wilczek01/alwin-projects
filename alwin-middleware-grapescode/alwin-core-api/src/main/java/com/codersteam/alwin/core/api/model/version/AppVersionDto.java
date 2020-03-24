package com.codersteam.alwin.core.api.model.version;

/**
 * @author Adam Stepnowski
 */
public class AppVersionDto {

    private final String version;

    public AppVersionDto(final String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}