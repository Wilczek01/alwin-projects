package com.codersteam.alwin.common.sort;

/**
 * Nazwy kolumn dostępnych do sortowania dla czynności zlecenia
 *
 * @author Michal Horowic
 */
public enum ActivitySortField {
    ID("id"),
    STATE("state"),
    TYPE("key"),
    TYPE_NAME("name"),
    REMARK("remark"),
    OPERATOR("operator"),
    OPERATOR_FIRST_NAME("firstName"),
    OPERATOR_LAST_NAME("lastName"),
    CREATION_DATE("creationDate"),
    ACTIVITY_DATE("activityDate"),
    PLANNED_DATE("plannedDate");

    private final String column;

    ActivitySortField(final String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
