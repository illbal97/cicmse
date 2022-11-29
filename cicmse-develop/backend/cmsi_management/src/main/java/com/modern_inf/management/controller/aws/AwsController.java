package com.modern_inf.management.controller.aws;

import com.amazonaws.services.ec2.model.Instance;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.aws.EC2instance;
import com.modern_inf.management.model.dto.aws.AwsDto;
import com.modern_inf.management.service.aws.AwsService;
import com.modern_inf.management.service.userService.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/aws")
public class AwsController {

    private final AwsService awsService;

    private final UserService userService;
    private final SymmetricEncryption symmetricEncryption;

    private List<String> errors = new ArrayList<>();

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AwsController(AwsService awsService, UserService userService, SymmetricEncryption symmetricEncryption) {
        this.awsService = awsService;
        this.userService = userService;
        this.symmetricEncryption = symmetricEncryption;
    }

    @PostMapping("ec2-instance")
    public ResponseEntity<?> getEc2Instances(@RequestBody AwsDto awsDto) {
        List<Instance> instances;
        errors.clear();
        var awsAccount = this.awsService.getAwsAccountByUser(awsDto.getUser().getId());
        if(awsAccount == null) {
            errors.add("There are not any AWS account");
            LOGGER.error(errors.get(0));
            return ResponseEntity.ok(errors);
        }
        List<EC2instance> ec2instancesFromDb = awsService.getAllEC2Instances();
        try{
            if(!ec2instancesFromDb.isEmpty() && LocalDateTime.now().isBefore(awsAccount.getTokenExpirationTime())) {
                return ResponseEntity.ok(ec2instancesFromDb);
            }else {
                instances = awsService.getEC2Instances();
                for(Instance instance: instances) {
                    if(ec2instancesFromDb.stream().noneMatch(ec2 -> !Objects.equals(ec2.getInstanceId(), instance.getInstanceId()))) {
                        this.awsService.saveEC2Instance(EC2InstanceBuilder(instance));
                    }
                }
                this.awsService.updateAsanaTokenExpirationTime(awsAccount);
                return ResponseEntity.ok(instances);
            }

        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            errors.add("Bad AWS access keys");
            return ResponseEntity.ok(errors);
        }

    }

    @PostMapping("add-access-token")
    public ResponseEntity<?> setUserPersonalAccessToken(@RequestBody User u) {
        var user = this.userService.findByUsername(u.getUsername());
        try {
            user.get().setAwsAccessKey(this.symmetricEncryption.encrypt(u.getAwsAccessKey()));
            user.get().setAwsAccessSecretKey(this.symmetricEncryption.encrypt(u.getAwsAccessSecretKey()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        var us = this.userService.setAccessKeyAndSecretAccessKeyForAws(user.get());
        this.awsService.setAwsAccountForUser(user.get());
        try {
            us.setAwsAccessKey(this.symmetricEncryption.decrypt(us.getAwsAccessKey()));
            us.setAwsAccessSecretKey(this.symmetricEncryption.decrypt(us.getAwsAccessSecretKey()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        us.setAccessToken(u.getAccessToken());
        us.setRefreshToken(u.getRefreshToken());
        return ResponseEntity.ok(us);
    }

    public EC2instance EC2InstanceBuilder(Instance instance) {
        return EC2instance.builder()
                .instanceId(instance.getInstanceId())
                .tagName(instance.getTags().get(0).getValue())
                .instanceType(instance.getInstanceType())
                .publicIpAddress(instance.getPublicIpAddress())
                .securityGroupId(instance.getSecurityGroups().get(0).getGroupId())
                .securityGroupName(instance.getSecurityGroups().get(0).getGroupName())
                .build();
    }
}
