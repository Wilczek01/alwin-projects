package com.codersteam.alwin.core.service.mapper;

import ma.glasnost.orika.MappingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Używany do mapowania encji faktur do dto
 *
 * @author Piotr Naroznik
 */
public class IssueMappingContext extends MappingContext {

    public static final String ISSUE_ID = "issueId";

    /**
     * Tworzenie kontekstu dla mappera z identyfikatorem zlecenia
     *
     * @param issueId - identyfikator zlecenia
     */
    public IssueMappingContext(final Long issueId) {
        super(prepareMap(issueId));
    }

    private static Map<Object, Object> prepareMap(final Long issueId) {
        final Map<Object, Object> globalProperties = new HashMap<>(1);
        globalProperties.put(ISSUE_ID, issueId);
        return globalProperties;
    }
}