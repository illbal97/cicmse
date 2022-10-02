package com.modern_inf.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "asana")
public class Asana {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @OneToMany(mappedBy = "asana")
    @JsonIgnore()
    private List<AsanaWorkspaces> asanaWorkspaces;

    private LocalDateTime tokenLastTimeUsed;

    private LocalDateTime tokenExpirationTime;

    @OneToOne(fetch=FetchType.LAZY)
    @JsonIgnore()
    private User user;

}
