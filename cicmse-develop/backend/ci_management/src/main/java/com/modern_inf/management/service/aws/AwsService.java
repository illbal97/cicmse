package com.modern_inf.management.service.aws;

import com.amazonaws.services.ec2.model.Instance;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.aws.AwsAccount;
import com.modern_inf.management.model.aws.EC2instance;
import com.modern_inf.management.model.dto.aws.AwsDto;

import java.util.List;

public interface AwsService {
    List<Instance> getEC2Instances(AwsDto awsDto) throws Exception;

    List<EC2instance> getAllEC2Instances();

    void saveEC2Instance(EC2instance instance);

    AwsAccount getAwsAccountByUser(Long id);

    void updateAwsUserTokenExpirationTime(AwsAccount a);

    void setAwsAccountForUser(User user);

    void startEC2Instance(AwsDto awsDto) throws Exception;

    void stopEC2Instance(AwsDto awsDto) throws Exception;

    Instance createEC2Instance(AwsDto awsDto) throws Exception;

    void updateEC2InstanceState(List<EC2instance> ec2instances, Instance instance);

    void createS3(AwsDto awsDto) throws Exception;

    void RDSCreation(AwsDto awsDto) throws Exception;

    void deleteEC2Instance(AwsDto awsDto);
}
