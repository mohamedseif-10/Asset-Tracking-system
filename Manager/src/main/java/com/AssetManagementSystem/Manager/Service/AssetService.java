package com.AssetManagementSystem.Manager.Service;

import com.AssetManagementSystem.Manager.Model.entity.*;
import com.AssetManagementSystem.Manager.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepo assetRepo;

    @Autowired
    private AssetHistoryRepo assetHistoryRepo;

    @Autowired
    private UserRepo userRepo;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Asset> getAllAssets() {
        return assetRepo.findAll();
    }

    public Asset getAssetById(int id) {
        return assetRepo.findById(id).orElse(null);
    }

    public List<Asset> getAssetsByName(String name) {
        return assetRepo.findByAsNameContainingIgnoreCase(name);
    }
    public List<Asset> getAssetsByStatus(AssetStatus status) {
        return assetRepo.findByAsStatus(status);
    }
    public List<Asset> getAssetsByUserId(Integer userId) {
        return assetRepo.findByAssignedToUserId(userId);
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

    public void deleteAsset(int id) {
        assetRepo.deleteById(id);
        createTransactionHistory(null, null, AssetHistoryStatus.DELETED);
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

            return updatedAsset;
        } else if (asset != null && asset.getStatus() == AssetStatus.IN_USE) {
            throw new IllegalStateException("The asset is already in use and cannot be assigned.");
        }
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public List<AssetHistory> getAssetHistory(int assetId) {
        return assetHistoryRepo.findByAssetIdOrderByStartDateDesc(assetId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createTransactionHistory(Asset asset, User user, AssetHistoryStatus status) {
        AssetHistory history = new AssetHistory();
        history.setAsset(asset);
        history.setUser(user);
        history.setStatus(status);
        history.setLogDate(LocalDateTime.now());
        assetHistoryRepo.save(history);
    }
}
