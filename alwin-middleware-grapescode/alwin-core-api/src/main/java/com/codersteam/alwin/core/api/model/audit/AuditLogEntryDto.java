package com.codersteam.alwin.core.api.model.audit;

/**
 * @author Tomasz Sliwinski
 */
public class AuditLogEntryDto {

    private String objectName;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String operatorLogin;
    private String changeDate;
    private AuditChangeType changeType;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(final String objectName) {
        this.objectName = objectName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(final String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(final String newValue) {
        this.newValue = newValue;
    }

    public String getOperatorLogin() {
        return operatorLogin;
    }

    public void setOperatorLogin(final String operatorLogin) {
        this.operatorLogin = operatorLogin;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(final String changeDate) {
        this.changeDate = changeDate;
    }

    public AuditChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(final AuditChangeType changeType) {
        this.changeType = changeType;
    }

    @Override
    public String toString() {
        return "AuditInfoDto{" +
                "objectName='" + objectName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", operatorLogin=" + operatorLogin +
                ", changeDate=" + changeDate +
                ", changeType=" + changeType +
                '}';
    }
}
