package com.codersteam.alwin.core.service.impl.message

import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.core.service.impl.message.PolishCharactersUtil.replacePolishCharacters

/**
 * @author Piotr Naroznik
 */
class PolishCharactersUtilTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = PolishCharactersUtil.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should replace Polish characters in text"() {
        given:
            def text = 'ą,ę,ś,ć,ż,ź,ł,ó,ń,Ą,Ę,Ś,Ć,Ż,Ź,Ł,Ó,Ń'
        when:
            def textWithoutPolishCharacters = replacePolishCharacters(text)
        then:
            textWithoutPolishCharacters == 'a,e,s,c,z,z,l,o,n,A,E,S,C,Z,Z,L,O,N'
    }
}