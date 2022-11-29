package com.modern_inf.management.model.aws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "aws_ec2_instance")
public class EC2instance {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String instanceId;

    private String tagName;

    private String instanceType;

    private String publicIpAddress;

    private String securityGroupName;

    private String securityGroupId;
}
