package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.Address;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do adresów
 *
 * @author Piotr Naroznik
 */
@Stateless
public class AddressDao extends AbstractAuditDao<Address> {

    @Override
    public Class<Address> getType() {
        return Address.class;
    }
}