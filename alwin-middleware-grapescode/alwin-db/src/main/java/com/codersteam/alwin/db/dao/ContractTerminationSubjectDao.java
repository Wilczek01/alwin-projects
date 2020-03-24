package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.termination.ContractTerminationSubject;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do przedmiotów leasingu dla wypowiedzeń umów
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class ContractTerminationSubjectDao extends AbstractAuditDao<ContractTerminationSubject> {

    @Override
    public Class<ContractTerminationSubject> getType() {
        return ContractTerminationSubject.class;
    }

}
