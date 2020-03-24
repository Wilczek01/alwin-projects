package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do faktur w procesie windykacji formalnej
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class FormalDebtCollectionInvoiceDao extends AbstractAuditDao<FormalDebtCollectionInvoice> {

    @Override
    public Class<FormalDebtCollectionInvoice> getType() {
        return FormalDebtCollectionInvoice.class;
    }

}
