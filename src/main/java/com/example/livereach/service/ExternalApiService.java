package com.example.livereach.service;

import com.example.livereach.api.LiveReachExternalAPI;
import com.example.livereach.model.ContentAvailableForEmbedResponse;
import com.example.livereach.repository.entity.AvailabilityEntity;
import com.example.livereach.repository.entity.ReferenceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final LiveReachExternalAPI liveReachExternalAPI;
    private final ReferenceService referenceService;
    private final AvailabilityService availabilityService;

    public void requestAndSaveContentAvailabilityData() {
        List<ReferenceEntity> references = referenceService.findAll();
        log.info("Performing request on {} reference(s)", references.size());
        List<AvailabilityEntity> availabilityEntities = references
                .stream()
                .map(ref -> {
                    ContentAvailableForEmbedResponse response = queryApi(ref);
                    return response != null ? availabilityService.toAvailabilityEntity(ref, response) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        log.info("Saving {} new availability result(s)", availabilityEntities.size());
        availabilityService.saveAll(availabilityEntities);
    }

    private ContentAvailableForEmbedResponse queryApi(ReferenceEntity reference) {
        try {
            ContentAvailableForEmbedResponse response = liveReachExternalAPI.findContentAvailableForEmbed(reference.getEmbedId(), reference.getProductId());
            log.info("Received response {}", response);
            return response;
        } catch (Exception e) {
            log.error("API request failed", e);
            return null;
        }
    }
}
