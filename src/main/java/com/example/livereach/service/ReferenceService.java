package com.example.livereach.service;

import com.example.livereach.repository.ReferenceRepository;
import com.example.livereach.repository.entity.ReferenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    public final ReferenceRepository repository;

    public List<ReferenceEntity> findAll() {
        return repository.findAll();
    }

}
