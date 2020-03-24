package com.codersteam.alwin.rest

import com.codersteam.alwin.core.api.service.auth.JwtService
import com.codersteam.alwin.core.api.service.notification.NotificationService
import com.codersteam.alwin.core.api.service.operator.OperatorService
import com.codersteam.alwin.validator.NotificationValidator
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.NotificationTestData.*
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_4
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto4
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class NotificationResourceImplTest extends Specification {

    @Subject
    private NotificationResource service
    private OperatorService operatorService = Mock(OperatorService)
    private NotificationService notificationService = Mock(NotificationService)
    private JwtService jwtService = Mock(JwtService)
    private NotificationValidator notificationValidator = Mock(NotificationValidator)


    def setup() {
        service = new NotificationResource(notificationService, jwtService, notificationValidator, operatorService)
    }

    def "should have default public constructor"() {
        when:
            def resource = new NotificationResource()

        then:
            resource
    }

    def "should set notification fields"() {
        when:
            operatorService.findOperatorById(OPERATOR_ID_4) >> testOperatorDto4()

        and:
            def notificationDto = service.setNotificationFields(OPERATOR_ID_4, ISSUE_ID_1, MESSAGE_1, READ_CONFIRMATION_REQUIRED_1)

        then:
            assertThat(notificationDto).isEqualToComparingFieldByField(notificationFieldsSetupDto())
    }
}