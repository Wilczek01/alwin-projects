package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration;
import com.codersteam.alwin.jpa.operator.OperatorType;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Klasa dostępu do konfiguracji zlecenia
 *
 * @author Piotr Naroznik
 */
@Stateless
public class IssueTypeConfigurationDao extends AbstractAuditDao<IssueTypeConfiguration> {

    /**
     * Pobieranie ile dni ma trwać zlecenie windykacyjne na podstawie typu zlecenia i segmentu klienta
     *
     * @param issueTypeName - typ zlecenia windykacyjnego
     * @param segment       - segment klienta
     * @return ile dni ma trwać zlecenie windykacyjne
     */
    public Integer findDurationByTypeAndSegment(final IssueTypeName issueTypeName, final Segment segment) {
        final String sql = "SELECT i.duration FROM IssueTypeConfiguration i WHERE i.issueType.name =:issueTypeName and i.segment =:segment";
        final TypedQuery<Integer> query = entityManager.createQuery(sql, Integer.class);
        query.setParameter("issueTypeName", issueTypeName);
        query.setParameter("segment", segment);
        return query.getSingleResult();
    }

    /**
     * Pobieranie dpd_start - zlecenie windykacyjne na podstawie typu zlecenia i segmentu klienta
     *
     * @param issueTypeName - typ zlecenia windykacyjnego
     * @param segment       - segment klienta
     * @return ile dni ma trwać zlecenie windykacyjne
     */
    public Integer findDpdStartByTypeAndSegment(final IssueTypeName issueTypeName, final Segment segment) {
        final String sql = "SELECT i.dpdStart FROM IssueTypeConfiguration i WHERE i.issueType.name =:issueTypeName and i.segment =:segment";
        final TypedQuery<Integer> query = entityManager.createQuery(sql, Integer.class);
        query.setParameter("issueTypeName", issueTypeName);
        query.setParameter("segment", segment);
        return query.getSingleResult();
    }

    /**
     * Pobieranie konfiguracji typu zlecenia
     *
     * @param issueTypeName - typ zlecenia windykacyjnego
     * @param segment       - segment klienta
     * @return konfiguracja typu zlecenia
     */
    public Optional<IssueTypeConfiguration> findIssueTypeConfigurationByTypeAndSegment(final IssueTypeName issueTypeName, final Segment segment) {
        final String sql = "SELECT i FROM IssueTypeConfiguration i WHERE i.issueType.name =:issueTypeName and i.segment =:segment";
        final List<IssueTypeConfiguration> resultList = entityManager.createQuery(sql, IssueTypeConfiguration.class)
                .setParameter("issueTypeName", issueTypeName)
                .setParameter("segment", segment)
                .getResultList();
        return checkForSingleResult(resultList);
    }

    /**
     * Pobranie informacji czy dla danego typu zlecenia i segmentu klienta należy dołączać dłużne dokumenty jako przedmiot zlecenia w trakcie jego trwania
     *
     * @param issueTypeName - typ zlecenia windykacyjnego
     * @param segment       - segment klienta
     * @return <code>true</code> jeśli należy dołączać zaległe dokumenty w trakcie trwania zlecenia jako przedmiot zlecenia
     */
    public Boolean findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(final IssueTypeName issueTypeName, final Segment segment) {
        final String sql = "SELECT itc.includeDebtInvoicesDuringIssue FROM IssueTypeConfiguration itc " +
                "WHERE itc.issueType.name =:issueTypeName and itc.segment =:segment";
        final TypedQuery<Boolean> query = entityManager.createQuery(sql, Boolean.class);
        query.setParameter("issueTypeName", issueTypeName);
        query.setParameter("segment", segment);
        return query.getSingleResult();
    }

    /**
     * Pobiera wszystkie konfiguracje zlecenia windykacyjnego
     *
     * @return lista konfiguracji zlecenia windykacyjnego
     */
    public List<IssueTypeConfiguration> findAllIssueTypeConfiguration() {
        final String sql = "SELECT itc FROM IssueTypeConfiguration itc order by itc.id";
        return entityManager.createQuery(sql, IssueTypeConfiguration.class).getResultList();
    }

    /**
     * Uaktualnia konfiguracje zlecenia windykacyjnego
     * Pobiera referencje zależnych encji, nie modyfikuje ich zawartości
     *
     * @param configuration    - konfiguracje zlecenia windykacyjnego do uaktualnienia
     * @param issueTypeId      - identyfikator typu zlecenia
     * @param operatorTypesIds - identyfikatory typów operatorów do przypisania do zlecenia
     */
    public void update(final IssueTypeConfiguration configuration, final Long issueTypeId, final Set<Long> operatorTypesIds) {
        final IssueType referenceIssueType = entityManager.getReference(IssueType.class, issueTypeId);
        configuration.setIssueType(referenceIssueType);
        referenceIssueType.getOperatorTypes().clear();
        if (isEmpty(operatorTypesIds)) {
            update(configuration);
            return;
        }

        final Set<OperatorType> referenceOperatorTypes = new HashSet<>(operatorTypesIds.size());
        operatorTypesIds.forEach(operatorTypeId -> referenceOperatorTypes.add(entityManager.getReference(OperatorType.class, operatorTypeId)));
        referenceIssueType.getOperatorTypes().addAll(referenceOperatorTypes);
        update(configuration);
    }

    /**
     * Pobranie wszystkich typów konfiguracji zleceń do tworzenia automatycznego
     * Zwracana konfiguracja typów posortowana jest po DPD (malejąco)
     *
     * @return lista konfiguracji zleceń windykacyjnych tworzonych automatycznie
     */
    public List<IssueTypeConfiguration> findAllCreateAutomaticallyIssueTypeConfigurations() {
        final String sql = "SELECT itc FROM IssueTypeConfiguration itc WHERE itc.createAutomatically = true ORDER BY itc.dpdStart desc,  itc.id";
        return entityManager.createQuery(sql, IssueTypeConfiguration.class).getResultList();
    }

    @Override
    public Class<IssueTypeConfiguration> getType() {
        return IssueTypeConfiguration.class;
    }
}
