package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "setupInformation")
@Data
public class SetupInformation implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

    @OneToOne(optional = false)
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    @JsonIgnoreProperties("setupInformation")
    private Room room;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;
}
