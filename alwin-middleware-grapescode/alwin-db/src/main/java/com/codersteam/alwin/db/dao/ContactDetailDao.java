package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.ContactDetail;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do kontaktów
 *
 * @author Piotr Naroznik
 */
@Stateless
public class ContactDetailDao extends AbstractAuditDao<ContactDetail> {

    @Override
    public Class<ContactDetail> getType() {
        return ContactDetail.class;
    }
}