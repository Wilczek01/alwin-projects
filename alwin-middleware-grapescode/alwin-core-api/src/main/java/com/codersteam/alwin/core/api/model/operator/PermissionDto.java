package com.codersteam.alwin.core.api.model.operator;

import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class PermissionDto {

    private Long id;
    private boolean allowedToExternallyPlanActivities;
    private boolean allowedToChangeActivityCharge;
    private boolean allowedToChangeActivityChargePercent;
    private boolean allowedToMarkOwnAttachmentDeleted;
    private boolean allowedToMarkOthersAttachmentsDeleted;
    private boolean allowedToTransferIssuesInternally;
    private boolean allowedToTransferIssuesExternally;
    private boolean allowedToExcludeIssue;
    private boolean allowedToSetCustomerOutOfService;
    private boolean allowedToTerminateIssue;
    private boolean allowedToExcludeInvoice;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PermissionDto that = (PermissionDto) o;
        return allowedToExternallyPlanActivities == that.allowedToExternallyPlanActivities &&
                allowedToChangeActivityCharge == that.allowedToChangeActivityCharge &&
                allowedToChangeActivityChargePercent == that.allowedToChangeActivityChargePercent &&
                allowedToMarkOwnAttachmentDeleted == that.allowedToMarkOwnAttachmentDeleted &&
                allowedToMarkOthersAttachmentsDeleted == that.allowedToMarkOthersAttachmentsDeleted &&
                allowedToTransferIssuesInternally == that.allowedToTransferIssuesInternally &&
                allowedToTransferIssuesExternally == that.allowedToTransferIssuesExternally &&
                allowedToExcludeIssue == that.allowedToExcludeIssue &&
                allowedToSetCustomerOutOfService == that.allowedToSetCustomerOutOfService &&
                allowedToTerminateIssue == that.allowedToTerminateIssue &&
                allowedToExcludeInvoice == that.allowedToExcludeInvoice &&
                allowedToManageDemandsForPayment == that.allowedToManageDemandsForPayment &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, allowedToExternallyPlanActivities, allowedToChangeActivityCharge, allowedToChangeActivityChargePercent,
                allowedToMarkOwnAttachmentDeleted, allowedToMarkOthersAttachmentsDeleted, allowedToTransferIssuesInternally, allowedToTransferIssuesExternally,
                allowedToExcludeIssue, allowedToSetCustomerOutOfService, allowedToTerminateIssue, allowedToExcludeInvoice, allowedToManageDemandsForPayment);
    }

    @Override
    public String toString() {
        return "PermissionDto{" +
                "id=" + id +
                ", allowedToExternallyPlanActivities=" + allowedToExternallyPlanActivities +
                ", allowedToChangeActivityCharge=" + allowedToChangeActivityCharge +
                ", allowedToChangeActivityChargePercent=" + allowedToChangeActivityChargePercent +
                ", allowedToMarkOwnAttachmentDeleted=" + allowedToMarkOwnAttachmentDeleted +
                ", allowedToMarkOthersAttachmentsDeleted=" + allowedToMarkOthersAttachmentsDeleted +
                ", allowedToTransferIssuesInternally=" + allowedToTransferIssuesInternally +
                ", allowedToTransferIssuesExternally=" + allowedToTransferIssuesExternally +
                ", allowedToExcludeIssue=" + allowedToExcludeIssue +
                ", allowedToSetCustomerOutOfService=" + allowedToSetCustomerOutOfService +
                ", allowedToTerminateIssue=" + allowedToTerminateIssue +
                ", allowedToExcludeInvoice=" + allowedToExcludeInvoice +
                ", allowedToManageDemandsForPayment=" + allowedToManageDemandsForPayment +
                '}';
    }
}
