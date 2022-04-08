package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "roomDocument")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomDocument implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "roomId", referencedColumnName = "id")
////    @JsonManagedReference
//    @JsonIgnoreProperties("roomDocuments")
//    private Room room;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "addedUserId", referencedColumnName = "userId")
////    @JsonBackReference
//    @JsonIgnoreProperties("roomDocuments")
//    private User addedUser;

//    private UserRole userRole;

    @Column(nullable = false, length = 254)
    private String image;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;


}
