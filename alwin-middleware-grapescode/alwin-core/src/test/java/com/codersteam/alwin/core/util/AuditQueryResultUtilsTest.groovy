package com.codersteam.alwin.core.util

import com.codersteam.alwin.db.util.AuditQueryResultUtils
import com.codersteam.alwin.jpa.customer.Customer
import com.codersteam.alwin.jpa.customer.Person
import org.hibernate.envers.RevisionType
import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.AuditLogEntryTestData.firstPersonAuditLogEntry
import static com.codersteam.alwin.testdata.PersonTestData.person1
import static com.codersteam.alwin.testdata.UserRevEntityTestData.userRevEntity1
import static org.assertj.core.api.Java6Assertions.assertThat

/**
 * @author Tomasz Sliwinski
 */
class AuditQueryResultUtilsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = AuditQueryResultUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should return null if AuditQuery returned null response"() {
        given:
            def logEntryItem = null
        when:
            def auditLogEntry = AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            auditLogEntry == null
    }

    def "should return null if AuditQuery returned response without revision type"() {
        given:
            Object[] logEntryItem = [person1(), userRevEntity1()]
        when:
            def auditLogEntry = AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            auditLogEntry == null
    }

    def "should return audit log entry"() {
        given:
            Object[] logEntryItem = [person1(), userRevEntity1(), RevisionType.ADD]
        when:
            def auditLogEntry = AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            assertThat(auditLogEntry).isEqualToComparingFieldByFieldRecursively(firstPersonAuditLogEntry())
    }

    def "should throw an exception when AuditQuery returned entity of unexpected class"() {
        given:
            Object[] logEntryItem = [person1(), userRevEntity1(), RevisionType.ADD]
        when:
            AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Customer.class)
        then:
            def e = thrown(IllegalArgumentException)
            e.message == "Expected class com.codersteam.alwin.jpa.customer.Customer entity, but got: class com.codersteam.alwin.jpa.customer.Person"
    }

    def "should throw an exception when AuditQuery returned null entity"() {
        given:
            Object[] logEntryItem = [null, userRevEntity1(), RevisionType.ADD]
        when:
            AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            def e = thrown(IllegalArgumentException)
            e.message == "Expected class com.codersteam.alwin.jpa.customer.Person entity, but got: null"
    }

    def "should throw an exception when AuditQuery returned unexpected object as revision type"() {
        given:
            Object[] logEntryItem = [person1(), userRevEntity1(), "some unexpected string"]
        when:
            AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            def e = thrown(IllegalArgumentException)
            e.message == "Expected RevisionType, but got: class java.lang.String"
    }

    def "should throw an exception when AuditQuery returned null revision type"() {
        given:
            Object[] logEntryItem = [person1(), userRevEntity1(), null]
        when:
            AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            def e = thrown(IllegalArgumentException)
            e.message == "Expected RevisionType, but got: null"
    }

    def "should throw an exception when AuditQuery returned unexpected object as user revision entity"() {
        given:
            Object[] logEntryItem = [person1(), "some unexpected string", RevisionType.ADD]
        when:
            AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            def e = thrown(IllegalArgumentException)
            e.message == "Expected UserRevEntity, but got: class java.lang.String"
    }

    def "should throw an exception when AuditQuery returned null user revision entity"() {
        given:
            Object[] logEntryItem = [person1(), null, RevisionType.ADD]
        when:
            AuditQueryResultUtils.getAuditQueryResult(logEntryItem, Person.class)
        then:
            def e = thrown(IllegalArgumentException)
            e.message == "Expected UserRevEntity, but got: null"
    }
}