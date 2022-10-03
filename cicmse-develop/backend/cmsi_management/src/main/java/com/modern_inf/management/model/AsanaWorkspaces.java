package com.modern_inf.management.model;

import com.asana.Client;
import com.asana.models.Workspace;
import com.asana.resources.Workspaces;
import com.asana.resources.gen.WorkspacesBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asana_workspaces")
public class AsanaWorkspaces{
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gid;

    private String emailDomains;

    private String resourceType;

    private String name;

    private boolean isOrganization;

    @OneToMany(mappedBy = "asanaWorkspaces")
    @JsonIgnore()
    private List<AsanaProjects> asanaProjects;

    @ManyToOne()
    @JsonIgnore()
    private Asana asana;

}
