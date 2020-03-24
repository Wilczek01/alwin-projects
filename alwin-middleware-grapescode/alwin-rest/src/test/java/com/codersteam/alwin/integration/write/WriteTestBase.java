package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.integration.common.HttpRestTestBase;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.TestExecutionPhase;

@CleanupUsingScript(value = "cleanup.sql", phase = TestExecutionPhase.BEFORE)
public abstract class WriteTestBase extends HttpRestTestBase {
}