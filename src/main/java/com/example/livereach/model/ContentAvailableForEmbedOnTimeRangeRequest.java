package com.example.livereach.model;

import com.example.livereach.validator.TimeRangeValidator;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@TimeRangeValidator
public class ContentAvailableForEmbedOnTimeRangeRequest {

    @NotNull
    private Integer embedId;

    @NotEmpty
    private String productId;

    @NotNull
    private Long minTime;

    @NotNull
    private Long maxTime;

    @Min(value = 30, message = "internal can be between 30 minutes and 24 hours")
    @Max(value = 1440, message = "internal can be between 30 minutes and 24 hours")
    @NotNull
    private Integer interval;

}
