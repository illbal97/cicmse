package com.modern_inf.management.repository.aws;

import com.modern_inf.management.model.aws.AwsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwsAccountDao extends JpaRepository<AwsAccount, Long> {

    AwsAccount findByUserId(Long id);

}
