package com.codersteam.alwin.core.api.model.issue;

/**
 * @author Dariusz Rackowski
 */
public class IssueForFieldDebtCollectorDto extends IssueDto {

    public enum ReminderMark {
        NONE,
        NORMAL,
        HIGH
    }

    private ReminderMark reminderMark;

    public ReminderMark getReminderMark() {
        return reminderMark;
    }

    public void setReminderMark(final ReminderMark reminderMark) {
        this.reminderMark = reminderMark;
    }
}
