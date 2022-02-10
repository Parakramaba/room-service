package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "amenity")
@Data
public class Amenity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 254)
    private String name;

    @ManyToMany(mappedBy = "amenities")
    @JsonIgnore
    private List<Room> rooms;

}
