package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "roomAvailability")
@SQLDelete(sql = "UPDATE room_availability SET deleted = true WHERE id=?")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomAvailability implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomId", referencedColumnName = "id")
//    @JsonBackReference
    @JsonIgnoreProperties("roomAvailabilities")
    private Room room;

    private String day;

    private String session;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

}
