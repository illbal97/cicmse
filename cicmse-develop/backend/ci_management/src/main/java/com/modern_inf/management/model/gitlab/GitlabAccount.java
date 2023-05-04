package com.modern_inf.management.model.gitlab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern_inf.management.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "gitlab")
public class GitlabAccount {

    @javax.persistence.Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @OneToMany(mappedBy = "gitlab")
    @JsonIgnore()
    private List<GitlabProject> gitlabProjects;

    private LocalDateTime lastActivityTime;

    private LocalDateTime cacheExpirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;
}
