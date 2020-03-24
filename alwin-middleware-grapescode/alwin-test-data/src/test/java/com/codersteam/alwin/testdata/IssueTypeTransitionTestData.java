package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeTransition;
import com.codersteam.alwin.jpa.type.IssueTypeTransitionCondition;

import java.util.List;

import static com.codersteam.alwin.jpa.type.IssueTypeTransitionCondition.NONE;
import static com.codersteam.alwin.jpa.type.IssueTypeTransitionCondition.NON_SECURED_ITEM;
import static com.codersteam.alwin.jpa.type.IssueTypeTransitionCondition.SECURED_ITEM;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType3;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType4;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType5;
import static java.util.Arrays.asList;

public class IssueTypeTransitionTestData {

    private static final long ID_1 = 1L;
    private static final IssueTypeTransitionCondition CONDITION_1 = NONE;
    public static final IssueTypeTransition ISSUE_TYPE_TRANSITION_1 = issueTypeTransition1();

    private static final long ID_2 = 2L;
    private static final IssueTypeTransitionCondition CONDITION_2 = NONE;
    public static final IssueTypeTransition ISSUE_TYPE_TRANSITION_2 = issueTypeTransition2();

    private static final long ID_3 = 3L;
    private static final IssueTypeTransitionCondition CONDITION_3 = NON_SECURED_ITEM;
    public static final IssueTypeTransition ISSUE_TYPE_TRANSITION_3 = issueTypeTransition3();

    private static final long ID_4 = 4L;
    private static final IssueTypeTransitionCondition CONDITION_4 = SECURED_ITEM;
    public static final IssueTypeTransition ISSUE_TYPE_TRANSITION_4 = issueTypeTransition4();


    public static List<IssueTypeTransition> all() {
        return asList(ISSUE_TYPE_TRANSITION_1, ISSUE_TYPE_TRANSITION_2, ISSUE_TYPE_TRANSITION_3, ISSUE_TYPE_TRANSITION_4);
    }

    public static List<Long> allTypeIds() {
        return asList(ID_1, ID_2, ID_3, ID_4);
    }

    private static IssueTypeTransition issueTypeTransition1() {
        return issueTypeTransition(ID_1, issueType1(), issueType2(), CONDITION_1);
    }

    private static IssueTypeTransition issueTypeTransition2() {
        return issueTypeTransition(ID_2, issueType2(), issueType3(), CONDITION_2);
    }

    private static IssueTypeTransition issueTypeTransition3() {
        return issueTypeTransition(ID_3, issueType3(), issueType4(), CONDITION_3);
    }

    private static IssueTypeTransition issueTypeTransition4() {
        return issueTypeTransition(ID_4, issueType3(), issueType5(), CONDITION_4);
    }

    private static IssueTypeTransition issueTypeTransition(final Long id, final IssueType closedIssueType, final IssueType childIssueType, final
    IssueTypeTransitionCondition condition) {
        final IssueTypeTransition issueTypeTransition = new IssueTypeTransition();
        issueTypeTransition.setClosedIssueType(closedIssueType);
        issueTypeTransition.setChildIssueType(childIssueType);
        issueTypeTransition.setCondition(condition);
        issueTypeTransition.setId(id);
        return issueTypeTransition;
    }
}
