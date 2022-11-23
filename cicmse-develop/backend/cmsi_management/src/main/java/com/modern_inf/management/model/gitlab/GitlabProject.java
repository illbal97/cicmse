package com.modern_inf.management.model.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "gitlab_project")
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
public class GitlabProject {

    @javax.persistence.Id
    private Long id;

    private String name;

    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    private GitlabAccount gitlab;

    @OneToMany(mappedBy = "gitlabProject")
    @JsonIgnore
    private List<GitlabBranch> gitlabBranches;



}
