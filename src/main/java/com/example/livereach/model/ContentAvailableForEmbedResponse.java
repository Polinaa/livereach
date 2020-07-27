package com.example.livereach.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class ContentAvailableForEmbedResponse {

    private Integer code;
    private String message;
    private Boolean contentAvailable;

    private Long timestamp = Instant.now().getEpochSecond();

}
