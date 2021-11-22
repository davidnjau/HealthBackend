package com.example.healthbackend.authentication.service_class.service;


import com.example.healthbackend.authentication.entity.Role;

public interface RoleService {

    boolean isRoleExists(String roleName);
    Role addRole(Role role);
    Role getRoleDetails(String roleName);
    void addRoleToUser(Long roleId, String userId);
//    List<Role> getAllRoles();

}
