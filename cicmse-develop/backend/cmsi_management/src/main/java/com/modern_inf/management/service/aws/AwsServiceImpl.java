package com.modern_inf.management.service.aws;

import com.amazonaws.services.ec2.model.Instance;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.aws.AwsAccount;
import com.modern_inf.management.model.aws.EC2instance;
import com.modern_inf.management.model.dto.aws.AwsDto;
import com.modern_inf.management.repository.aws.AwsAccountDao;
import com.modern_inf.management.repository.aws.EC2InstanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Service
public class AwsServiceImpl implements AwsService{


    private final AwsApiService awsApiService;

    private final EC2InstanceDao ec2InstanceDao;

    private final AwsAccountDao awsAccountDao;
    private final ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());

    @Value("${app.aws.expiration-in-ms}")
    private Long AWS_EXPIRATION_TIME_IN_MS;

    @Autowired
    public AwsServiceImpl(AwsApiService awsApiService, EC2InstanceDao ec2InstanceDao, AwsAccountDao awsAccountDao) {
        this.awsApiService = awsApiService;
        this.ec2InstanceDao = ec2InstanceDao;
        this.awsAccountDao = awsAccountDao;
    }


    @Override
    public List<Instance> getEC2Instances(AwsDto awsDto) throws Exception {
        return awsApiService.getEC2Instances(awsDto);
    }

    @Override
    public List<EC2instance> getAllEC2Instances() {
        return ec2InstanceDao.findAll();
    }

    @Override
    public void saveEC2Instance(EC2instance instance) {
        this.ec2InstanceDao.save(instance);
    }

    @Override
    public AwsAccount getAwsAccountByUser(Long id) {
        return this.awsAccountDao.findByUserId(id);
    }

    @Override
    public void updateAsanaTokenExpirationTime(AwsAccount a) {
        a.setTokenLastTimeUsed(LocalDateTime.now());
        long milliseconds = convertLocalDateTimeToMilliSecond(a.getTokenLastTimeUsed());
        a.setTokenExpirationTime(convertMilliSecondToLocalDateTime(milliseconds));
        this.awsAccountDao.save(a);
    }

    @Override
    public void setAwsAccountForUser(User user) {
        AwsAccount awsAccount = new AwsAccount();
        List<AwsAccount> awsAccounts = this.awsAccountDao.findAll();
        var alreadyExistAwsAccount = awsAccounts.stream().filter(as -> Objects.equals(as.getUser().getId(), user.getId())).toList();
        if (alreadyExistAwsAccount.isEmpty()) {
            awsAccount.setUser(user);
            awsAccount.setTokenLastTimeUsed(LocalDateTime.now());
            long milliseconds = convertLocalDateTimeToMilliSecond(awsAccount.getTokenLastTimeUsed());
            awsAccount.setTokenExpirationTime(convertMilliSecondToLocalDateTime(milliseconds));
            this.awsAccountDao.save(awsAccount);
        }
    }

    @Override
    public void startEC2Instance(AwsDto awsDto) throws Exception {
        this.awsApiService.startEC2Instance(awsDto);
    }

    @Override
    public void stopEC2Instance(AwsDto awsDto) throws Exception {
        this.awsApiService.stopEC2Instance(awsDto);
    }

    @Override
    public Instance createEC2Instance(AwsDto awsDto) throws Exception {
        return this.awsApiService.createEC2Instance(awsDto);
    }

    @Override
    public void updateEC2InstanceState(List<EC2instance> ec2instances, Instance instance) {
        for(EC2instance ec2instance: ec2instances) {
            ec2instance.setState(instance.getState().getName());
            this.saveEC2Instance(ec2instance);
        }
    }

    @Override
    public void createS3(AwsDto awsDto) throws Exception {
        this.awsApiService.createS3(awsDto);
    }

    @Override
    public void RDSCreation(AwsDto awsDto) throws Exception {
        this.awsApiService.createRDS(awsDto);
    }

    @Override
    public void deleteEC2Instance(AwsDto awsDto) {
        var instance =this.ec2InstanceDao.findByInstanceId(awsDto.getInstanceId());
        this.ec2InstanceDao.deleteById(instance.getId());
    }

    private LocalDateTime convertMilliSecondToLocalDateTime(long milliSecond) {
        return Instant.ofEpochMilli(milliSecond).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private long convertLocalDateTimeToMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(zoneOffset) * 1000 + AWS_EXPIRATION_TIME_IN_MS;
    }

}
