package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "roomType")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomType implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "roomType")
//    @JsonBackReference
    @JsonIgnoreProperties("roomType")
    private List<Room> rooms;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;
}
