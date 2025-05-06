package com.AssetManagementSystem.Manager.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "as_id")
    private int id;

    @Column(name = "as_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private AssetStatus status;

    @Column(name = "as_description", length = 500)
    private String description;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "asset")
    private List<AssetHistory> assetHistory;

    
    public Asset() {}

    public Asset(String name, AssetStatus status, String description, User user,
            List<AssetHistory> assetHistory) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.addDate = LocalDateTime.now();
        this.user = user;
        this.assetHistory = assetHistory;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public List<AssetHistory> getAssetHistory() {
        return assetHistory;
    }

    public void setAssetHistory(List<AssetHistory> assetHistory) {
        this.assetHistory = assetHistory;
    }
}
