package com.codersteam.alwin.jpa;

import com.codersteam.alwin.jpa.type.MessageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Przechowuje szablony wysyłanych wiadomości
 *
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "message_template", uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "name"})})
public class MessageTemplate {

    @SequenceGenerator(name = "messageTemplateSEQ", sequenceName = "message_template_id_seq", allocationSize = 1, initialValue = 100)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageTemplateSEQ")
    private Long id;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private String body;

    @Column(length = 100)
    private String topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
