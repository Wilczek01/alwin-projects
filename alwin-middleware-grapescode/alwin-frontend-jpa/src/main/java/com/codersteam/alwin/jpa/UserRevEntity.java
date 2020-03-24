package com.codersteam.alwin.jpa;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "REVINFO")
@RevisionEntity
public class UserRevEntity extends DefaultRevisionEntity {

    @Column(name = "OPERATOR_ID")
    private Long operator;

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }
}
