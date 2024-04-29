package com.logicea.cards.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_status")
public class Status {

    @Id
    @Column(name = "status_id ")
    protected long statusId;

    @Column(name = "name", nullable = false)
    protected String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable=false ,insertable=false, updatable=false)
    private Card card;
}