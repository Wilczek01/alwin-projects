package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.activity.DefaultIssueActivity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO do pobierania domyślnych czynności windykacyjnych dla zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Stateless
public class DefaultIssueActivityDao extends AbstractDao<DefaultIssueActivity> {

    public List<DefaultIssueActivity> findDefaultActivities(final Long issueTypeId) {
        final TypedQuery<DefaultIssueActivity> query = entityManager.createQuery("SELECT a FROM DefaultIssueActivity a WHERE a.version = " +
                "(SELECT max(a2.version) FROM DefaultIssueActivity a2 WHERE a.activityType.id = a2.activityType.id and a2.issueType.id = a.issueType.id) " +
                "AND a.issueType.id = :issueTypeId " +
                "ORDER BY a.defaultDay", DefaultIssueActivity.class);
        query.setParameter("issueTypeId", issueTypeId);
        return query.getResultList();
    }

    public List<DefaultIssueActivity> findAllDefaultActivities() {
        final TypedQuery<DefaultIssueActivity> query = entityManager.createQuery("SELECT a FROM DefaultIssueActivity a WHERE a.version = " +
                "(SELECT max(a2.version) FROM DefaultIssueActivity a2 WHERE a.activityType.id = a2.activityType.id and a2.issueType.id = a.issueType.id) " +
                "ORDER BY a.defaultDay", DefaultIssueActivity.class);
        return query.getResultList();
    }

    @Override
    public Class<DefaultIssueActivity> getType() {
        return DefaultIssueActivity.class;
    }

    public DefaultIssueActivity findDefaultIssueActivity(final Long defaultIssueActivityId) {
        final DefaultIssueActivity newDefaultIssueActivity = entityManager.find(DefaultIssueActivity.class, defaultIssueActivityId);
        entityManager.detach(newDefaultIssueActivity);
        return newDefaultIssueActivity;
    }

}
