package com.example.healthbackend.authentication.service_class.impl;

import com.example.healthbackend.authentication.entity.Role;
import com.example.healthbackend.authentication.entity.StaffDetails;
import com.example.healthbackend.authentication.repository.RoleRepository;
import com.example.healthbackend.authentication.repository.StaffDetailsRepository;
import com.example.healthbackend.authentication.service_class.service.RoleService;
import com.example.healthbackend.authentication.service_class.service.StaffDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import com.example.healthbackend.webapp.helperclass.*;

@Service
@Transactional
public class StaffDetailsServiceImpl implements StaffDetailsService, RoleService {

    @Autowired
    private StaffDetailsRepository staffDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean isEmailAddress(String emailAddress) {
        return staffDetailsRepository.existsByEmailAddress(emailAddress);
    }

    @Override
    public boolean isUserName(String userName) {
        return staffDetailsRepository.existsByUsername(userName);
    }

    @Override
    public boolean isRoleExists(String roleName) {
        return roleRepository.existsByName(roleName);
    }

    @Override
    public Role addRole(Role roles) {
        return roleRepository.save(roles);
    }

    public void saveRoles(Role role){
        if (!isRoleExists(role.getName())){
            addRole(role);
        }
    }

    @Override
    public List<StaffDetails> getAllUsers() {
        return staffDetailsRepository.findAll();
    }

    @Override
    public StaffDetails getStaffDetailsByEmailAddress(String emailAddress) {

        return staffDetailsRepository.findAllByEmailAddress(emailAddress);
    }


    @Override
    public Role getRoleDetails(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void addRoleToUser(Long roleId, String userId) {

        StaffDetails userDetails = staffDetailsRepository.findAllByUserId(userId);
        Role roles = getRoles(roleId);
        userDetails.getRolesCollection().add(roles);

    }

    public Role getRoles(Long id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public StaffDetails addStaffDetails(StaffDetails staffDetails) {
        return staffDetailsRepository.save(staffDetails);
    }

    public StaffDetails getStaffDetails(String userId){

        Optional<StaffDetails> optionalUserDetails = staffDetailsRepository.findById(userId);
        return optionalUserDetails.orElse(null);


    }

    public Results getStaffDetailsData(String userId){

        StaffDetails userDetails1 = getStaffDetails(userId);
        if (userDetails1 != null){
            RegisterResponse registerResponse = new RegisterResponse(
                    userId,
                    userDetails1.getFullNames(),
                    userDetails1.getEmailAddress(),
                    userDetails1.getRolesCollection().stream()
                            .map(Role::getName)
                            .collect(Collectors.toList()));

            return new Results(200, registerResponse);
        }else {
            return new Results(400, "User cannot be found.");

        }



    }

    public Results registerUser(StaffDetails staffDetails){

        String error = "";

        boolean isEmailExists = isEmailAddress(staffDetails.getEmailAddress());
        boolean isUserExists = isUserName(staffDetails.getUsername());

        if (!isEmailExists){

            if (!isUserExists){

                staffDetails.setPassword(passwordEncoder.encode(staffDetails.getPassword()));
                StaffDetails userDetails1 = addStaffDetails(staffDetails);
                if (userDetails1 != null){
                    String userId = userDetails1.getUserId();
//                    Long roleId = getRoleDetails("ROLE_USER").getId();
//                    addRoleToUser(roleId, userId);

                    Long roleId1 = getRoleDetails("ROLE_ADMIN").getId();
                    addRoleToUser(roleId1, userId);

                    RegisterResponse registerResponse = new RegisterResponse(
                            userId,
                            userDetails1.getFullNames(),
                            userDetails1.getEmailAddress(),
                            userDetails1.getRolesCollection().stream()
                                    .map(Role::getName)
                                    .collect(Collectors.toList()));

                    return new Results(201, registerResponse);

                }

            }else {
                error = error + "Username already exists.";
            }

        }else {
            error = error + "Email already exists.";

        }

        return new Results(400, error);

    }

    public StaffDetails getLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            String currentEmailAddress = authentication.getName();
            return getStaffDetailsByEmailAddress(currentEmailAddress);
        }else {
            return null;
        }

    }

    public Collection<? extends GrantedAuthority> getAuthorities() {

        StaffDetails user = getLoggedInUser();
        Set<Role> roles = (Set<Role>) user.getRolesCollection();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;

    }

    public List<StaffDetails> getPaginatedUsers(int pageNo, int pageSize, String sortField, String sortDirection){

        String sortPageField = "";
        String sortPageDirection = "";

        if (sortField.equals("")){sortPageField = "createdAt";}else {sortPageField = sortField;}
        if (sortDirection.equals("")){sortPageDirection = "DESC";}else {sortPageDirection = sortField;}

        Sort sort = sortPageDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPageField).ascending() :
                Sort.by(sortPageField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<StaffDetails> userDetailsPage = staffDetailsRepository.findAll(pageable);
        return userDetailsPage.getContent();

    }

}