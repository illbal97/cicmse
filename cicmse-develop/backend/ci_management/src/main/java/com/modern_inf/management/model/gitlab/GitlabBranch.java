package com.modern_inf.management.model.gitlab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "gitlab_branch")
public class GitlabBranch {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @OneToMany(mappedBy = "gitlabBranch")
    @JsonIgnore()
    private List<GitlabCommit> gitlabCommits;

    @ManyToOne()
    @JsonIgnore()
    private GitlabProject gitlabProject;

}
