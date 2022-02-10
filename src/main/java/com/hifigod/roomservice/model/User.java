package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    @Id
    @Column(nullable = false)
    private long userId;

    //private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
//    @JsonIgnore
    private List<Room> rooms;

    @OneToMany(mappedBy = "addedUser")
    @JsonManagedReference
    private List<RoomDocument> roomDocuments;
}
