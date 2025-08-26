package com.civiclens.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, length=1000)
    private String description;

    @Column(nullable=false)
    private String status = "OPEN";

    @Column(nullable=false)
    private String location;

    @Column(name = "created_at", nullable=false,updatable=false)
    // @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Complaint() {}

    public Complaint(String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    // Add to Complaint.java
@OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
private List<ComplaintImage> images = new ArrayList<>();

public List<ComplaintImage> getImages() {
    return images;
}
public void setImages(List<ComplaintImage> images) {
    this.images = images;
}
}
