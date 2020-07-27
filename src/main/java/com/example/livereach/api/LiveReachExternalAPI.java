package com.example.livereach.api;

import com.example.livereach.model.ContentAvailableForEmbedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@FeignClient(name = "livereach-external-api",
        url = "${livereach-external.api-base-url}",
        configuration = FeignConfiguration.class,
        decode404 = true)
public interface LiveReachExternalAPI {

    @RequestMapping(value = "/embed/contentAvailableForEmbed/{embedId}",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_FORM_URLENCODED_VALUE)
    ContentAvailableForEmbedResponse findContentAvailableForEmbed(
            @PathVariable("embedId") Integer embedId,
            @RequestParam("productIds") String productId);
}
