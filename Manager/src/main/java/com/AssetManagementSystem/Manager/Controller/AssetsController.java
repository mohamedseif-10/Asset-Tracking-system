package com.AssetManagementSystem.Manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.AssetManagementSystem.Manager.model.entity.Asset;
import com.AssetManagementSystem.Manager.model.entity.AssetStatus;
import com.AssetManagementSystem.Manager.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// API endpoints for asset management
@RestController
@RequestMapping("/api")
public class AssetsController {

    @Autowired
    private AssetService assetService;

    // CREATE
    @PostMapping("/assets/create")
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset createdAsset = assetService.createAsset(asset.getName(), asset.getDescription());
        if (createdAsset == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
    }

    // READ
    @GetMapping("/assets")
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/assets/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable int id) {
        Asset asset = assetService.getAssetById(id);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/assets/name/{name}")
    public ResponseEntity<List<Asset>> getAssetsByName(@PathVariable String name) {
        List<Asset> assets = assetService.getAssetsByName(name);
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/assets/status/{status}")
    public ResponseEntity<List<Asset>> getAssetsByStatus(@PathVariable AssetStatus status) {
        List<Asset> assets = assetService.getAssetsByStatus(status);
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/assets/user/{userId}")
    public ResponseEntity<List<Asset>> getAssetsByUserId(@PathVariable Integer userId) {
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        if (assets == null || assets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(assets);
    }

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

    @PatchMapping("/assets/update-asset-user")
    public ResponseEntity<Asset> updateAssetUser(@RequestParam("a") int id, @RequestParam("u") Integer userId) {
        Asset updatedAsset = assetService.updateAssetAssignedUser(id, userId);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }

    // DELETE
    @DeleteMapping("/assets/delete/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable int id) {
        boolean deleted = assetService.deleteAsset(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Assign asset to user
    @PatchMapping("/assets/assign")
    public ResponseEntity<Asset> assignAssetToUser(@RequestParam int assetId, @RequestParam Integer userId) {
        Asset updatedAsset = assetService.assignAssetToUser(assetId, userId);
        if (updatedAsset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedAsset);
    }
}

    // @GetMapping("/assets-name")
    // public ResponseEntity<String> AssetNameDB(@RequestParam("n") String
    // AssetName, @RequestParam("c") int cost) {
    // return ResponseEntity.ok(assetsService.printAU(AssetName, cost));
    // }

    // @GetMapping("/assets-id")
    // public int getAssetId() {
    // return 201;
    // }

    // // Use search assets name from the list
    // @GetMapping("/get-asset")
    // public ResponseEntity<String> searchAssets(@RequestParam("n") String
    // assetName, @RequestParam("c") int cost) {
    // List<Assets> matches = assetsService.searchAssets(assetName, cost);
    // StringBuilder sb = new StringBuilder();
    // if (matches != null) {
    // for (Assets m : matches) {
    // sb.append(String.format("Asset name is : %s The cost is : %d%n", m.getName(),
    // m.getCost()));
    // }
    // }
    // return ResponseEntity.ok(sb.toString());
    // }

    // //////////////////////////////////////////////////////////////////////////////
    // @PostMapping("/save")
    // public ResponseEntity<Assets> saveAssetName(@RequestBody Assets asset) {
    // return ResponseEntity.ok(asset);
    // }
