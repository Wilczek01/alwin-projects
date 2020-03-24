package com.codersteam.alwin.core.util

import com.codersteam.alwin.db.util.AuditQueryUtils
import com.codersteam.alwin.jpa.customer.Person
import org.hibernate.envers.RevisionType
import org.hibernate.envers.query.AuditQuery
import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.AuditLogEntryTestData.firstPersonAuditLogEntry
import static com.codersteam.alwin.testdata.PersonTestData.person1
import static com.codersteam.alwin.testdata.UserRevEntityTestData.userRevEntity1
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class AuditQueryUtilsTest extends Specification {

    private AuditQuery query = Mock(AuditQuery)

    def "should have private default constructor"() {
        when:
            def constructor = AuditQueryUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should transform AuditQuery results into audit log entries omitting nulls"() {
        given:
            Object[] firstEntry = [person1(), userRevEntity1(), RevisionType.ADD]
            Object[] nullEntry = null
            query.getResultList() >> [firstEntry, nullEntry]
        when:
            def auditLogEntries = AuditQueryUtils.getAuditQueryResults(query, Person.class)
        then:
            auditLogEntries.size() == 1
            assertThat(auditLogEntries[0]).isEqualToComparingFieldByFieldRecursively(firstPersonAuditLogEntry())
    }
}