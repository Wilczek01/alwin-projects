package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.operator.PermissionDto;
import com.codersteam.alwin.jpa.operator.Permission;

/**
 * @author Piotr Naroznik
 */
public class PermissionTestData {

    public static Permission permission2() {
        return permission(2L, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public static PermissionDto permissionDto2() {
        return permissionDto(2L, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public static Permission permission3() {
        return permission(3L, false, false, false, false, false, false, false, false, false, false, false, false);
    }

    public static Permission permission4() {
        return permission(4L, false, false, false, false, false, false, false, false, false, false, false, true);
    }

    public static PermissionDto permissionDto3() {
        return permissionDto(3L, false, false, false, false, false, false, false, false, false, false, false, false);
    }

    public static PermissionDto permissionDto4() {
        return permissionDto(4L, false, false, false, false, false, false, false, false, false, false, false, true);
    }

    public static Permission permission(final Long id, final boolean allowedToExternallyPlanActivities, final boolean allowedToChangeActivityCharge,
                                        final boolean allowedToChangeActivityChargePercent, final boolean allowedToMarkOwnAttachmentDeleted,
                                        final boolean allowedToMarkOthersAttachmentsDeleted, final boolean allowedToTransferIssuesInternally,
                                        final boolean allowedToTransferIssuesExternally, final boolean allowedToExcludeIssue,
                                        final boolean allowedToSetCustomerOutOfService, final boolean allowedToTerminateIssue,
                                        final boolean allowedToExcludeInvoice, final boolean allowedToManageDemandsForPayment) {
        final Permission permission = new Permission();
        permission.setId(id);
        permission.setAllowedToExternallyPlanActivities(allowedToExternallyPlanActivities);
        permission.setAllowedToChangeActivityCharge(allowedToChangeActivityCharge);
        permission.setAllowedToChangeActivityChargePercent(allowedToChangeActivityChargePercent);
        permission.setAllowedToMarkOwnAttachmentDeleted(allowedToMarkOwnAttachmentDeleted);
        permission.setAllowedToMarkOthersAttachmentsDeleted(allowedToMarkOthersAttachmentsDeleted);
        permission.setAllowedToTransferIssuesInternally(allowedToTransferIssuesInternally);
        permission.setAllowedToTransferIssuesExternally(allowedToTransferIssuesExternally);
        permission.setAllowedToExcludeIssue(allowedToExcludeIssue);
        permission.setAllowedToSetCustomerOutOfService(allowedToSetCustomerOutOfService);
        permission.setAllowedToTerminateIssue(allowedToTerminateIssue);
        permission.setAllowedToExcludeInvoice(allowedToExcludeInvoice);
        permission.setAllowedToManageDemandsForPayment(allowedToManageDemandsForPayment);
        return permission;
    }

    public static PermissionDto permissionDto(final Long id, final boolean allowedToExternallyPlanActivities, final boolean allowedToChangeActivityCharge,
                                              final boolean allowedToChangeActivityChargePercent, final boolean allowedToMarkOwnAttachmentDeleted,
                                              final boolean allowedToMarkOthersAttachmentsDeleted, final boolean allowedToTransferIssuesInternally,
                                              final boolean allowedToTransferIssuesExternally, final boolean allowedToExcludeIssue,
                                              final boolean allowedToSetCustomerOutOfService, final boolean allowedToTerminateIssue,
                                              final boolean allowedToExcludeInvoice, final boolean allowedToManageDemandsForPayment) {
        final PermissionDto permission = new PermissionDto();
        permission.setId(id);
        permission.setAllowedToExternallyPlanActivities(allowedToExternallyPlanActivities);
        permission.setAllowedToChangeActivityCharge(allowedToChangeActivityCharge);
        permission.setAllowedToChangeActivityChargePercent(allowedToChangeActivityChargePercent);
        permission.setAllowedToMarkOwnAttachmentDeleted(allowedToMarkOwnAttachmentDeleted);
        permission.setAllowedToMarkOthersAttachmentsDeleted(allowedToMarkOthersAttachmentsDeleted);
        permission.setAllowedToTransferIssuesInternally(allowedToTransferIssuesInternally);
        permission.setAllowedToTransferIssuesExternally(allowedToTransferIssuesExternally);
        permission.setAllowedToExcludeIssue(allowedToExcludeIssue);
        permission.setAllowedToSetCustomerOutOfService(allowedToSetCustomerOutOfService);
        permission.setAllowedToTerminateIssue(allowedToTerminateIssue);
        permission.setAllowedToExcludeInvoice(allowedToExcludeInvoice);
        permission.setAllowedToManageDemandsForPayment(allowedToManageDemandsForPayment);
        return permission;
    }
}
