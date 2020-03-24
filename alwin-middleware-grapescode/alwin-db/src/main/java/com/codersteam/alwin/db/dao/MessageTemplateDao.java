package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.MessageTemplate;
import com.codersteam.alwin.jpa.type.MessageType;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Klasa dostępu do szablonów wysyłanych wiadomości
 *
 * @author Piotr Naroznik
 */
@Stateless
public class MessageTemplateDao extends AbstractDao<MessageTemplate> {

    /**
     * Pobieranie szablonów wiadomości po typie
     *
     * @param type - typ wiadomości
     * @return lista szablonów
     */
    public List<MessageTemplate> findByType(final MessageType type) {
        final String query = "select mt from MessageTemplate mt where mt.type = :type";
        return entityManager.createQuery(query, MessageTemplate.class)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public Class<MessageTemplate> getType() {
        return MessageTemplate.class;
    }
}
