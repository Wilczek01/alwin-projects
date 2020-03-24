package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.core.api.model.tag.TagInputDto;
import com.codersteam.alwin.core.api.service.issue.TagService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueTagDao;
import com.codersteam.alwin.db.dao.TagDao;
import com.codersteam.alwin.jpa.issue.IssueTag;
import com.codersteam.alwin.jpa.issue.Tag;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Michal Horowic
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final IssueTagDao issueTagDao;
    private final AlwinMapper mapper;

    @Inject
    public TagServiceImpl(final TagDao tagDao, final IssueTagDao issueTagDao, final AlwinMapper mapper) {
        this.tagDao = tagDao;
        this.issueTagDao = issueTagDao;
        this.mapper = mapper;
    }

    @Override
    public List<TagDto> findAllTags() {
        return mapper.mapAsList(tagDao.allOrderedById(), TagDto.class);
    }

    @Override
    public void updateTag(final long tagId, final TagInputDto tag) {
        final Tag existingTag = mapper.map(tag, Tag.class);
        existingTag.setId(tagId);
        final Optional<Tag> optionalTag = tagDao.get(tagId);
        optionalTag.ifPresent(t -> existingTag.setType(t.getType()));
        tagDao.update(existingTag);
    }

    @Override
    public void createTag(final TagInputDto tag) {
        final Tag newTag = mapper.map(tag, Tag.class);
        tagDao.create(newTag);
    }

    @Override
    public void deleteTag(final long tagId) {
        tagDao.delete(tagId);
    }

    @Override
    public boolean checkIfTagExists(final long tagId) {
        return tagDao.exists(tagId);
    }

    @Override
    public Set<Long> findExistingTagsIds(final Set<Long> tagsIds) {
        if (isEmpty(tagsIds)) {
            return Collections.emptySet();
        }
        return tagDao.findExistingTagsIds(tagsIds);
    }

    @Override
    public boolean isDeletable(final long id) {
        return tagDao.isDeletable(id);
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void tagOverdueIssue(final Long issueId) {
        final Long overdueTagId = tagDao.findOverdueTag().getId();
        issueTagDao.create(new IssueTag(issueId, overdueTagId));
    }
}
