package com.example.livereach.repository;

import com.example.livereach.repository.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {

    @Query(value = "SELECT * from availability a "
            + "JOIN reference r ON a.reference_id = r.id "
            + "WHERE r.embed_id=:embedId "
            + "AND r.product_id=:productId "
            + "AND a.timestamp >= :minTime "
            + "AND a.timestamp <= :maxTime", nativeQuery = true)
    List<AvailabilityEntity> findAllByEmbedIdAndProductIdWithinTimeRange(
            @Param("embedId") Integer embedId,
            @Param("productId") String productId,
            @Param("minTime") Long minTime,
            @Param("maxTime") Long maxTime);
}
