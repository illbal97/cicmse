package com.modern_inf.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asana_tasks")
public class AsanaTask {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gid;

    private LocalDateTime createdAt;

    private String name;

    private LocalDateTime dueDate;

    private String resourceType;

    private String resourceSubType;


    @ManyToOne()
    @JsonIgnore()
    private AsanaSection asanaSection;

}
