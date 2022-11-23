package com.modern_inf.management.model.gitlab;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gitlab_commit")
public class GitlabCommit {
    @javax.persistence.Id
    private String id;

    private LocalDateTime createdAt;

    private String message;

    private String name;

    @ManyToOne()
    @JsonIgnore()
    private GitlabBranch gitlabBranch;
}
