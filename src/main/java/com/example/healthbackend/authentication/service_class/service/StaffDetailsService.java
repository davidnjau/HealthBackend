package com.example.healthbackend.authentication.service_class.service;

import com.example.healthbackend.authentication.entity.StaffDetails;

import java.util.List;

public interface StaffDetailsService {

    StaffDetails addStaffDetails(StaffDetails userDetails);
    List<StaffDetails> getAllUsers();
    StaffDetails getStaffDetailsByEmailAddress(String emailAddress);
    boolean isEmailAddress(String emailAddress);

}
