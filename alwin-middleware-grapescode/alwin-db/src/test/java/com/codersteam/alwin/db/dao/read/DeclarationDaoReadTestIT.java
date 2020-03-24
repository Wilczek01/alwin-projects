package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.DeclarationDao;
import com.codersteam.alwin.jpa.activity.Declaration;
import org.junit.Test;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
public class DeclarationDaoReadTestIT extends ReadTestBase {

    @EJB
    private DeclarationDao declarationDao;

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Declaration> type = declarationDao.getType();

        // then
        assertEquals(Declaration.class, type);
    }
}