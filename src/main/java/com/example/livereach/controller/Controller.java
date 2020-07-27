package com.example.livereach.controller;

import com.example.livereach.model.ContentAvailableForEmbedOnTimeRangeRequest;
import com.example.livereach.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final AvailabilityService availabilityService;

    @GetMapping(path = "/availability")
    public ResponseEntity<Map<Long, Boolean>> findAvailability(@Valid ContentAvailableForEmbedOnTimeRangeRequest request) {
        return ResponseEntity.ok().body(availabilityService.findContentAvailability(request));
    }
}
