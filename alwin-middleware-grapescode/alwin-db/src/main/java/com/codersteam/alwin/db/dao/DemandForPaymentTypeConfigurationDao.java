package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa dostępu do konfiguracji typów wezwań do zapłaty
 *
 * @author Tomasz Sliwinski
 */
@Stateless
public class DemandForPaymentTypeConfigurationDao extends AbstractDao<DemandForPaymentTypeConfiguration> {

    /**
     * Pobiera konfiguracje wezwań do zapłaty wszystkich typów
     *
     * @return lista wszystkich typów wezwań do zapłaty
     */
    public List<DemandForPaymentTypeConfiguration> findAllDemandForPaymentTypes() {
        final String sql = "SELECT dfptc FROM DemandForPaymentTypeConfiguration dfptc order by dfptc.id";
        return entityManager.createQuery(sql, getType()).getResultList();
    }

    /**
     * Pobranie konfiguracji typu wezwania po kluczu i segmencie
     *
     * @param typeKey - klucz typu wezwania
     * @param segment - segment klienta
     * @return typ wezwania do zapłaty
     */
    public DemandForPaymentTypeConfiguration findDemandForPaymentTypeConfigurationByKeyAndSegment(final DemandForPaymentTypeKey typeKey, final Segment segment) {
        final TypedQuery<DemandForPaymentTypeConfiguration> query = entityManager.createQuery("SELECT dfptc FROM DemandForPaymentTypeConfiguration dfptc " +
                "WHERE dfptc.key = :typeKey and dfptc.segment = :companySegment", DemandForPaymentTypeConfiguration.class);
        query.setParameter("typeKey", typeKey);
        query.setParameter("companySegment", segment);
        return query.getSingleResult();
    }

    @Override
    public Class<DemandForPaymentTypeConfiguration> getType() {
        return DemandForPaymentTypeConfiguration.class;
    }
}
