package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.activity.Declaration;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do deklaracji spłat
 *
 * @author Piotr Naroznik
 */
@Stateless
public class DeclarationDao extends AbstractAuditDao<Declaration> {

    @Override
    public Class<Declaration> getType() {
        return Declaration.class;
    }
}