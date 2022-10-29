package com.modern_inf.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asana_section")
public class AsanaSection {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private String gid;

    private String resourceType;

    private String name;

    @ManyToOne()
    @JsonIgnore()
    private AsanaProject asanaProject;

    @OneToMany(mappedBy = "asanaSection")
    @JsonIgnore()
    private List<AsanaTask> asanaTasks;
}
