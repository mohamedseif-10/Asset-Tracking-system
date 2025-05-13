package com.AssetManagementSystem.Manager.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "asset_history")
public class AssetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // No FK relation—just a raw user ID
    @Column(name = "user_id")
    private Integer userId;

    // No FK relation—just a raw asset ID
    @Column(name = "asset_id", nullable = false)
    private Integer assetId;

    @Column(name = "log_date")
    private LocalDateTime logDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssetHistoryStatus status;

    // -- Constructors (optional) --

    public AssetHistory() {
    }

    public AssetHistory(Integer userId, Integer assetId, LocalDateTime logDate, AssetHistoryStatus status) {
        this.userId = userId;
        this.assetId = assetId;
        this.logDate = logDate;
        this.status = status;
    }

    // -- Getters & Setters --

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public AssetHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(AssetHistoryStatus status) {
        this.status = status;
    }
}
