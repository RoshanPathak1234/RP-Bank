package com.RPBank.main.Authenticator;

import com.RPBank.main.DTO.EnquiryRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnquiryRequestValidator implements ConstraintValidator<ValidEnquiryRequest, EnquiryRequest> {

    @Override
    public boolean isValid(EnquiryRequest request, ConstraintValidatorContext context) {
        return request.getCustomerId() != null && !request.getCustomerId().isBlank() ||
                request.getAccountNumber() != null && !request.getAccountNumber().isBlank();
    }
}
