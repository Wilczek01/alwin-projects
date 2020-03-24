package com.codersteam.alwin.core.config

import spock.lang.Specification

import javax.enterprise.inject.spi.Annotated
import javax.enterprise.inject.spi.InjectionPoint

import static com.codersteam.alwin.testdata.EmailTestData.EMAIL_FROM

/**
 * @author Tomasz Sliwinski
 */
class PropertyProducerTest extends Specification {

    private PropertyProducer producer

    def "setup"() {
        producer = new PropertyProducer()
    }

    def "should load configuration for producer"() {
        when:
            producer.init()
        then:
            noExceptionThrown()
    }

    def "should resolve emailFrom from configuration"() {
        given:
            def property = Mock(Property)
            property.value() >> "email.from"
        and:
            def annotated = Mock(Annotated)
            annotated.isAnnotationPresent(Property) >> true
            annotated.getAnnotation(Property) >> property
        and:
            def injectionPoint = Mock(InjectionPoint)
            injectionPoint.getAnnotated() >> annotated
        and:
            producer.init()
        when:
            def string = producer.produceString(injectionPoint)
        then:
            string == EMAIL_FROM
    }
}
