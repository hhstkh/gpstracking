package com.gpstracking.repository;

import com.gpstracking.domain.GPSEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GPSRepository extends JpaRepository<GPSEntity, Integer> {
    GPSEntity findByIdAndUserId(Integer gpsId, Integer userId);
    List<GPSEntity> findByUserId(Integer userId);
}
