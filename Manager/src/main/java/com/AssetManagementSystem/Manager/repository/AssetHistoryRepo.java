package com.AssetManagementSystem.Manager.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetManagementSystem.Manager.model.entity.AssetHistory;
import com.AssetManagementSystem.Manager.model.entity.AssetHistoryStatus;

public interface AssetHistoryRepo extends JpaRepository<AssetHistory, Integer> {

    
    List<AssetHistory> findById(int assetId);
    List<AssetHistory> findByUserId(int userId);
    List<AssetHistory> findByAssetId(int assetId);
    List<AssetHistory> findByLogDate(LocalDateTime logDate);
    List<AssetHistory> findByStatus(AssetHistoryStatus status);

}