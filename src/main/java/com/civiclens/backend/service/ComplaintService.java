package com.civiclens.backend.service;

import java.util.List;

import com.civiclens.backend.model.Complaint;

public interface ComplaintService{

    Complaint createComplaint(Complaint complaint);
    Complaint getComplaintById(Long id);
    List<Complaint> getAllComplaints();
    Complaint updateComplaint(Long id, Complaint updatedComplaint);
    Complaint saveComplaint(Complaint complaint);
    void deleteComplaint(Long id);
}