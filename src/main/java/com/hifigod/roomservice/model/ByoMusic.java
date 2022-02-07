package com.hifigod.roomservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "byoMusic")
@Data
public class ByoMusic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 254)
    private String name;

    @Column(length = 254)
    private String description;

}
