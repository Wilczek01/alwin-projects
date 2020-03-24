package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.issue.IssueStateDto
import com.codersteam.alwin.jpa.type.IssueState
import spock.lang.Specification

class IssueStateDtoConverterTest extends Specification {

    def "should convert issue state to issue state dto"() {
        given:
            def converter = new IssueStateDtoConverter()
        expect:
            converter.convert(issueState, null, null) == issueStateDto
        where:
            issueState                         | issueStateDto
            IssueState.NEW                     | IssueStateDto.NEW
            IssueState.IN_PROGRESS             | IssueStateDto.IN_PROGRESS
            IssueState.WAITING_FOR_TERMINATION | IssueStateDto.WAITING_FOR_TERMINATION
            IssueState.DONE                    | IssueStateDto.DONE
            IssueState.CANCELED                | IssueStateDto.CANCELED
    }
}