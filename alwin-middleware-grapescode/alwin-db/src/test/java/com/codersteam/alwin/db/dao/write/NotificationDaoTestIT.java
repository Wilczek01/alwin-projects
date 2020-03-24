package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.NotificationDao;
import com.codersteam.alwin.jpa.Notification;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.NotificationTestData.ID_3;
import static com.codersteam.alwin.testdata.NotificationTestData.READ_DATE_3;
import static com.codersteam.alwin.testdata.NotificationTestData.buildNewNotification;
import static com.codersteam.alwin.testdata.NotificationTestData.notificationToUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Stepnowski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-notification.json"})
public class NotificationDaoTestIT extends WriteTestBase {


    @EJB
    private NotificationDao notificationDao;

    @Test
    public void shouldCreateNewNotification() {
        // given
        final Notification notification = buildNewNotification();

        // when
        notificationDao.create(notification);

        // then
        final Optional<Notification> savedNotification = notificationDao.get(notification.getId());
        assertTrue(savedNotification.isPresent());
        assertThat(savedNotification.get()).isEqualToComparingFieldByFieldRecursively(notification);
    }

    @Test
    public void shouldUpdateNotification() {
        //given
        final Notification notification = notificationToUpdate();

        //when
        notificationDao.update(notification);

        //then
        assertUpdatedNotification();
    }

    private void assertUpdatedNotification() {
        final Optional<Notification> notification = notificationDao.get(ID_3);
        assertTrue(notification.isPresent());
        assertTrue(notification.get().getReadDate().equals(READ_DATE_3));
    }
}
