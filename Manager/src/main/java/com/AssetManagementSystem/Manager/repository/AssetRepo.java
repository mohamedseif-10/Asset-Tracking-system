package com.AssetManagementSystem.Manager.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetManagementSystem.Manager.model.entity.Asset;
import com.AssetManagementSystem.Manager.model.entity.AssetStatus;


public interface AssetRepo extends JpaRepository<Asset, Integer> {
    
Optional<Asset> findById(int id);
List<Asset> findByName(String name);
List<Asset> findByStatus(AssetStatus status);
List<Asset> findByAddDate(LocalDate addDate);
List<Asset> findByUpdateDate(LocalDate updateDate);
List<Asset> findByNameContainingIgnoreCase(String keyword);
List<Asset> findByUserId(Integer userId);
List<Asset> findByUserIdIsNull();
long countByName(String name);
long countByStatus(AssetStatus status);
void deleteById(int id);
    
}