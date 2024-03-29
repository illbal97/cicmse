package com.modern_inf.management.model.asana;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern_inf.management.model.User;
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


    @OneToMany(mappedBy = "asana", cascade = CascadeType.ALL)
    private List<AsanaWorkspace> asanaWorkspaces;

    private LocalDateTime lastActivityTime;

    private LocalDateTime cacheExpirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

}
