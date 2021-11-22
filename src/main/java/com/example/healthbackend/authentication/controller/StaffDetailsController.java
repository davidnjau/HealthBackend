package com.example.healthbackend.authentication.controller;

import com.example.healthbackend.authentication.entity.StaffDetails;
import com.example.healthbackend.authentication.service_class.impl.StaffDetailsServiceImpl;
import com.example.healthbackend.webapp.helperclass.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StaffDetailsController {

    @Autowired
    private StaffDetailsServiceImpl staffDetailsService;

    @RequestMapping(value = "/api/v1/auth/registration", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody StaffDetails StaffDetails){

        Results addedResults = staffDetailsService.registerUser(StaffDetails);
        if (addedResults != null){

            var statusCode = addedResults.getStatusCode();
            var results = addedResults.getDetails();

            if (statusCode == 201){
                return new ResponseEntity(results, HttpStatus.CREATED);
            }else {
                var errorMessage = results.toString();
                return ResponseEntity.badRequest().body(new ErrorMessage(errorMessage));
            }

        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("There was an error with your request."));
        }

    }

    @RequestMapping(value = "/api/v1/users/get-users/{userId}", method = RequestMethod.GET)
    public ResponseEntity getStaffDetails(@PathVariable("userId") String userId){

        Results staffDetails = staffDetailsService.getStaffDetailsData(userId);
        if (staffDetails != null){

            int statusCode = staffDetails.getStatusCode();
            if (statusCode == 200){
                return new ResponseEntity(staffDetails.getDetails(), HttpStatus.OK);
            }else {
                return ResponseEntity.badRequest().body(new ErrorMessage((String) staffDetails.getDetails()));
            }


        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage((String) staffDetails.getDetails()));
        }

    }



}
