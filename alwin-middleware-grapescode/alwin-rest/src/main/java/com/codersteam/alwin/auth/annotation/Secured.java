package com.codersteam.alwin.auth.annotation;

import com.codersteam.alwin.core.api.model.user.OperatorType;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Tomasz Sliwinski
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {

    /**
     * @return true jeśli operacja ma być dostępna dla wszystkich ról, w innym wypadku false
     */
    boolean all() default false;

    /**
     * @return lista ról dla których operacja ma być dostępna
     */
    OperatorType[] value() default {};
}