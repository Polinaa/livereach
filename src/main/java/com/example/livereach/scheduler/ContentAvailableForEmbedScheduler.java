package com.example.livereach.scheduler;

import com.example.livereach.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentAvailableForEmbedScheduler {

    private final ExternalApiService externalApiService;

    @Scheduled(cron = "${livereach.schedule.cron}")
    public void checkContentAvailableForEmbed() {
        log.info("ContentAvailableForEmbedScheduler started");
        externalApiService.requestAndSaveContentAvailabilityData();
        log.info("ContentAvailableForEmbedScheduler finished");
    }

}
