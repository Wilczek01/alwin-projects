package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.issue.Tag;
import com.codersteam.alwin.jpa.type.TagType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa dostępu do etykiet
 *
 * @author Michal Horowic
 */
@Stateless
public class TagDao extends AbstractDao<Tag> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private IssueTagDao issueTagDao;

    public TagDao() {
    }

    @Inject
    public TagDao(IssueTagDao issueTagDao) {
        this.issueTagDao = issueTagDao;
    }

    @Override
    public Class<Tag> getType() {
        return Tag.class;
    }

    /**
     * Zwraca listę istniejących identyfikatorów etykiet z podanych
     *
     * @param tagsIds - podane identyfikatory etykiet
     * @return lista istniejących identyfikatorów etykiet
     */
    public Set<Long> findExistingTagsIds(final Set<Long> tagsIds) {
        final TypedQuery<Long> query = entityManager.createQuery("select t.id from Tag t where t.id in (:tagsIds)", Long.class);
        query.setParameter("tagsIds", tagsIds);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public void delete(final Object id) {
        issueTagDao.deleteAllAssignments(Long.parseLong(id.toString()));
        super.delete(id);
    }

    /**
     * Sprawdza czy etykietę można usunąć
     *
     * @param id - identyfikator etykiety
     * @return true jeżeli etykieta nie jest predefiniowana, false w przeciwnym wypadku
     */
    public boolean isDeletable(final long id) {
        try {
            final TypedQuery<Boolean> query = entityManager.createQuery("select t.predefined from Tag t where t.id = :id", Boolean.class);
            query.setParameter("id", id);
            final Boolean predefined = query.getSingleResult();
            return predefined != null && !predefined;
        } catch (final NoResultException e) {
            LOG.warn("Trying to check if not existing tag is predefined");
            return false;
        }
    }

    /**
     * Znajduje etykietę "Zaległe"
     *
     * @return etykieta "Zaległe"
     */
    public Tag findOverdueTag() {
        return entityManager.createQuery("select t from Tag t where t.type = :tagType", Tag.class)
                .setParameter("tagType", TagType.OVERDUE)
                .getSingleResult();
    }
}
