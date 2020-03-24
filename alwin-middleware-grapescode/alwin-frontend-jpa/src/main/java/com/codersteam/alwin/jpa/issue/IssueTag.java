package com.codersteam.alwin.jpa.issue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Michal Horowic
 */
@Entity
@Table(name = "issue_tag")
public class IssueTag {

    @SequenceGenerator(name = "issueTagSEQ", sequenceName = "issue_tag_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueTagSEQ")
    private Long id;

    @Column(name = "issue_id", nullable = false)
    private Long issueId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    public IssueTag() {
    }

    public IssueTag(final Long issueId, final Long tagId) {
        this.issueId = issueId;
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(final Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueTag)) return false;

        final IssueTag issueTag = (IssueTag) o;

        if (id != null ? !id.equals(issueTag.id) : issueTag.id != null) return false;
        if (issueId != null ? !issueId.equals(issueTag.issueId) : issueTag.issueId != null) return false;
        return tagId != null ? tagId.equals(issueTag.tagId) : issueTag.tagId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (issueId != null ? issueId.hashCode() : 0);
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueTag{" +
                "id=" + id +
                ", issueId=" + issueId +
                ", tagId=" + tagId +
                '}';
    }
}


