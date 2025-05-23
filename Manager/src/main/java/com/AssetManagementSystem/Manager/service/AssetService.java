package com.AssetManagementSystem.Manager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AssetManagementSystem.Manager.dto.AssetHistoryDTO;
import com.AssetManagementSystem.Manager.model.entity.Asset;
import com.AssetManagementSystem.Manager.model.entity.AssetHistory;
import com.AssetManagementSystem.Manager.model.entity.AssetHistoryStatus;
import com.AssetManagementSystem.Manager.model.entity.AssetStatus;
import com.AssetManagementSystem.Manager.model.entity.User;
import com.AssetManagementSystem.Manager.repository.AssetHistoryRepo;
import com.AssetManagementSystem.Manager.repository.AssetRepo;
import com.AssetManagementSystem.Manager.repository.UserRepo;

@Service
public class AssetService {

    @Autowired
    private AssetRepo assetRepo;

    @Autowired
    private AssetHistoryRepo assetHistoryRepo;

    @Autowired
    private UserRepo userRepo;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Asset> getAll_Assets() {
        return assetRepo.findAll();
    }

    public Asset getAssetById(int id) {
        return assetRepo.findById(id).orElse(null);
    }

    public List<Asset> getAssetsByName(String name) {
        return assetRepo.findByNameContainingIgnoreCase(name);
    }
    public List<Asset> getAssetsByStatus(AssetStatus status) {
        return assetRepo.findByStatus(status);
    }
    public List<Asset> getAssetsByUserId(Integer userId) {
        return assetRepo.findByUserId(userId);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Transactional
    public Asset createAsset(String name, String description) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setDescription(description);
        asset.setStatus(AssetStatus.AVAILABLE);
        Asset savedAsset = assetRepo.save(asset);

        createTransactionHistory(savedAsset, null, AssetHistoryStatus.UNASSIGNED);

        return savedAsset;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public Asset updateAsset(int id, String name, String description, AssetStatus status) {
        Asset asset = assetRepo.findById(id).orElse(null);
        if (asset != null) {
            asset.setName(name);
            asset.setDescription(description);
            asset.setStatus(status);
            // set the user_id to null if the status is available
            if (status == AssetStatus.AVAILABLE || status == AssetStatus.UNDER_MAINTENANCE) {
                asset.setUser(null);
            }
            Asset updatedAsset = assetRepo.save(asset);

            createTransactionHistory(updatedAsset, null, AssetHistoryStatus.UPDATED);
            return updatedAsset;
        }
        return null;
    }


    @Transactional
    public Asset updateAssetName(int id, String name) {
        Asset asset = assetRepo.findById(id).orElse(null);
        if (asset != null) {
            asset.setName(name);
            Asset updatedAsset = assetRepo.save(asset);

            createTransactionHistory(updatedAsset, null, AssetHistoryStatus.UPDATED);
            return updatedAsset;
        }
        return null;
    }

    @Transactional
    public Asset updateAssetDescription(int id, String description) {
        Asset asset = assetRepo.findById(id).orElse(null);
        if (asset != null) {
            asset.setDescription(description);
            Asset updatedAsset = assetRepo.save(asset);

            createTransactionHistory(updatedAsset, null, AssetHistoryStatus.UPDATED);
            return updatedAsset;
        }
        return null;
    }

    @Transactional
    public Asset updateAssetStatus(int id, AssetStatus status) {
        Asset asset = assetRepo.findById(id).orElse(null);
        if (asset != null) {
            if (asset.getStatus() != status) {
                asset.setStatus(status);
            }
            Asset updatedAsset = assetRepo.save(asset);

            createTransactionHistory(updatedAsset, null, AssetHistoryStatus.UPDATED);
            return updatedAsset;
        }
        return null;
    }

    
    @Transactional
    public Asset updateAssetAssignedUser(int asset_id, Integer userId) {
        Asset asset = assetRepo.findById(asset_id).orElse(null);
        User user = null;
        if (userId != null) {
            user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with the given ID does not exist."));
        }
        if (asset != null) {
            asset.setUser(user);
            Asset updatedAsset = assetRepo.save(asset);

            createTransactionHistory(updatedAsset, user, AssetHistoryStatus.ASSIGNED);

            return updatedAsset;
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public boolean deleteAsset(int id) {
        if (assetRepo.existsById(id)) {
            Asset asset = assetRepo.findById(id).orElse(null);
            assetRepo.deleteById(id);
            
            createTransactionHistory(asset, null, AssetHistoryStatus.DELETED);

            return true;
        }
        return false;
    }    

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public Asset assignAssetToUser(int assetId, int userId) {
        Asset asset = assetRepo.findById(assetId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);

        if (asset != null && user != null && asset.getStatus() == AssetStatus.AVAILABLE) {
            asset.setStatus(AssetStatus.IN_USE);
            asset.setUser(user);

            Asset updatedAsset = assetRepo.save(asset);
            createTransactionHistory(updatedAsset, user, AssetHistoryStatus.ASSIGNED);

            return updatedAsset;}
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // public List<AssetHistory> getAssetHistoryID(int assetId) {
    //     return assetHistoryRepo.findById(assetId);
    // }

    // public List<AssetHistory> getUserAssetHistory(int userId) {
    //     return assetHistoryRepo.findByUserId(userId);
    // }

    // public List<AssetHistory> getAllHistory() {
    //     return assetHistoryRepo.findAll();
    // }

    @Transactional
    public List<AssetHistoryDTO> getAssetHistoryByAssetId(int assetId) {
        return assetHistoryRepo.findByAssetId(assetId)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<AssetHistoryDTO> getAssetHistoryByUserId(int userId) {
        return assetHistoryRepo.findByUserId(userId)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<AssetHistoryDTO> getAllAssetHistory() {
        return assetHistoryRepo.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    private AssetHistoryDTO toDto(AssetHistory e) {
        return new AssetHistoryDTO(
            e.getId(),
            e.getUserId(),
            e.getAssetId(),
            e.getLogDate(),
            e.getStatus().name()
        );
    }


    @Transactional
    public List<AssetHistoryDTO> getAssetHistoryByStatus(AssetHistoryStatus status) {
        return assetHistoryRepo.findByStatus(status)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional
    private void createTransactionHistory(Asset asset, User user, AssetHistoryStatus status) {
        AssetHistory history = new AssetHistory();
        history.setAssetId(asset.getId());
        history.setUserId(user != null ? user.getId() : null);
        history.setStatus(status);
        history.setLogDate(LocalDateTime.now());
        assetHistoryRepo.save(history);
    }
}
