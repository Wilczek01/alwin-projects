package com.codersteam.alwin.jpa.operator;

import org.hibernate.envers.Audited;

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
@Audited
@Entity
@Table(name = "permission")
public class Permission {

    @SequenceGenerator(name = "permissionSEQ", sequenceName = "permission_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissionSEQ")
    private Long id;

    @Column(name = "allowed_to_externally_plan_activities", nullable = false)
    private boolean allowedToExternallyPlanActivities;

    @Column(name = "allowed_to_change_activity_charge", nullable = false)
    private boolean allowedToChangeActivityCharge;

    @Column(name = "allowed_to_change_activity_charge_percent", nullable = false)
    private boolean allowedToChangeActivityChargePercent;

    @Column(name = "allowed_to_mark_own_attachment_deleted", nullable = false)
    private boolean allowedToMarkOwnAttachmentDeleted;

    @Column(name = "allowed_to_mark_others_attachments_deleted", nullable = false)
    private boolean allowedToMarkOthersAttachmentsDeleted;

    @Column(name = "allowed_to_transfer_issues_internally", nullable = false)
    private boolean allowedToTransferIssuesInternally;

    @Column(name = "allowed_to_transfer_issues_externally", nullable = false)
    private boolean allowedToTransferIssuesExternally;

    @Column(name = "allowed_to_exclude_issue", nullable = false)
    private boolean allowedToExcludeIssue;

    @Column(name = "allowed_to_set_customer_out_of_service", nullable = false)
    private boolean allowedToSetCustomerOutOfService;

    @Column(name = "allowed_to_terminate_issue", nullable = false)
    private boolean allowedToTerminateIssue;

    @Column(name = "allowed_to_exclude_invoice", nullable = false)
    private boolean allowedToExcludeInvoice;

    @Column(name = "allowed_to_manage_demands_for_payment", nullable = false)
    private boolean allowedToManageDemandsForPayment;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public boolean isAllowedToExternallyPlanActivities() {
        return allowedToExternallyPlanActivities;
    }

    public void setAllowedToExternallyPlanActivities(final boolean allowedToExternallyPlanActivities) {
        this.allowedToExternallyPlanActivities = allowedToExternallyPlanActivities;
    }

    public boolean isAllowedToChangeActivityCharge() {
        return allowedToChangeActivityCharge;
    }

    public void setAllowedToChangeActivityCharge(final boolean allowedToChangeActivityCharge) {
        this.allowedToChangeActivityCharge = allowedToChangeActivityCharge;
    }

    public boolean isAllowedToChangeActivityChargePercent() {
        return allowedToChangeActivityChargePercent;
    }

    public void setAllowedToChangeActivityChargePercent(final boolean allowedToChangeActivityChargePercent) {
        this.allowedToChangeActivityChargePercent = allowedToChangeActivityChargePercent;
    }

    public boolean isAllowedToMarkOwnAttachmentDeleted() {
        return allowedToMarkOwnAttachmentDeleted;
    }

    public void setAllowedToMarkOwnAttachmentDeleted(final boolean allowedToMarkOwnAttachmentDeleted) {
        this.allowedToMarkOwnAttachmentDeleted = allowedToMarkOwnAttachmentDeleted;
    }

    public boolean isAllowedToMarkOthersAttachmentsDeleted() {
        return allowedToMarkOthersAttachmentsDeleted;
    }

    public void setAllowedToMarkOthersAttachmentsDeleted(final boolean allowedToMarkOthersAttachmentsDeleted) {
        this.allowedToMarkOthersAttachmentsDeleted = allowedToMarkOthersAttachmentsDeleted;
    }

    public boolean isAllowedToTransferIssuesInternally() {
        return allowedToTransferIssuesInternally;
    }

    public void setAllowedToTransferIssuesInternally(final boolean allowedToTransferIssuesInternally) {
        this.allowedToTransferIssuesInternally = allowedToTransferIssuesInternally;
    }

    public boolean isAllowedToTransferIssuesExternally() {
        return allowedToTransferIssuesExternally;
    }

    public void setAllowedToTransferIssuesExternally(final boolean allowedToTransferIssuesExternally) {
        this.allowedToTransferIssuesExternally = allowedToTransferIssuesExternally;
    }

    public boolean isAllowedToExcludeIssue() {
        return allowedToExcludeIssue;
    }

    public void setAllowedToExcludeIssue(final boolean allowedToExcludeIssue) {
        this.allowedToExcludeIssue = allowedToExcludeIssue;
    }

    public boolean isAllowedToSetCustomerOutOfService() {
        return allowedToSetCustomerOutOfService;
    }

    public void setAllowedToSetCustomerOutOfService(final boolean allowedToSetCustomerOutOfService) {
        this.allowedToSetCustomerOutOfService = allowedToSetCustomerOutOfService;
    }

    public boolean isAllowedToTerminateIssue() {
        return allowedToTerminateIssue;
    }

    public void setAllowedToTerminateIssue(final boolean allowedToTerminateIssue) {
        this.allowedToTerminateIssue = allowedToTerminateIssue;
    }

    public boolean isAllowedToExcludeInvoice() {
        return allowedToExcludeInvoice;
    }

    public void setAllowedToExcludeInvoice(final boolean allowedToExcludeInvoice) {
        this.allowedToExcludeInvoice = allowedToExcludeInvoice;
    }

    public boolean isAllowedToManageDemandsForPayment() {
        return allowedToManageDemandsForPayment;
    }

    public void setAllowedToManageDemandsForPayment(final boolean allowedToManageDemandsForPayment) {
        this.allowedToManageDemandsForPayment = allowedToManageDemandsForPayment;
    }
}
