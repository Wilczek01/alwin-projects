package com.codersteam.alwin.core.service.mapper;

import com.codersteam.aida.core.api.model.CompanySegment;
import com.codersteam.alwin.common.issue.Segment;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author Piotr Naroznik
 */
public class SegmentConverter extends CustomConverter<CompanySegment, Segment> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Segment convert(final CompanySegment aidaSegment, final Type<? extends Segment> type, final MappingContext mappingContext) {
        switch (aidaSegment) {
            case A:
                return Segment.A;
            case B:
                return Segment.B;
            default:
                return handleUnsupportedValue(aidaSegment);
        }
    }

    protected Segment handleUnsupportedValue(final CompanySegment aidaSegment) {
        final String message = String.format("company segment from aida has unsupported value = %s", aidaSegment);
        LOG.error(message, aidaSegment);
        throw new IllegalArgumentException(message);
    }
}
