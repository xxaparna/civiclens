package com.civiclens.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civiclens.backend.dto.ComplaintRequest;
import com.civiclens.backend.dto.ComplaintResponse;
import com.civiclens.backend.dto.ComplaintUpdateRequest;
import com.civiclens.backend.model.Complaint;
import com.civiclens.backend.service.ComplaintService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(@Valid @RequestBody ComplaintRequest request) {
        Complaint entity = new Complaint();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setLocation(request.getLocation());
        // status/createdAt are set in service/entity
        Complaint saved = complaintService.createComplaint(entity);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable long id) {
        Complaint c = complaintService.getComplaintById(id);
        return ResponseEntity.ok(toResponse(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintResponse> updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintUpdateRequest request) {

        // Map only provided fields; service should ignore nulls
        Complaint patch = new Complaint();
        patch.setTitle(request.getTitle());
        patch.setDescription(request.getDescription());
        patch.setLocation(request.getLocation());
        patch.setStatus(request.getStatus());

        Complaint updated = complaintService.updateComplaint(id, patch);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok("Complaint deleted successfully");
    }

    // ----- mapping helper -----
    private ComplaintResponse toResponse(Complaint c) {
        ComplaintResponse r = new ComplaintResponse();
        r.setId(c.getId());
        r.setTitle(c.getTitle());
        r.setDescription(c.getDescription());
        r.setStatus(c.getStatus());
        r.setLocation(c.getLocation());
        r.setCreatedAt(c.getCreatedAt());
        return r;
    }

    @PostMapping("/{id}/upload")
public ResponseEntity<?> uploadImages(
        @PathVariable Long id,
        @org.springframework.web.bind.annotation.RequestParam("files") java.util.List<org.springframework.web.multipart.MultipartFile> files) {

    Complaint complaint = complaintService.getComplaintById(id);

    java.util.List<com.civiclens.backend.model.ComplaintImage> uploadedImages = new java.util.ArrayList<>();
    for (org.springframework.web.multipart.MultipartFile file : files) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        java.nio.file.Path filePath = java.nio.file.Paths.get("uploads", fileName);
        try {
            java.nio.file.Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());

            // Link image with complaint
            com.civiclens.backend.model.ComplaintImage img =
                    new com.civiclens.backend.model.ComplaintImage("/uploads/" + fileName, complaint);
            uploadedImages.add(img);
        } catch (java.io.IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed for " + file.getOriginalFilename());
        }
    }

    // Save complaint with images
    complaint.getImages().addAll(uploadedImages);
    complaintService.saveComplaint(complaint);

    return ResponseEntity.ok(uploadedImages);
}

}
