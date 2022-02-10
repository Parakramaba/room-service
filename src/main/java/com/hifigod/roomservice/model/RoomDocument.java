package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "roomDocument")
@Data
public class RoomDocument implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    @JsonBackReference
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "addedUserId", referencedColumnName = "userId")
    @JsonBackReference
    private User addedUser;

//    private UserRole userRole;

    @Column(nullable = false, length = 254)
    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime addedOn;


}
