package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.type.IssueTypeTransitionCondition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "issue_type_transition")
public class IssueTypeTransition {


    @SequenceGenerator(name = "issueTypeTransitionSEQ", sequenceName = "issue_type_transition_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueTypeTransitionSEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "closed_issue_type_id", referencedColumnName = "id", nullable = false)
    private IssueType closedIssueType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_issue_type_id", referencedColumnName = "id", nullable = false)
    private IssueType childIssueType;

    @Column(name = "condition", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private IssueTypeTransitionCondition condition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueType getClosedIssueType() {
        return closedIssueType;
    }

    public void setClosedIssueType(IssueType closedIssueType) {
        this.closedIssueType = closedIssueType;
    }

    public IssueType getChildIssueType() {
        return childIssueType;
    }

    public void setChildIssueType(IssueType childIssueType) {
        this.childIssueType = childIssueType;
    }

    public IssueTypeTransitionCondition getCondition() {
        return condition;
    }

    public void setCondition(IssueTypeTransitionCondition condition) {
        this.condition = condition;
    }
}
