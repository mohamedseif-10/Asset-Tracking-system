package com.AssetManagementSystem.Manager.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetManagementSystem.Manager.Model.entity.Asset;
import com.AssetManagementSystem.Manager.Model.entity.AssetStatus;


public interface AssetRepo extends JpaRepository<Asset, Integer> {
    

    // ASSET READ

    List<Asset> findByAsId(int id);
    List<Asset> findByASName(String name);
    List<Asset> findByAsStatus(AssetStatus status);
    List<Asset> findByAsAddDate(String addDate);
    List<Asset> findByAsUpdateDate(String updateDate);
    List<Asset> findByAssignedToUserId(Integer userId);
    List<Asset> findByAsNameContainingIgnoreCase(String keyword);
    List<Asset> findByAsIdAndAsStatusAndAsUpdateDateIsNull(int id, AssetStatus status);
    List<Asset> findByNameContainingIgnoreCaseAndStatus(AssetStatus status);
    List<Asset> findByStatus(AssetStatus status);
    List<Asset> findByUserId(Integer userId);
    List<Asset> findByNameContainingIgnoreCaseAndStatus(String name, AssetStatus status);

    // List<Asset> findByUpdateDateIsNotNullOrderByUpdateDateDesc();
    // Find unassigned assets
    List<Asset> findByUserIdIsNull();

    // Use count 
    List<Asset> countByASName(String name);
    List<Asset> countByAsStatus(AssetStatus status);
    

    void deleteByAsId(int id);




    
}