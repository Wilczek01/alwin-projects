package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueTagDao
import com.codersteam.alwin.db.dao.TagDao
import com.codersteam.alwin.jpa.issue.IssueTag
import com.codersteam.alwin.jpa.issue.Tag
import com.codersteam.alwin.jpa.type.TagType
import com.google.common.collect.Sets
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.TagTestData.*
import static java.util.Arrays.asList
import static java.util.Collections.emptySet
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class TagServiceImplTest extends Specification {

    def tagDao = Mock(TagDao)
    def issueTagDao = Mock(IssueTagDao)

    @Subject
    private TagServiceImpl service

    def setup() {
        service = new TagServiceImpl(tagDao, issueTagDao, new AlwinMapper())
    }

    def "should find all tags"() {
        given:
            tagDao.allOrderedById() >> [testTag1(), testTag2()]

        when:
            def tags = service.findAllTags()

        then:
            assertThat(tags).isEqualToComparingFieldByFieldRecursively(asList(testTagDto1(), testTagDto2()))

    }

    def "should not find tags"() {
        given:
            tagDao.allOrderedById() >> []

        when:
            def tags = service.findAllTags()

        then:
            tags.isEmpty()
    }


    def "should update existing tag without changing tag type"() {
        given:
            def originTag = testTag1()
            originTag.setType(TagType.OVERDUE)

        and:
            tagDao.get(ID_1) >> Optional.of(originTag)

        and:
            assert originTag.type != testTagDtoToModifiy1().type

        when:
            service.updateTag(ID_1, testTagDtoToModifiy1())

        then:
            1 * tagDao.update(_ as Tag) >> { args ->
                def tag = (Tag) args[0]
                def modifiedTag = testModifiedTag1()
                modifiedTag.setType(originTag.type)
                assertThat(tag).isEqualToComparingFieldByFieldRecursively(modifiedTag)
                tag
            }
    }

    def "should create new tag"() {
        when:
            service.createTag(testTagDtoToAdd())

        then:
            1 * tagDao.create(_ as Tag) >> { args ->
                def tag = (Tag) args[0]
                assertThat(tag).isEqualToComparingFieldByFieldRecursively(testTagToAdd())
                tag
            }
    }

    def "should delete existing tag"() {
        when:
            service.deleteTag(ID_1)

        then:
            1 * tagDao.delete(ID_1)
    }

    def "should check if tag exists"(exists, expectedResult) {
        given:
            tagDao.exists(ID_1) >> exists

        when:
            def result = service.checkIfTagExists(ID_1)

        then:
            result == expectedResult

        where:
            exists | expectedResult
            true   | true
            false  | false
    }

    def "should find existing tags ids"() {
        given:
            def tagsIds = Sets.newHashSet(ID_1, ID_2, NOT_EXISTING_ID)
            tagDao.findExistingTagsIds(tagsIds) >> [ID_1, ID_2]

        when:
            def result = service.findExistingTagsIds(tagsIds)

        then:
            result.size() == 2
            result.contains(ID_1)
            result.contains(ID_2)
    }

    def "should not find existing tags ids for empty set"() {
        given:
            def tagsIds = emptySet()

        when:
            def result = service.findExistingTagsIds(tagsIds)

        then:
            result.size() == 0

        and:
            0 * tagDao.findExistingTagsIds(tagsIds)
    }

    def "should check if tag is deletable"(deletable, expectedResult) {
        given:
            tagDao.isDeletable(ID_1) >> deletable

        when:
            def result = service.isDeletable(ID_1)

        then:
            result == expectedResult

        where:
            deletable | expectedResult
            true      | true
            false     | false
    }

    def "should tag overdue issue"() {
        given:
            tagDao.findOverdueTag() >> testTag3()

        when:
            service.tagOverdueIssue(ISSUE_ID_1)

        then:
            1 * issueTagDao.create(_ as IssueTag) >> { List arguments ->
                def issueTag = arguments[0] as IssueTag
                assert issueTag.tagId == testTag3().id
                assert issueTag.issueId == ISSUE_ID_1
            }
    }
}
