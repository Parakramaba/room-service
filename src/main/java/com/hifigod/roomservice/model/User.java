package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Id
    @Column(nullable = false)
    private long userId;

    //private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
//    @JsonIgnore
    private List<Room> rooms;

    @OneToMany(mappedBy = "addedUser")
    private List<RoomDocument> roomDocuments;
}
