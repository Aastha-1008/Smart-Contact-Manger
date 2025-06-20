package com.scm.entities;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String profileImage;

    @Column(length = 1000)
    private String description;
    private boolean favorite = false;

    private String WebsiteLink;
    private String LinkedInLink;

    private String cloudinaryImagePublicId;
    // private List<SocialLink> socialLink = new ArrayList<>();


    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "contact" , cascade = CascadeType.ALL , fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink>links = new ArrayList<>();
}
