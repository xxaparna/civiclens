package com.civiclens.backend.service.impl;

// TODO: Update the import below to match the actual package of Complaint, or create the Complaint class in com.civiclens.model if it does not exist.
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.civiclens.backend.model.Complaint;
import com.civiclens.backend.repository.ComplaintRepository;
import com.civiclens.backend.service.ComplaintService;

@Service

public class ComplaintServiceImpl implements ComplaintService{
    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public Complaint createComplaint(Complaint complaint){
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setStatus("OPEN");
          if (complaint.getTitle() == null || complaint.getTitle().trim().isEmpty()) {
        throw new IllegalArgumentException("Title cannot be empty");
    }
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint getComplaintById(Long id){
        return complaintRepository.findById(id).orElseThrow(()-> new RuntimeException("Complaint not found with id:"+id));
    }

    @Override
    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }

    @Override
    public Complaint updateComplaint(Long id, Complaint updatedComplaint){
        Complaint existing = getComplaintById(id);
        if (updatedComplaint.getTitle() != null && !updatedComplaint.getTitle().isEmpty()) {
        existing.setTitle(updatedComplaint.getTitle());
    }
    if (updatedComplaint.getDescription() != null && !updatedComplaint.getDescription().trim().isEmpty()) {
            existing.setDescription(updatedComplaint.getDescription());
        }

        if (updatedComplaint.getLocation() != null && !updatedComplaint.getLocation().trim().isEmpty()) {
            existing.setLocation(updatedComplaint.getLocation());
        }

        if (updatedComplaint.getStatus() != null && !updatedComplaint.getStatus().trim().isEmpty()) {
            existing.setStatus(updatedComplaint.getStatus());
        }

        return complaintRepository.save(existing);
    }

    @Override 
    public void deleteComplaint(Long id){
        complaintRepository.deleteById(id);
    }
    @Override
    public Complaint saveComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }
}