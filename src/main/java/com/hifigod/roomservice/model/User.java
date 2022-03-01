package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    @Id
    @Column(nullable = false)
    private String userId;

    //private String password;

    @OneToMany(mappedBy = "user")
//    @JsonBackReference
    @JsonIgnoreProperties("user")
    private List<Room> rooms;

//    @OneToMany(mappedBy = "addedUser")
////    @JsonManagedReference
//    @JsonIgnoreProperties("addedUser")
//    private List<RoomDocument> roomDocuments;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;
}
