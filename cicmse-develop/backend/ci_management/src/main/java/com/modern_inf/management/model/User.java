package com.modern_inf.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern_inf.management.model.asana.Asana;
import com.modern_inf.management.model.aws.AwsAccount;
import com.modern_inf.management.model.gitlab.GitlabAccount;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore()
    private Asana asanaAccount;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore()
    private AwsAccount awsAccount;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore()
    private GitlabAccount gitlabAccount;

    @Column(columnDefinition = "varchar(255)")
    private String asanaPersonalAccessToken;

    @Column(columnDefinition = "varchar(255)")
    private String gitlabPersonalAccessToken;

    @Column(name="aws_access_key", columnDefinition = "varchar(255)")
    private String awsAccessKey;

    @Column(name="aws_access_secret_key", columnDefinition = "varchar(255)")
    private String awsAccessSecretKey;


}
