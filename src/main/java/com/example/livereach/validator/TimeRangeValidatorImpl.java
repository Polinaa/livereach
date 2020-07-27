package com.example.livereach.validator;

import com.example.livereach.model.ContentAvailableForEmbedOnTimeRangeRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeRangeValidatorImpl implements ConstraintValidator<TimeRangeValidator, ContentAvailableForEmbedOnTimeRangeRequest> {

    private static final int MAX_DAYS_DIFF = 31;

    @Override
    public boolean isValid(ContentAvailableForEmbedOnTimeRangeRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.getMaxTime() != null
                && request.getMinTime() != null
                && request.getMinTime() < request.getMaxTime()
                && isLessThanMonth(request.getMinTime(), request.getMaxTime());
    }

    private boolean isLessThanMonth(Long min, Long max) {
        return ChronoUnit.DAYS.between(Instant.ofEpochSecond(min), Instant.ofEpochSecond(max)) <= MAX_DAYS_DIFF;
    }
}
