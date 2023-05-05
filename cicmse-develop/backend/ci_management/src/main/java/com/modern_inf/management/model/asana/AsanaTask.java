package com.modern_inf.management.model.asana;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asana_task")
public class AsanaTask {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gid;

    private LocalDateTime createdAt;

    private String name;

    private LocalDateTime dueDate;

    private String resourceType;


    @ManyToOne()
    @JsonIgnore
    private AsanaSection asanaSection;

}
