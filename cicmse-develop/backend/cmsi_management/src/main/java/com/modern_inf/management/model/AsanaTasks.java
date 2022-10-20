package com.modern_inf.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asana_tasks")
public class AsanaTasks {

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
    private AsanaProjects asanaProjects;

}
