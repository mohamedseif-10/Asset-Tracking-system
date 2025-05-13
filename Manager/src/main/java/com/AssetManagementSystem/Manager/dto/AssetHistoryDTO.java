package com.AssetManagementSystem.Manager.dto;

import java.time.LocalDateTime;

public class AssetHistoryDTO {
    private Integer id;
    private Integer userId;
    private Integer assetId;
    private LocalDateTime logDate;
    private String status;

    public AssetHistoryDTO(Integer id, Integer userId, Integer assetId, LocalDateTime logDate, String status) {
        this.id = id;
        this.userId = userId;
        this.assetId = assetId;
        this.logDate = logDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}