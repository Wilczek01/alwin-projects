package com.codersteam.alwin.db.dao.listener;

import com.codersteam.alwin.common.RequestOperator;
import com.codersteam.alwin.jpa.UserRevEntity;
import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.inject.spi.CDI;
import java.lang.invoke.MethodHandles;

/**
 * Listener dodający informację o zalogowanym operatorze w audytowanych danych
 *
 * @author Piotr Naroznik
 */
public class OperatorRevisionListener implements RevisionListener {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void newRevision(final Object revisionEntity) {
        final UserRevEntity revision = (UserRevEntity) revisionEntity;
        revision.setOperator(getOperatorId());
    }

    private Long getOperatorId() {
        try {
            return CDI.current().select(RequestOperator.class).get().getOperatorId();
        } catch (final ContextNotActiveException e) {
            LOG.info("Brak aktywnego kontekstu dla javax.enterprise.context.RequestScoped");
            return null;
        }
    }

}