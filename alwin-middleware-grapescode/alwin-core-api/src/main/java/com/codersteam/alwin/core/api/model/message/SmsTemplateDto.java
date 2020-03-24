package com.codersteam.alwin.core.api.model.message;

/**
 * @author Piotr Naroznik
 */
public class SmsTemplateDto {

    private Long id;
    private String name;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
