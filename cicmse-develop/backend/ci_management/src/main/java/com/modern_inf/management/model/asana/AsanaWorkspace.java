package com.modern_inf.management.model.asana;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern_inf.management.model.asana.Asana;
import com.modern_inf.management.model.asana.AsanaProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asana_workspaces")
public class AsanaWorkspace {
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
    private List<AsanaProject> asanaProjects;

    @ManyToOne()
    @JsonIgnore()
    private Asana asana;

}
