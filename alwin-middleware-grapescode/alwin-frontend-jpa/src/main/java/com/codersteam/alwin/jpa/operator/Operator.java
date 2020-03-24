package com.codersteam.alwin.jpa.operator;

import com.codersteam.alwin.jpa.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Michal Horowic
 */
@Audited
@Entity
@Table(name = "alwin_operator")
public class Operator {

    @SequenceGenerator(name = "alwinoperatorSEQ", sequenceName = "alwin_operator_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alwinoperatorSEQ")
    private Long id;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "login", length = 40, nullable = false, unique = true)
    private String login;

    @NotAudited
    @Column(name = "password", nullable = false)
    private String password;

    @NotAudited
    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "force_update_password", nullable = false)
    private boolean forceUpdatePassword;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    private Permission permission;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_alwin_operator_id", referencedColumnName = "id")
    private Operator parentOperator;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "alwin_user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "operator_type_id", referencedColumnName = "id", nullable = false)
    private OperatorType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isForceUpdatePassword() {
        return forceUpdatePassword;
    }

    public void setForceUpdatePassword(final boolean forceUpdatePassword) {
        this.forceUpdatePassword = forceUpdatePassword;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Operator getParentOperator() {
        return parentOperator;
    }

    public void setParentOperator(Operator parentOperator) {
        this.parentOperator = parentOperator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OperatorType getType() {
        return type;
    }

    public void setType(OperatorType type) {
        this.type = type;
    }
}
