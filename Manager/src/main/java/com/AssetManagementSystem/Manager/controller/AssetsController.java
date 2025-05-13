package com.AssetManagementSystem.Manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AssetManagementSystem.Manager.model.entity.Asset;
import com.AssetManagementSystem.Manager.model.entity.AssetHistory;
import com.AssetManagementSystem.Manager.model.entity.AssetStatus;
import com.AssetManagementSystem.Manager.service.AssetService;

@RestController
@RequestMapping("/api/auth")
public class AssetsController {

    @Autowired
    private AssetService 
    assetService;

    // CREATE
    // Tested with Postman
    // @RequestBody is used to bind the HTTP request body to a Java object
    // RequestBody is
    // {
    // "name":"Asset1",
    // "description":"Crying now"
    // }
    @PostMapping("/assets/create")
    public ResponseEntity<Asset> createAssets(@RequestBody Asset asset) {
        Asset createdAsset = assetService.createAsset(asset.getName(), asset.getDescription());
        if (createdAsset == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // READ
    // Tested
    @GetMapping("/assets")
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAll_Assets();
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    // Tested
    @GetMapping("/assets/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable int id) {
        Asset asset = assetService.getAssetById(id);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(asset);
    }

    // Tested
    @GetMapping("/assets/name/{name}")
    public ResponseEntity<List<Asset>> getAssetsByName(@PathVariable String name) {
        List<Asset> assets = assetService.getAssetsByName(name);
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    // Tested
    @GetMapping("/assets/status/{status}")
    public ResponseEntity<List<Asset>> getAssetsByStatus(@PathVariable AssetStatus status) {
        List<Asset> assets = assetService.getAssetsByStatus(status);
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    // Tested
    @GetMapping("/assets/user/{userId}")
    public ResponseEntity<List<Asset>> getAssetsByUserId(@PathVariable Integer userId) {
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    // Tested 
    // There is bug that user_id and asset_id doesn't appear in the json response
    @GetMapping("/asset_history/{assetId}")
    public ResponseEntity<List<AssetHistory>> getAssetHistory(@PathVariable Integer assetId) {
        List<AssetHistory> assetHistory = assetService.getAssetHistoryID(assetId);
        if (assetHistory == null || assetHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assetHistory);
    }

    // Tested
    // The same as above    
    @GetMapping("/asset_history/user/{userId}")
    public ResponseEntity<List<AssetHistory>> getUserAssetHistory(@PathVariable Integer userId) {
        List<AssetHistory> assetHistory = assetService.getUserAssetHistory(userId);
        if (assetHistory == null || assetHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assetHistory);
    }


    @GetMapping("/asset_history")
    public ResponseEntity<List<AssetHistory>> getAllAssetHistory() {
        List<AssetHistory> assetHistory = assetService.getAllHistory();
        if (assetHistory == null || assetHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assetHistory);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /
    // UPDATE
    @PutMapping("/assets/update/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable int id, @RequestBody Asset asset) {
        Asset updatedAsset = assetService.updateAsset(id, asset.getName(), asset.getDescription(), asset.getStatus());
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }

    @PatchMapping("/assets/update-status")
    public ResponseEntity<Asset> updateAssetStatus(@RequestParam int id, @RequestParam AssetStatus status) {
        Asset updatedAsset = assetService.updateAssetStatus(id, status);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }

    @PatchMapping("/assets/update-name")
    public ResponseEntity<Asset> updateAssetName(@RequestParam int id, @RequestParam String name) {
        Asset updatedAsset = assetService.updateAssetName(id, name);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }

    @PatchMapping("/assets/update-description")
    public ResponseEntity<Asset> updateAssetDescription(@RequestParam int id, @RequestParam String description) {
        Asset updatedAsset = assetService.updateAssetDescription(id, description);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }

    // Update asset assigned user
    // Tested with Postman
    @PatchMapping("/assets/update-asset-user")
    public ResponseEntity<Asset> updateAssetUser(@RequestParam("a") int id, @RequestParam("u") Integer userId) {
        Asset updatedAsset = assetService.updateAssetAssignedUser(id, userId);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // DELETE
    // Tested with Postman
    @DeleteMapping("/assets/delete/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable int id) {
        boolean isDeleted = assetService.deleteAsset(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Assign asset to user
    // Tested with Postman
    @PatchMapping("/assets/assign")
    public ResponseEntity<Asset> assignAssetToUser(@RequestParam("as_id") int assetId,
            @RequestParam("u_id") Integer userId) {
        Asset updatedAsset = assetService.assignAssetToUser(assetId, userId);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }
}
