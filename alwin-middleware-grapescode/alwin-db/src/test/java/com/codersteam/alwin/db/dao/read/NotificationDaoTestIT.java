package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.NotificationDao;
import com.codersteam.alwin.jpa.Notification;
import com.codersteam.alwin.jpa.operator.Operator;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Comparator;
import java.util.List;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.NotificationTestData.exampleNotifications;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;

/**
 * @author Adam Stepnowski
 */
public class NotificationDaoTestIT extends ReadTestBase {

    @EJB
    private NotificationDao notificationDao;

    @Test
    public void shouldFindAllNotificationsByIssueId() {
        // when
        final List<Notification> allIssueNotifications = notificationDao.findAllIssueNotifications(ISSUE_ID_1);

        // then
        assertFalse(allIssueNotifications.isEmpty());
        assertThat(allIssueNotifications)
                .usingComparatorForFields((Comparator<List<Operator>>) (o1, o2) -> 0, "sender.user.operators")
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "creationDate", "sender.user.updateDate",
                        "sender.user.creationDate", "readDate")
                .isEqualToComparingFieldByFieldRecursively(exampleNotifications());

    }
}
