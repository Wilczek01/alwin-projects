package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.db.dao.DemandForPaymentTypeConfigurationDao;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.allDemandForPaymentTypes;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

public class DemandForPaymentTypeConfigurationDaoTestIT extends ReadTestBase {

    @Inject
    private DemandForPaymentTypeConfigurationDao dao;

    @Test
    public void shouldFindAllDemandForPaymentTypes() {
        // when
        final List<DemandForPaymentTypeConfiguration> allDemandForPaymentTypes = dao.findAllDemandForPaymentTypes();

        // then
        assertThat(allDemandForPaymentTypes).isEqualToComparingFieldByFieldRecursively(allDemandForPaymentTypes());
    }

    @Test
    public void shouldFindDemandForPaymentTypeConfigurationByKeyAndSegment() {
        // when
        final DemandForPaymentTypeConfiguration demandForPaymentTypeConfiguration
                = dao.findDemandForPaymentTypeConfigurationByKeyAndSegment(DemandForPaymentTypeKey.FIRST, Segment.A);

        // then
        assertThat(demandForPaymentTypeConfiguration).isEqualToComparingFieldByFieldRecursively(demandForPaymentTypeConfigurationFirstSegmentA());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<DemandForPaymentTypeConfiguration> type = dao.getType();

        // then
        assertEquals(DemandForPaymentTypeConfiguration.class, type);
    }
}
