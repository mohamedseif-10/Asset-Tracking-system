package com.AssetManagementSystem.Manager.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetManagementSystem.Manager.model.entity.AssetHistory;

public interface AssetHistoryRepo extends JpaRepository<AssetHistory, Integer> {

    List<AssetHistory> findById(int assetId);

    List<AssetHistory> findByLogDate(LocalDateTime logDate);

    // List<AssetHistory> findByUserIdOrderByStartDateDesc(Integer userId);
    // List<AssetHistory> findByStatus(AssetHistoryStatus status);
    // List<AssetHistory> findByStatusAndEndDateIsNull(AssetHistoryStatus status);
    // List<AssetHistory> findByEndDateIsNull();
    // List<AssetHistory> findByStartDateBetween(LocalDateTime startDate,
    // LocalDateTime endDate);
    // List<AssetHistory> findByAssetIdAndStatusAndEndDateIsNull(int assetId, String
    // status);

}