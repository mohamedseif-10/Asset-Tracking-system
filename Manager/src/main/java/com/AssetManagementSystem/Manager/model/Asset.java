package com.AssetManagementSystem.Manager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "as_id")
    private Long id;

    @Column(name = "as_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    private String status;

    private String description;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "asset")
    private List<AssetHistory> assetHistory;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<AssetHistory> getAssetHistory() {
        return assetHistory;
    }

    public void setAssetHistory(List<AssetHistory> assetHistory) {
        this.assetHistory = assetHistory;
    }
} 