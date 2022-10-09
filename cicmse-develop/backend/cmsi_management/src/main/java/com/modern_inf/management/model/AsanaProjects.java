package com.modern_inf.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asana_projects")
public class AsanaProjects {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private DateTime createdAt;

    private String currentStatus;

    private DateTime dueDate;

    private String gid;

    private boolean isPublic;

    private String resourceType;

    private String name;

    @ManyToOne()
    @JsonIgnore()
    private AsanaWorkspaces asanaWorkspaces;
}
