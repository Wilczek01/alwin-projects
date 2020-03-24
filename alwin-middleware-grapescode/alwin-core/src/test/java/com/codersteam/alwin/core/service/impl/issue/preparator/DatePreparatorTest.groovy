package com.codersteam.alwin.core.service.impl.issue.preparator

import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class DatePreparatorTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = DatePreparator.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should set dates on issue"() {
        given:
            def issue = issue1()
            def startDate = parse("2019-01-04")
            def expirationDate = parse("2020-04-03")
            def dpdStartDate = parse("2018-12-30")
        when:
            def issueWithDatesSet = DatePreparator.fillDates(issue, startDate, expirationDate, dpdStartDate)
        then:
            issueWithDatesSet.startDate == startDate
            issueWithDatesSet.expirationDate == expirationDate
            issueWithDatesSet.dpdStartDate == dpdStartDate
    }
}
