package com.example.livereach.service;

import com.example.livereach.model.ContentAvailableForEmbedOnTimeRangeRequest;
import com.example.livereach.model.ContentAvailableForEmbedResponse;
import com.example.livereach.repository.AvailabilityRepository;
import com.example.livereach.repository.entity.AvailabilityEntity;
import com.example.livereach.repository.entity.ReferenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    public final AvailabilityRepository repository;

    public void saveAll(List<AvailabilityEntity> availabilityList) {
        if (isEmpty(availabilityList)) {
            return;
        }
        repository.saveAll(availabilityList);
    }

    public Map<Long, Boolean> findContentAvailability(@NotNull @Valid ContentAvailableForEmbedOnTimeRangeRequest request) {
        List<AvailabilityEntity> availabilities = repository.findAllByEmbedIdAndProductIdWithinTimeRange(
                request.getEmbedId(),
                request.getProductId(),
                request.getMinTime(),
                request.getMaxTime());
        Long interval = MINUTES.toSeconds(request.getInterval());
        Map<Long, Boolean> timeToAvailabilityMap = new LinkedHashMap<>();
        Long start = request.getMinTime();
        Long end;
        do {
            end = start + interval > request.getMaxTime() ? request.getMaxTime() : start + interval;
            timeToAvailabilityMap.put(end, hasOccurrences(availabilities, start, end));
            start += interval;
        } while (start < request.getMaxTime());
        return timeToAvailabilityMap;
    }

    public AvailabilityEntity toAvailabilityEntity(@NotNull ReferenceEntity reference,
            @NotNull ContentAvailableForEmbedResponse response) {
        return new AvailabilityEntity().setReferenceEntity(reference)
                                       .setTimestamp(response.getTimestamp())
                                       .setAvailable(response.getContentAvailable());
    }

    private boolean hasOccurrences(List<AvailabilityEntity> availabilityEntities, Long min, Long max) {
        return availabilityEntities.stream()
                                   .anyMatch(a -> a.getTimestamp() >= min && a.getTimestamp() <= max);
    }
}
