package com.modern_inf.management.model.aws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern_inf.management.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "aws")
@Data
public class AwsAccount {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;

    private LocalDateTime lastActivityTime;

    @OneToMany(mappedBy = "awsAccount")
    @JsonIgnore()
    private List<EC2instance> ec2instance;

    private LocalDateTime cacheExpirationTime;


}
