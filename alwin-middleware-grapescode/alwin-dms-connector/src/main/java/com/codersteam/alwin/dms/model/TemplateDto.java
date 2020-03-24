package com.codersteam.alwin.dms.model;

/**
 * @author Piotr Naroznik
 */
public class TemplateDto {

    private String description;
    private String htmlUrl;
    private String id;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(final String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TemplateDto{" +
                "description='" + description + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}