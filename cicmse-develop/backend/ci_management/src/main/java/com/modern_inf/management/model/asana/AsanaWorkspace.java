package com.modern_inf.management.model.asana;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asana_workspace")
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

    @OneToMany(mappedBy = "asanaWorkspace")
    @JsonIgnore()
    private List<AsanaProject> asanaProjects;

    @ManyToOne()
    @JsonIgnore()
    private Asana asana;

}
