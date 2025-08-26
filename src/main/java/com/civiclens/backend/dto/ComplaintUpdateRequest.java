package com.civiclens.backend.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * All fields optional; only send what you want to change.
 */
public class ComplaintUpdateRequest {

    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;

    @Pattern(
        regexp = "OPEN|IN_PROGRESS|RESOLVED",
        message = "Status must be one of: OPEN, IN_PROGRESS, RESOLVED"
    )
    private String status;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 