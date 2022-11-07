package com.modern_inf.management.model.asana;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asana_projects")
public class AsanaProject {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private LocalDateTime createdAt;

    private String currentStatus;

    private LocalDateTime dueDate;

    private String owner;
    private String notes;

    private String gid;

    private boolean isPublic;

    private String resourceType;

    private String name;

    @ManyToOne()
    @JsonIgnore()
    private AsanaWorkspace asanaWorkspaces;


    @OneToMany(mappedBy = "asanaProject")
    @JsonIgnore()
    private List<AsanaSection> asanaSections;
}
