package com.AssetManagementSystem.Manager.Model.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "asset_history")
public class AssetHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "log_date")
    private LocalDateTime logDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssetHistoryStatus status;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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