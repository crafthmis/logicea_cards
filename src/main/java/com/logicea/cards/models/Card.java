package com.logicea.cards.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@Data
@Table(name = "tbl_card")
public class Card {

    @Id
    @Column(name = "card_id ")
    protected long cardId;

    @Column(name="status_id")
    private Long statusId;

    @Column(name = "user_id ")
    protected long userId;

    @Column(name="name",nullable = false)
    protected String name;

    @Column(name="description")
    protected String description;

    @Column(name="color")
    protected String color;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    protected Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Date updatedAt;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable=false ,insertable=false, updatable=false)
    private User user;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "status_id",nullable=false ,insertable=false, updatable=false)
    private Status status;

}
