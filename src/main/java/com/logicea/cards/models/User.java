package com.logicea.cards.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_user")
public class User{
    @Id
    @Column(name = "user_id ")
    protected long userId;

    @Column(nullable = false)
    protected String fullName;

    @Column(name="email",unique = true, length = 100, nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    protected Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userRole", cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> userCards;
}