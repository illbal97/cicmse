package com.modern_inf.management.service.aws;

import com.amazonaws.services.ec2.model.Instance;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.aws.AwsAccount;
import com.modern_inf.management.model.aws.EC2instance;

import java.util.List;

public interface AwsService {
    List<Instance> getEC2Instances();

    List<EC2instance> getAllEC2Instances();

    void saveEC2Instance(EC2instance instance);

    AwsAccount getAwsAccountByUser(Long id);

    void updateAsanaTokenExpirationTime(AwsAccount a);

    void setAwsAccountForUser(User user);
}
