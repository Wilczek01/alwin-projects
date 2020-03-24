package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.DaoTestBase;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.TestExecutionPhase;

@CleanupUsingScript(value = "cleanup.sql", phase = TestExecutionPhase.BEFORE)
public abstract class WriteTestBase extends DaoTestBase {
}