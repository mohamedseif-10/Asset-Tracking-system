package com.AssetManagementSystem.Manager.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetManagementSystem.Manager.Model.entity.AssetHistory;
import com.AssetManagementSystem.Manager.Model.entity.AssetHistoryStatus;

public interface AssetHistoryRepo extends JpaRepository<AssetHistory, Integer> {
    
    List<AssetHistory> findByAssetIdOrderByStartDateDesc(Integer assetId);
    List<AssetHistory> findByUserIdOrderByStartDateDesc(Integer userId);
    List<AssetHistory> findByStatus(AssetHistoryStatus status);
    List<AssetHistory> findByStatusAndEndDateIsNull(AssetHistoryStatus status);
    List<AssetHistory> findByEndDateIsNull();
    List<AssetHistory> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<AssetHistory> findByAssetIdAndStatusAndEndDateIsNull(int assetId, String status);    

}

