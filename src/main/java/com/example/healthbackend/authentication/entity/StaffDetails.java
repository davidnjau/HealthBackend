package com.example.healthbackend.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uui_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "userId", updatable = false, nullable = false)
    private String userId;

    private String fullNames;

    @Column(unique = true)
    private String emailAddress;

    private String password;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> rolesCollection = new ArrayList<>();

    public StaffDetails(String fullNames, String emailAddress, String password) {
        this.fullNames = fullNames;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
