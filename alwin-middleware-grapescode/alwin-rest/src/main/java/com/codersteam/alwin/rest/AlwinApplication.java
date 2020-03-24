package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.JWTTokenNeededFilter;
import com.codersteam.alwin.mapper.AlwinExceptionMapper;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Adam Stepnowski
 */
@ApplicationPath("/")
public class AlwinApplication extends Application {

    private final Set<Class<?>> classes = new HashSet<>();

    public AlwinApplication() {
        // --- swagger
        classes.add(ApiListingResource.class);
        classes.add(SwaggerSerializers.class);

        // --- filters
        classes.add(JWTTokenNeededFilter.class);

        addRestResourceClasses();
        addRestExceptionMappers();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    private void addRestResourceClasses() {
        classes.add(VersionResource.class);
        classes.add(UserResource.class);
        classes.add(IssueResource.class);
        classes.add(OperatorResource.class);
        classes.add(ActivityResource.class);
        classes.add(AddressResource.class);
        classes.add(ContactDetailResource.class);
        classes.add(InvoiceResource.class);
        classes.add(AidaCompanyResource.class);
        classes.add(BalanceResource.class);
        classes.add(MessageResource.class);
        classes.add(NotificationResource.class);
        classes.add(ContractResource.class);
        classes.add(CustomerResource.class);
        classes.add(SettlementResource.class);
        classes.add(WalletResource.class);
        classes.add(PersonResource.class);
        classes.add(HolidayResource.class);
        classes.add(AuditResource.class);
        classes.add(TagResource.class);
        classes.add(SchedulerExecutionResource.class);
        classes.add(DateResource.class);
        classes.add(DemandForPaymentResource.class);
        classes.add(ContractTerminationResource.class);
        classes.add(PostalCodeResource.class);
    }

    private void addRestExceptionMappers() {
        classes.add(AlwinExceptionMapper.class);
    }
}
