package com.RPBank.main.Authenticator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnquiryRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnquiryRequest {

    String message() default "Either customerId or accountNumber must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
