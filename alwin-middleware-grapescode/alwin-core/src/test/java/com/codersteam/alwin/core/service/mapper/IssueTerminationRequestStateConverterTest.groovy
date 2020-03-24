package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestStateDto
import com.codersteam.alwin.jpa.type.IssueTerminationRequestState
import spock.lang.Specification
import spock.lang.Subject

class IssueTerminationRequestStateConverterTest extends Specification {

    @Subject
    def converter = new IssueTerminationRequestStateConverter();

    def "should convert IssueTerminationRequestStateDto to IssueTerminationRequestState"() {
        expect:
            converter.convert(stateDto, null, null) == state
        where:
            stateDto                                 | state
            IssueTerminationRequestStateDto.NEW      | IssueTerminationRequestState.NEW
            IssueTerminationRequestStateDto.ACCEPTED | IssueTerminationRequestState.ACCEPTED
            IssueTerminationRequestStateDto.REJECTED | IssueTerminationRequestState.REJECTED
    }
}
