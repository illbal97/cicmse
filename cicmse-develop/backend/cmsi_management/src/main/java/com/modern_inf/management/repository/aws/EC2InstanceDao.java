package com.modern_inf.management.repository.aws;

import com.modern_inf.management.model.aws.EC2instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EC2InstanceDao extends JpaRepository<EC2instance, Long> {
    EC2instance findByInstanceId(String instanceId);
}
