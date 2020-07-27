package com.example.livereach.repository;

import com.example.livereach.repository.entity.ReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<ReferenceEntity, Long> {

}
